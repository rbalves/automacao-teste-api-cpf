package br.am.rbalves.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.javafaker.Faker;

import br.am.rbalves.core.BaseTest;

public class SimulacoesTest extends BaseTest {
	
	@Test
	public void criarSimulacaoDadosValidos() {
		
		Faker faker = new Faker();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", faker.name().firstName());
		params.put("cpf", Long.toString(faker.number().randomNumber(11, true)));
		params.put("email", params.get("nome").toString().toLowerCase() + "@mail.com");
		params.put("valor", faker.number().randomDouble(2, 1000, 40000));
		params.put("parcelas", faker.number().numberBetween(2, 48));
		params.put("seguro", faker.bool().bool());
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(201)
			.contentType("application/json; charset=utf-8")
			.body("id", is(notNullValue()))
			.body("nome", is(params.get("nome")))
			.body("cpf", is(params.get("cpf")))
			.body("email", is(params.get("email")))
			.body("valor", is(Float.parseFloat(params.get("valor").toString())))
			.body("parcelas", is(params.get("parcelas")))
			.body("seguro", is(params.get("seguro")))
		;
	}
	
	@Test
	public void criarSimulacaoDadosInvalidos() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", "");
		params.put("cpf", "");
		params.put("email", "");
		params.put("valor", 0);
		params.put("parcelas", 1);
		params.put("seguro", false);
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(400)
			.contentType("application/json; charset=utf-8")
			.body("erros.nome", is(notNullValue()))
			.body("erros.cpf", is(notNullValue()))
			.body("erros.email", is(notNullValue()))
			.body("erros.valor", is(notNullValue()))
			.body("erros.parcelas", is(notNullValue()))

		;
	}
	
	@Test
	public void criarSimulacaoCpfExistente() {
		
		Faker faker = new Faker();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", faker.name().firstName());
		params.put("cpf", "66414919004");
		params.put("email", params.get("nome").toString().toLowerCase() + "@mail.com");
		params.put("valor", faker.number().randomDouble(2, 1000, 40000));
		params.put("parcelas", faker.number().numberBetween(2, 48));
		params.put("seguro", faker.bool().bool());
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(409)
			.contentType("application/json; charset=utf-8")
			.body("mensagem", is("CPF já existente"))
		;
	}
	
	@Test
	public void alterarSimulacaoDadosValidos() {
		
		Faker faker = new Faker();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", faker.name().firstName());
		params.put("cpf", "66414919004");
		params.put("email", params.get("nome").toString().toLowerCase() + "@mail.com");
		params.put("valor", faker.number().randomDouble(2, 1000, 40000));
		params.put("parcelas", faker.number().numberBetween(2, 48));
		params.put("seguro", faker.bool().bool());
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.put("/simulacoes/" + params.get("cpf"))
		.then()
			.statusCode(200)
			.contentType("application/json; charset=utf-8")
			.body("id", is(notNullValue()))
			.body("nome", is(params.get("nome")))
			.body("cpf", is(params.get("cpf")))
			.body("email", is(params.get("email")))
			.body("valor", is(Float.parseFloat(params.get("valor").toString())))
			.body("parcelas", is(params.get("parcelas")))
			.body("seguro", is(params.get("seguro")))
		;
	}
	
	@Test
	public void alterarSimulacaoCpfInexistente() {
		
		Faker faker = new Faker();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", faker.name().firstName());
		params.put("cpf", "00000000000");
		params.put("email", params.get("nome").toString().toLowerCase() + "@mail.com");
		params.put("valor", faker.number().randomDouble(2, 1000, 40000));
		params.put("parcelas", faker.number().numberBetween(2, 48));
		params.put("seguro", faker.bool().bool());
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.put("/simulacoes/" + params.get("cpf"))
		.then()
			.statusCode(404)
			.contentType("application/json; charset=utf-8")
			.body("mensagem", is("CPF " + params.get("cpf") + " não encontrado"))
		;
	}
	
	@Test
	public void alterarSimulacaoCpfExistente() {
		
		Faker faker = new Faker();

		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nome", faker.name().firstName());
		params.put("cpf", "17822386034");
		params.put("email", params.get("nome").toString().toLowerCase() + "@mail.com");
		params.put("valor", faker.number().randomDouble(2, 1000, 40000));
		params.put("parcelas", faker.number().numberBetween(2, 48));
		params.put("seguro", faker.bool().bool());
		
		String cpfAtual = "66414919004";
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.put("/simulacoes/" + cpfAtual)
		.then()
			.statusCode(409)
			.contentType("application/json; charset=utf-8")
			.body("mensagem", is("CPF já existente"))
		;
	}
	
	@Test
	public void consultarSimulacoes() {
		given()
		.when()
			.get("/simulacoes")
		.then()
			.statusCode(200)
			.body("$", hasSize(greaterThan(0)))
		;
	}
	
	@Test
	public void consultarSimulacoesRetornoVazio() {
		given()
		.when()
			.get("/simulacoes")
		.then()
			.body("$", hasSize(greaterThan(0)))
			.statusCode(204)
		;
	}
	
	@Test
	public void consultarSimulacaoPorCpf() {
		
		int id = 12;
		String nome = "Deltrano";
		String cpf = "17822386034";
		String email = "deltrano@gmail.com";
		double valor = 20000.00;
		int parcelas = 5;
		boolean seguro = false;
		
		given()
		.when()
			.get("/simulacoes/" + cpf)
		.then()
			.statusCode(200)
			.body("id", is(id))
			.body("nome", is(nome))
			.body("cpf", is(cpf))
			.body("email", is(email))
			.body("valor", is((float) valor))
			.body("parcelas", is(parcelas))
			.body("seguro", is(seguro))
		;
	}
	
	@Test
	public void consultarSimulacaoInexistente() {
	
		String cpf = "00000000000";
		
		given()
		.when()
			.get("/simulacoes/" + cpf)
		.then()
			.statusCode(404)
			.body("mensagem", is("CPF " + cpf + " não encontrado"))
		;
	}
	
	@Test
	public void removerSimulacao() {
		
		int id = 12;
		
		given()
		.when()
			.delete("/simulacoes/" + id)
		.then()
			.log().all()
			.statusCode(200)
		;
	}

}
