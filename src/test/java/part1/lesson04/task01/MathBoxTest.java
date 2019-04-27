package part1.lesson04.task01;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class MathBoxTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MathBoxTest.class);

    @Test
    void integerValuesTest() {
        LOGGER.info("Test summator with integer values");
        MathBox testMathBox = new MathBox(new Integer[] {10, 20, 30});
        testSummator(testMathBox, 60.0);
        testSplitter(testMathBox, 10, new MathBox(new Double[]{1.0, 2.0, 3.0}));
        testValueDeleting(testMathBox, 2, new MathBox(new Double[]{1.0, 3.0}));
    }

    @Test
    void doubleValuesTest() {
        LOGGER.info("Test summator with double values");
        MathBox testMathBox = new MathBox(new Double[] {10.0, 20.0, 30.0});
        testSummator(testMathBox, 60.0);
        testSplitter(testMathBox, 10, new MathBox(new Double[]{1.0, 2.0, 3.0}));
        testValueDeleting(testMathBox, 2, new MathBox(new Double[]{1.0, 3.0}));
    }

    @Test
    void floatValueTest() {
        LOGGER.info("Test summator with float values");
        MathBox testMathBox = new MathBox(new Float[] {10f, 20f, 30f});
        testSummator(testMathBox, 60.0);
        testSplitter(testMathBox, 10, new MathBox(new Double[]{1.0, 2.0, 3.0}));
        testValueDeleting(testMathBox, 2, new MathBox(new Double[]{1.0, 3.0}));
    }

    @Test
    void longValueTest() {
        LOGGER.info("Test summator with long values");
        MathBox testMathBox = new MathBox(new Long[] {10L, 20L, 30L});
        testSummator(testMathBox, 60.0);
        testSplitter(testMathBox, 10, new MathBox(new Double[]{1.0, 2.0, 3.0}));
        testValueDeleting(testMathBox, 2, new MathBox(new Double[]{1.0, 3.0}));
    }

    @Test
    void testMathBoxSet() {
        Set<MathBox> mathBoxSet = new HashSet<>();
        mathBoxSet.add(new MathBox(new Integer[] {1, 2, 3}));
        mathBoxSet.add(new MathBox(new Integer[] {1, 2}));
        assertThat(mathBoxSet.size()).isEqualTo(2);
        mathBoxSet.add(new MathBox(new Integer[]{2, 3, 1}));
        assertThat(mathBoxSet.size()).isEqualTo(2);
        mathBoxSet.remove(new MathBox(new Double[]{1.0, 2.0}));
        assertThat(mathBoxSet.size()).isEqualTo(1);
    }

    private void testSummator(MathBox mathBox, Number expectedResult) {
        LOGGER.info("Initial set of elements: {}", mathBox);
        Number result = mathBox.summator();
        LOGGER.info("The result of summator: {}", result);
        assertThat(result).isEqualTo(expectedResult);
    }

    private void testSplitter(MathBox mathBox, Number divisor, MathBox expectedResult) {
        LOGGER.info("Initial set of elements: {}", mathBox);
        mathBox.splitter(divisor);
        LOGGER.info("The result of splitter: {}", mathBox);
        assertThat(mathBox).isEqualTo(expectedResult);
    }

    private void testValueDeleting(MathBox mathBox, Integer value, MathBox expectedResult) {
        LOGGER.info("Initial set of elements: {}", mathBox);
        mathBox.deleteValue(value);
        LOGGER.info("The result of deleting value: {}", mathBox);
        assertThat(mathBox).isEqualTo(expectedResult);
    }
}
