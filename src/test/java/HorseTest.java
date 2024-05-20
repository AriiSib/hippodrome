import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    private static final String EXPECTED_NULL_NAME_MESSAGE = "Name cannot be null.";
    private static final String EXPECTED_BLANK_NAME_MESSAGE = "Name cannot be blank.";
    private static final String EXPECTED_NEGATIVE_SPEED_MESSAGE = "Speed cannot be negative.";
    private static final String EXPECTED_NEGATIVE_DISTANCE_MESSAGE = "Distance cannot be negative.";

    @Test
    void shouldThrowIllegalArgumentExceptionWhenProvidedNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 0.1);
        });
    }

    @Test
    void shouldContainsExpectedExceptionMessageWhenProvidedNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 0.1);
        });

        assertEquals(EXPECTED_NULL_NAME_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldThrowIllegalArgumentExceptionWhenProvidedWhitespace(String whitespace) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(whitespace, 0.2);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldContainsExpectedExceptionMessageWhenProvidedWhitespace(String whitespace) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(whitespace, 0.2);
        });

        assertEquals(EXPECTED_BLANK_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenProvidedNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", -0.1);
        });
    }

    @Test
    void shouldContainsExpectedExceptionMessageWhenSecondParameterNegativeNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", -0.1);
        });

        assertEquals(EXPECTED_NEGATIVE_SPEED_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenThirdParameterNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", 0.1, -0.1);
        });
    }

    @Test
    void shouldContainsExpectedExceptionMessageWhenThirdParameterNegativeNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", 0.1, -0.1);
        });

        assertEquals(EXPECTED_NEGATIVE_DISTANCE_MESSAGE, exception.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"validName", "s", " validName "})
    void shouldReturnFirstParameterOfConstructor(String validName) {
        assertEquals(validName, new Horse(validName, 0.1).getName());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0, 0d})
    void shouldReturnSecondParameterOfConstructor(double validSpeed) {
        assertEquals(validSpeed, new Horse("validName", validSpeed, 0.2).getSpeed());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0, 0d})
    void shouldReturnThirdParameterOfConstructor(double validDistance) {
        assertEquals(validDistance, new Horse("validName", 0.2, validDistance).getDistance());
    }

    @Test
    void shouldReturnDefaultValueOfDistance() {
        assertEquals(0.0, new Horse("validName", 0.2).getDistance());
    }

    @Test
    void shouldCallGetRandomMethodWithValues() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {

            Horse horseTest = new Horse("ValidName", 0.1);
            horseTest.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({"0.0, 1.2, 0.2, 0.9"})
    void shouldUpdateDistanceAccordingToFormula(double distance, double speed, double min, double max) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(0.69);

            Horse horseTest = new Horse("ValidName", speed, distance);
            horseTest.move();
            assertEquals(0.828, horseTest.getDistance());
        }
    }
}