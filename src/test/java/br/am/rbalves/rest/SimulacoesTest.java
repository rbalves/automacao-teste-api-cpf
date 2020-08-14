package br.am.rbalves.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Test;
import br.am.rbalves.core.*;
import br.am.rbalves.core.factories.MensagemErroFactory;
import br.am.rbalves.core.factories.SimulacaoFactory;
import io.restassured.http.ContentType;

public class SimulacoesTest extends BaseTest {
	
	@Test
	public void deveCadastrarSimulacaoComSucesso() {
		
		Simulacao simulacao = SimulacaoFactory.gerarSimulacaoCadastro();
		
		given()
			.contentType(ContentType.JSON)
			.body(simulacao)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.contentType(ContentType.JSON)
			.body("id", is(notNullValue()))
			.body("nome", is(simulacao.getNome()))
			.body("cpf", is(simulacao.getCpf()))
			.body("email", is(simulacao.getEmail()))
			.body("valor", is((float) (simulacao.getValor())))
			.body("parcelas", is(simulacao.getParcelas()))
			.body("seguro", is(simulacao.isSeguro()))
		;
	}
	
	@Test
	public void deveReportarCadastroComDadosInvalidos() {
		
		Simulacao simulacao = SimulacaoFactory.gerarSimulacaoCadastroDadosInvalidos();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType(ContentType.JSON)
            .body("erros.nome", is("Nome não pode ser vazio"))
            .body("erros.email", is("E-mail não deve ser vazio"))
            .body("erros.cpf", is(MensagemErroFactory.mensagemCpf(simulacao.getCpf())))
            .body("erros.valor", is(MensagemErroFactory.mensagemValor((float) simulacao.getValor())))
            .body("erros.parcelas", is(MensagemErroFactory.mensagemParcelas(simulacao.getParcelas())))
        ;
	}
	
	@Test
	public void deveReportarCadastroComCpfInvalido() {
		
		Simulacao simulacao = SimulacaoFactory.gerarSimulacaoCadastro();
        Simulacao simulacaoCadastrada = SimulacaoFactory.buscarSimulacaoCadastrada();

        simulacao.setCpf(simulacaoCadastrada.getCpf());

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes")
        .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .contentType(ContentType.JSON)
            .body("mensagem", is(MensagemErroFactory.mensagemCpf(simulacao.getCpf())))
        ;
	}
		
	@Test
    public void deveAtualizarSimulacaoComSucesso() {

        List<Simulacao> simulacoes = SimulacaoFactory.buscarSimulacoes();

        Simulacao simulacao = simulacoes.get(0);
        Simulacao simulacaoCadastro = SimulacaoFactory.gerarSimulacaoCadastro();

        simulacao.setNome(simulacaoCadastro.getNome());
        simulacao.setEmail(simulacaoCadastro.getEmail());

        given()
            .pathParam("cpf", simulacao.getCpf())
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .put("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType("application/json; charset=utf-8")
            .body("id", is(notNullValue()))
            .body("nome", is(simulacao.getNome()))
            .body("cpf", is(simulacao.getCpf()))
            .body("email", is(simulacao.getEmail()))
            .body("valor", is((float) simulacao.getValor()))
            .body("parcelas", is(simulacao.getParcelas()))
            .body("seguro", is(simulacao.isSeguro()))
        ;
    }

    @Test
    public void deveReportarAtualizacaoComCpfInexistente() {

        Simulacao simulacao = SimulacaoFactory.buscarSimulacaoRemovida();

        given()
            .pathParam("cpf", simulacao.getCpf())
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .put("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body("mensagem", is("CPF " + simulacao.getCpf() + " não encontrado"))
        ;

    }
	
    @Test
    public void deveListarSimulacoes() {
        given()
        .when()
            .get("/simulacoes")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("$", hasSize(greaterThan(0)))
        ;
    }

    @Test
    public void deveBuscarSimulacaoPorCpfComSucesso() {

        Simulacao simulacao = SimulacaoFactory.buscarSimulacaoCadastrada();

        given()
            .pathParam("cpf", simulacao.getCpf())
        .when()
            .get("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", is(notNullValue()))
            .body("nome", is(simulacao.getNome()))
            .body("cpf", is(simulacao.getCpf()))
            .body("email", is(simulacao.getEmail()))
            .body("valor", is((float) simulacao.getValor()))
            .body("parcelas", is(simulacao.getParcelas()))
            .body("seguro", is(simulacao.isSeguro()))
        ;
    }

    @Test
    public void deveReportarBuscaDeSimulacaoComCpfInexistente() {

        Simulacao simulacao = SimulacaoFactory.buscarSimulacaoRemovida();

        given()
            .pathParam("cpf", simulacao.getCpf())
        .when()
            .get("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body("mensagem", is("CPF " + simulacao.getCpf() + " não encontrado"))
        ;
    }

    @Test
    public void devePesquisarPorNomeComSucesso() {

        List<Simulacao> simulacoes = SimulacaoFactory.buscarSimulacoes();

        Simulacao simulacao = simulacoes.get(0);

        given()
            .queryParam("nome", simulacao.getNome())
        .when()
            .get("/simulacoes")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "[0].nome", is(simulacao.getNome()),
                "[0].cpf", is(simulacao.getCpf()),
                "[0].email", is(simulacao.getEmail()),
                "[0].valor", is((float) simulacao.getValor()),
                "[0].parcelas", is(simulacao.getParcelas()),
                "[0].seguro", is(simulacao.isSeguro())
            )
        ;
    }

    @Test
    public void deveReportarPesquisaPorNomeInexistente() {

        String nome = SimulacaoFactory.buscarNomeInexistente();

        given()
            .queryParam("nome", nome)
        .when()
            .get("/simulacoes")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }
    
    @Test
    public void deveRemoverSimulacaoComSucesso() {

        Simulacao simulacao = SimulacaoFactory.buscarSimulacaoCadastrada();

        given()
            .pathParam("cpf", simulacao.getCpf())
        .when()
            .delete("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

    }

    @Test
    public void deveReportarRemocaoComCpfInexistente() {

        Simulacao simulacao = SimulacaoFactory.buscarSimulacaoRemovida();

        given()
            .pathParam("cpf", simulacao.getCpf())
        .when()
            .delete("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(ContentType.JSON)
            .body("mensagem", is("CPF " + simulacao.getCpf() + " não encontrado"))
        ;

    }

}
