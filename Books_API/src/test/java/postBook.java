import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class postBook {

    String nameValue = "Test";

    @Test
    @DisplayName("Тестирование запроса Post c проверкой status code = 201 и проверкой key/value по обязательному полю name")
    public void ValidTest() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + "\"name\":"
                        +"\""
                        + nameValue
                        +"\""
                        + "}")
                .when().post("/api/books")
                .then().log().body().statusCode(HttpStatus.SC_CREATED)
                .and().body("book.name", Matchers.is(nameValue));
    }

    @Test
    @DisplayName("Тестирование запроса Post c пустым полем name")
    public void notValidTest() {

        given().spec(Specifications.requestSpecification()).body("{" + "}")
                .when().post("/api/books")
                .then().log().body().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тестирование запроса Post с подключением к ошибочному Endpoint")
    public void wrongEndPoint() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + "\"name\":"
                        +"\""
                        + nameValue
                        +"\""
                        + "}")
                .when().post("/api/books/3")
                .then().log().body().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

}
