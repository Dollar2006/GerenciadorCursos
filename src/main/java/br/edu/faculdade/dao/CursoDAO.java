package br.edu.faculdade.dao;

import br.edu.faculdade.factory.ConnectionFactory;
import br.edu.faculdade.modelo.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    
    public void inserir(Curso curso) throws SQLException {
        String sql = "INSERT INTO cursos (nome, carga_horaria, limite_alunos) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Curso curso) throws SQLException {
        String sql = "UPDATE cursos SET nome = ?, carga_horaria = ?, limite_alunos = ?, ativo = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            stmt.setInt(5, curso.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cursos WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public Curso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarCursoDoResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Curso> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cursos ORDER BY nome";
        List<Curso> cursos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                cursos.add(criarCursoDoResultSet(rs));
            }
        }
        return cursos;
    }
    
    public List<Curso> listarAtivos() throws SQLException {
        String sql = "SELECT * FROM cursos WHERE ativo = true ORDER BY nome";
        List<Curso> cursos = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                cursos.add(criarCursoDoResultSet(rs));
            }
        }
        return cursos;
    }
    
    private Curso criarCursoDoResultSet(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        curso.setId(rs.getInt("id"));
        curso.setNome(rs.getString("nome"));
        curso.setCargaHoraria(rs.getInt("carga_horaria"));
        curso.setLimiteAlunos(rs.getInt("limite_alunos"));
        curso.setAtivo(rs.getBoolean("ativo"));
        return curso;
    }
} 