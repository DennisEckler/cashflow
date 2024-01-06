INSERT INTO category (label, userID)
VALUES ( 'test', 'test');

INSERT INTO identifier (label, categoryID)
VALUES ( 'test', 1);

INSERT INTO transaktion (date, amount, userID, purpose, source, identifierID)
VALUES ( '2023-08-15', 10, 'test', 'test', 'test', 1);