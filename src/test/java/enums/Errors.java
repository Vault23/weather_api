package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.ErrorDto;

@Getter
@AllArgsConstructor
public enum Errors {
    UNAUTHORIZED(401, ErrorDto.builder().error(new ErrorDto.Error(1002, "API key is invalid or not provided.")).build()),
    WRONG_KEY(403, ErrorDto.builder().error(new ErrorDto.Error(2008, "API key has been disabled.")).build()),
    WRONG_CITY(400, ErrorDto.builder().error(new ErrorDto.Error(1006, "No matching location found.")).build()),
    NO_PARAMETER(400, ErrorDto.builder().error(new ErrorDto.Error(1003, "Parameter q is missing.")).build());

    final int statusCode;
    final ErrorDto errorDto;

    public static Errors fromStatusCode(int code) {
        for (Errors e : Errors.values()) {
            if (e.getStatusCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum found with code: " + code);
    }

    public static Errors fromCode(int code) {
        for (Errors e : Errors.values()) {
            if (e.getErrorDto().getError().getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum found with code: " + code);
    }
}
