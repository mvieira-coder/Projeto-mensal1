# Projeto Locadora de Veículos 🚗🏍️🚤

Sistema de locação de veículos desenvolvido em Java, utilizando **POO (Programação Orientada a Objetos)**, **JPA/Hibernate** e banco de dados **PostgreSQL**. Criado como parte do Projeto Integrador e Mensal do 3º semestre de Análise e Desenvolvimento de Sistemas da UniAmérica Descomplica.

---

## 🛠 Tecnologias Utilizadas

- Java 17
- Maven
- PostgreSQL
- JPA / Hibernate
- IntelliJ IDEA

---
## 📦 Funcionalidades

### ✅ Cadastro e locação
- Cadastro automático de 3 veículos (1 por tipo).
- Cliente escolhe tipo de veículo e aluga por horas.
- Registro de locações com nome do locatário, data e valor total.

### ✅ Devolução
- Permite devolver o veículo e torná-lo disponível novamente.

### ✅ Relatórios
1. **Locações (JOIN)**: lista de locações com nome, veículo e valor.
2. **Filtro por valor total**: exibe locações entre dois valores.
3. **LIKE (nome)**: busca locações por parte do nome do locatário.
4. **Agregados (COUNT / AVG)**: total de locações e média de horas.

### ✅ Extras
- Menu interativo via console
- Tratamento de erros e entradas inválidas
- Opção de voltar ao menu anterior em todos os fluxos

---

## 💽 Banco de Dados

- As tabelas são criadas automaticamente com `hibernate.hbm2ddl.auto = create`
- Os dados iniciais são populados na primeira execução
- O console exibe os comandos SQL gerados automaticamente pelo Hibernate

---

## ▶️ Como Executar

1. Instale o **PostgreSQL** e crie um banco chamado `locadora_veiculos`
2. No `persistence.xml`, ajuste o usuário e senha do banco
3. Execute o projeto no IntelliJ ou via terminal com Maven:
   ```bash
   mvn compile exec:java -Dexec.mainClass="locadora.app.SistemaLocacao"



