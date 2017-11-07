# SpringBoot-API-RESTFull-OAuth2-JWT

> Back-end Java feito com Spring Boot e suas principais starters para criação de API REST e persistência com JPA. Controle de segurança dos recursos com OAuth2 + JWT


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

### Obtendo o token

`https://algamoneyapp-api.herokuapp.com/oauth/token`

#### Headers

| chave         | Valor         |
| ------------- | ------------- |
| Content-Type  | application/x-www-form-urlencoded  |
| Authorization  | Basic YW5ndWxhcjpAbmd1bEByMA==  |

#### Body

| chave         | Valor         |
| ------------- | ------------- |
| client  | angular  |
| username  | admin@algamoney.com  |
| password  | admin  |
| grant_type  | password  |


#### Response

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTAwODQzMjQsInVzZXJfbmFtZSI6ImFkbWluQGFsZ2Ftb25leS5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0NBREFTVFJBUl9DQVRFR09SSUEiLCJST0xFX1BFU1FVSVNBUl9QRVNTT0EiLCJST0xFX1JFTU9WRVJfUEVTU09BIiwiUk9MRV9DQURBU1RSQVJfTEFOQ0FNRU5UTyIsIlJPTEVfUEVTUVVJU0FSX0xBTkNBTUVOVE8iLCJST0xFX1JFTU9WRVJfTEFOQ0FNRU5UTyIsIlJPTEVfQ0FEQVNUUkFSX1BFU1NPQSIsIlJPTEVfUEVTUVVJU0FSX0NBVEVHT1JJQSJdLCJqdGkiOiJhYzNlNWVjYi0wMjMzLTQ4MzItYWIwOC03ZWZkNmZhZjNkN2EiLCJjbGllbnRfaWQiOiJhbmd1bGFyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.f_N0-HyUTtjMR0rKaxwyWrXGH7mB9wdkzeVs_iyX_G8",
    "token_type": "bearer",
    "expires_in": 1799,
    "scope": "read write",
    "jti": "ac3e5ecb-0233-4832-ab08-7efd6faf3d7a"
}
```

> O Refresh Token é devolvido como um cookie, chamado 'refreshToken'.

### Renovando o token

`https://algamoneyapp-api.herokuapp.com/oauth/token`

#### Headers

| chave         | Valor         |
| ------------- | ------------- |
| Content-Type  | application/x-www-form-urlencoded  |
| Authorization  | Basic YW5ndWxhcjpAbmd1bEByMA==  |

#### Body

| chave         | Valor         |
| ------------- | ------------- |
| grant_type  | refresh_token  |
| refresh_token  | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJmMGZhZTNjZi00Y2MxLTRhYzItYTQzMS1jYTdjYzY0YzA5YWMiLCJleHAiOjE1MDgyNDQyNjQsImF1dGhvcml0aWVzIjpbIlJPTEVfUk9MRSJdLCJqdGkiOiJjMzcyNDY1Yy1kNDFkLTRjMDAtYmE4Yi01MGQ1OGM2MzFiNWMiLCJjbGllbnRfaWQiOiJhbmd1bGFyIn0.3MMwWjjSVFf2wgWq7Pe_-I2kepZVVsyLF0rCBSU1yhY  |

#### Response

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTAwOTc3MTksInVzZXJfbmFtZSI6ImFkbWluQGFsZ2Ftb25leS5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0NBREFTVFJBUl9DQVRFR09SSUEiLCJST0xFX1BFU1FVSVNBUl9QRVNTT0EiLCJST0xFX1JFTU9WRVJfUEVTU09BIiwiUk9MRV9DQURBU1RSQVJfTEFOQ0FNRU5UTyIsIlJPTEVfUEVTUVVJU0FSX0xBTkNBTUVOVE8iLCJST0xFX1JFTU9WRVJfTEFOQ0FNRU5UTyIsIlJPTEVfQ0FEQVNUUkFSX1BFU1NPQSIsIlJPTEVfUEVTUVVJU0FSX0NBVEVHT1JJQSJdLCJqdGkiOiIwMTRjOTNjNy05ZTM2LTRlYjUtYjYzNi1jZWQyMDg1YWFiOTIiLCJjbGllbnRfaWQiOiJhbmd1bGFyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.LpxKD9DFtO83TQPEZXXVISZBYaWmpSeObjA0cmw113A",
    "token_type": "bearer",
    "expires_in": 1799,
    "scope": "read write",
    "jti": "014c93c7-9e36-4eb5-b636-ced2085aab92"
}
```


## Demo

Através do endereço abaixo é possível ter uma demonstração de uma aplicação front-end feita em angular que consome esta API.

>https://algamoneyapp-ui.herokuapp.com/

Para mais informações acesse `https://github.com/TomasAzevedo/Angular-OAuth-JWT`.


## Direitos



Esta aplicação foi desenvolvido no curso "Angular, REST e Spring Boot" da Algaworks. Para mais informações visite: `http://www.algaworks.com`.


