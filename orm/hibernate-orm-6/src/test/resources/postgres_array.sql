CREATE TABLE currency (
  num  NUMERIC(3) NOT NULL,
  CONSTRAINT pk_currency PRIMARY KEY(num)
);

INSERT INTO currency(num)
     VALUES (392),
            (756),
            (826),
            (840),
            (978);
