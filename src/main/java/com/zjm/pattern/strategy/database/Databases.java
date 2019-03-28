package com.zjm.pattern.strategy.database;

/**
 * Create by zjm on 2019/3/28
 */
public class Databases {
    private DatabasesStrategy databasesStrategy;
    public Databases(DatabasesStrategy databasesStrategy) {
        this.databasesStrategy = databasesStrategy;
    }

    public void execute(){
        databasesStrategy.save();
    }
}
