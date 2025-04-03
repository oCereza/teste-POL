# Teste-POL

Este é um projeto Spring Boot para gerenciar processos. Ele inclui funcionalidades como salvar, listar, buscar, atualizar e deletar processos.

## Estrutura do Projeto

- **Backend**: Desenvolvido em Java com Spring Boot.
- **Banco de Dados**: Configurado no arquivo `application.properties` (localizado em `src/main/resources`).
- **Testes**: Testes unitários estão localizados em `src/test/java`.

## Como executar o projeto

1. Clone o repositório:

   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd teste-POL
   ```

2. Configure o banco de dados no arquivo application.properties:

    O arquivo está localizado em `src/main/resources/application.properties`.
   
    Atualize as seguintes propriedades para corresponder ao seu ambiente local:

    Por exemplo:

     ```bash
    spring.datasource.url=jdbc:postgresql://<SEU_HOST>:<SUA_PORTA>/<SEU_BANCO>
    spring.datasource.username=<SEU_USUARIO>
    spring.datasource.password=<SUA_SENHA>
     ```
3. Inicialize o banco de dados:

4. Compile o projeto:

   ```bash
   ./mvn clean install
   ```

5. Execute o projeto:

   ```bash
   ./mvn spring-boot:run
   ```

6. Acesse a aplicação:

   O servidor estará disponível em `http://localhost:8080`.

## Testes

Para executar os testes, use o comando:

```bash
./mvn test
```

