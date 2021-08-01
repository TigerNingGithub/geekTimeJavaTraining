-- ###批量插入数据
-- # 插入数据到用户表中
DROP PROCEDURE IF EXISTS users_initData;
DELIMITER $  
CREATE PROCEDURE users_initData()
BEGIN

    DECLARE n INT DEFAULT 1; 
    WHILE n<=100 DO       
        insert into shops.users (name, password, nick_name, identify_card)
        VALUES (CONCAT("user", n), "password", CONCAT("nick_name", n), "identify_card");
        SET n = n+1;
    END WHILE;
    commit;
END $
CALL users_initData();

select * from shops.users;

-- # 插入数据到供应商表中

DROP PROCEDURE IF EXISTS supplier_initData;
DELIMITER $  
CREATE PROCEDURE supplier_initData()
BEGIN

    DECLARE n INT DEFAULT 1; 
    WHILE n<=100 DO      
        insert into shops.supplier (name, registration_number, contactPerson)
        VALUES (CONCAT("supplierName", n), "registration_number", CONCAT("contactPerson", n));
        SET n = n+1;
    END WHILE;
    commit;
END $
CALL supplier_initData();

select * from shops.supplier;

-- ###商品
=name,classify,weight,supplier_id

DROP PROCEDURE IF EXISTS goods_initData;
DELIMITER $  
CREATE PROCEDURE goods_initData()
BEGIN

    DECLARE n INT DEFAULT 1; 
    WHILE n<=100 DO      
        insert into shops.goods (name, classify, weight,supplier_id)
        VALUES (CONCAT("goodName", n), "classify", n,n);
        SET n = n+1;
    END WHILE;
    commit;
END $
CALL goods_initData();

select * from  goods;

-- ###订单
-- =user_id,money,state,goods_id
DROP PROCEDURE IF EXISTS orders_initData;
DELIMITER $  
CREATE PROCEDURE orders_initData()
BEGIN

    DECLARE n INT DEFAULT 1; 
    WHILE n<=100 DO       
        insert into shops.orders (user_id, money, state,goods_id)
        VALUES (n, n+1, n+2,n);
        SET n = n+1;
    END WHILE;
    commit;
END $
CALL orders_initData();

select * from  orders;