package com.example.demo.test;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-15 5:39 PM
 */
public abstract class Processor <T extends Event> {

    public abstract void process(T event);

}
