package part1.lesson01.task02;

import part1.lesson01.task02.exceptions.SquareRootNegativeException;

/**
 * Class tries calculate square root of number.
 * @author Sheronova Anna
 */
public class SquareRootCalculator {
    public static void main(String[] args) {
        SquareRootCalculator calculator = new SquareRootCalculator();
        Integer[] numbers = NumbersGenerator.generate(100);
        for (Integer number: numbers) {
            try {
                calculator.calculate(number);
            } catch (SquareRootNegativeException e) {
                System.out.println(e.getMessage() + " " + number);
            }
        }
    }

    /**
     * Method calculates square root
     * If to take square root is possible, method displays it on the screen.
     * If number is negative, method throws exception
     * @param num
     * @throws SquareRootNegativeException
     */
    private void calculate(Integer num) throws SquareRootNegativeException{
        if (num < 0) {
            throw new SquareRootNegativeException("Нельзя извлечь корень из отрицательного числа");
        }
        double squareRoot = Math.sqrt((double) num);
        if (Math.pow(Math.round(squareRoot), 2) == num) {
            System.out.println(num);
        }
    }
}
