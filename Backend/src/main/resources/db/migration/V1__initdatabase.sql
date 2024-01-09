CREATE TABLE category (
      categoryid SERIAL PRIMARY KEY NOT NULL,
      label VARCHAR(255) NOT NULL,
      userID VARCHAR(255) NOT NULL
  );
CREATE TABLE identifier (
    identifierid SERIAL PRIMARY KEY NOT NULL,
    label VARCHAR(255) NOT NULL,
    categoryID VARCHAR(255),
    FOREIGN KEY (categoryid) REFERENCES category(categoryid) ON DELETE CASCADE
);
CREATE TABLE transaktion (
    transaktionid SERIAL PRIMARY KEY NOT NULL,
    date DATE NOT NULL,
    amount FLOAT(2) NOT NULL,
    userID VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    identifierid VARCHAR(100),
    FOREIGN KEY (identifierid) REFERENCES Identifier(identifierid)
);