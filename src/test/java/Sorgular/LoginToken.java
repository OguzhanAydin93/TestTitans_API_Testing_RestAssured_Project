package Sorgular;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class LoginToken {



    @BeforeClass
    public void setup(){

        baseURI = "https://www.themoviedb.org";


    }

    @Test
    public void test(){

        given()
                .when()
                .get("/login")


                .then()
                .log().all()
                .statusCode(200)


                ;




    }

}
