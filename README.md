# Desafio CBYK Gestão de Tarefas

## Detalhemento do desenvolvimento
- Foi criado uma aplicação MVC;
- A parte de autenticação e autorização está utilizando token jwt, aqui um 
ponto importante quando é executada a requisição de login tem como resposta o token para ser utilizado 
e o usuarioId (algumas requisições faz uso do id do usuário).
- URI que não precisa da execução do login:
  - "/api/v1/login"; 
  - "/api/v1/usuarios"; 
  - "/h2-console/**"; -> banco de dados
  - "/swagger-ui/**". -> doc
- Para o processo de logout no caso de estarmos em uma aplicação de teste, foi criado
uma black list para os tokens e quando o usuário realiza o logout o token é salvo nessa black list
e após isso quando o usuário tenta executar algo com esse token da black list o mesmo não é permitido.
- Ao criar um usuário é retornado no header/location o recurso para acessá-lo, ex:
  - http://localhost:8080/api/v1/usuarios/1
  - a mesma coisa ocorre quando se é criado uma tarefa
- No package config estão todas as configurações da aplicação: documentação, handler de exception, segurança e versionamento de api.
- Para criação de alguns objetos (Tarefa e Usuário) foi utilizado o pattern Builder.
- Os testes de unidade foram feitos para as classes TarefaService e UsuarioService.
  - Foram utilizadas técnicas como ArgumentCaptor para validar dados de entidades que foram salvos;
  - assertThrows para validar exceptions.
  - libs usadas Junit e Mockito.
- Os Controladores estão anotados com @RestController, e no possível tentei ao máximo faze bom uso de conceitos rest como:
  - tendo um retorno padronizado com uso do ResponseEntity do spring;
  - voltando os status corretos para cada tipo de requisição;
  - usar os verbos HTTP de forma correta;
  - conter informação de versão da api na rota.


## Pré-requisitos
Para executar e construir este projeto, as seguintes dependências devem ser instaladas:

* Windows, Linux or macOS

* Java 21 JDK (https://www.oracle.com/br/java/technologies/downloads/#java21)

Verifique se o java está instalado usando o terminal:

```java -version```

Deve gerar algo como:

```
java version "21.0.3" 2024-04-16 LTS
Java(TM) SE Runtime Environment (build 21.0.3+7-LTS-152)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.3+7-LTS-152, mixed mode, sharing)
```

* Maven 3.x (https://maven.apache.org/download.cgi)

Extraia o maven para uma pasta e adicione-o à sua variável de ambiente PATH.

```set PATH=%PATH%;installdir\apache-maven-3.x.x\bin```

Verifique se o maven está instalado usando o terminal:

```mvn --version```

Deve gerar algo como:


```
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
Maven home: /opt/homebrew/Cellar/maven/3.9.6/libexec
Java version: 21.0.2, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/21.0.2/libexec/openjdk.jdk/Contents/Home
Default locale: en_BR, platform encoding: UTF-8
OS name: "mac os x", version: "14.4.1", arch: "aarch64", family: "mac"
```
Obs.: Algumas informação podem divergir por conta do sistema operacional.
O exemplo acima foi tirado do mac os.


## Develop

Para subir a aplicação

```
cd gerenciamentodetarefas
mvn spring-boot:run
```

Para executar testes via terminal
```
mvn test
```

Com a aplicação de pé você consegui acessar a documentação:

```http://localhost:8080/swagger-ui/index.html```

Acessar  banco de dados:

```http://localhost:8080/h2-console/login.jsp```