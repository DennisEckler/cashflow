CREATE TABLE IF NOT EXISTS category (
  id bigserial not null PRIMARY KEY,
  label VARCHAR(255) not null,
  type varchar(255) not null check (type in ('FIXED','VARIABLE','UNIQUE','INCOME')),
  userID VARCHAR(255)
);

