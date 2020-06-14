# Teste de API de restrições e simulações através do CPF
Projeto de automação desenvolvido para testar API que gerencia restrições e simulações por CPF.

## Preparando o ambiente

Para executar o projeto, foi necessário instalar e configurar os seguintes programas:

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

## Criando o projeto
Um projeto Maven foi criado a partir do Eclipse. 
As dependências são gerenciadas através do arquivo pom.xml.
Os trechos XML das dependências foram consultados no [MVNRepository](https://mvnrepository.com/).

## Dependências
- REST Assured 4.0.0
- JUnit 4.12
- JavaFaker 1.0.2
- Gson 2.8.5

## Estrutura do projeto
O projeto está estruturado da seguinte forma:
1. **src/test/java**: neste local, foram criados dois pacotes: "core" e "rest".
	- No pacote "core", foi adicionada a classe "BaseTest", responsável por setar os parâmetros do caminho padrão para as requisições. 
	- No pacote "rest", foram adicionadas duas classes com seus respectivos testes:
		- RestricoesTest
			- verificarCPFSemRestricao
			- verificarCPFsComRestricao
		- SimulacoesTest
			- criarSimulacaoDadosValidos
			- criarSimulacaoDadosInvalidos
			- criarSimulacaoCpfExistente
			- alterarSimulacaoDadosValidos
			- alterarSimulacaoCpfInexistente
			- alterarSimulacaoCpfExistente
			- consultarSimulacoes
			- consultarSimulacoesRetornoVazio
			- consultarSimulacaoPorCpf
			- consultarSimulacaoInexistente
			- removerSimulacao

## Executando os testes
Para executar os testes, é necessário abrir o terminal na raiz do projeto e executar o comando, conforme exemplicado a seguir:
```shell
mvn -Dtest=<classe>#<teste> test	
```

## Possíveis falhas encontradas
Ao executar os testes desenvolvidos, conforme orientações na documentação da API, foram detectadas as seguintes falhas:
1. ao tentar cadastrar uma simulação com CPF já existente:
	- era esperado o retorno do status code 409, porém retornou 400.
	- era esperada a mensagem "CPF já existente", porém retornou "CPF duplicado"
2. ao tentar cadastrar uma simulação com dados todos obrigatórios não informados, 
não são retornadas mensagens de erro para os atributos "nome", "cpf" e "valor", conforme era esperado, segundo as regras.
3. ao tentar atualizar uma simulação, o atributo "valor" não é atualizado.
