DROP TABLE If EXISTS round;
--DROP TABLE IF EXISTS roundid;

DELETE FROM round;

ROLLBACK;
COMMIT;

--CREATE TABLE roundid (
--	round_id SERIAL PRIMARY KEY,
--	word_category VARCHAR(300) NOT NULL,
--	word VARCHAR(300) NOT NULL
--);

CREATE TABLE round (
	round_id SERIAL PRIMARY KEY,
	team_id INTEGER NOT NULL,
	round_score INTEGER NOT NULL,
	spin_score INTEGER NOT NULL,
	spin_token BOOLEAN NOT NULL,
	CONSTRAINT teamid_fk FOREIGN KEY (team_id) REFERENCES team(team_id)
);

INSERT INTO round (
	team_id, round_score, spin_score, spin_token 
) VALUES 
	(1, 1, 0, false)


-- ===== INSERT
INSERT INTO round (
	
)


-- ===== QUERYING
SELECT *
FROM "fhm".round 
WHERE word = "";

SELECT *
FROM round;
