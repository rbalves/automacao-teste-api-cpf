package br.am.rbalves.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.am.rbalves.core.BaseTest;

public class RestricoesTest extends BaseTest {

	private String[] cpfsComRestricao = { "97093236014", "60094146012", "84809766080", "62648716050", "26276298085",
			"01317496094", "55856777050", "19626829001", "24094592008", "58063164083" };

	private String[] cpfsSemRestricao = { "99999999999" };

	public void verificarCPFComRestricao(String cpf) {
		given()
		.when()
			.get("/restricoes/" + cpf)
		.then()
			.statusCode(200)
			.contentType("application/json; charset=utf-8")
			.body("mensagem", is("O CPF " + cpf + " tem problema"))
		;
	}

	public void verificarCPFSemRestricao(String cpf) {
		given()
		.when()
			.get("/restricoes/" + cpf)
		.then()
			.statusCode(204)
		;
	}

	@Test
	public void verificarCPFsComRestricao() {
		for (String cpf : cpfsComRestricao) {
			verificarCPFComRestricao(cpf);
		}
	}

	@Test
	public void verificarCPFsSemRestricao() {
		for (String cpf : cpfsSemRestricao) {
			verificarCPFSemRestricao(cpf);
		}
	}
}
