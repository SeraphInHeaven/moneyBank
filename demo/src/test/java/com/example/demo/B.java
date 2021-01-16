package com.example.demo;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-23 1:52 PM
 */
public class B implements Serializable, Cloneable {
    A a;

    @Override
    public B clone() throws CloneNotSupportedException {
        B b = (B) super.clone();
        b.a = a.clone();

        return b;
    }

}
