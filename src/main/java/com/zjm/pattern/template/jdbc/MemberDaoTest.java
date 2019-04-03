package com.zjm.pattern.template.jdbc;


import com.zjm.pattern.template.jdbc.dao.MemberDao;

import java.util.List;

/**
 * Created by Tom.
 */
public class MemberDaoTest {

    public static void main(String[] args) {
        MemberDao memberDao = new MemberDao(null);
        List<?> result = memberDao.selectAll();
        System.out.println(result);
    }
}
