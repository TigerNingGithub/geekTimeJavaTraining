C:\Users\Administrator.DESKTOP-3OD2TVJ>mysql -h 127.0.0.1 -P 13306 -uroot -proot    //主要大小写P，p。
mysql> show databases;
mysql> use sharding_db;
mysql> insert t_order values(1,1);//插入数据，记得查看sharding_db 输出的sql
mysql> insert t_order values(2,2);
mysql> insert t_order values(3,3);
mysql> insert t_order values(4,4);
mysql> insert t_order values(5,5);
mysql> insert t_order values(6,6);
mysql> insert t_order values(7,7);
mysql> insert t_order values(17,7);
mysql> insert t_order values(14,14);
mysql> insert t_order values(15,15);
mysql> select * from t_order ;//查询
mysql> delete from t_order where order_id=1;//删除
mysql> select * from t_order where order_id=1 ;
mysql> update t_order set order_id=8 where order_id=2;//更新失败，可能是因为没有id字段？
ERROR 10002 (C1000): 2Unknown exception: [Can not update sharding key, logic table: [t_order], column: [order_id].]