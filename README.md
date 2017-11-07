# SpringBoot-API-RESTFull-OAuth2-JWT
> Back-end Java feito com Spring Boot e suas principais starters para criação de API REST e persistência com JPA. Controle de segurança > dos recursos com OAuth2 + JWT

API feita para cadastro de recursos como lançamentos e pessoas de uma aplicação que gerancia lançamentos financeiros (receitas e despesas).

## Dependências

- Java 8
- Apache Maven
- MySQL
- Postman (opcional)

## Iniciando a aplicação

Para rodar a aplicação de maneira local é preciso ter um banco de dados MySQL rodando conforme o arquivo /algamoney-api/src/main/resources/application.properties

```properties
spring.jpa.database=MYSQL
spring.jpa.show-sql=true

spring.datasource.url=jdbc:mysql://localhost:3306/algamoneyapi?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

spring.jackson.deserialization.fail-on-unknown-properties=true
spring.jackson.date-format=yyyy-MM-dd

spring.profiles.active=oauth-security
```

Com o banco configurado corretamente é preciso rodar a classe com.algaworks.algamoney.api.AlgamoneyApiApplication

```java
package com.algaworks.algamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.algaworks.algamoney.api.config.property.AlgaMoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(AlgaMoneyApiProperty.class)
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamoneyApiApplication.class, args);
	}
}
```

## Exemplos de uso

### Recursos

Os recursos que a API disponibiliza são:

- categorias
- pessoas
- lancamentos

### URL

`https://algamoneyapp-api.herokuapp.com`





