package com.finance;

import java.util.ArrayList;
import java.util.List;


public class IrrUtil {

    //迭代次数
    public static int LOOPNUM=1000;
    //最小差异
    public static final double MINDIF=0.00000001;


    /**
     * @desc 使用方法参考main方法
     * @param cashFlow  资金流
     * @return 月化收益率
     */
    public static double getIrr(List<Double> cashFlow){
        double flowOut=cashFlow.get(0);
        double minValue=0d;
        double maxValue=1d;
        double testValue=0d;
        while(LOOPNUM>0){
            testValue=(minValue+maxValue)/2;
            double npv=NPV(cashFlow,testValue);
            if(Math.abs(flowOut+npv)<MINDIF){
                break;
            }else if(Math.abs(flowOut)>npv){
                maxValue=testValue;
            }else{
                minValue=testValue;
            }
            LOOPNUM--;
        }
        return testValue;
    }

    public static double NPV(List<Double> flowInArr,double rate){
        double npv=0;
        for(int i=1;i<flowInArr.size();i++){
            npv+=flowInArr.get(i)/Math.pow(1+rate, i);
        }
        return npv;
    }

    public static void x1() {
        //工行居逸贷 ，等本等息，手续费分期付款
        double flowOut=-100000d;
        List<Double> flowInArr=new ArrayList<Double>();
        flowInArr.add(flowOut);

        double p = 0.034; //分期手续费
        int n = 12;
        for (int i=1; i<=n; i++) {
            flowInArr.add(-1 * flowOut * (1 + p) / n);
        }


        System.out.println(IrrUtil.getIrr(flowInArr)*12);
    }

    public static void x2() {
        //按月还息，到期还本
        double flowOut=-100000d;
        List<Double> flowInArr=new ArrayList<Double>();
        flowInArr.add(flowOut);

        double p = 0.065;//年利率
        int n = 12;
        for (int i=1; i<=n-1; i++) {
            flowInArr.add(-1 * flowOut * p / 12);
        }
        flowInArr.add(-1 * flowOut);

        System.out.println(IrrUtil.getIrr(flowInArr)*12);
    }

    public static void x3() {
        // 银行贷款 等额本息
        // 等额本息和等额本金还款方式的实际利率都是一样的，都等于名义利率 ？
        double flowOut=-100000d;
        List<Double> flowInArr=new ArrayList<Double>();
        flowInArr.add(flowOut);

        double p = 0.049 * 1.1;//年利率
        int n = 360;
        for (int i=1; i<=n-1; i++) {
            flowInArr.add(560.91);
        }

        System.out.println(IrrUtil.getIrr(flowInArr)*12);
    }

    public static void main(String[] args) {
        x1();

        x2();

        x3();
    }
}
