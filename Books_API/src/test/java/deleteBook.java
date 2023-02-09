import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class deleteBook {

    String nameValue = "Test";

    Response res = given().spec(Specifications.requestSpecification())
            .when().get("/api/books");
    JsonPath books = new JsonPath(res.asString());
    int id = books.getInt("books[-1].id");

    @Test
    @DisplayName("Тестирование запроса Delete c проверкой status code = 200 и на отсутствие удаленного id")
    public void validTest() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + "\"name\":"
                        +"\""
                        + nameValue
                        +"\""
                        + "}")
                .when().post("/api/books");

        given().spec(Specifications.requestSpecification())
                .when().delete("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_OK);

        given().spec(Specifications.requestSpecification())
                .when().get("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Тестирование запроса Delete с несуществующим id и проверкой status code = 404")
    public void notValidTest() {

        id += id;

        given().spec(Specifications.requestSpecification())
                .when().delete("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Тестирование запроса Delete c подключением к ошибочному Endpoint")
    public void notValidEndpoint() {

        given().spec(Specifications.requestSpecification())
                .when().delete("/api/books")
                .then().log().body().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}
