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
class ValidateRoundUnitTest {

    @Mock
    private ValidateRound validateRoundUnderTest;

    @Test
    void validateSpinToken_spinTokenNotAllow_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateRoundUnderTest.validateSpinToken("TESTING"));
        Assertions.assertEquals("Spin token must be either TRUE or FALSE.", actualExceptionThrown.getMessage());

    }
}