-- noinspection SqlResolveForFile

INSERT INTO Student (id, full_name) VALUES
('-1', '');

INSERT INTO TOKEN (token_id, content, validity) VALUES
('-1', 'password','1998-01-18 00:00:00');


INSERT INTO RESERVATION (begin_date, class_id, end_date, active, student_full_name, title, student_id)
VALUES
         ('2021-10-01 09:00:00', '108', '2021-10-01 12:00:00', '1', 'Wykładowca 1', 'Bazy danych', '-1'),
         ('2021-10-01 16:00:00', '108', '2021-10-01 18:00:00', '1', 'Wykładowca 2', 'Metody programowania', '-1'),
         ('2021-10-01 13:00:00', '107', '2021-10-01 15:30:00', '1', 'Wykładowca 2', 'Metody programowania', '-1'),
         ('2021-10-01 13:00:00', '4', '2021-10-01 15:00:00', '1', 'Wykładowca 3', 'Podstawy grafiki komputerowej', '-1'),


('2021-11-05 09:00:00', '108', '2021-11-05 12:00:00', '1', 'Wykładowca 1', 'Bazy danych', '-1'),
         ('2021-11-05 14:00:00', '108', '2021-11-05 16:00:00', '1', 'Wykładowca 2', 'Metody programowania', '-1'),
         ('2021-11-05 12:00:00', '107', '2021-11-05 14:30:00', '1', 'Wykładowca 2', 'Metody programowania', '-1'),
         ('2021-11-05 13:00:00', '4', '2021-11-05 15:00:00', '1', 'Wykładowca 3', 'Podstawy grafiki komputerowej', '-1');




