INSERT INTO "order"(user_id, time, address_id, type_id, status_id, payment_type_id) VALUES
(1,now(),1,2,1,1),
(2,now(),2,2,1,1),
(3,now(),3,2,1,1),
(4,now(),4,2,1,2),
(5,now(),1,1,1,1),
(2,now(),1,2,1,2),
(4,now(),1,2,1,1),
(3,now(),1,2,1,2),
(1,now(),1,2,1,1);
INSERT INTO "product_list"(product_id, order_id, count) VALUES
(1,1,1000),
(2,1,100),
(3,1,45),
(4,1,10),
(1,1,65);
INSERT INTO "recipe"(product_list_id, link, order_id) VALUES
(1,'01Д1234567890',1),
(2,'abcdefghijk',3),
(3,'ОД1234567890',4),
(1,'ОД1234567890',1);

