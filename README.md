
# Golden Raspeberry Awards - Winners [![CodeFactor](https://www.codefactor.io/repository/github/germanorenner/api_texo_it/badge)](https://www.codefactor.io/repository/github/germanorenner/api_texo_it)

Este projeto se resume em uma API que tem o intuito de demonstrar os produtores ganhadores do prêmio em questão e classificar o período de anos entre os maiores vencedores.

## Tecnologias :computer:
>* Java 17
>* Spring Boot 2.7
>* Gradle 7.0
>* Swagger
>* H2 Database
>* Kubernetes

## Configurações Ambiente :wrench:
1. Clone o Projeto em um diretório local.
2. Baixar e instalar a JDK 17 e o Eclipse
    * JDK 17  - (https://www.oracle.com/br/java/technologies/javase/jdk17-archive-downloads.html) 
    * Eclipse - (https://www.eclipse.org/downloads/)
3. Agora basta abrir o eclipse e importar o projeto.

## Funcionamento :gear:
Ao realizar a inicialização da API uma base inicial (resources/dataset.csv) é carregada em um banco de dados em memória (H2 Database). É disponibilizado alguns endpoints para fazer o CRUD dos filmes manualmente **(Acesso local: http://localhost:9180/api/swagger-ui.html | Acesso Remoto: http://34.121.35.215:9180/api/swagger-ui.html)**. <br>
Além da manipulação dos filmes também existe um funcionalidade para **IMPORTAÇÃO** de uma base em formato csv, que faz uma carga completa nos dados da base.

**@ Detalhes da Importação:**
Foi desenvolvido um Helper que realiza a importação dessa nova base de dados. Por se tratar de um projeto simples, tal importação é limitada a 500 linhas de registro no arquivo que será importado. Para a importação da entidade *Movies* o arquivo deve seguir a seguinte estrutura:
````
year;title;studios;producers;winner
1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
1980;Cruising;Lorimar Productions, United Artists;Jerry Weintraub;
````

Onde a primeira linha representa o cabeçalho obrigatoriamente.<br>
**OBS:** Esta importação não contempla regras de negócio, como por exemplo: validação para títulos duplicados. **E a cada nova importação a base de dados antiga é substituída pelo novo arquivo importado.**

## Execução :fast_forward:
**Para execução/utilização localmente do projeto foi disponibilizado um .jar do projeto na pasta raiz. Para executar siga os comandos abaixo:**
*Execução do Projeto:*
```java
java -jar texoit-api-1.0.jar // Jar comitado apenas para testes.
```

*Build do Projeto via gradle:*
```
./gradlew build -x test
```

*Execução dos Testes de Integração via gradle: *
```java
./gradlew test
```
**OBS:** Para as execuções é necessário o ambiente configurado com Java 17 e Gradle 7.4 .

**A API também se encontra online no seguinte endereço: *http://34.121.35.215:9180/api/swagger-ui.html***
