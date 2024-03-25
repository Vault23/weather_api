package http;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import utils.PropertyLoader;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class HttpClient {

    protected Map<String, String> cookies;
    protected Map<String, String> headers;
    protected Map<String, String> queries;
    protected Object body;
    protected String path;
    protected ContentType contentType = JSON;

    protected void setHeaders(Map<String, String> headers) {
        log.info("Headers for request:\n" + printMapToLog(headers));
        this.headers = headers;
    }

    protected void setBody(Object body) {
        log.info("Body for request:\n" + body.toString());
        this.body = body;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    public void setQueries(Map<String, String> queries) {
        log.debug("Queries for request:\n" + printMapToLog(queries));
        this.queries = queries;
    }

    protected <T> T get(int code, Class<T> tClass) {
        log.debug("Sending get request...");
        Response response = buildRequestSpecification()
                .when()
                .get();
        verifyResponse(response, code);
        return response.as(tClass);
    }

    protected <T> T get(Map<String, ?> params, int code, Class<T> tClass) {
        log.debug("Sending get request...");
        Response response = buildRequestSpecification()
                .queryParams(params)
                .when()
                .get();
        verifyResponse(response, code);
        return response.as(tClass);
    }

    protected RequestSpecification buildRequestSpecification(RequestSpecBuilder requestSpecBuilder) {
        return given(requestSpecBuilder
                .setBaseUri(PropertyLoader.getString("baseUri"))
                .log(LogDetail.ALL)
                .build());
    }

    private RequestSpecification buildRequestSpecification() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(JSON).setContentType(JSON);
        requestSpecBuilder.setBasePath(PropertyLoader.getString("currentWeatherPath"));
        if (cookies != null) {
            requestSpecBuilder.addCookies(cookies);
        }
        if (headers != null) {
            requestSpecBuilder.addHeaders(headers);
        }
        if (contentType.equals(XML)) {
            requestSpecBuilder.setAccept(XML).setContentType(XML);
        }
        if (queries != null) {
            requestSpecBuilder.addQueryParams(queries);
        }
        if (path != null) {
            requestSpecBuilder.setBasePath(path);
        }
        return buildRequestSpecification(requestSpecBuilder);
    }

    private String printMapToLog(Map<?, ?> headers) {
        StringBuilder sb = new StringBuilder();
        headers.forEach((key, value) -> sb.append("\"")
                .append(key)
                .append("\": ")
                .append("\"")
                .append(value)
                .append("\"\n"));
        return sb.toString();
    }

    private void verifyResponse(Response response, int code) {
        assertThat(response.statusCode())
                .as("Код ответа неверный")
                .isEqualTo(code);
    }
}
