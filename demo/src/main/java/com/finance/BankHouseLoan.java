package com.finance;

import com.google.common.base.MoreObjects;

import java.util.Arrays;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2021-01-16 3:17 PM
 */
public class BankHouseLoan {

    public static class Details {

        public Details(double totalMoney, double totalInterests, double[][] detail) {
            this.totalMoney = totalMoney;
            this.totalInterests = totalInterests;
            this.principalDetail = new double[detail.length];
            this.interestDetail = new double[detail.length];
            this.totalDetail = new double[detail.length];
            for (int i=0; i<detail.length; i++) {
                principalDetail[i] = detail[i][0];
                interestDetail[i] = detail[i][1];
                totalDetail[i] = detail[i][2];
            }

        }

        private final double totalMoney;  //还款金额（本金+利息）

        private final double totalInterests; //利息总额

        private final double[] principalDetail;
        private final double[] interestDetail;
        private final double[] totalDetail;

        public double getTotalMoney() {
            return totalMoney;
        }

        public double getTotalInterests() {
            return totalInterests;
        }


        public double[] getPrincipalDetail() {
            return principalDetail;
        }

        public double[] getInterestDetail() {
            return interestDetail;
        }

        public double[] getTotalDetail() {
            return totalDetail;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)

                    .add("totalMoney", totalMoney)
                    .add("totalInterests", totalInterests + "\n")
                    .add("detail-principal", Arrays.toString(getPrincipalDetail()) + "\n")
                    .add("detail-interest", Arrays.toString(getInterestDetail()) + "\n")
                    .add("detail-total", Arrays.toString(getTotalDetail()))
                    .toString();
        }

    }

    private final double loanMoney;  //贷款金额

    private final int years; //贷款年限

    private final double yIns; //年利率


    public BankHouseLoan(double loanMoney, int years, double yIns) {
        this.loanMoney = loanMoney;
        this.years = years;
        this.yIns = yIns;
    }



    //等额本息
    public Details calEqualPrincipalAndInterest() {
        double mIns = yIns / 100 / 12; //月利率
        int months = years * 12; //还款所需月份
        double pow = Math.pow(1 + mIns, months);
        double remains = loanMoney;
        double totalMoney = (months * loanMoney * mIns * pow) / (pow - 1);  //总还款金额
        totalMoney = Math.floor(totalMoney * 100 + 0.5) / 100;  //floor函数 保留两位小数
        double totalInterests = totalMoney - loanMoney;
        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;
        double temp[][] = new double[months][3];
        for (int i = 0; i < months; i++) {
            if(i == months - 1) {
                temp[i][1] = remains * mIns;
                temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
                temp[i][0] = remains;
                temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
                temp[i][2] = temp[i][0] + temp[i][1];
                temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
                break;
            }
            //由于精度问题 最后一个月实际的本金会有差别 需要单独计算
            temp[i][1] = remains * mIns;
            temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
            temp[i][2] = totalMoney / months;
            temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
            temp[i][0] = temp[i][2] - temp[i][1];
            temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
            remains -= temp[i][0];
        }
        //temp[][0]为每月还款本金 temp[][1]为每月还款利息 temp[][2]为每月还款总额

        Details details = new Details(totalMoney, totalInterests, temp);
        return details;
    }


    //等额本金
    public Details calEqualPrincipal() {

        double mIns = yIns / 100 / 12; //月利率
        int months = (years * 12);
        double remains = loanMoney;
        double sum = 0; // 总计还款金额
        double temp[][] = new double[months][3];
        for (int i = 0; i < months; i++) {
            temp[i][0] = loanMoney / months;
            temp[i][0] = Math.floor(temp[i][0] * 100 + 0.5) / 100;
            temp[i][1] = remains * mIns;
            temp[i][1] = Math.floor(temp[i][1] * 100 + 0.5) / 100;
            remains -= temp[i][0];
            temp[i][2] = temp[i][0] + temp[i][1];
            temp[i][2] = Math.floor(temp[i][2] * 100 + 0.5) / 100;
            sum += temp[i][2];
        }
        //temp[][0]为每月还款本金 temp[][1]为每月还款利息 temp[][2]为每月还款总额

        double totalMoney = Math.floor(sum * 100 + 0.5) / 100;
        double totalInterests = totalMoney - loanMoney;
        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;

        Details details = new Details(totalMoney, totalInterests, temp);
        return details;
    }

    public static void main(String[] args) {
        BankHouseLoan bankHouseLoan = new BankHouseLoan(100000, 30, 5.39);
        System.out.println(bankHouseLoan.calEqualPrincipalAndInterest());
        System.out.println(bankHouseLoan.calEqualPrincipal());
    }
}
