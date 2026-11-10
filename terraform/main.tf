terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-2" # You can change this to your preferred region
}

# 1. Create the ECR Repository for your Spring Boot Container
resource "aws_ecr_repository" "springboot_app" {
  name                 = "springboot_backend"
  image_tag_mutability = "MUTABLE"
}

resource "aws_security_group" "rds_sg" {
  name   = "rds-security-group"
  vpc_id = data.aws_vpc.default.id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.apprunner_sg.id] # Permite acceso desde App Runner
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Luego asigna rds_sg a tu aws_db_instance:
# vpc_security_group_ids = [aws_security_group.rds_sg.id]

# 2. Create the MySQL Database (RDS Instance)
# Note: For strict learning & lowest cost, we use a db.t4g.micro instance
resource "aws_db_instance" "mysql_db" {
  allocated_storage    = 20
  engine               = "mysql"
  engine_version       = "8.4"
  instance_class       = "db.t4g.micro" # Fits in AWS Free Tier (first 12 months)
  db_name              = "cuci"
  username             = "root"
  password             = "rootroot" # Change this!
  parameter_group_name = "default.mysql8.4"
  skip_final_snapshot  = true
  publicly_accessible  = true # Set to true for easier local debugging during practice
  allow_major_version_upgrade = true
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
}


# A. Fetch your default VPC and subnets to establish a connection path
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

# B. Create the security group required for App Runner outbound traffic
resource "aws_security_group" "apprunner_sg" {
  name        = "apprunner-egress-sg"
  description = "Allow App Runner to send traffic out"
  vpc_id      = data.aws_vpc.default.id

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    # ipv6_cidr_blocks = ["::/0"]
  }
}



# C. Provision the AWS App Runner VPC Connector
resource "aws_apprunner_vpc_connector" "connector" {
  vpc_connector_name = "apprunner-to-rds"
  subnets            = data.aws_subnets.default.ids
  security_groups    = [aws_security_group.apprunner_sg.id]
}

# 3. Create AWS App Runner to run the Container
resource "aws_apprunner_service" "backend_service" {
  service_name = "springboot-service"

  # --- INSERT THIS NETWORK CONFIGURATION BLOCK ---
  network_configuration {
    egress_configuration {
      egress_type       = "VPC"
      vpc_connector_arn = aws_apprunner_vpc_connector.connector.arn
    }
  }


  source_configuration {
    # AQUÍ SE SOLUCIONA EL ERROR DE AUTENTICACIÓN
    authentication_configuration {
      # Copia el ARN del rol de IAM que modificaste manualmente en la consola
      access_role_arn = "arn:aws:iam::152088127399:role/AppRunnerECRAccessRole"
    }
    image_repository {
      image_identifier      = "${aws_ecr_repository.springboot_app.repository_url}:latest"
      image_repository_type = "ECR"

      image_configuration {
        port = "8083"
        runtime_environment_variables = {
          "SPRING_DATASOURCE_URL"      = "jdbc:mysql://${aws_db_instance.mysql_db.address}:${aws_db_instance.mysql_db.port}/cuci"
          "SPRING_DATASOURCE_USERNAME" = "root"
          "SPRING_DATASOURCE_PASSWORD" = "rootroot"
        }
      }
    }
    auto_deployments_enabled = true # This handles the automation!
  }

  # --- AGREGA ESTE BLOQUE DE HEALTH CHECK ABAJO DE SOURCE_CONFIGURATION ---
  health_check_configuration {
    protocol            = "HTTP"
    path                = "/" # O el path principal de tu API que devuelva un 200 OK
    interval            = 10
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 5
  }
}
