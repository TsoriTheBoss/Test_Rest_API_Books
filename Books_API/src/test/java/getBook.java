import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class getBook {

    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200 и проверкой key/value по полю id")
    public void validTest() {

        Response res = given().spec(Specifications.requestSpecification())
                .when().get("/api/books");
        JsonPath books = new JsonPath(res.asString());
        int size = books.getInt("books.size()");
        for (int i = 1; i <= size; i++) {
            given().spec(Specifications.requestSpecification())
                    .when().get("/api/books/{size}", i)
                    .then().log().body().statusCode(HttpStatus.SC_OK)
                    .and().body("book.id", Matchers.is(i));
        }
    }

    @Test
    @DisplayName("Тестирование запроса Get c подключением к несуществующему id")
    public void notValidTest() {

        Response res = given().spec(Specifications.requestSpecification())
                .when().get("/api/books");
        JsonPath books = new JsonPath(res.asString());
        int size = books.getInt("books.size()");
        size += 1;
            given().spec(Specifications.requestSpecification())
                    .when().get("/api/books/{size}", size)
                    .then().log().body().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
