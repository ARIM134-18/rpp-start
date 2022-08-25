DROP TABLE IF EXISTS radnik CASCADE;
DROP TABLE IF EXISTS sektor CASCADE;
DROP TABLE IF EXISTS obrazovanje CASCADE;
DROP TABLE IF EXISTS preduzece CASCADE;

DROP SEQUENCE IF EXISTS radnik_seq;
DROP SEQUENCE IF EXISTS sektor_seq;
DROP SEQUENCE IF EXISTS obrazovanje_seq;
DROP SEQUENCE IF EXISTS preduzece_seq;

CREATE TABLE preduzece(
	id integer NOT NULL,
    naziv varchar(100) NOT NULL,
	pib integer NOT NULL,
    sediste varchar(100) NOT NULL,
	opis varchar(500)
);

CREATE TABLE obrazovanje(
	id integer NOT NULL,
    naziv VARCHAR(100) NOT NULL,
    stepen_strucne_spreme VARCHAR(10) NOT NULL,
    opis VARCHAR(500)
);

CREATE TABLE sektor(
	id integer NOT NULL,
	naziv VARCHAR(100) NOT NULL,
	oznaka VARCHAR(10) NOT NULL,
	preduzece integer NOT NULL
);

CREATE TABLE radnik(
	id integer NOT NULL,
    ime VARCHAR(100) NOT NULL,
    prezime VARCHAR(100) NOT NULL,
    broj_lk integer NOT NULL,
    obrazovanje integer NOT NULL,
	sektor integer NOT NULL
);

ALTER TABLE preduzece ADD CONSTRAINT PK_Preduzece
	PRIMARY KEY(id);
ALTER TABLE obrazovanje ADD CONSTRAINT PK_Obrazovanje
	PRIMARY KEY(id);
ALTER TABLE sektor ADD CONSTRAINT PK_Sektor
	PRIMARY KEY(id);
ALTER TABLE radnik ADD CONSTRAINT PK_Radnik
	PRIMARY KEY(id);

ALTER TABLE sektor ADD CONSTRAINT FK_Sektor_Preduzece
	FOREIGN KEY (preduzece) REFERENCES preduzece (id);
ALTER TABLE radnik ADD CONSTRAINT FK_Radnik_Obrazovanje
	FOREIGN KEY (obrazovanje) REFERENCES obrazovanje (id);
ALTER TABLE radnik ADD CONSTRAINT FK_Radnik_Sektor
	FOREIGN KEY (sektor) REFERENCES sektor (id);

CREATE INDEX IDXFK_FK_Sektor_Preduzece
	ON sektor (preduzece);
CREATE INDEX IDXFK_Radnik_Obrazovanje
	ON radnik (obrazovanje);
CREATE INDEX IDXFK_Radnik_Sektor
	ON radnik (sektor);
	
CREATE SEQUENCE preduzece_seq
INCREMENT 1
START 1;
CREATE SEQUENCE obrazovanje_seq
INCREMENT 1
START 1;
CREATE SEQUENCE sektor_seq
INCREMENT 1
START 1;
CREATE SEQUENCE radnik_seq
INCREMENT 1
START 1;

