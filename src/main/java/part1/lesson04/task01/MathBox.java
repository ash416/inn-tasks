package part1.lesson04.task01;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class allows to store a set of numbers, add them, divide by a given number, remove a number from a set
 * @author Sheronova Anna
 */

public class MathBox {

    private Set<Double> numberSet = new HashSet<>();

    MathBox(Number[] numbers) {
        for(Number number: numbers) {
            if (number != null) {
                numberSet.add(number.doubleValue());
            }
        }
    }

    Number summator() {
        return numberSet.stream()
                .reduce((num1, num2) -> num1 + num2)
                .orElse(0.0);
    }

    void splitter(Number divisor) {
        numberSet = numberSet.stream()
                .map(item -> item /= divisor.doubleValue())
                .collect(Collectors.toSet());
    }

    void deleteValue(Integer value) {
        numberSet.remove(Double.valueOf(value));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        numberSet.forEach(item -> stringBuilder.append(item).append(" "));
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return numberSet.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MathBox) || this.hashCode() != object.hashCode()) {
            return false;
        }
        return numberSet.equals(((MathBox) object).numberSet);
    }
}
