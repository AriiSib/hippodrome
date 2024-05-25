import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    private static final String EXPECTED_NULL_NAME_MESSAGE = "Horses cannot be null.";
    private static final String EXPECTED_EMPTY_NAME_MESSAGE = "Horses cannot be empty.";

    @Test
    void should_ThrowIllegalArgumentException_When_HorsesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    void should_ContainExpectedMessage_When_HorsesIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });

        assertEquals(EXPECTED_NULL_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void should_ThrowIllegalArgumentException_When_HorsesListIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
    }

    @Test
    void should_ContainExpectedMessage_When_HorsesListIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });

        assertEquals(EXPECTED_EMPTY_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void should_ReturnSameHorsesList_When_CalledGetHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horseList.add(new Horse("Horse_" + i, 0.1));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void should_CallMoveOnAllHorses_When_MoveIsCalled() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horseList.add(Mockito.mock(Horse.class));
        }

        new Hippodrome(horseList).move();

        for (Horse horse : horseList) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void should_ReturnHorseWithMaxDistance_When_GetWinnerIsCalled() {
        Horse horse1 = new Horse("ValidName1", 0.1, 0.1);
        Horse horse2 = new Horse("ValidName2", 0.2, 0.4);
        Horse horse3 = new Horse("ValidName3", 0.3, 0.2);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse2, hippodrome.getWinner());
    }
}
