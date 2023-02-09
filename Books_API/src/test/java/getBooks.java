import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class getBooks {
    @Test
    @DisplayName("Тестирование запроса Get на получение списка всех книг c проверкой status code = 200")
    public void getBooks () {
        given().spec(Specifications.requestSpecification())
                .when().get("/api/books")
                .then().log().body().statusCode(HttpStatus.SC_OK);
    }
}