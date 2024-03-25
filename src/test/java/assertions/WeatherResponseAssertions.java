package assertions;

import lombok.experimental.UtilityClass;
import model.WeatherResponseDto;
import org.assertj.core.api.SoftAssertions;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class WeatherResponseAssertions {

    public void assertWeatherResponseDto(WeatherResponseDto responseDto, WeatherResponseDto expected) {
        assertThat(responseDto.getLocation())
                .usingRecursiveComparison()
                .ignoringFields("localtimeEpoch", "localtime")
                .as("Ошибка в проверке ответа с эталоном")
                .isEqualTo(expected.getLocation());
    }
}
