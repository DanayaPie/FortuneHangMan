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
class ValidateTeamUnitTest {

    @Mock
    private ValidateTeam validateTeamUnderTest;

    @Test
    void validateTeamNameBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateTeamUnderTest.validateTeamNameBlank(""));
        Assertions.assertEquals("Team name cannot be blank.", actualExceptionThrown.getMessage());

    }
}