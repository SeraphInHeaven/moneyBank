package com.example.demo;

import org.junit.Test;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-23 1:45 PM
 */
public class CloneTest {

    @Test
    public void testCycle() throws CloneNotSupportedException {
        A a = new A();
        B b = new B();
        a.b = b;
        b.a = a;

        A clonedA = a.clone();

    }

}
