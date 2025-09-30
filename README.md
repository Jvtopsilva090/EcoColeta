# 🌱 EcoColeta

EcoColeta é um sistema simples de **gestão e consulta de pontos de coleta seletiva**, desenvolvido como projeto integrador para aplicar **Engenharia de Requisitos** e **Desenvolvimento de Sistemas Cliente-Servidor em Java**.  

O sistema segue uma arquitetura **cliente-servidor**:  
- **Servidor**: API REST construída em **Spring Boot**.  
- **Cliente**: Aplicação em linha de comando (CLI) que se comunica com a API.  

🔗 Repositório do projeto: [EcoColeta](https://github.com/Jvtopsilva090/EcoColeta)

---

## 📖 Motivação

O descarte inadequado de resíduos sólidos é um dos maiores desafios ambientais enfrentados pelas cidades.  
Apesar da coleta seletiva ser uma estratégia eficaz, muitos cidadãos desconhecem:  
- Onde estão os pontos de coleta mais próximos.  
- Quais tipos de resíduos cada ponto aceita.  

O EcoColeta busca solucionar esse problema ao oferecer uma aplicação simples que:  
- Auxilia os cidadãos a localizar pontos de coleta seletiva.  
- Dá suporte a administradores para cadastrar e atualizar esses pontos.  

---

## 🎯 Objetivos

### Objetivo Geral
Desenvolver um sistema cliente-servidor, simples e funcional, para gerenciar e consultar pontos de coleta seletiva, integrando práticas de engenharia de requisitos e desenvolvimento de software em Java.

### Objetivos Específicos
- Levantar, analisar e documentar requisitos com base em técnicas de engenharia de software.  
- Projetar uma arquitetura cliente-servidor básica em Java.  
- Implementar a comunicação entre cliente e servidor.  
- Refletir sobre o papel da tecnologia na promoção da sustentabilidade ambiental.  

---

## 📌 Requisitos

### Requisitos Funcionais
1. O sistema deve permitir o cadastro de pontos de coleta seletiva.  
2. O sistema deve permitir a atualização de informações de pontos de coleta.  
3. O sistema deve listar todos os pontos de coleta cadastrados.  
4. O sistema deve permitir consultas de pontos de coleta filtradas por tipo de resíduo.  
5. O cliente deve se conectar ao servidor para exibir as informações consultadas.  

### Requisitos Não-Funcionais
1. O sistema deve ser desenvolvido em Java.  
2. O servidor deve expor uma API REST construída com Spring Boot.  
3. O cliente deve ser uma aplicação em CLI que consome a API.  
4. A comunicação entre cliente e servidor deve ser baseada em HTTP.  
5. O sistema não deve exigir persistência em banco de dados (dados podem ser armazenados em memória).  

---

## 🏗️ Arquitetura

O projeto é dividido em dois módulos principais:

- **Servidor (Spring Boot)**  
  - Exposição de endpoints REST para cadastro e consulta de pontos de coleta.  
  - Dados mantidos em memória para simplificação.  

- **Cliente (CLI)**  
  - Interface em linha de comando que consome os endpoints da API.  
  - Usuário pode consultar pontos de coleta e visualizar os resultados no terminal.  

---

## 🚀 Como Executar

### Servidor (API REST)
```bash
cd ecocoleta-api
./mvnw spring-boot:run
```

### Servidor (CLI APPLICATION)
```bash
cd ecocoleta-cli
./mvnw spring-boot:run
```
