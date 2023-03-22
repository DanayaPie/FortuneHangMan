DROP TABLE If EXISTS game;

ROLLBACK;

COMMIT;

CREATE TABLE game (
	game_id SERIAL PRIMARY KEY,
	round_id INTEGER NOT NULL,
	word_id INTEGER NOT NULL,
	game_status VARCHAR(300) NOT NULL,
	letter_guessed VARCHAR(26),
	current_team_turn INTEGER NOT NULL,
	current_round INTEGER NOT NULL,
	CONSTRAINT round_fk FOREIGN KEY (round_id) REFERENCES round(round_id),
	CONSTRAINT word_fk FOREIGN KEY (word_id) REFERENCES word(word_id)
);


-- ===== INSERT
INSERT INTO game (
	round_id, word_id, game_status, current_team_turn, current_round 
) VALUES 
	(1, 3, 'started', 1, 1)

	
-- ===== QUERYING
SELECT *
FROM "fhm".game 
WHERE game_id = "";


SELECT *
FROM game;