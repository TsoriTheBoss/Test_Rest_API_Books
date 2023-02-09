import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class Specifications {
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:5000/")
                .setContentType(JSON)
                .build();
    }

    public static ResponseSpecification responseSpecificationScOk() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)
                .expectContentType(JSON)
                .build();
    }
}
