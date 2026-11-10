-- Migración V3: Cambiar columnas financieras de FLOAT a DECIMAL para evitar pérdida de precisión en montos grandes
ALTER TABLE capture
    MODIFY COLUMN requestAmount DECIMAL(15,2),
    MODIFY COLUMN isrRetention DECIMAL(15,2),
    MODIFY COLUMN totalAmountPaid DECIMAL(15,2);
