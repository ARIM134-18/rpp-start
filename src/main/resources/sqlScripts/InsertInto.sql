--PREDUZECE

INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'Continental', 123612, 'Novi Sad');
INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'NIS', 563912, 'Novi Sad');
INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'Delta', 983412, 'Beograd');
INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'Bosch', 553912, 'Simanovci');
INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'Henkel Srbija', 773412, 'Beograd');
INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(nextval('preduzece_seq'), 'Cooper', 333412, 'Sremska Mitrovica');

INSERT INTO "preduzece"("id", "naziv", "pib", "sediste")
VALUES(-100, 'testn', 11111, 'tests');

--OBRAZOVANJE


INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Elektricar', '4');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Mehanicar', '4');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Elektro inzenjer', '7');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Masinski inzenjer', '7');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Vozac', '4');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Tehnolog', '6');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Higijenicar', '3');
INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(nextval('obrazovanje_seq'), 'Hemicar', '5');

INSERT INTO "obrazovanje"("id", "naziv", "stepen_strucne_spreme")
VALUES(-100, 'testn', 'test1');

--SEKTOR

INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Automoto', 'AM', 1);
INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Energija', 'EN', 2);
INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Trgovina', 'TR', 3);
INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Automoto', 'AM', 4);
INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Hemija', 'HE', 5);
INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(nextval('sektor_seq'), 'Prevoz', 'PR', 6);

INSERT INTO "sektor"("id", "naziv", "oznaka", "preduzece")
VALUES(-100, 'testn', 'testo', 1);

--RADNIK

INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Aleksandar', 'Ristic', 1025689, 1, 1);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Tamara', 'Jasic', 9925689, 2, 4);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Bela', 'Praslin', 1825689, 3, 2);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Nina', 'Radina', 1125689, 4, 3);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Marko', 'Jovanovic', 3325689, 5, 3);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Mario', 'Jovanovic', 4425689, 6, 5);
INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(nextval('radnik_seq'), 'Ljiljana', 'Kosjer', 7725689, 7, 6);

INSERT INTO "radnik"("id", "ime", "prezime", "broj_lk", "obrazovanje", "sektor")
VALUES(-100, 'Ljiljana', 'Kosjer', 7725689, 1, 1);
