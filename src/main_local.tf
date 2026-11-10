variable "entorno_local" {
  type        = bool
  default     = true
  description = "Cambiar a false cuando vayas a desplegar en AWS real"
}

provider "aws" {
  region     = "us-east-1"
  access_key = var.entorno_local ? "mock" : null
  secret_key = var.entorno_local ? "mock" : null

  # Si la variable es verdadera, sobreescribe los endpoints hacia LocalStack
  dynamic "endpoints" {
    for_each = var.entorno_local ? [1] : []
    content {
      s3       = "http://localhost:4566"
      dynamodb = "http://localhost:4566"
      lambda   = "http://localhost:4566"
    }
  }
}

# Recurso de prueba: Un bucket S3
resource "aws_s3_bucket" "bucket_prueba" {
  bucket = "mi-bucket-de-prueba-local"
}
