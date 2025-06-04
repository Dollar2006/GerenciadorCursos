# Sistema de Gerenciamento de Cursos e Alunos

Este é um sistema desenvolvido em Java para gerenciar cursos e alunos de uma instituição de ensino. O sistema permite o cadastro, edição, exclusão e consulta de cursos e alunos, além de gerar relatórios.

## Requisitos do Sistema

- Java JDK 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

## Configuração do Banco de Dados

1. Instale o MySQL Server em sua máquina
2. Crie um banco de dados chamado `sistema_cursos`
3. Execute o script SQL localizado em `src/main/resources/database.sql`
4. Configure as credenciais do banco de dados no arquivo `src/main/java/br/edu/faculdade/factory/ConnectionFactory.java`

## Compilação e Execução

1. Clone o repositório
2. Abra um terminal na pasta do projeto
3. Execute o comando para compilar:
   ```bash
   mvn clean package
   ```
4. Execute o comando para rodar:
   ```bash
   java -jar target/sistema-cursos-1.0-SNAPSHOT.jar
   ```

## Manual do Usuário

### Tela Principal

A tela principal do sistema apresenta quatro opções principais:
1. Cursos
2. Alunos
3. Relatórios
4. Sair

### Gerenciamento de Cursos

#### Cadastro de Curso
1. Na tela principal, clique em "Cursos"
2. Na janela de cursos, clique em "Novo Curso"
3. Preencha os campos:
   - Nome do Curso (mínimo 3 caracteres)
   - Carga Horária (mínimo 20 horas)
   - Limite de Alunos (mínimo 1 aluno)
4. Clique em "Salvar"

#### Edição de Curso
1. Na lista de cursos, selecione o curso desejado
2. Clique em "Editar Curso"
3. Modifique os campos necessários
4. Clique em "Salvar"

#### Exclusão de Curso
1. Na lista de cursos, selecione o curso desejado
2. Clique em "Excluir Curso"
3. Confirme a exclusão

#### Ativação/Desativação de Curso
1. Na lista de cursos, selecione o curso desejado
2. Clique em "Ativar/Desativar Curso"
3. Confirme a ação

### Gerenciamento de Alunos

#### Cadastro de Aluno
1. Na tela principal, clique em "Alunos"
2. Na janela de alunos, clique em "Novo Aluno"
3. Preencha os campos:
   - CPF (11 dígitos, único)
   - Nome (mínimo 3 caracteres)
   - Email (formato válido)
   - Data de Nascimento (aluno deve ter pelo menos 16 anos)
   - Curso (deve estar ativo)
4. Clique em "Salvar"

#### Edição de Aluno
1. Na lista de alunos, selecione o aluno desejado
2. Clique em "Editar Aluno"
3. Modifique os campos necessários
4. Clique em "Salvar"

#### Exclusão de Aluno
1. Na lista de alunos, selecione o aluno desejado
2. Clique em "Excluir Aluno"
3. Confirme a exclusão

#### Ativação/Desativação de Aluno
1. Na lista de alunos, selecione o aluno desejado
2. Clique em "Ativar/Desativar Aluno"
3. Confirme a ação

### Relatórios

#### Listar Alunos de um Curso
1. Na tela principal, clique em "Relatórios"
2. Selecione o curso desejado no ComboBox
3. Clique em "Listar Alunos do Curso"
4. Escolha o local para salvar o arquivo
5. O relatório será gerado com:
   - Dados do curso
   - Lista de alunos matriculados
   - Total de alunos

#### Listar Todos os Cursos e Alunos
1. Na tela principal, clique em "Relatórios"
2. Clique em "Listar Cursos e Alunos"
3. Escolha o local para salvar o arquivo
4. O relatório será gerado com:
   - Lista de todos os cursos
   - Alunos matriculados em cada curso
   - Totais por curso

#### Exportar Alunos Ativos
1. Na tela principal, clique em "Relatórios"
2. Clique em "Exportar Alunos Ativos"
3. Escolha o local para salvar o arquivo
4. O relatório será gerado com:
   - Lista de alunos ativos por curso
   - Dados completos dos alunos
   - Totais por curso

#### Exportar Alunos Inativos
1. Na tela principal, clique em "Relatórios"
2. Clique em "Exportar Alunos Inativos"
3. Escolha o local para salvar o arquivo
4. O relatório será gerado com:
   - Lista de alunos inativos por curso
   - Dados completos dos alunos
   - Totais por curso

### Dicas de Uso

1. **Validações**:
   - Todos os campos obrigatórios são marcados com asterisco (*)
   - As validações são feitas em tempo real
   - Mensagens de erro são exibidas quando necessário

2. **Navegação**:
   - Use a tecla Tab para navegar entre os campos
   - Use Enter para confirmar ações
   - Use Esc para fechar janelas

3. **Relatórios**:
   - Os arquivos são salvos em formato .txt
   - Escolha nomes descritivos para os arquivos
   - Verifique se tem permissão de escrita no local escolhido

4. **Boas Práticas**:
   - Mantenha os dados atualizados
   - Faça backup regular dos relatórios
   - Verifique a consistência dos dados periodicamente

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── edu/
│   │           └── faculdade/
│   │               ├── dao/
│   │               │   ├── AlunoDAO.java
│   │               │   └── CursoDAO.java
│   │               ├── factory/
│   │               │   └── ConnectionFactory.java
│   │               ├── gui/
│   │               │   ├── JanelaAluno.java
│   │               │   ├── JanelaCurso.java
│   │               │   ├── JanelaListarAlunos.java
│   │               │   ├── JanelaListarCursos.java
│   │               │   ├── JanelaPrincipal.java
│   │               │   └── JanelaRelatorios.java
│   │               ├── model/
│   │               │   ├── Aluno.java
│   │               │   └── Curso.java
│   │               └── util/
│   │                   └── RelatorioUtil.java
│   └── resources/
│       └── database.sql
└── test/
    └── java/
        └── br/
            └── edu/
                └── faculdade/
                    └── teste/
                        ├── AlunoTest.java
                        └── CursoTest.java
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes. 