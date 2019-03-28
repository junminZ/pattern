package com.zjm.pattern.strategy.database;

/**
 * Create by zjm on 2019/3/28
 */
public class DatabasesTest {
    public static void main(String[] args) {
        Databases databases = new Databases(new MysqlStrategy());
        databases.execute();
    }
}
