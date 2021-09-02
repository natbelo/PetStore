//1-Pacote
package petstore;

//2-Bibliotecas


import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

//3-Classe
public class Pet {
    //3.1-Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade Pet


    //3.2-Métodos e Funções
    public String lerJson(String caminhoJson) throws Exception{

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));

    }

    //Incluir - Create - Post
    @Test(priority = 1)//Identifica o método de ou função como um teste para o TesteNG
    public void incluirPet() throws Exception {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin

        given()
                .contentType("application/json") //comum em API REST
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Star"))
                .body("status", is("available"))
                .body("category.name", is("AX3254CRUD"))
                .body("tags.name", contains("sta"))
        ;

    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "201925062021";

        String token=
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Star"))
                .body("category.name",is("AX3254CRUD"))
                .body("status",is("available"))
        .extract()
                .path("category.name")

        ;
        System.out.println("O token é " + token);
    }

    @Test(priority = 3)
    public void alterarPet() throws Exception {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Star"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void excluirPet(){
        String petId = "201925062021";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code",is(200))
                .body("type",is("unknown"))
                .body("message", is(petId))
        ;
    }

    @Test
    public void consultarPetStatus(){
        String status = "available";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/findByStatus?status=" + status)
        .then()
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Star")))
        ;

    }



}
