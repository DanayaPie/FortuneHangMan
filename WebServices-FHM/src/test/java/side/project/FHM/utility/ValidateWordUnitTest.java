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
class ValidateWordUnitTest {

    @Mock
    private ValidateWord validateWordUnderTest;

    @Test
    void validateCategoryAndWordBlank_categoryIsBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordBlank("", "ORANGE"));
        Assertions.assertEquals("Category cannot be blank.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateCategoryAndWordBlank_wordIsBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordBlank("FRUIT", ""));
        Assertions.assertEquals("Word cannot be blank.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateCategoryAndWordBlank_categoryAndWordIsBlank_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordBlank(null, null));
        Assertions.assertEquals("Category, word cannot be blank.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateCategoryAndWordChar_categoryContainNotAllowChar_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordChar("123", "ORANGE"));
        Assertions.assertEquals("Categories can only contain alphabets.", actualExceptionThrown.getMessage());
    }

    @Test
    void validateCategoryAndWordChar_wordContainNotAllowChar_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordChar("FRUIT", "123@"));
        Assertions.assertEquals("Words can contain alphabets and some special characters such as ;, :, $, !, &, %, -, ', and ,. If words contain numbers, please spell them out."
                , actualExceptionThrown.getMessage());
    }

    @Test
    void validateCategoryAndWordChar_categoryAndWordContainNotAllowChar_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryAndWordChar("123", "123@"));
        Assertions.assertEquals("Categories can only contain alphabets, but words can contain alphabets and some special characters such as ;, :, $, !, &, %, -, ', and ,. If words contain numbers, please spell them out."
                , actualExceptionThrown.getMessage());
    }


    @Test
    void validateCategoryChar_categoryContainNonAllowChar_negative() {

        Throwable actualExceptionThrown = Assertions.assertThrows(InvalidParameterException.class, () -> validateWordUnderTest.validateCategoryChar("123"));
        Assertions.assertEquals("Category can only contain alphabets.", actualExceptionThrown.getMessage());
    }
}