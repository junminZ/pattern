package com.zjm.pattern.prototype;

import java.io.Serializable;

/**
 * Create by zjm on 2019/3/15
 */
public class Gun implements Serializable {
    public int bullet = 20;

    void shoot() {
        this.bullet -= 1;
    }
}
