package com.example.demo;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-23 1:51 PM
 */
public class A implements Serializable, Cloneable {
    B b;

    @Override
    public A clone() throws CloneNotSupportedException {
        A a = (A) super.clone();
        a.b = b.clone();

        return a;
    }
}
