# Teste de API: Restrições e simulações através do CPF
Projeto de automação desenvolvido para testar API que gerencia restrições e simulações por CPF, que pode ser acessada [aqui](https://backend-treinamento-rest-api-f.herokuapp.com/swagger-ui.html).

## Ferramentas necessárias

Para executar o projeto, é necessário instalar e configurar as seguintes ferramentas:

- [Git](https://git-scm.com/)

- [JDK](https://www.oracle.com/technetwork/java/javase/14-0-1-relnotes-5972653.html)
	
Após a instalação do JDK, foi necessário executar as seguintes etapas:
1. Criar a variável de ambiente JAVA_HOME com o caminho JDK padrão, que, no Windows, geralmente é C: \ Arquivos de Programas \ Java \ <versão do jdk>
2. Editar a variável Path adicionando %JAVA_HOME% \ bin;
3. Confirmar a instalação usando o comando 'java -version' (a versão deve ser informada)

- [Eclipse](https://www.eclipse.org/downloads/packages/)

- [Maven](https://maven.apache.org/download.cgi)
	
Após baixar o arquivo .zip, foi necessário executar as seguintes etapas:
1. Descompactar em uma pasta e copiar o caminho;
2. Criar a variável M2_HOME e incluir o caminho copiado;
3. Editar a variável Path adicionando %M2_HOME% \ bin;
4. Confirmar a instalação usando o comando 'mvn' (mensagens de erro podem aparecer, porém o Maven foi instalado)

## Clonando o projeto
1. Abra o Prompt de Comando (Windows) ou o Terminal (Mac ou Linux)
2. Navegue até um diretório de sua escolha
3. Execute o seguinte comando git clone https://github.com/rbalves/automacao-teste-api-cpf.git
4. Navegue, via Prompt de Comando ou Terminal, até o diretório do projeto automacao-teste-api-cpf
5. Execute o comando mvn compile e aguarde até o término do build
6. Abra a sua IDE de Desenvolvimento
7. Nela, seleciona a opção de importação de projetos para o tipo Maven apontando para a pasta automacao-teste-api-cpf
8. Aguarde sua IDE efetuar todas as configurações necessárias

## Dependências
- REST Assured 4.0.0
- JUnit 4.12
- JavaFaker 1.0.2
- Gson 2.8.5

## Estrutura do projeto
O projeto está estruturado da seguinte forma:
1. **src/main/java**: 
	- No pacote "core", foram adicionadas:
		- a classe "BaseTest", responsável por setar os parâmetros do caminho padrão para as requisições.
		- a classe Simulacao
		- o pacote factories, com as classes de fabricação.
2. **src/test/java**: 
	- No pacote "rest", foram adicionadas duas classes com seus respectivos testes:
		- RestricoesTest
			- deveValidarCPFComRestricao
			- deveValidarCPFSemRestricao
		- SimulacoesTest
			- deveCadastrarSimulacaoComSucesso
			- deveReportarCadastroComDadosInvalidos
			- deveReportarCadastroComCpfInvalido
			- deveAtualizarSimulacaoComSucesso
			- deveReportarAtualizacaoComCpfInexistente
			- deveListarSimulacoes
			- deveBuscarSimulacaoPorCpfComSucesso
			- deveReportarBuscaDeSimulacaoComCpfInexistente
			- devePesquisarPorNomeComSucesso
			- deveReportarPesquisaPorNomeInexistente
			- deveRemoverSimulacaoComSucesso
			- deveReportarRemocaoComCpfInexistente



## Executando os testes

Para executar os testes, é necessário abrir o terminal na raiz do projeto e executar o comando a seguir:
```shell
mvn test	
```
