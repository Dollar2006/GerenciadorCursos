package br.edu.faculdade.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import br.edu.faculdade.dao.AlunoDAO;
import br.edu.faculdade.dao.CursoDAO;
import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;

public class RelatorioUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void gerarRelatorioAlunosPorCurso(int idCurso, String caminhoArquivo) throws IOException, SQLException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            CursoDAO cursoDAO = new CursoDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            
            Curso curso = cursoDAO.buscarPorId(idCurso);
            if (curso == null) {
                throw new IOException("Curso não encontrado");
            }
            
            writer.println("RELATÓRIO DE ALUNOS - " + curso.getNome());
            writer.println("==========================================");
            writer.println();
            
            List<Aluno> alunos = alunoDAO.listarPorCurso(idCurso);
            for (Aluno aluno : alunos) {
                writer.println("Matrícula: " + aluno.getMatricula());
                writer.println("CPF: " + aluno.getCpf());
                writer.println("Nome: " + aluno.getNome());
                writer.println("Email: " + aluno.getEmail());
                writer.println("Telefone: " + aluno.getTelefone());
                writer.println("Status: " + (aluno.isAtivo() ? "Ativo" : "Inativo"));
                writer.println("------------------------------------------");
            }
            
            writer.println();
            writer.println("Total de alunos: " + alunos.size());
        }
    }
    
    public static void gerarRelatorioCursosEAlunos(String caminhoArquivo) throws IOException, SQLException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            CursoDAO cursoDAO = new CursoDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            
            List<Curso> cursos = cursoDAO.listarTodos();
            for (Curso curso : cursos) {
                writer.println("CURSO: " + curso.getNome());
                writer.println("Carga Horária: " + curso.getCargaHoraria() + " horas");
                writer.println("Vagas: " + curso.getVagasOcupadas() + "/" + curso.getVagas());
                writer.println("Status: " + (curso.isAtivo() ? "Ativo" : "Inativo"));
                writer.println();
                writer.println("ALUNOS MATRICULADOS:");
                writer.println("------------------------------------------");
                
                List<Aluno> alunos = alunoDAO.listarPorCurso(curso.getId());
                for (Aluno aluno : alunos) {
                    writer.println("Matrícula: " + aluno.getMatricula());
                    writer.println("Nome: " + aluno.getNome());
                    writer.println("Status: " + (aluno.isAtivo() ? "Ativo" : "Inativo"));
                    writer.println("------------------------------------------");
                }
                
                writer.println();
                writer.println("Total de alunos: " + alunos.size());
                writer.println("==========================================");
                writer.println();
            }
        }
    }
    
    public static void gerarRelatorioAlunosAtivosPorCurso(String caminhoArquivo) throws IOException, SQLException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            CursoDAO cursoDAO = new CursoDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            
            List<Curso> cursos = cursoDAO.listarAtivos();
            for (Curso curso : cursos) {
                writer.println("CURSO: " + curso.getNome());
                writer.println("------------------------------------------");
                
                List<Aluno> alunos = alunoDAO.listarAtivosPorCurso(curso.getId());
                for (Aluno aluno : alunos) {
                    writer.println("Matrícula: " + aluno.getMatricula());
                    writer.println("CPF: " + aluno.getCpf());
                    writer.println("Nome: " + aluno.getNome());
                    writer.println("Email: " + aluno.getEmail());
                    writer.println("Telefone: " + aluno.getTelefone());
                    writer.println("------------------------------------------");
                }
                
                writer.println();
                writer.println("Total de alunos ativos: " + alunos.size());
                writer.println("==========================================");
                writer.println();
            }
        }
    }
    
    public static void gerarRelatorioAlunosInativosPorCurso(String caminhoArquivo) throws IOException, SQLException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            CursoDAO cursoDAO = new CursoDAO();
            AlunoDAO alunoDAO = new AlunoDAO();
            
            List<Curso> cursos = cursoDAO.listarAtivos();
            for (Curso curso : cursos) {
                writer.println("CURSO: " + curso.getNome());
                writer.println("------------------------------------------");
                
                List<Aluno> alunos = alunoDAO.listarPorCurso(curso.getId());
                alunos.removeIf(Aluno::isAtivo);
                
                for (Aluno aluno : alunos) {
                    writer.println("Matrícula: " + aluno.getMatricula());
                    writer.println("CPF: " + aluno.getCpf());
                    writer.println("Nome: " + aluno.getNome());
                    writer.println("Email: " + aluno.getEmail());
                    writer.println("Telefone: " + aluno.getTelefone());
                    writer.println("------------------------------------------");
                }
                
                writer.println();
                writer.println("Total de alunos inativos: " + alunos.size());
                writer.println("==========================================");
                writer.println();
            }
        }
    }
} 