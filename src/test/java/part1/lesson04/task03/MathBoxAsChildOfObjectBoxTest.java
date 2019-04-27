package part1.lesson04.task03;

import org.junit.jupiter.api.Test;
import part1.lesson04.task03.exceptions.NotValidTypeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MathBoxAsChildOfObjectBoxTest {

    @Test
    void addingNewElementsToMathBoxTest() throws NotValidTypeException {
        MathBox mathBox = new MathBox(new Integer[]{1, 2, 3});
        mathBox.addObject(4);
        assertThat(mathBox).isEqualTo(new MathBox(new Integer[]{1, 2, 3, 4}));
        mathBox.addObject(4);
        assertThat(mathBox).isEqualTo(new MathBox(new Integer[]{1, 2, 3, 4}));
    }

    @Test
    void deletingElementFromMathBox() throws NotValidTypeException {
        MathBox mathBox = new MathBox(new Integer[]{1, 2, 3, 4});
        mathBox.deleteObject(4);
        assertThat(mathBox).isEqualTo(new MathBox(new Integer[]{1, 2, 3}));
    }

    @Test
    void throwExceptionAfterTryingToAddObject() {
        MathBox mathBox = new MathBox(new Integer[]{1});
        assertThrows(NotValidTypeException.class, () -> mathBox.addObject(new Object()));
    }
}
