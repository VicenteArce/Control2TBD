INSERT INTO users (name, email, password)
VALUES 
('Vicente Arce', 'vicente.arce.p@usach.cl', '123'),
('example1', 'example@gmail.com', '123'),
('example2', 'example2@gmail.com', '123');


INSERT INTO task (user_id, title, description, expiration_date, state)
VALUES
((SELECT user_id FROM users WHERE name = 'Vicente Arce'), 'Hacer tarea 1', 'Hacer la portada de la tarea 1', '2024-11-20', 'Pending');