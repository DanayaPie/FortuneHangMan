-- word
INSERT INTO word (
	category, word)
VALUES
	('FRUIT', 'MANGO'),
	('ANIMAL', 'CAT');

-- team
INSERT INTO team
	(team_name, team_turn, game_id, total_score)
VALUES
	('Shrek', 1, 1, 0),
	('Donkey', 2, 1, 0);

-- game
INSERT INTO game (
	game_name, round_id, word_id, game_status, letter_guessed, current_team_turn, current_round, total_team
) VALUES
	('test', 1, null, 'STARTED', null, 1, 1, 2);

-- round
DROP TABLE IF EXISTS round_seq_dao;

CREATE SEQUENCE round_seq_dao
	AS int
	START WITH 1
	INCREMENT BY 1
;

INSERT INTO round (
	round_id, team_id, game_id, round_score, spin_score, spin_token)
VALUES
	(nextval('round_seq_dao'), 1, 1, 0, 0, false);