package com.example.demo;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2019-04-13 下午3:15
 */
public class Parent {

    public void par(){
        System.out.print("what:" + a);
    }

    protected String a;


    public static class A extends Sup{
        public A(String a){
            super();
            this.a = a;
        }

        public void hello(){
            System.out.println("what in a:" + a);
            par();
        }

        protected String a;
    }

    public static class Sup {
        public void par(){
            System.out.println("what:" + a);
        }

        protected String a;
    }

    public static void main(String[] args){
        A a = new A("c");
        a.hello();

    }
}
