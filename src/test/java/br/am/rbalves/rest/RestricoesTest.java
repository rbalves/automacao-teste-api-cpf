package br.am.rbalves.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import org.apache.http.HttpStatus;
import io.restassured.http.ContentType;

import org.junit.Test;

import br.am.rbalves.core.BaseTest;
import br.am.rbalves.core.factories.RestricaoFactory;

public class RestricoesTest extends BaseTest {

	@Test
    public void deveValidarCPFComRestricao() {

        String cpf = RestricaoFactory.buscarCpfComRestricao();

        given()
            .pathParam("cpf", cpf)
        .when()
            .get("/restricoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("mensagem", is("O CPF " + cpf + " possui restrição"))
        ;
    }

    @Test
    public void deveValidarCPFSemRestricao() {

        String cpf = RestricaoFactory.buscarCpfSemRestricao();

        given()
            .pathParam("cpf", cpf)
        .when()
            .get("/restricoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }
	
}
