package Sorgular;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Sorgular {

    String authenticity_token;
    RequestSpecification reqSpec;

    String url = "https://api.themoviedb.org/3";

    int accountId = 0;



    @BeforeClass
    public void setup(){

        baseURI = "https://www.themoviedb.org";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ODFjZWRlY2MwNmFhYTA1NjM1MDUyY2UzMWUyMzc5MSIsInN1YiI6IjY2MzUxODczYWY0MzI0MDEyMjU0YmMxOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cD8BdV1YfT7WsAr2EFviH9YpAmn6NVkKjQPL44oQw54")
                .setContentType(ContentType.JSON)
                .build();

    }

//    @Test
//    public void LoginToken(){
//
//        authenticity_token =
//
//        given()
//                .when()
//                .get("/login")
//
//
//                .then()
//                .statusCode(200)
//                .extract().response().htmlPath().prettyPrint().substring(27436,27480)
//
//                ;
//
//        System.out.println("authenticity_token = " + authenticity_token);
//
//
//    }

    @Test
    public void getAccountDetails(){


        accountId =
        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("id")

                ;

        System.out.println("id = " + accountId);


    }

    @Test(dependsOnMethods = "getAccountDetails")
    public void addMovieToFavorite(){

        Map<String,Object> favoriteMovie =new HashMap<>();
        favoriteMovie.put("media_type","movie");
        favoriteMovie.put("media_id","5de6f6133faba00015133c4d");
        favoriteMovie.put("favorite",true);

        given()
                .spec(reqSpec)
                .body(favoriteMovie)

                .when()
                .post(url+"/account/"+accountId+"/favorite")

                .then()
                .log().body()
                .statusCode(201)

                ;

    }

    @Test(dependsOnMethods = "addMovieToFavorite")
    public void AddMovieToWatchlist(){

        Map<String,Object> watchListMovie = new HashMap<>();
        watchListMovie.put("media_type","movie");
        watchListMovie.put("media_id","5de6f6133faba00015133c4d");
        watchListMovie.put("watchlist",true);

        given()
                .spec(reqSpec)
                .body(watchListMovie)

                .when()
                .get(url+"/account/"+accountId+"/watchlist")

                .then()
                .statusCode(201)

                ;

    }



}
