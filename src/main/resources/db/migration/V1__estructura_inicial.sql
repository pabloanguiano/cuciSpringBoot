-- 1. Catálogos base y roles
CREATE TABLE roles_user_catalog (
                                           idCatalogUser TINYINT AUTO_INCREMENT PRIMARY KEY,
                                           category VARCHAR(255),
                                           type VARCHAR(255)
);

CREATE TABLE catalogs_model (
                                idCatalog SMALLINT AUTO_INCREMENT PRIMARY KEY,
                                description VARCHAR(255)
);

-- 2. Tablas principales con minúsculas
CREATE TABLE users (
                       id TINYINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       roles TINYINT NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       deleteApplication BOOLEAN,
                       CONSTRAINT fk_user_role FOREIGN KEY (roles) REFERENCES roles_user_catalog(idCatalogUser)
);

CREATE TABLE catalog_capture (
                                 idCatalogCapture INT AUTO_INCREMENT PRIMARY KEY,
                                 catalog SMALLINT NOT NULL,
                                 description VARCHAR(255),
                                 CONSTRAINT fk_catalog_parent FOREIGN KEY (catalog) REFERENCES catalogs_model(idCatalog)
);

-- 3. Sub-catálogos
CREATE TABLE sub_catalog_capture_model (
                                           id SMALLINT AUTO_INCREMENT PRIMARY KEY,
                                           identifier VARCHAR(255),
                                           description VARCHAR(255),
                                           catalogCaptureId INT,
                                           CONSTRAINT fk_subcatalog_capture FOREIGN KEY (catalogCaptureId) REFERENCES catalog_capture(idCatalogCapture)
);

-- 4. Tabla principal de capturas (en minúsculas: capture)
CREATE TABLE capture (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         receiptDate VARCHAR(255) NOT NULL,
                         userCreateApplication TINYINT,
                         userLastUpdateApplication TINYINT,
                         applicationNumber INT NOT NULL,
                         idTypeApplication SMALLINT,
                         idStatusApplication SMALLINT,
                         paymentType VARCHAR(255),
                         paymentComplement BOOLEAN,
                         patrimonialRegistration BOOLEAN,
                         travelFormat BOOLEAN,
                         applicationConcept VARCHAR(255),
                         beneficiaryName VARCHAR(255),
                         paymentMethod VARCHAR(255),
                         projectP3e VARCHAR(255),
                         idFundApplication SMALLINT,
                         idStockNumber SMALLINT,
                         idNumberUre SMALLINT,
                         numberDepcon INT,
                         depcon VARCHAR(255),
                         requestAmount FLOAT,
                         isrRetention FLOAT,
                         totalAmountPaid FLOAT,
                         numberPackage INT,
                         namePackage VARCHAR(255),
                         deleteApplication TINYINT,
                         userDeleted TINYINT,

                         CONSTRAINT fk_capture_user_create FOREIGN KEY (userCreateApplication) REFERENCES users(id),
                         CONSTRAINT fk_capture_user_update FOREIGN KEY (userLastUpdateApplication) REFERENCES users(id),
                         CONSTRAINT fk_capture_user_deleted FOREIGN KEY (userDeleted) REFERENCES users(id),

                         CONSTRAINT fk_capture_type FOREIGN KEY (idTypeApplication) REFERENCES sub_catalog_capture_model(id),
                         CONSTRAINT fk_capture_status FOREIGN KEY (idStatusApplication) REFERENCES sub_catalog_capture_model(id),
                         CONSTRAINT fk_capture_fund FOREIGN KEY (idFundApplication) REFERENCES sub_catalog_capture_model(id),
                         CONSTRAINT fk_capture_ure FOREIGN KEY (idNumberUre) REFERENCES sub_catalog_capture_model(id),
                         CONSTRAINT fk_capture_stock FOREIGN KEY (idStockNumber) REFERENCES sub_catalog_capture_model(id)
);