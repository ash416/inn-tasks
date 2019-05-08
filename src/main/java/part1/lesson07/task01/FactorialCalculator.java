package part1.lesson07.task01;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * The class is for counting factorial a given number
 */

public class FactorialCalculator implements Callable<BigInteger> {

    private int number;

    FactorialCalculator(int number) {
        this.number = number;
    }

    @Override
    public BigInteger call() {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
