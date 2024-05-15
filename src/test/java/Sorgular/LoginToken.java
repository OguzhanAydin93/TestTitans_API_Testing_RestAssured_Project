package Sorgular;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class LoginToken {

    String authenticity_token;
    RequestSpecification reqSpec;





    @BeforeClass
    public void setup(){

        baseURI = "https://www.themoviedb.org";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ODFjZWRlY2MwNmFhYTA1NjM1MDUyY2UzMWUyMzc5MSIsInN1YiI6IjY2MzUxODczYWY0MzI0MDEyMjU0YmMxOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cD8BdV1YfT7WsAr2EFviH9YpAmn6NVkKjQPL44oQw54")
                .setContentType(ContentType.JSON)
                .build();


    }

    @Test
    public void test(){

        authenticity_token =

        given()
                .when()
                .get("/login")


                .then()
                .statusCode(200)
                .extract().response().htmlPath().prettyPrint().substring(27436,27480)

                ;

        System.out.println("authenticity_token = " + authenticity_token);


    }

}
