# Manual do Usuário - Sistema de Gerenciamento de Cursos

## Índice
1. [Requisitos do Sistema](#requisitos-do-sistema)
2. [Instalação](#instalação)
3. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
4. [Executando o Sistema](#executando-o-sistema)
5. [Funcionalidades Principais](#funcionalidades-principais)
6. [Suporte](#suporte)

## Requisitos do Sistema

### Requisitos Mínimos
- Java 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior
- Sistema Operacional: Windows, Linux ou macOS

### Requisitos Recomendados
- 4GB de RAM
- 1GB de espaço em disco
- Resolução de tela mínima: 1024x768

## Instalação

1. **Instalar o Java**
   - Baixe e instale o Java 11 (JDK) do site oficial da Oracle ou use o OpenJDK
   - Configure a variável de ambiente JAVA_HOME
   - Verifique a instalação executando `java -version` no terminal

2. **Instalar o MySQL**
   - Baixe e instale o MySQL Server 8.0
   - Durante a instalação, anote a senha do usuário root
   - Verifique se o serviço MySQL está rodando

3. **Instalar o Maven**
   - Baixe o Maven do site oficial
   - Extraia o arquivo em um diretório de sua preferência
   - Configure a variável de ambiente MAVEN_HOME
   - Adicione o Maven ao PATH do sistema

## Configuração do Banco de Dados

1. Abra o MySQL Workbench ou linha de comando do MySQL
2. Execute os seguintes comandos:
   ```sql
   CREATE DATABASE sistema_cursos;
   USE sistema_cursos;
   ```
3. Importe o arquivo de script SQL fornecido com o sistema (está na pasta src/resourses, o nome do arquivo é database.sql)

## Executando o Sistema

### Método 1: Usando o Maven
1. Abra o terminal na pasta raiz do projeto
2. Execute o comando:
   ```bash
   mvn clean package
   ```
3. Execute o sistema:
   ```bash
   java -jar target/sistema-cursos-1.0-SNAPSHOT.jar
   ```

### Método 2: Usando a IDE
1. Abra o projeto em sua IDE preferida (Eclipse, IntelliJ IDEA, etc.)
2. Localize a classe principal: `br.edu.faculdade.gui.JanelaPrincipal`
3. Execute a classe principal

## Funcionalidades Principais

### Interface do Usuário
- O sistema utiliza a biblioteca FlatLaf para uma interface moderna e responsiva
- Todas as janelas seguem um padrão visual consistente
- Utiliza o componente JCalendar para seleção de datas

### Guia de Uso das Funcionalidades

#### 1. Gerenciamento de Cursos

##### Cadastrar Novo Curso
1. Na tela principal, clique no botão "Cursos"
2. Na janela de cursos, clique em "Novo Curso"
3. Preencha os campos:
   - Nome do Curso
   - Duração (em horas)
   - Valor do Curso
   - Descrição
4. Clique em "Salvar" para confirmar o cadastro

##### Editar Curso Existente
1. Na lista de cursos, selecione o curso desejado
2. Clique no botão "Editar"
3. Modifique os campos necessários
4. Clique em "Salvar" para confirmar as alterações

##### Excluir Curso
1. Na lista de cursos, selecione o curso desejado
2. Clique no botão "Excluir"
3. Confirme a exclusão na janela de confirmação

#### 2. Cadastro de Alunos

##### Cadastrar Novo Aluno
1. Na tela principal, clique no botão "Alunos"
2. Na janela de alunos, clique em "Novo Aluno"
3. Preencha os campos:
   - Nome Completo
   - CPF
   - Data de Nascimento (use o calendário)
   - Endereço
   - Telefone
   - Email
4. Clique em "Salvar" para confirmar o cadastro

##### Editar Dados do Aluno
1. Na lista de alunos, selecione o aluno desejado
2. Clique no botão "Editar"
3. Modifique os campos necessários
4. Clique em "Salvar" para confirmar as alterações

#### 3. Controle de Matrículas

##### Realizar Nova Matrícula
1. Na tela principal, clique no botão "Matrículas"
2. Clique em "Nova Matrícula"
3. Selecione o aluno na lista
4. Selecione o curso desejado
5. Escolha a data de início
6. Clique em "Confirmar Matrícula"

##### Cancelar Matrícula
1. Na lista de matrículas, selecione a matrícula desejada
2. Clique no botão "Cancelar Matrícula"
3. Informe o motivo do cancelamento
4. Confirme o cancelamento

#### 4. Relatórios e Consultas

##### Gerar Relatório de Cursos
1. Na tela principal, clique em "Relatórios"
2. Selecione "Relatório de Cursos"
3. Escolha o período desejado
4. Clique em "Gerar Relatório"
5. Escolha o local para salvar o arquivo PDF

##### Gerar Relatório de Alunos
1. Na tela principal, clique em "Relatórios"
2. Selecione "Relatório de Alunos"
3. Escolha o período desejado
4. Clique em "Gerar Relatório"
5. Escolha o local para salvar o arquivo PDF

##### Consultar Matrículas
1. Na tela principal, clique em "Consultas"
2. Selecione "Matrículas"
3. Use os filtros disponíveis:
   - Por aluno
   - Por curso
   - Por período
4. Clique em "Buscar" para ver os resultados

### Dicas de Uso
- Use a barra de pesquisa para encontrar rapidamente alunos ou cursos
- Os relatórios podem ser exportados em formato PDF
- Mantenha os dados dos alunos sempre atualizados
- Faça backup regular dos dados importantes

## Suporte

### Problemas Comuns e Soluções

1. **Erro de Conexão com o Banco de Dados**
   - Verifique se o MySQL está rodando
   - Confirme se as credenciais estão corretas
   - Verifique se o banco de dados foi criado

2. **Erro ao Executar o Sistema**
   - Verifique se o Java 11 está instalado corretamente
   - Confirme se todas as dependências foram baixadas
   - Verifique se o arquivo JAR foi gerado corretamente
