package side.project.FHM.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import side.project.FHM.exception.InvalidParameterException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ValidateGameUnitTest {

    @Mock
    private ValidateGame validateGameUnderTest;

    @Test
    void validateGameStatus_gameStatusNotAllow_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateGameUnderTest.validateGameStatus("TESTING"));
        Assertions.assertEquals("Status must be either IN PROGRESS or ENDED.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateGameNameTotalTeamBlank_gameNameBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateGameUnderTest.validateGameNameTotalTeamBlank(null, "2"));
        Assertions.assertEquals("Game name cannot be blank.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateGameNameTotalTeamBlank_totalTeamBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateGameUnderTest.validateGameNameTotalTeamBlank("FHM TEST", null));
        Assertions.assertEquals("Total team cannot be blank.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateGameNameTotalTeamBlank_gameNameTotalTeamBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateGameUnderTest.validateGameNameTotalTeamBlank(null, null));
        Assertions.assertEquals("Game name, total team cannot be blank.", actualExceptionThrown.getMessage());
    }
}