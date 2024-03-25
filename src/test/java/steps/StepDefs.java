package steps;

import assertions.ErrorAssertions;
import assertions.WeatherResponseAssertions;
import com.github.javafaker.Faker;
import enums.Errors;
import http.HttpClient;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.ErrorDto;
import model.WeatherResponseDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import providers.WeatherResponseDtoProvider;
import utils.PropertyLoader;

import java.util.Map;


@Slf4j
public class StepDefs extends HttpClient {
    private WeatherResponseDto response;
    private ErrorDto errorDto;

    @Before
    public void before() {
        setQueries(Map.of("key", PropertyLoader.getString("apiKey")));
    }

    @When("Запрашиваем текущую погоду для {word}")
    @Step("Запрашиваем текущую погоду для {city}")
    public void getCurrentWeather(String city) {
        Map<String, String> queryParams = Map.of("q", city);
        response = get(queryParams, HttpStatus.SC_OK, WeatherResponseDto.class);
    }

    @When("Запрашиваем текущую погоду для {word} без ApiKey")
    @Step("Запрашиваем текущую погоду для {city} без ApiKey")
    public void getCurrentWeatherWithoutApiKey(String city) {
        setQueries(Map.of("q", city));
        errorDto = get(HttpStatus.SC_UNAUTHORIZED, ErrorDto.class);
    }

    @And("Проверяем значения для {word} с эталонными данными")
    @Step("Проверяем значения для {city} с эталонными данными")
    public void validateResponseForCity(String city) {
        WeatherResponseDto expected = WeatherResponseDtoProvider.getDefault(city);
        WeatherResponseAssertions.assertWeatherResponseDto(response, expected);

    }

    @And("Проверяем код ответа {int}")
    @Step("Проверяем код ответа {code}")
    public void checkErrorForResponseCode(int code) {
        ErrorAssertions.assertForResponseCode(errorDto, Errors.fromStatusCode(code));
    }

    @When("Запрашиваем текущую погоду без города")
    @Step("Запрашиваем текущую погоду без города")
    public void getCurrentWeatherWithoutCity() {
        errorDto = get(HttpStatus.SC_BAD_REQUEST, ErrorDto.class);
    }

    @When("Запрашиваем текущую погоду для {word} с неверным apiKey")
    @Step("Запрашиваем текущую погоду для {city} с неверным apiKey")
    public void getCurrentWeatherWithIncorrectApiKey(String city) {
        setQueries(Map.of("q", city, "key", RandomStringUtils.random(31)));
        errorDto = get(HttpStatus.SC_FORBIDDEN, ErrorDto.class);
    }

    @And("Проверяем сообщение об ошибке для кода {int}")
    @Step("Проверяем сообщение об ошибке для кода {code}")
    public void checkErrorMessage(int code) {
        ErrorAssertions.assertForResponseCode(errorDto, Errors.fromCode(code));
    }

    @When("Запрашиваем текущую погоду для неверного города")
    @Step("Запрашиваем текущую погоду для неверного города")
    public void getCurrentWeatherWithIncorrectCity() {
        setQueries(Map.of(
                "key", PropertyLoader.getString("apiKey"),
                "q", RandomStringUtils.randomNumeric(10)));
        errorDto = get(HttpStatus.SC_BAD_REQUEST, ErrorDto.class);
    }
}
