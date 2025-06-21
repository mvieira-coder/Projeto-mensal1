# Projeto Mensal 4 – Locadora de Veículos

Este repositório contém o código-fonte da aplicação Java Swing para gerenciamento de locadora de veículos, com persistência em PostgreSQL via JPA/Hibernate.

## Instruções de compilação e execução

1. **Compilar e empacotar**

   ```bash
   cd LocadoraVeiculos
   mvn clean package
   ```

   O comando acima gera o arquivo JAR em:

   ```text
   LocadoraVeiculos/target/projeto-mensal1-1.0.jar
   ```

2. **Executar o JAR**

   ```bash
   java -jar LocadoraVeiculos/target/projeto-mensal1-1.0.jar
   ```

> Caso o JAR não seja executável diretamente, utilize:
>
> ```bash
> java -cp LocadoraVeiculos/target/projeto-mensal1-1.0.jar locadora.app.Main
> ```
