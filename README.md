# PROJETO DEV POLIGLOTA
__SÍNTESE__: Projeto desenvolvido como atividade prática da instituição EETEPA IEEP para o curso Técnico de Desenvolvimento de Sistemas (orientado pelo Prof. Mathaus Borges, 2026). O objetivo principal é demonstrar a integração colaborativa e interoperabilidade entre diferentes linguagens de programação (PHP, Java e Python) utilizando um banco de dados MySQL centralizado (via XAMPP) como canal de comunicação e persistência entre os módulos.

## 📚 Pré-requisitos e Configuração:
Para executar o ecossistema do projeto, é necessário configurar as seguintes tecnologias e dependências:

### 1. Banco de Dados & Servidor (XAMPP / MySQL)
Inicie os serviços Apache e MySQL no painel do XAMPP, acesse o phpMyAdmin (`http://localhost/phpmyadmin`) e execute a criação da tabela base:
```sql
CREATE TABLE alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    curso VARCHAR(50),
    matricula VARCHAR(20) DEFAULT 'Pendente'
);
```

### 2. Dependências por Linguagem
| Linguagem / Módulo | Conector / Biblioteca | Instalação / Configuração |
|:------------------:|:--------------------:|:------------------------:|
| **PHP 8** | `PDO (pdo_mysql)` | Nativo no XAMPP (ativo por padrão) |
| **Java** | `JDBC MySQL Connector` | Adicionar o arquivo `.jar` do MySQL Connector/J ao classpath |
| **Python** | `mysql-connector-python` | `pip install mysql-connector-python` |

## 🔩 Arquitetura e Divisão do Sistema:
O projeto adota uma arquitetura em esteira de processamento desacoplada, dividida entre três componentes principais e um banco de dados relacional central:

| Módulo / Arquivo | Tecnologia | Papel no Fluxo / Finalidade |
|:----------------:|:----------:|:----------------------------|
| `index.php` | **PHP 8 + PDO** | Interface web de cadastro (Tailwind CSS) que realiza a gravação inicial do aluno no MySQL com estado `matricula = 'Pendente'`. |
| `Processador.java` | **Java + JDBC** | Worker/Backend assíncrono que lê registros pendentes, aplica regras de negócio (formatação em caixa alta e geração do número de matrícula `MAT-100X`) e consolida no banco. |
| `relatorio.py` | **Python + Connector** | Módulo de BI/Relatório via CLI que consome os dados consolidados do banco e exibe o relatório gerencial final formatado no terminal. |

## 📒 Detalhamento do Fluxo de Dados:
* __Cadastro e Ingestão (PHP):__ A interface frontend/backend em PHP recebe `nome` e `curso` via requisição `POST` e os insere na tabela `alunos`. O campo `matricula` assume o valor padrão `'Pendente'`.
* __Processamento de Regras de Negócio (Java):__ O script Java busca registros onde `matricula = 'Pendente' LIMIT 1`. Ao encontrar, converte o nome para caixa alta (`toUpperCase()`), gera a matrícula no formato `MAT-(1000 + id)` e atualiza o registro no MySQL, alterando o status de pendente para a nova matrícula.
* __Visualização e Relatórios (Python):__ O script Python consulta todos os alunos processados (`WHERE matricula != 'Pendente'`) e renderiza um relatório gerencial formatado diretamente no terminal.
* __Colaboração e Versionamento (Git & VS Code):__ Fluxo de trabalho em trio estruturado através de repositório centralizado no GitHub (`projeto-dev-poliglota`), com branches/commits individuais integrados diretamente no VS Code.

## 💭 Melhorias Futuras:
* __Automação de Loop/Daemon em Java:__ Implementar um loop contínuo ou agendador de tarefas no `Processador.java` para que continue escutando novos registros pendentes sem necessidade de reexecução manual.
* __Tratamento de Erros e Logs Avançados:__ Expandir o tratamento de exceções de conexão no banco de dados e criar registros de log em arquivo para auditar as transações efetuadas por cada linguagem.
* __Interface de Dashboard Web em Python:__ Evoluir o script CLI de relatório em Python para uma interface web interativa utilizando frameworks como Streamlit ou Flask/Dash.
