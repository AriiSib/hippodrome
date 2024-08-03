# Hippodrome Simulation


This project is a simulation of a hippodrome race.
The project includes adding testing and logging to ensure code reliability and maintainability.


### Prerequisites

+ Java 15 or higher
+ Maven

## Testing
The project includes comprehensive tests for the Horse and Hippodrome classes. Each test checks specific behaviors and edge cases.

### Running Tests
To run the tests, use the following command:

`mvn test`

### Logging
Logging is implemented using SLF4J with Logback(default). The logs are written to a file hippodrome.log located in the logs-directory. The log files are rolled over daily and kept for 7 days.

It is also possible to switch to SLF4J with Log4j2. To do this, in pom.xml you need to comment out the Logback section and uncomment the Log4j2 section

To configure the required logging level or appenders (by default, logs are output to the console and saved to the logs directory in the project root), you need to go to the appropriate configuration file located at:
`.\hippodrome\src\main\resources\ `
`logback.xml` or  `log4j2.xml`

### Project Structure

#### Main Classes
- `Horse.java`: Represents a horse with a name, speed, and distance.
- `Hippodrome.java`: Manages a list of horses and controls their movement.
- `Main.java`: The entry point of the application.

#### Tests

**Horse Tests**

##### Constructor Tests
- `Should_ThrowIllegalArgumentException_When_NameIsNull`
- `Should_ThrowExceptionWithMessage_When_NameIsNull`
- `Should_ThrowIllegalArgumentException_When_NameIsBlank`
- `Should_ThrowExceptionWithMessage_When_NameIsBlank`
- `Should_ThrowIllegalArgumentException_When_SpeedIsNegative`
- `Should_ThrowExceptionWithMessage_When_SpeedIsNegative`
- `Should_ThrowIllegalArgumentException_When_DistanceIsNegative`
- `Should_ThrowExceptionWithMessage_When_DistanceIsNegative`

##### Getter Tests
- `Should_ReturnHorseName_When_CalledGetName`
- `Should_ReturnHorseSpeed_When_CalledGetSpeed`
- `Should_ReturnHorseDistance_When_CalledGetDistance`
- `Should_ReturnDefaultDistance_When_CreatedWithTwoParameters`

##### Move Method Tests
- `Should_CallGetRandomDouble_When_MoveIsCalled`
- `Should_UpdateDistanceAccordingToFormula_When_MoveIsCalled`

**Hippodrome Tests**

##### Constructor Tests
- `Should_ThrowIllegalArgumentException_When_HorsesListIsNull`
- `Should_ThrowExceptionWithMessage_When_HorsesListIsNull`
- `Should_ThrowIllegalArgumentException_When_HorsesListIsEmpty`
- `Should_ThrowExceptionWithMessage_When_HorsesListIsEmpty`

##### Method Tests
- `Should_ReturnHorsesList_When_CalledGetHorses`
- `Should_CallMoveOnAllHorses_When_MoveIsCalled`
- `Should_ReturnHorseWithMaxDistance_When_GetWinnerIsCalled`

**Main Method Test**
- `Should_CompleteExecutionWithinTimeout_When_MainIsCalled`