package com.zjm.pattern.singleton;

import java.io.Serializable;

/**
 * Create by zjm on 2019/3/11
 */
public class SeriableSing implements Serializable {

        public final static SeriableSing INSTANCE = new SeriableSing();

        private SeriableSing(){}

        public static SeriableSing getInstance(){
            return INSTANCE;
        }
        private Object readResolve(){
            return INSTANCE;
        }
}
