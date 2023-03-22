DROP TABLE If EXISTS word;

DELETE FROM word;

ROLLBACK;
COMMIT;

CREATE TABLE word (
	word_id SERIAL PRIMARY KEY,
	word_category VARCHAR(300) NOT NULL,
	word VARCHAR(500) NOT NULL
);

-- ===== INSERT
INSERT INTO word (
	word_category, word)
VALUES
	('Anime Name', 'I Want To Eat Your Pancreas Is Far From A Horror Series'),
	('Fruit', 'Mangosteen'),
	('Fruit', 'Rambutan'),
	('Anime Name', 'Attack on Titan')


-- ===== QUERYING
SELECT *
FROM "fhm".word
WHERE word = "";


SELECT *
FROM word;