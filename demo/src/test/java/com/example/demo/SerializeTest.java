package com.example.demo;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-06-22 11:28 PM
 */
public class SerializeTest {

    static class A implements Serializable {
        B b;
    }

    static class B implements Serializable {
        A a;

    }


    static class Node implements Serializable {

        public Node(String a) {
            this.a = a;
        }

        String a;

        Node next;

    }

    @Test
    public void testCycRef() {
        A a = new A();
        B b = new B();

        a.b = b;
        b.a = a;

        byte[] abytes = SerializationUtils.serialize(a);
        A ap = (A) SerializationUtils.deserialize(abytes);

    }

    @Test
    public void testSOF() {

        Map<String, String> map = new HashMap<>();
        for (int i=0; i<1000; i++) {
            map = Collections.unmodifiableMap(map);
        }

        byte[] mapBytes = SerializationUtils.serialize((Serializable) map);
        Map<String, String> deserializedMap = (Map<String, String>) SerializationUtils.deserialize(mapBytes);
    }



    @Test
    public void testSOF2() {
        //LinkedList is OK because it custom its serialize/deserialize method
//        LinkedList<String> l = new LinkedList();
//        for (int i=0; i<5000; i++) {
//            l.add(i + "");
//        }
//
//        byte[] lbytes = SerializationUtils.serialize(l);
//        LinkedList<String> deserializedL = (LinkedList<String>) SerializationUtils.deserialize(lbytes);

        //
        Node pre = new Node("-1");
        Node head = pre;

        for (int i=0; i<2000; i++) {
            Node newNode = new Node(i + "");
            pre.next = newNode;
            pre = newNode;
        }

        byte[] nodebytes = SerializationUtils.serialize(head);
        Node deserializedNode = (Node) SerializationUtils.deserialize(nodebytes);


    }
}
