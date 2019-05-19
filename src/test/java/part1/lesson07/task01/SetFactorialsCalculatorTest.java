package part1.lesson07.task01;

import com.google.common.math.BigIntegerMath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

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

    @Test
    public void calculateWithoutMistakes() throws ExecutionException, InterruptedException {
        final int countNumbers = 10000;
        List<Integer> numbers = Stream
                .generate(() -> (int) (10000 * Math.random()))
                .limit(countNumbers)
                .collect(Collectors.toList());
        SetFactorialsCalculator calculator = new SetFactorialsCalculator(10);
        List<BigInteger> result = calculator.calculate(numbers);
        List<BigInteger> expectedResult = numbers.stream()
                .map(BigIntegerMath::factorial)
                .collect(Collectors.toList());
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void timeTest() throws ExecutionException, InterruptedException {
        final int countNumbers = 1000;
        List<Integer> numbers = Stream
                .generate(() -> (int) (10000 * Math.random()))
                .limit(countNumbers)
                .collect(Collectors.toList());
        printTime(1, numbers);
        printTime(2, numbers);
        printTime(4, numbers);
        printTime(8, numbers);
        printTime(16, numbers);

    }

    private void printTime(int countThreads, List<Integer> numbers) throws ExecutionException, InterruptedException {
        String message = "Количество потоков: %s; время: %s";
        long time = System.currentTimeMillis();
        new SetFactorialsCalculator(countThreads).calculate(numbers);
        System.out.println(format(message, countThreads, System.currentTimeMillis() - time));
    }


}
