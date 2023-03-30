DROP TABLE If EXISTS round;

DELETE FROM round;

ROLLBACK;
COMMIT;

--CREATE TABLE roundid (
--	round_id SERIAL PRIMARY KEY,
--	word_category VARCHAR(300) NOT NULL,
--	word VARCHAR(300) NOT NULL
--);

CREATE SEQUENCE fhm.round_seq
	AS int
	START WITH 1
	INCREMENT BY 1
;

CREATE TABLE round (
	round_id INTEGER NOT NULL,
	team_id INTEGER NOT NULL,
	game_id Integer NOT NULL,
	round_score INTEGER NOT NULL,
	spin_score INTEGER NOT NULL,
	spin_token BOOLEAN NOT NULL,
	PRIMARY KEY(round_id,team_id,game_id),
	CONSTRAINT teamid_fk FOREIGN KEY (team_id) REFERENCES team(team_id),
	CONSTRAINT gameid_fk FOREIGN KEY (game_id) REFERENCES game(game_id)
);

INSERT INTO round (
	round_id, team_id, game_id, round_score, spin_score, spin_token) 
VALUES 
	(nextval('round_seq'), 1, 1, 0, 0, false);


INSERT INTO round (
	round_id, team_id, game_id, round_score, spin_score, spin_token) 
VALUES 
	((select LAST_VALUE from round_seq), 1, 1, 0, 1, false);


-- ===== INSERT
INSERT INTO round (
	
)


-- ===== QUERYING
SELECT *
FROM "fhm".round 
WHERE word = "";

SELECT *
FROM round;
