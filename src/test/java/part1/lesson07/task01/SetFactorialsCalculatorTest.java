package part1.lesson07.task01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SetFactorialsCalculatorTest {
    @Test
    public void calculatorGivesCorrectAnswer() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<BigInteger> expectedFactorials = new ArrayList<BigInteger>() {{
            add(BigInteger.valueOf(1));
            add(BigInteger.valueOf(2));
            add(BigInteger.valueOf(6));
            add(BigInteger.valueOf(24));
        }};

        SetFactorialsCalculator calculator = new SetFactorialsCalculator(2);
        List<BigInteger> result = new ArrayList<>();
        try {
            result = calculator.calculate(numbers);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertThat(result).isEqualTo(expectedFactorials);
    }

}
