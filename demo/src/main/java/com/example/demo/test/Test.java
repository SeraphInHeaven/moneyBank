package com.example.demo.test;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-15 5:41 PM
 */
public class Test {

    public static void main(String[] args) {
        Processor<AEvent> processor = new AProcessor();

        //Exception in thread "main" java.lang.ClassCastException: com.example.demo.test.BEvent cannot be cast to com.example.demo.test.AEvent
        //Processor processor = new AProcessor();
        //processor.process(new BEvent());
    }
}
