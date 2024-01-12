INSERT INTO category (categoryLabel, userID)
VALUES ( 'test', 'test');

INSERT INTO identifier (identifierLabel, categoryID)
VALUES ( 'test', 1);

INSERT INTO transaction (date, amount, userID, purpose, source, identifierID)
VALUES ( '2023-08-15', 10, 'test', 'test', 'test', 1);