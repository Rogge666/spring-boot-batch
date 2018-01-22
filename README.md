**业务描述**

step1  
从多个csv文件中读出所有数据,并添加到all.csv中并修改状态为1(使用csv2csv   module4)

step2  
把all.csv的数据存到mysql数据库中并修改状态为2,表名为stock_data(使用csv2db   module3)，前提条件为数据库中没有该条数据(判断重复依据为代码加日期是否相等)

step3  
把stock_data中所有股票市盈率低于20的筛选出来存入stock_expect_data并修改状态为3(使用db2db   module1)，前提条件为数据库中没有该条数据(判断重复依据为代码加日期是否相等)

step3  
把stock_expect_data的数据存入expect.csv(使用db2csv   module2)并修改状态为4，前提条件为expect.csv中没有该条数据(判断重复依据为代码加日期是否相等)

注：此工程需要配置Lombok Plugin

