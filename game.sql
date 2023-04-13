DROP TABLE If EXISTS game CASCADE;

ROLLBACK;

COMMIT;

CREATE TABLE game (
	game_id SERIAL PRIMARY KEY,
	game_name VARCHAR(100) NOT NULL,
	round_id INTEGER,
	word_id INTEGER,
	game_status VARCHAR(300) NOT NULL,
	letter_guessed VARCHAR(26),
	current_team_turn INTEGER,
	current_round INTEGER,
	total_team INTEGER NOT NULL,
--	CONSTRAINT roundid_fk FOREIGN KEY (round_id) REFERENCES round(round_id),
	CONSTRAINT wordid_fk FOREIGN KEY (word_id) REFERENCES word(word_id)
);


-- ===== INSERT
INSERT INTO game (
	game_name, round_id, word_id, game_status, current_team_turn, current_round, total_team
) VALUES 
	('for fun', 0, 3, 'started', 1, 1, 4);

	
-- ===== QUERYING
SELECT *
FROM "fhm".game 
WHERE game_id = "";


SELECT *
FROM game;