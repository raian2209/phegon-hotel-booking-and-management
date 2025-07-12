-- db-init-scripts/02-trigger.sql

-- 1. Criar a tabela de auditoria
CREATE TABLE IF NOT EXISTS booking_audit (
    audit_id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT,
    confirmation_code VARCHAR(255),
    action_type VARCHAR(50),
    action_timestamp TIMESTAMPTZ
);

-- 2. Criar a FUNÇÃO que o trigger irá chamar
CREATE OR REPLACE FUNCTION log_booking_insert()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO booking_audit(booking_id, confirmation_code, action_type, action_timestamp)
    VALUES (NEW.id, NEW.booking_confirmation_code, 'INSERT', NOW());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 3. Criar o TRIGGER que executa a função após um INSERT na tabela de bookings
-- Dropamos primeiro para garantir que não haja erro se o script rodar mais de uma vez em testes
DROP TRIGGER IF EXISTS after_booking_insert_trigger ON bookings;
CREATE TRIGGER after_booking_insert_trigger
AFTER INSERT ON bookings
FOR EACH ROW
EXECUTE FUNCTION log_booking_insert();