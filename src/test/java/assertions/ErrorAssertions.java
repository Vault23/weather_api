package assertions;

import enums.Errors;
import lombok.experimental.UtilityClass;
import model.ErrorDto;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class ErrorAssertions {

    public void assertForResponseCode(ErrorDto response, Errors errors) {
        assertThat(response.getError().getMessage())
                .as("Сообщение об ошибке отличается")
                .isEqualTo(errors.getErrorDto().getError().getMessage());
    }

}
