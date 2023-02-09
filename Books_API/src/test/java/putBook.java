import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class putBook {

    @Test
    @DisplayName("Создание нового элемента")
    public void postBook() {
        String bookName = "Test";

        given().spec(Specifications.requestSpecification()).body("{"
                        + "\"name\":"
                        +"\""
                        + bookName
                        +"\""
                        + "}")
                .when().post("/api/books");
    }

    Response res = given().spec(Specifications.requestSpecification())
            .when().get("/api/books");
    JsonPath books = new JsonPath(res.asString());
    int id = books.getInt("books[-1].id");

    String nameValue = "TestName";
    String authorValue = "TestAuthor";
    Integer yearValue = 1234;
    Boolean isElectronicBookValue = true;

    String insertName = "\"name\":" + "\"" + nameValue +"\"";
    String insertAuthor = "\"author\":" + "\"" + authorValue +"\"";
    String insertYear = "\"year\":" + yearValue;
    String insertIsElectronicBookValue = "\"isElectronicBook\":" + isElectronicBookValue;

    @Test
    @DisplayName("Тестирование запроса Put c проверкой status code = 200 и проверкой key/value по полям id/name/author/year/isElectronicBook")
    public void ValidTest() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + insertName
                        +","
                        + insertAuthor
                        +","
                        + insertYear
                        +","
                        + insertIsElectronicBookValue
                        + "}")
                .when().put("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_OK)
                .and()
                .body("book.id", Matchers.is(id))
                .body("book.name", Matchers.is(nameValue))
                .body("book.author", Matchers.is(authorValue))
                .body("book.year", Matchers.is(yearValue))
                .body("book.isElectronicBook", Matchers.is(isElectronicBookValue));
    }

    @Test
    @DisplayName("Тестирование запроса Put c пустым полем name/author/year/isElectronicBook")
    public void notValidTest() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + insertName
                        +","
                        + insertAuthor
                        +","
                        //+ insertYear
                        +","
                        + insertIsElectronicBookValue
                        + "}")
                .when().put("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тестирование запроса Put с обращением к несуществующему id")
    public void notValidId() {
        id += id;

        given().spec(Specifications.requestSpecification()).body("{"
                        + insertName
                        +","
                        + insertAuthor
                        +","
                        + insertYear
                        +","
                        + insertIsElectronicBookValue
                        + "}")
                .when().put("/api/books/{id}", id)
                .then().log().body().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Тестирование запроса Put с подключением к ошибочному Endpoint")
    public void wrongEndPoint() {

        given().spec(Specifications.requestSpecification()).body("{"
                        + insertName
                        +","
                        + insertAuthor
                        +","
                        + insertYear
                        +","
                        + insertIsElectronicBookValue
                        + "}")
                .when().put("/api/books")
                .then().log().body().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}
