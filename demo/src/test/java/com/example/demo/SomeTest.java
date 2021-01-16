package com.example.demo;

import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-03 4:29 PM
 */
public class SomeTest {

    public void hi() throws Exception {
        x();
    }

    public void x() throws Exception {

    }

    @Test
    public void floatVSInt() throws Exception {
        float a = 1.11f;
        System.out.println(Integer.valueOf("" + a));
    }
}
