DROP TABLE If EXISTS team;

DELETE FROM team;

ROLLBACK;
COMMIT;

CREATE TABLE team (
	team_id SERIAL PRIMARY KEY,
	team_name VARCHAR(255) NOT NULL,
	team_turn INTEGER NOT NULL,
	game_id INTEGER,
	total_score INTEGER NOT NULL
);


-- ===== INSERT
INSERT INTO team
	(team_name, team_turn, game_id, total_score)
VALUES
	('Dragon', 1, 1, 0),
	('Monkey', 2, 1, 0)


-- ===== QUERYING
SELECT *
FROM "fhm".team 
WHERE team_name;


SELECT *
FROM team;

where team_id = 1;