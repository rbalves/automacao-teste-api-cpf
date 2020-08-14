package br.am.rbalves.core.factories;

import com.github.javafaker.Faker;

import br.am.rbalves.core.Simulacao;

import org.apache.http.HttpStatus;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import java.util.List;

public class SimulacaoFactory {
	
	public static Simulacao gerarSimulacaoCadastro() {

		Faker faker = new Faker();
		
		Simulacao simulacao = new Simulacao();
		simulacao.setNome(faker.name().firstName());
		simulacao.setCpf(Long.toString(faker.number().randomNumber(11, true)));
		simulacao.setEmail(simulacao.getNome().toLowerCase() + "@mail.com");
		simulacao.setValor(faker.number().randomDouble(2, 1000, 40000));
		simulacao.setParcelas(faker.number().numberBetween(2, 48));
		simulacao.setSeguro(faker.bool().bool());
		
		return simulacao;
		
    }
	
	public static Simulacao gerarSimulacaoCadastroDadosInvalidos() {
        Faker faker = new Faker();

        int opcaoValor = faker.number().numberBetween(0, 2);
        int opcaoParcelas = faker.number().numberBetween(0, 2);
		
		Simulacao simulacao = new Simulacao();
		simulacao.setNome(null);
		simulacao.setCpf(null);
		simulacao.setEmail(null);
		simulacao.setValor(buscarValorInvalido(opcaoValor));
		simulacao.setParcelas(buscarNumeroParcelasInvalido(opcaoParcelas));
		simulacao.setSeguro(faker.bool().bool());
		
		return simulacao;
    }
	
	public static Float buscarValorInvalido(int opcao) {
        Faker faker = new Faker();
        return opcao == 0
            ? (float) faker.number().randomDouble(2, 0, 999)
            : (float) faker.number().randomDouble(2, 40001, 100000);
    }

    public static int buscarNumeroParcelasInvalido(int opcao) {
        Faker faker = new Faker();
        return opcao == 0
            ? faker.number().numberBetween(0, 1)
            :faker.number().numberBetween(48, 100);
    }
    
    public static Simulacao buscarSimulacaoCadastrada() {

        Simulacao simulacao = gerarSimulacaoCadastro();

        return
                given()
                    .contentType(ContentType.JSON)
                    .body(simulacao)
                .when()
                    .post("/simulacoes")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .body()
                    .as(Simulacao.class)
                ;
    }
    
    public static List<Simulacao> buscarSimulacoes() {
        return
                given()
                .when()
                    .get("/simulacoes")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", Simulacao.class)
                ;

    }
    
    public static Simulacao buscarSimulacaoRemovida() {

        Simulacao simulacao = buscarSimulacaoCadastrada();

        given()
            .pathParam("cpf", simulacao.getCpf())
        .when()
            .delete("/simulacoes/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        return simulacao;

    }
    
    public static Boolean verificarSeNomeExiste(String nome) {
        List<Simulacao> simulacoes = buscarSimulacoes();
        for (Simulacao simulacao : simulacoes) {
            if (simulacao.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public static String buscarNomeInexistente() {

        Faker faker = new Faker();
        String nome = faker.name().firstName();

        while (verificarSeNomeExiste(nome)) {
            nome = faker.name().firstName();
        }

        return nome;
    }

}
