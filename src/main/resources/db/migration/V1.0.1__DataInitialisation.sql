INSERT INTO USERS (birth_date, email, name, password, phone, role, is_active)
VALUES ('1990-05-15', 'john.doe@email.com', 'John Doe', 'pass123', '555-123-4567', 'ROLE_EMPLOYEE', true),
       ('1985-09-20', 'jane.smith@email.com', 'Jane Smith', 'abc456', '555-987-6543', 'ROLE_EMPLOYEE', true),
       ('1978-03-08', 'bob.jones@email.com', 'Bob Jones', 'qwerty789', '555-321-6789', 'ROLE_EMPLOYEE', true),
       ('1982-11-25', 'alice.white@email.com', 'Alice White', 'secret567', '555-876-5432', 'ROLE_EMPLOYEE', true),
       ('1995-07-12', 'mike.wilson@email.com', 'Mike Wilson', 'mypassword', '555-234-5678', 'ROLE_EMPLOYEE', true),
       ('1989-01-30', 'sara.brown@email.com', 'Sara Brown', 'letmein123', '555-876-5433', 'ROLE_EMPLOYEE', true),
       ('1975-06-18', 'tom.jenkins@email.com', 'Tom Jenkins', 'pass4321', '555-345-6789', 'ROLE_EMPLOYEE', true),
       ('1987-12-04', 'lisa.taylor@email.com', 'Lisa Taylor', 'securepwd', '555-789-0123', 'ROLE_EMPLOYEE', true),
       ('1992-08-22', 'david.wright@email.com', 'David Wright', 'access123', '555-456-7890', 'ROLE_EMPLOYEE', true),
       ('1980-04-10', 'emily.harris@email.com', 'Emily Harris', '1234abcd', '555-098-7654', 'ROLE_EMPLOYEE', true);

INSERT INTO USERS (balance, email, name, password, role , is_active)
VALUES (1000.00, 'client1@example.com', 'Medelyn Wright', 'password123', 'ROLE_CLIENT', true),
       (1500.50, 'client2@example.com', 'Landon Phillips', 'securepass', 'ROLE_CLIENT', true),
       (800.75, 'client3@example.com', 'Harmony Mason', 'abc123', 'ROLE_CLIENT', true),
       (1200.25, 'client4@example.com', 'Archer Harper', 'pass456', 'ROLE_CLIENT', true),
       (900.80, 'client5@example.com', 'Kira Jacobs', 'letmein789', 'ROLE_CLIENT', true),
       (1100.60, 'client6@example.com', 'Maximus Kelly', 'adminpass', 'ROLE_CLIENT', true),
       (1300.45, 'client7@example.com', 'Sierra Mitchell', 'mypassword', 'ROLE_CLIENT', true),
       (950.30, 'client8@example.com', 'Quinton Saunders', 'test123', 'ROLE_CLIENT', true),
       (1050.90, 'client9@example.com', 'Amina Clarke', 'qwerty123', 'ROLE_CLIENT', true),
       (880.20, 'client10@example.com', 'Bryson Chavez', 'pass789', 'ROLE_CLIENT', true);

INSERT INTO BOOKS (name, genre, age_group, price, publication_year, author, number_of_pages, characteristics,description, language, quantity)
VALUES ('The Hidden Treasure', 'Adventure', 'ADULT', 24.99, '2018-05-15', 'Emily White', 400, 'Mysterious journey','An enthralling adventure of discovery', 'ENGLISH', 50),
       ('Echoes of Eternity', 'Fantasy', 'TEEN', 16.50, '2011-01-15', 'Daniel Black', 350, 'Magical realms', 'A spellbinding tale of magic and destiny', 'ENGLISH', 50),
       ('Whispers in the Shadows', 'Mystery', 'ADULT', 29.95, '2018-08-11', 'Sophia Green', 450, 'Intriguing suspense','A gripping mystery that keeps you guessing', 'ENGLISH', 50),
       ('The Starlight Sonata', 'Romance', 'ADULT', 21.75, '2011-05-15', 'Michael Rose', 320, 'Heartwarming love story','A beautiful journey of love and passion', 'ENGLISH', 50),
       ('Beyond the Horizon', 'Science Fiction', 'CHILD', 18.99, '2004-05-15', 'Alex Carter', 280,'Interstellar adventure', 'An epic sci-fi adventure beyond the stars', 'ENGLISH', 50),
       ('Dancing with Shadows', 'Thriller', 'ADULT', 26.50, '2015-05-15', 'Olivia Smith', 380, 'Suspenseful twists','A thrilling tale of danger and intrigue', 'ENGLISH', 50),
       ('Voices in the Wind', 'Historical Fiction', 'ADULT', 32.00, '2017-05-15', 'William Turner', 500,'Rich historical setting', 'A compelling journey through time', 'ENGLISH', 50),
       ('Serenade of Souls', 'Fantasy', 'TEEN', 15.99, '2013-05-15', 'Isabella Reed', 330, 'Enchanting realms','A magical fantasy filled with wonder', 'ENGLISH', 50),
       ('Silent Whispers', 'Mystery', 'ADULT', 27.50, '2021-05-15', 'Benjamin Hall', 420, 'Intricate detective work','A mystery that keeps you on the edge', 'ENGLISH',50),
       ('Whirlwind Romance', 'Romance', 'OTHER', 23.25, '2022-05-15', 'Emma Turner', 360, 'Passionate love affair','A romance that sweeps you off your feet', 'ENGLISH', 50);
