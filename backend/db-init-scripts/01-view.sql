-- db-init-scripts/01-view.sql
CREATE OR REPLACE VIEW v_booking_summary AS
SELECT
    b.id AS booking_id,
    b.booking_confirmation_code,
    b.check_in_date,
    b.check_out_date,
    u.name AS guest_name,
    r.room_type,
    r.room_price
FROM
    bookings b
JOIN
    users u ON b.user_id = u.id
JOIN
    rooms r ON b.room_id = r.id;