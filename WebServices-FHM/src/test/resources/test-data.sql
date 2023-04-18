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
	game_name, round_id, word_id, game_status, current_team_turn, current_round, total_team
) VALUES
	('test', 0, 1, 'started', 1, 1, 4);

-- round
INSERT INTO round (
	round_id, team_id, game_id, round_score, spin_score, spin_token)
VALUES
	(1, 1, 1, 0, 0, false);