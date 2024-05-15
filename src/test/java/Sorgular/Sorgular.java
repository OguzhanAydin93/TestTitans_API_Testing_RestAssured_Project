package Sorgular;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;

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
                .body("status_message",equalTo("The item/record was updated successfully."))

                ;

    }

    @Test(dependsOnMethods = "addMovieToFavorite")
    public void addMovieToWatchlist(){

        Map<String,Object> watchListMovie = new HashMap<>();
        watchListMovie.put("media_type","movie");
        watchListMovie.put("media_id","5de6f6133faba00015133c4d");
        watchListMovie.put("watchlist",true);

        given()
                .spec(reqSpec)
                .body(watchListMovie)

                .when()
                .post(url+"/account/"+accountId+"/watchlist")

                .then()
                .statusCode(201)
                .body("status_message",equalTo("The item/record was updated successfully."))

                ;

    }

    @Test(dependsOnMethods = "addMovieToWatchlist")
    public void favoriteMovies(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/favorite/movies")

                .then()
                .statusCode(200)
                .extract().path("page")

                ;

        Assert.assertTrue(page==1);

    }

    @Test(dependsOnMethods = "favoriteMovies")
    public void favoriteTv(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/favorite/tv")

                .then()
                .statusCode(200)
                .extract().path("page")

        ;

        Assert.assertTrue(page==1);

    }

    @Test(dependsOnMethods = "favoriteTv")
    public void ratedMovie(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/rated/movies")


                .then()
                .statusCode(200)
                .extract().path("page")

        ;

        Assert.assertTrue(page==1);

    }

    @Test(dependsOnMethods = "ratedMovie")
    public void ratedTv(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/rated/tv")

                .then()
                .statusCode(200)
                .extract().path("page")

        ;

        Assert.assertTrue(page==1);


    }

    @Test(dependsOnMethods = "ratedTv")
    public void watchlistMovies(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/watchlist/movies")

                .then()
                .statusCode(200)
                .extract().path("page")

        ;

        Assert.assertTrue(page==1);


    }

    @Test(dependsOnMethods = "watchlistMovies")
    public void watchlistTv(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/account/"+accountId+"/watchlist/tv")

                .then()
                .statusCode(200)
                .extract().path("page")

        ;

        Assert.assertTrue(page==1);



    }

    @Test(dependsOnMethods = "watchlistTv")
    public void moreList(){

        int genres =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/genre/movie/list")

                .then()
                .statusCode(200)
                .extract().path("genres.id[0]")

                ;

        Assert.assertTrue(genres==28);

    }

    @Test(dependsOnMethods = "moreList")
    public void tvList(){

        int genres =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/genre/tv/list")

                .then()
                .statusCode(200)
                .extract().path("genres.id[0]")

                ;

        Assert.assertTrue(genres==10759);


    }

    @Test(dependsOnMethods = "tvList")
    public void nowPlaying(){

        String dates =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/movie/now_playing")

                .then()
                .statusCode(200)
                .extract().path("dates.maximum")

                ;

        Assert.assertEquals(dates,"2024-05-22");



    }

    @Test(dependsOnMethods = "nowPlaying")
    public void popular(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/movie/popular")

                .then()
                .statusCode(200)
                .extract().path("page")

                ;

        Assert.assertTrue(page==1);


    }

    @Test(dependsOnMethods = "popular")
    public void topRated(){

        int page =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/movie/top_rated")

                .then()
                .statusCode(200)
                .extract().path("page")

                ;

        Assert.assertTrue(page==1);
    }

    @Test(dependsOnMethods = "topRated")
    public void upComming(){

        String dates =

        given()
                .spec(reqSpec)

                .when()
                .get(url+"/movie/upcoming")

                .then()
                .statusCode(200)
                .extract().path("dates.minimum")

                ;

        Assert.assertEquals(dates,"2024-05-22");
    }





}
