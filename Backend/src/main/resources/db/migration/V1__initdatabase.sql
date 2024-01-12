CREATE TABLE category (
      categoryID SERIAL PRIMARY KEY NOT NULL,
      categoryLabel VARCHAR(255) NOT NULL,
      userID VARCHAR(255) NOT NULL
  );
CREATE TABLE identifier (
    identifierID SERIAL PRIMARY KEY NOT NULL,
    identifierLabel VARCHAR(255) NOT NULL,
    categoryID VARCHAR(255),
    FOREIGN KEY (categoryID) REFERENCES category(categoryID) ON DELETE CASCADE
);
CREATE TABLE transaction (
    transactionID SERIAL PRIMARY KEY NOT NULL,
    date DATE NOT NULL,
    amount FLOAT(2) NOT NULL,
    userID VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    identifierID VARCHAR(100),
    FOREIGN KEY (identifierID) REFERENCES identifier(identifierID)
);