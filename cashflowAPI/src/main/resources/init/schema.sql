CREATE TABLE IF NOT EXISTS category (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    label VARCHAR(255) not null,
    type varchar(255) not null check (type in ('FIXED','VARIABLE','UNIQUE','INCOME')),
    userID VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS identifier (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    category_id BIGINT REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    date DATE NOT NULL,
    amount NUMERIC(9,2) NOT NULL,
    userID VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    identifier_id BIGINT REFERENCES identifier(id) ON DELETE SET NULL
)