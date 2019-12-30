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
        BigInteger lastPrimaryNumberFound = BigInteger.valueOf(2);
        int primaryNumberIndex = 0;
        boolean isPrimeFound = true;

        while (primaryNumberIndex != range) {
            lastPrimaryNumberFound = lastPrimaryNumberFound.add(BigInteger.ONE);
            for (BigInteger i = BigInteger.valueOf(2); i.compareTo(lastPrimaryNumberFound.subtract(BigInteger.ONE)) != 0; i = i.add(BigInteger.ONE)) {
                if (lastPrimaryNumberFound.remainder(i).compareTo(BigInteger.ZERO) == 0) {
                    isPrimeFound = false;
                    break;
                } else {
                    isPrimeFound = true;
                }
            }
            if (isPrimeFound) {
                primaryNumberIndex++;
            }
        }
        return lastPrimaryNumberFound;
    }

    public static void main(String[] args) {
    }
}
