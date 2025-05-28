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

## Funcionalidades

### Gerenciamento de Cursos
- Cadastro de novos cursos
- Edição de cursos existentes
- Exclusão de cursos
- Ativação/desativação de cursos
- Listagem de todos os cursos
- Visualização de alunos por curso

### Gerenciamento de Alunos
- Cadastro de novos alunos
- Edição de dados dos alunos
- Exclusão de alunos
- Ativação/desativação de alunos
- Listagem de alunos por curso
- Filtro de alunos por curso

### Relatórios
- Lista de todos os cursos e seus alunos
- Lista de alunos ativos por curso
- Lista de alunos inativos por curso
- Exportação de relatórios para arquivo texto

## Validações

### Curso
- Nome: obrigatório, mínimo 3 caracteres
- Carga Horária: obrigatória, mínimo 20 horas
- Limite de Alunos: obrigatório, mínimo 1 aluno

### Aluno
- CPF: obrigatório, único, 11 dígitos
- Nome: obrigatório, mínimo 3 caracteres
- Email: obrigatório, formato válido
- Data de Nascimento: obrigatória, aluno deve ter pelo menos 16 anos
- Curso: obrigatório, deve estar ativo

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
│   │               │   └── JanelaPrincipal.java
│   │               └── modelo/
│   │                   ├── Aluno.java
│   │                   └── Curso.java
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