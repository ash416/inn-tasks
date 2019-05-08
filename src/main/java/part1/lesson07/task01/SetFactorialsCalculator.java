package part1.lesson07.task01;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The class counts factorials of numbers from given array
 */

class SetFactorialsCalculator {

    private int threadsCount;

    SetFactorialsCalculator(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    List<BigInteger> calculate(List<Integer> numbers) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<BigInteger>> futures = new ArrayList<>();
        for (Integer number: numbers) {
            futures.add(executorService.submit(new FactorialCalculator(number)));
        }
        List<BigInteger> results = new ArrayList<>();
        for (Future<BigInteger> future: futures) {
            results.add(future.get());
        }
        executorService.shutdown();
        return results;
    }
}
