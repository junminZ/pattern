package com.zjm.pattern.adapter.loginadapter.v2.adapters;


import com.zjm.pattern.adapter.loginadapter.ResultMsg;

/**
 * Created by Tom.
 */
public class LoginForTelAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
