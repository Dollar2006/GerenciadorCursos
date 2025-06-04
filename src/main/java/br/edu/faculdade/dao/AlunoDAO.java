package br.edu.faculdade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdade.factory.ConnectionFactory;
import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;

public class AlunoDAO {
    
    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (matricula, cpf, nome, email, telefone, id_curso) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getNome());
            stmt.setString(4, aluno.getEmail());
            stmt.setString(5, aluno.getTelefone());
            stmt.setInt(6, aluno.getCurso().getId());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET matricula = ?, cpf = ?, nome = ?, email = ?, telefone = ?, id_curso = ?, ativo = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getNome());
            stmt.setString(4, aluno.getEmail());
            stmt.setString(5, aluno.getTelefone());
            stmt.setInt(6, aluno.getCurso().getId());
            stmt.setBoolean(7, aluno.isAtivo());
            stmt.setInt(8, aluno.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM alunos WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public Aluno buscarPorId(int id) throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a LEFT JOIN cursos c ON a.id_curso = c.id WHERE a.id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarAlunoDoResultSet(rs);
            }
        }
        return null;
    }
    
    public List<Aluno> listarPorCurso(int idCurso) throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a " +
                    "JOIN cursos c ON a.id_curso = c.id " +
                    "WHERE a.id_curso = ? ORDER BY a.nome";
        List<Aluno> alunos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCurso);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }
    
    public List<Aluno> listarAtivosPorCurso(int idCurso) throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a " +
                    "JOIN cursos c ON a.id_curso = c.id " +
                    "WHERE a.id_curso = ? AND a.ativo = true ORDER BY a.nome";
        List<Aluno> alunos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCurso);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }
    
    public boolean existeMatricula(String matricula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM alunos WHERE matricula = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    public boolean existeCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM alunos WHERE cpf = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a LEFT JOIN cursos c ON a.id_curso = c.id";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }
    
    public List<Aluno> listarAtivos() throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a LEFT JOIN cursos c ON a.id_curso = c.id WHERE a.ativo = true";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }
    
    private Aluno criarAlunoDoResultSet(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("a.id"));
        aluno.setMatricula(rs.getString("a.matricula"));
        aluno.setCpf(rs.getString("a.cpf"));
        aluno.setNome(rs.getString("a.nome"));
        aluno.setEmail(rs.getString("a.email"));
        aluno.setTelefone(rs.getString("a.telefone"));
        aluno.setAtivo(rs.getBoolean("a.ativo"));
        
        if (rs.getInt("c.id") != 0) {
            Curso curso = new Curso();
            curso.setId(rs.getInt("c.id"));
            curso.setNome(rs.getString("c.nome"));
            curso.setCargaHoraria(rs.getInt("c.carga_horaria"));
            curso.setVagas(rs.getInt("c.vagas"));
            curso.setVagasOcupadas(rs.getInt("c.vagas_ocupadas"));
            curso.setAtivo(rs.getBoolean("c.ativo"));
            aluno.setCurso(curso);
        }
        
        return aluno;
    }
} 