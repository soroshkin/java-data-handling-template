package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return new BigDecimal(a / (b * 1.0), new MathContext(range));
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        BigInteger numberOfElementsToCheck = BigInteger.valueOf((long) (50 + 1.11182 * range * Math.log(1.20661 * range + 0.00435)));
        if (numberOfElementsToCheck.compareTo(BigInteger.valueOf(Integer.MAX_VALUE).subtract(BigInteger.ONE)) > 0) {
            System.out.println("Value \"range\" is out of range, try to decrease it");
            return BigInteger.ZERO;
        }

        boolean[] primaryNumbers = new boolean[numberOfElementsToCheck.intValue()];
        Arrays.fill(primaryNumbers, true);
        primaryNumbers[0] = false;
        primaryNumbers[1] = false;

        for (int i = 2; numberOfElementsToCheck.compareTo(BigInteger.valueOf(i)) > 0; i++) {
            for (int j = 2; numberOfElementsToCheck.compareTo(BigInteger.valueOf(j * i)) > 0; j++) {
                primaryNumbers[j * i] = false;
            }
        }

        int primaryNumberIndex = -1;
        BigInteger primaryNumberFound = BigInteger.ZERO;
        for (int i = 0; i < primaryNumbers.length; i++) {
            if (primaryNumbers[i]) {
                primaryNumberIndex++;
                if (primaryNumberIndex == range) {
                   return BigInteger.valueOf(i);
                }
            }
        }
        return primaryNumberFound;
    }

    public static void main(String[] args) {
    }
}
