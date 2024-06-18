INSERT INTO attribute(attribute) VALUES
('активное вещество'),
('пол'),
('эффект'),
('подходит');

INSERT INTO value(value) VALUES
('симетикон'),
('тонизирующий'),
('детям'),
('взрослым'),
('для мужчин'),
('для женщин'),
('расслабляющий');

INSERT INTO attribute_value(product_id, attribute_id, value_id) VALUES
(1,1,1),
(1,2,5),
(1,3,3),
(2,3,7),
(2,4,4),
(2,2,6),
(5,3,2),
(5,4,4),
(5,2,5);