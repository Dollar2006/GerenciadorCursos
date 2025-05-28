package br.edu.faculdade.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdade.factory.ConnectionFactory;
import br.edu.faculdade.modelo.Aluno;
import br.edu.faculdade.modelo.Curso;

public class AlunoDAO {
    
    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (cpf, nome, email, data_nascimento, id_curso) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getId());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET cpf = ?, nome = ?, email = ?, data_nascimento = ?, ativo = ?, id_curso = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setBoolean(5, aluno.isAtivo());
            stmt.setInt(6, aluno.getCurso().getId());
            stmt.setInt(7, aluno.getId());
            
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
        String sql = "SELECT a.*, c.* FROM alunos a " +
                    "JOIN cursos c ON a.id_curso = c.id " +
                    "WHERE a.id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarAlunoDoResultSet(rs);
                }
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
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(criarAlunoDoResultSet(rs));
                }
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
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alunos.add(criarAlunoDoResultSet(rs));
                }
            }
        }
        return alunos;
    }
    
    public boolean existeCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM alunos WHERE cpf = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT a.*, c.* FROM alunos a " +
                     "JOIN cursos c ON a.id_curso = c.id ORDER BY a.nome";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(criarAlunoDoResultSet(rs));
            }
        }
        return alunos;
    }
    
    private Aluno criarAlunoDoResultSet(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("a.id"));
        aluno.setCpf(rs.getString("a.cpf"));
        aluno.setNome(rs.getString("a.nome"));
        aluno.setEmail(rs.getString("a.email"));
        aluno.setDataNascimento(rs.getDate("a.data_nascimento").toLocalDate());
        aluno.setAtivo(rs.getBoolean("a.ativo"));
        
        Curso curso = new Curso();
        curso.setId(rs.getInt("c.id"));
        curso.setNome(rs.getString("c.nome"));
        curso.setCargaHoraria(rs.getInt("c.carga_horaria"));
        curso.setLimiteAlunos(rs.getInt("c.limite_alunos"));
        curso.setAtivo(rs.getBoolean("c.ativo"));
        
        aluno.setCurso(curso);
        return aluno;
    }
} 