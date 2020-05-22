-- Skript for å opprette databasen

-- DROP SCHEMA hvis den eksisterer allerede
DROP SCHEMA IF EXISTS oving8 CASCADE;

-- Oppretter schema
CREATE SCHEMA oving8;
SET search_path TO oving8;

DROP TABLE IF EXISTS ansatt;

CREATE TABLE ansatt (
	brukernavn VARCHAR(4),
	fornavn VARCHAR(20),
	etternavn VARCHAR(20),
	ansettelsesdato DATE,
	stilling VARCHAR(50),
	maanedslonn NUMERIC(8,2),
	CONSTRAINT ansatt_pk PRIMARY KEY (brukernavn)
);

-- Legger inn noen rader
INSERT INTO 
	ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES 
	('AFD', 'Anne', 'Drottning', '2019-04-11', 'Medarbeider', 9876.54),
	('IDB', 'Ida', 'Borve', '2018-12-13', 'Sjef', 12109.87),
	('STU', 'Stine', 'Uthaug', '2017-05-27', 'Salgssjef', 10987.65);
