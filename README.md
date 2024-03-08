<h1 align="center">UNIMPACT</h1>


<h2 align="center">💻Tecnologias Utilizadas </h2>
<p align="center">
<a href="https://docs.spring.io/spring-boot/docs/2.5.3.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.5.3-brightgreen.svg"/></a>
<!-- <a href="https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MachineResearchGroup/SoftwareLab-API&amp;utm_campaign=Badge_Grade"><img src="https://app.codacy.com/project/badge/Grade/cf253d76b9fa4d4887191a74c6bc30a9"/></a> -->
<a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="Java" src="https://img.shields.io/badge/Java-17-orange.svg"/></a>
<a href="https://maven.apache.org/"><img alt="Maven" src="https://img.shields.io/badge/Maven-4.0.0-yellowgreen.svg"/></a>
<a href="https://maven.apache.org/"><img alt="Maven" src="https://img.shields.io/badge/PostgreSQL-blue.svg"/></a>
<a href="https://reactjs.org/"><img alt="React" src="https://badges.aleen42.com/src/react.svg"/></a>
</p>

### 📁 Como baixar / configurar o projeto

``` 
# Clone o repositório do projeto
$ git clone https://github.com/c3-disciplina-pd/projeto-da-disciplina-unimpact.git

Apos clonar altere as seguintes informações no application-dev.properties
spring.datasource.url (nome do banco de dados e porta utilizada)
spring.datasource.username (usuário do banco de dados)
spring.datasource.password (senha do banco de dados)

# Instalar as dependências
$ maven install 
```

### 🐋 Como criar a imagem do Docker

1. Criando o container
   
   a. Abrir no terminal a pasta em que se encontra o docker-compose:
    ```sh
    cd /docker
    ```
   b. Rodar o comando:
   ```sh
    docker-compose up
    ```
2. Crie uma conexão no [DBeaver]
    ```sh
    ##Infos
    host=localhost
    database=unimpact
    port=5438
    ```
3. Acesse a aplicação pelo link ```http://localhost:3000```

OBS.: O host do banco de dados precisa estar igual ao nome do serviço definido no docker-compose.yaml

Contas e Senhas:
https://github.com/c3-disciplina-pd/projeto-da-disciplina-unimpact/pull/21

História do usuário:
https://github.com/c3-disciplina-pd/projeto-da-disciplina-unimpact/pull/17


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. 
There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[DBeaver]: <https://dbeaver.io/download/>
