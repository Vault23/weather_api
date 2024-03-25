package providers;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import model.WeatherResponseDto;

@UtilityClass
public class WeatherResponseDtoProvider {

   private static final Faker FAKER = new Faker();

    public WeatherResponseDto getDefault() {
        return WeatherResponseDto.builder()
                .location(WeatherResponseDto.Location.builder()
                        .name("London")
                        .region("City of London, Greater London")
                        .country("United Kingdom")
                        .lat(51.52)
                        .lon(-0.11)
                        .tzId("Europe/London")
                        .build())
                .build();
    }

    public WeatherResponseDto getDefault(String city) {
        if (city.equals("London")) {
            return getDefault();
        }
        return WeatherResponseDto.builder()
                .location(WeatherResponseDto.Location.builder()
                        .name(city)
                        .region(FAKER.address().secondaryAddress())
                        .country(FAKER.address().country())
                        .lat(FAKER.random().nextDouble())
                        .lon(FAKER.random().nextDouble())
                        .tzId(FAKER.address().timeZone())
                        .build())
                .build();
    }
}
