INSERT INTO football.teams(name, country) VALUES ('Shakhtar', 'Ukraine');
INSERT INTO football.teams(name, country) VALUES ('Dynamo', 'Ukraine');
INSERT INTO football.teams(name, country) VALUES ('Real Madrid', 'Spain');
INSERT INTO football.teams(name, country) VALUES ('PSG', 'France');

INSERT INTO football.players(team_id, name, price) VALUES (2, 'Bushan', 500000);
INSERT INTO football.players(team_id, name, price) VALUES (2, 'Shaparenko', 1000000);
INSERT INTO football.players(team_id, name, price) VALUES (2, 'Besedin', 500000);

INSERT INTO football.players(team_id, name, price) VALUES (1, 'Mudryk', 3000000);
INSERT INTO football.players(team_id, name, price) VALUES (1, 'Trubin', 2000000);

INSERT INTO football.players(team_id, name, price) VALUES (3, 'Lunin', 9000000);
INSERT INTO football.players(team_id, name, price) VALUES (3, 'Benzema', 4000000);
INSERT INTO football.players(team_id, name, price) VALUES (3, 'Cortois', 5000000);

INSERT INTO football.players(team_id, name, price) VALUES (4, 'Messi', 1000000);
INSERT INTO football.players(team_id, name, price) VALUES (4, 'Mbappe', 2000000);

# INSERT INTO football.players VALUES (0, 4, 'Mbappe', 2000000);

# SET FOREIGN_KEY_CHECKS = 0;
# TRUNCATE TABLE football.teams;
# SET FOREIGN_KEY_CHECKS = 1;