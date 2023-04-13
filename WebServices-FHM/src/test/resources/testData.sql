-- word
CREATE TABLE word (
	word_id SERIAL PRIMARY KEY,
	category VARCHAR(300) NOT NULL,
	word VARCHAR(500) NOT NULL
);
INSERT INTO word (
	category, word)
VALUES
	('ANIME NAME', 'I WANT TO EAT YOUR PANCREASE IS FAR FROM A HORROR SERIES'),
	('FRUIT', 'MANGOSTEEN'),
	('FRUIT', 'RAMBUTAN'),
	('ANIME NAME', 'ATTACK ON TITAN');

-- team
CREATE TABLE team (
	team_id SERIAL PRIMARY KEY,
	team_name VARCHAR(255) NOT NULL,
	team_turn INTEGER NOT NULL,
	game_id INTEGER,
	total_score INTEGER NOT NULL
);
INSERT INTO team
	(team_name, team_turn, game_id, total_score)
VALUES
	('Dragon', 1, 1, 0),
	('Monkey', 2, 1, 0);

-- game
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
	CONSTRAINT wordid_fk FOREIGN KEY (word_id) REFERENCES word(word_id)
);
INSERT INTO game (
	game_name, round_id, word_id, game_status, current_team_turn, current_round, total_team
) VALUES
	('for fun', 0, 3, 'started', 1, 1, 4);

-- round
CREATE SEQUENCE round_seq
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



