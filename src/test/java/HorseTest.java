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
    void should_ThrowIllegalArgumentException_When_NameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 0.1);
        });
    }

    @Test
    void should_ContainExpectedMessage_When_NameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 0.1);
        });

        assertEquals(EXPECTED_NULL_NAME_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void should_ThrowIllegalArgumentException_When_NameIsBlank(String blankName) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(blankName, 0.2);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void should_ContainExpectedMessage_When_NameIsBlank(String whitespace) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(whitespace, 0.2);
        });

        assertEquals(EXPECTED_BLANK_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void should_ThrowIllegalArgumentException_When_SpeedIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", -0.1);
        });
    }

    @Test
    void should_ContainExpectedMessage_When_SpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", -0.1);
        });

        assertEquals(EXPECTED_NEGATIVE_SPEED_MESSAGE, exception.getMessage());
    }

    @Test
    void should_ThrowIllegalArgumentException_When_DistanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", 0.1, -0.1);
        });
    }

    @Test
    void should_ContainExpectedMessage_When_DistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("ValidName", 0.1, -0.1);
        });

        assertEquals(EXPECTED_NEGATIVE_DISTANCE_MESSAGE, exception.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"validName", "s", "     validName "})
    void should_ReturnName_When_GetNameIsCalled(String validName) {
        assertEquals(validName, new Horse(validName, 0.1).getName());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0, 0d})
    void should_ReturnSpeed_When_GetSpeedIsCalled(double expectedSpeed) {
        assertEquals(expectedSpeed, new Horse("validName", expectedSpeed, 0.2).getSpeed());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0, 0d})
    void should_ReturnDistance_When_GetDistanceIsCalled(double expectedSpeed) {
        assertEquals(expectedSpeed, new Horse("validName", 0.2, expectedSpeed).getDistance());
    }

    @Test
    void should_ReturnDefaultDistance_When_DistanceIsNotProvided() {
        assertEquals(0.0, new Horse("validName", 0.2).getDistance());
    }

    @Test
    void should_CallGetRandomDoubleWithSpecificValues_When_MoveIsCalled() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("ValidName", 0.1).move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({"0.0, 1.2, 0.2, 0.9, 0.828"})
    void should_UpdateDistanceAccordingToFormula_When_MoveIsCalled(double distance, double speed, double min, double max, double result) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horseTest = new Horse("ValidName", speed, distance);
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(0.69);

            horseTest.move();

            assertEquals(result, horseTest.getDistance());
        }
    }
}