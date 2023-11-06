CREATE TABLE transaktion (
    id SERIAL PRIMARY KEY,
    valuta DATE NOT NULL,
    client_or_recipient VARCHAR(255) NOT NULL,
    booking_text VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    value FLOAT(2) NOT NULL,
    category VARCHAR(50)
);
