INSERT INTO SCREEN (screen_id) VALUES (1);
INSERT INTO SCREEN (screen_id) VALUES (2);
INSERT INTO SCREEN (screen_id) VALUES (3);
INSERT INTO SCREEN (screen_id) VALUES (4);
INSERT INTO SCREEN (screen_id) VALUES (5);
INSERT INTO SCREEN (screen_id) VALUES (6);
INSERT INTO SCREEN (screen_id) VALUES (7);
INSERT INTO SCREEN (screen_id) VALUES (8);
INSERT INTO SCREEN (screen_id) VALUES (9);
INSERT INTO SCREEN (screen_id) VALUES (10);

INSERT INTO MOVIE (movie_name) VALUES ('태극기 휘날리며');
INSERT INTO MOVIE (movie_name) VALUES ('실미도');
INSERT INTO MOVIE (movie_name) VALUES ('친구');
INSERT INTO MOVIE (movie_name) VALUES ('기생충');
INSERT INTO MOVIE (movie_name) VALUES ('미나리');

INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,1,'11:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,2,'13:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,3,'15:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,4,'17:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,1,'19:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (1,2,'22:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,4,'11:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,2,'13:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,1,'15:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,3,'17:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,4,'19:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (2,5,'22:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,2,'11:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,1,'13:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,3,'15:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,1,'17:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,2,'19:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (3,2,'22:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,5,'11:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,2,'13:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,1,'15:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,1,'17:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,1,'19:30:00');
INSERT INTO SCHEDULE (screen_id, movie_id,start_time) VALUES (4,2,'22:30:00');

INSERT INTO TICKET (seat_number, user_id, schedule_id) VALUES (1,'foo',1);

