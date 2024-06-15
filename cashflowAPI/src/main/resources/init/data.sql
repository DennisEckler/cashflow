INSERT INTO category (label, type, userID) VALUES
('Gehalt', 'INCOME', 'gojo'),
('Lebensmittel', 'VARIABLE', 'gojo'),
('Versicherung', 'FIXED', 'gojo'),
('Sonderzahlung', 'UNIQUE', 'gojo');

INSERT INTO identifier (label ,category_id) VALUES
('bertelsmann', 1),
('WEZ', 2),
('LVM', 3),
('xxx', 4);

INSERT INTO transaction (date, amount, userID, purpose, source, identifier_id) VALUES
('2023-03-29', 123.32, 'gojo', 'zweck', 'quelle', 1),
('2023-02-27', 13.32, 'gojo', 'zweck', 'quelle', 2),
('2023-01-29', 23.32, 'gojo', 'zweck', 'quelle', 3),
('2023-04-29', 12.32, 'gojo', 'zweck', 'quelle', 4);
