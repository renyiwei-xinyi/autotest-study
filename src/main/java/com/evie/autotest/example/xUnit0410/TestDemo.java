package com.evie.autotest.example.xUnit0410;

public class TestDemo {

    public static void main(String[] args) {
        int i , j ;
        double item , sum;
        // 1 +
        // 1*2 +
        // 1*2*3 +
        // ........

        sum = 0;

        for (i = 1; i <= 100; i++){
            item = 1;
            for (j = 1;j <= i;j++){
                item = item * j;
            }

            sum = sum + item;
        }
        System.out.println(sum);
    }
}
