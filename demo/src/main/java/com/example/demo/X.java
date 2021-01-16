package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2019-04-08 下午11:20
 */
@Component
public class X extends Parent implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("hello world");

        x();
//        exit(1);
    }

    private void x(){

        par();
        String x = null;
//        System.out.println(x.toString());
    }

    @Value("${a}}")
    protected String a;

    public static void main(String[] args){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        });
        t.start();
        System.out.println("ok");
    }
}
