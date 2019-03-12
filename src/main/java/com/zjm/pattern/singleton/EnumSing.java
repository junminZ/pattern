package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/11
 */
public enum EnumSing {

        INSTANCE;
        private Object data;

        public Object getData() {
            return data;
        }
        public void setData(Object data) {
            this.data = data;
        }
        public static EnumSing getInstance(){
            return INSTANCE;
        }
}
