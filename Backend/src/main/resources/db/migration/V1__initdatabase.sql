CREATE TABLE category (
      id SERIAL PRIMARY KEY NOT NULL,
      label VARCHAR(255) NOT NULL,
      userID VARCHAR(255) NOT NULL
  );
CREATE TABLE identifier (
    id SERIAL PRIMARY KEY NOT NULL,
    label VARCHAR(255) NOT NULL,
    categoryID VARCHAR(255),
    FOREIGN KEY (categoryID) REFERENCES category(id) ON DELETE CASCADE
);
CREATE TABLE transaktion (
    id SERIAL PRIMARY KEY NOT NULL,
    date DATE NOT NULL,
    amount FLOAT(2) NOT NULL,
    userID VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    identifierID VARCHAR(100),
    FOREIGN KEY (identifierID) REFERENCES Identifier(id)
);