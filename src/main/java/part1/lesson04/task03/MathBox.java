package part1.lesson04.task03;

import part1.lesson04.task03.exceptions.NotValidTypeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * The class allows to store a set of numbers, add them, divide by a given number, remove a number from a set
 * @author Sheronova Anna
 */

public class MathBox extends ObjectBox{
    private Set<Double> numberSet = new HashSet<>();

    MathBox(Number[] numbers) {
        for(Number number: numbers) {
            numberSet.add(number.doubleValue());
        }
    }

    @Override
    public void addObject(Object number) throws NotValidTypeException{
        if (!(number instanceof Number)) {
            throw new NotValidTypeException("Тип объекта не является наследником Number");
        }
        numberSet.add(Double.valueOf(number.toString()));
    }

    @Override
    public void deleteObject(Object number) throws NotValidTypeException{
        if (!(number instanceof Number)) {
            throw new NotValidTypeException("Тип объекта не является наследником Number");
        }
        numberSet.remove(Double.valueOf(number.toString()));
    }

    @Override
    public String dump() {
        return this.toString();
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

    void deleteIntValue(Integer value) {
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
