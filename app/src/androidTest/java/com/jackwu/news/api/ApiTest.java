package com.jackwu.news.api;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by JackWu on 2016/12/25.
 */
public class ApiTest {
    @Test
    public void getJson() throws Exception {
        String s = Api.getJson("top");
        String ss="";
        assertEquals(s, ss);

    }

}