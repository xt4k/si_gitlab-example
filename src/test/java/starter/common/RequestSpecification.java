package starter.common;

import io.restassured.builder.RequestSpecBuilder;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import static io.restassured.http.ContentType.JSON;
import static net.serenitybdd.core.environment.EnvironmentSpecificConfiguration.from;
import static starter.enums.Const.*;

public class RequestSpecification {

    public static io.restassured.specification.RequestSpecification searchReqSpec() {
        EnvironmentVariables environmentVariables = Injectors.getInjector()
                .getInstance(EnvironmentVariables.class);

        return new RequestSpecBuilder()
                .setBaseUri(from(environmentVariables).getProperty("baseurl"))
                .setBasePath("api")
                .setContentType("application/json")
                .build();
    }

    public static io.restassured.specification.RequestSpecification authorizedReqSpec() {
        EnvironmentVariables environmentVariables = Injectors.getInjector()
                .getInstance(EnvironmentVariables.class);

        return new RequestSpecBuilder()
                .setBaseUri(from(environmentVariables).getProperty("baseurl"))
                .setBasePath(BASE_PATH.getValue())
                .setContentType(JSON)
                .addHeader(ACCEPT_HEADER.getField(), ACCEPT_HEADER.getValue())
                .addHeader(AUTH_TOKEN.getField(), AUTH_TOKEN.getValue())
                .build();
    }
}