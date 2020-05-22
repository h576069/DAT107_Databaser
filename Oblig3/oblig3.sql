-- SQL script for Oblig3 i DAT107

-- Sletter og oppretter nytt schema
DROP SCHEMA IF EXISTS oblig3 CASCADE;
CREATE SCHEMA oblig3;
SET search_path TO oblig3;


-- Sletter og oppretter databasen Ansatt
DROP TABLE IF EXISTS Ansatt CASCADE;

CREATE TABLE Ansatt
(
	ansattid SERIAL,
	brukernavn VARCHAR(4) UNIQUE NOT NULL,
	fornavn VARCHAR(20) NOT NULL,
	etternavn VARCHAR(20) NOT NULL,
	ansattdato DATE NOT NULL,
	stilling VARCHAR(20) NOT NULL,
	maanedslonn NUMERIC(8,2) NOT NULL,
	avdeling INTEGER NOT NULL,
	--prosjekt INTEGER NOT NULL,
	CONSTRAINT AnsattPK PRIMARY KEY (ansattid)
);

-- Legger til noe eksempeldata i Ansatt
INSERT INTO Ansatt(brukernavn, fornavn, etternavn, ansattdato, stilling, maanedslonn, avdeling)
VALUES 
('dsc', 'Dwight', 'Schrute', '1995-04-15', 'salesperson', 25000, 1),
('msc', 'Micheal', 'Scott', '1992-05-29', 'manager', 45394.72, 1),
('jha', 'Jim', 'Halpert', '1998-07-11', 'salesperson', 25000, 1),
('pbe', 'Pam', 'Beesly', '1997-04-14', 'receptionist', 20000, 1),
('ama', 'Angela', 'Martin', '1996-03-21', 'accountant', 30000, 2),
('oma', 'Oscar', 'Martinez', '1997-02-19', 'accountant', 30000, 2),
('kma', 'Kevin', 'Malone', '1998-11-30', 'accountant', 30000, 2),
('tfl', 'Toby', 'Flenderson', '1996-01-01', 'hr rep', 32000, 3),
('shu', 'Stanley', 'Hudson', '1996-02-01', 'salesperson', 25000, 1),
('pla', 'Phyllis', 'Lapin', '1993-03-14', 'salesperson', 25000, 1);

-- Sletter og oppretter databasen Avdeling
DROP TABLE IF EXISTS Avdeling CASCADE;

CREATE TABLE Avdeling 
(
	avdelingid SERIAL NOT NULL,
	navn VARCHAR(30) NOT NULL,
	sjefsansatt INTEGER NOT NULL,
	CONSTRAINT AvdelingPK PRIMARY KEY (avdelingid),
	CONSTRAINT AnsattFK FOREIGN KEY (sjefsansatt) REFERENCES ansatt(ansattid)
);

-- Legger inn verdier i Avdeling
INSERT INTO Avdeling(navn, sjefsansatt)
VALUES ('Sales', 2), ('Accounting', 5), ('HR', 7);


-- Lager tabell for Prosjekt
DROP TABLE IF EXISTS Prosjekt CASCADE;

CREATE TABLE Prosjekt
(
	prosjektid SERIAL,
	navn VARCHAR(40),
	beskrivelse VARCHAR(200),
	CONSTRAINT Projekst_PK PRIMARY KEY (prosjektid)
);

-- Setter inn i prosjekt
INSERT INTO Prosjekt(navn, beskrivelse)
VALUES ('pranks', 'Mess with a coworker, preferrably Dwight. Jello time.'),
('prince paper', 'Get the client list for prince paper and steal them, shark time.'),
('become manager', 'A quest to become the manager and not just stay as assistant (to the) regional manager.' ),
('accounting', 'Check the books, pay the dollar.'),
('the worst', 'Be the worst, fake not being the scranton strangler'),
('vance rfd', 'Talk about vance refigeration, say popcarn.');

-- Koblingstabell
DROP TABLE IF EXISTS Prosjektdeltagelse CASCADE;

CREATE TABLE Prosjektdeltagelse
(
	pdid SERIAL,
	ansattid INTEGER NOT NULL,
	prosjektid INTEGER NOT NULL,
	rolle VARCHAR(20) NOT NULL,
	timer INTEGER NOT NULL,
	CONSTRAINT Prosjektdeltagelse_PK PRIMARY KEY (pdid), --(ansattid, prosjektid),
	CONSTRAINT Ansatt_FK FOREIGN KEY (ansattid) REFERENCES Ansatt(ansattid),
	CONSTRAINT Prosjekt_FK FOREIGN KEY (prosjektid) REFERENCES Prosjekt(prosjektid)
);

INSERT INTO Prosjektdeltagelse (ansattid, prosjektid, rolle, timer)
VALUES (1, 3, 'leader', 20), (2, 2, 'leader', 40), (3, 1, 'leader', 30), 
(4, 1, 'helper', 20), (5, 4, 'leader', 40), (6, 4, 'helper', 40), (7, 4, 'mistake maker', 40),
(8, 5, 'leader', 100), (9, 2, 'helper', 20), (10, 6, 'leader', 60);

-- Oppdaterer fremmednøkkel for Ansatt
ALTER TABLE Ansatt ADD CONSTRAINT AvdelingFK FOREIGN KEY (avdeling) REFERENCES Avdeling(avdelingid);