-- =================================================================
-- SCRIPT DE INSERÇÃO DE DADOS FALSOS (MOCK DATA)
-- Ordem de execução: 1. users, 2. rooms, 3. bookings, 4. login_logs
-- =================================================================


-- 2. Inserir Quartos (Rooms)
INSERT INTO rooms (room_type, room_price, room_photo_url, room_description) VALUES
('Quarto Solteiro', 150.00, 'https://example.com/photos/single.jpg', 'Um quarto aconchegante para uma pessoa, com Wi-Fi e TV a cabo.'),
('Quarto Casal', 250.00, 'https://example.com/photos/double.jpg', 'Quarto espaçoso com cama queen-size, ideal para casais.'),
('Suíte Luxo', 450.00, 'https://example.com/photos/suite.jpg', 'Nossa melhor suíte, com banheira de hidromassagem, varanda e vista para a cidade.'),
('Quarto Família', 350.00, 'https://example.com/photos/family.jpg', 'Quarto com uma cama de casal e duas de solteiro, perfeito para famílias.');
