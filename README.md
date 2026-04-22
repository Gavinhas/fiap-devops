## 🚀 Stack Tecnológica & DevOps
Este projeto foi desenvolvido aplicando conceitos de **CI/CD**, **Containerização** e **Cloud Computing**, conforme os requisitos do checklist:

* **Runtime:** Java 21 + Spring Boot 3.
* **Banco de Dados:** PostgreSQL (Docker local / Azure SQL em Staging).
* **Containerização:** Docker e Docker Compose para isolamento de ambiente.
* **CI/CD:** GitHub Actions com automação de Build, Test e Deploy.
* **Cloud:** Azure App Service para hospedagem dos ambientes.

### 🔄 Pipeline de CI/CD (GitHub Actions)

O arquivo [`.github/workflows/mural-esg-social-ci.yml`](.github/workflows/mural-esg-social-ci.yml) implementa um fluxo completo de entrega contínua:

1.  **Maven Verify:** Execução de testes unitários e build do artefato `.jar`.
2.  **Docker Build:** Criação da imagem Docker do projeto para garantir a portabilidade.
3.  **Continuous Deployment (CD):** Deploy automatizado para o **Azure App Service** assim que o código é aprovado na branch `main`.

> **Nota:** As credenciais de acesso à Azure (Publish Profile) e as chaves do banco de dados estão configuradas como **GitHub Secrets**, seguindo as melhores práticas de **DevSecOps**.

## ☁️ Infraestrutura na Nuvem (Azure)

Para atender ao requisito de **Isolamento de Ambientes**, a aplicação foi configurada no portal Azure utilizando:
- **Web App for Containers:** Para execução da imagem Docker.
- **Environment Variables:** Configuração de `DB_URL`, `DB_USER` e `DB_PASSWORD` diretamente no portal, garantindo que segredos não fiquem expostos no código.
- **Ambiente de Staging:** Provisionado em `brazilsouth` para validação final.# FIAP · DevOps — Mural ESG Social (Grupo 89)

Projeto acadêmico de um **Mural de Ações Sociais** alinhado ao pilar **Social** do ESG. A plataforma permite a criação de campanhas, engajamento da comunidade e acompanhamento de metas simbólicas.

---

## 🔗 Links do Projeto

| Recurso | URL |
|--------|-----|
| **Repositório GitHub** | [https://github.com/Gavinhas/fiap-devops](https://github.com/Gavinhas/fiap-devops) |
| **Ambiente de Staging (Azure)** | [https://ecoflow-staging-esg-apdaa5hjdah6gma9.brazilsouth-01.azurewebsites.net](https://ecoflow-staging-esg-apdaa5hjdah6gma9.brazilsouth-01.azurewebsites.net) |

---

## 🚀 Stack Tecnológica & DevOps

Este projeto foi desenvolvido aplicando conceitos de **CI/CD**, **Containerização** e **Cloud Computing**, garantindo um ciclo de entrega moderno e seguro:

- **Backend:** Spring Boot 3 / Java 21 / Thymeleaf.
- **Banco de Dados:** PostgreSQL (Docker local / Azure SQL em Nuvem).
- **Containerização:** Docker e Docker Compose para isolamento total do ambiente.
- **CI/CD:** Pipeline automatizado via GitHub Actions.
- **Cloud:** Hospedagem em Microsoft Azure App Service.
- **Segurança (DevSecOps):** Gestão de segredos via GitHub Secrets e Variáveis de Ambiente na Azure.

---

## 🔄 Pipeline de CI/CD

O fluxo de automação está definido em [`.github/workflows/mural-esg-social-ci.yml`](.github/workflows/mural-esg-social-ci.yml) e é disparado a cada `push` na branch `main`:

1.  **Maven Verify:** Compilação do código e execução de testes automatizados.
2.  **Docker Build:** Geração da imagem Docker da aplicação, garantindo paridade entre ambientes.
3.  **Continuous Deployment (CD):** Deploy automático para a **Azure** utilizando o *Publish Profile*. 

---

## 🛠️ Como rodar localmente (Docker)

### Pré-requisitos
- [Docker](https://docs.docker.com/get-docker/) e Docker Compose instalado.

### Passo a Passo
Na pasta raiz do projeto:

```bash
cd mural-esg-social
docker compose up --build