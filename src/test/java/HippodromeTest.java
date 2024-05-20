import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    private static final String EXPECTED_NULL_NAME_MESSAGE = "Horses cannot be null.";
    private static final String EXPECTED_EMPTY_NAME_MESSAGE = "Horses cannot be empty.";

    @Test
    void shouldThrowIllegalArgumentExceptionWhenProvidedNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    void shouldContainsExpectedMessageExceptionWhenProvidedNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });

        assertEquals(EXPECTED_NULL_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenProvidedEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
    }

    @Test
    void shouldContainsExpectedMessageExceptionWhenProvidedEmptyList() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });

        assertEquals(EXPECTED_EMPTY_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldReturnHorsesListSameAsPassedToConstructor() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horseList.add(new Horse("Horse_" + i, 0.1));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void shouldCallMoveOnAllHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Horse mockHorse = Mockito.mock(Horse.class);
            horseList.add(mockHorse);
        }

        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();

        for (Horse horse : horseList) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void shouldReturnHorseWithMaxDistanceWhenGetWinnerIsCalled() {
        List<Horse> horseList = new ArrayList<>();
        horseList.add(new Horse("ValidName1", 0.1, 0.1));
        horseList.add(new Horse("ValidName2", 0.2, 0.4));
        horseList.add(new Horse("ValidName3", 0.3, 0.2));

        Hippodrome hippodrome = new Hippodrome(horseList);

        Horse expectedWinner = horseList.stream()
                                        .max(Comparator.comparing(Horse::getDistance))
                                        .orElseThrow();

        assertEquals(expectedWinner, hippodrome.getWinner());
    }
}
