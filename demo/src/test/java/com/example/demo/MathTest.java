package com.example.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2021-01-09 9:00 PM
 */
public class MathTest {

    //工行居逸贷 ，等本等息，手续费分期付款
    public static void x1() {
        BigDecimal p = new BigDecimal(17);
        int precision = 100;
        int n = 60;
        BigDecimal sum = new BigDecimal(Double.toString(0));
        for (double i=1; i<=n; i++) {
            BigDecimal b = new BigDecimal(1).divide(new BigDecimal(i), precision, RoundingMode.HALF_UP);
            sum = sum.add(b);
            System.out.println(b);
        }

        System.out.println(">>>>" + sum.divide(new BigDecimal(n), precision, RoundingMode.HALF_UP).multiply(new BigDecimal(12)).multiply(p));
    }

    //按月付利息， 到期还本
    public static void x2() {
        BigDecimal p = new BigDecimal(6);
        int precision = 100;
        int n = 24;
        BigDecimal sum = new BigDecimal(Double.toString(0));


        for (double i=1; i<=n; i++) {
//            sum = sum.add(b);
//            System.out.println(b);
        }

        System.out.println(">>>>" + sum.divide(new BigDecimal(n)).multiply(new BigDecimal(12)).multiply(p));
    }

    public static void main(String[] args) {
        x1();
    }
}
