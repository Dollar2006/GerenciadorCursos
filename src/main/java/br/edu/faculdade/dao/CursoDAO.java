package br.edu.faculdade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdade.factory.ConnectionFactory;
import br.edu.faculdade.model.Curso;

public class CursoDAO {
    
    public void inserir(Curso curso) throws SQLException {
        String sql = "INSERT INTO cursos (nome, carga_horaria, vagas, vagas_ocupadas) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getVagas());
            stmt.setInt(4, curso.getVagasOcupadas());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public void atualizar(Curso curso) throws SQLException {
        String sql = "UPDATE cursos SET nome = ?, carga_horaria = ?, vagas = ?, vagas_ocupadas = ?, ativo = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getVagas());
            stmt.setInt(4, curso.getVagasOcupadas());
            stmt.setBoolean(5, curso.isAtivo());
            stmt.setInt(6, curso.getId());
            
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
        curso.setVagas(rs.getInt("vagas"));
        curso.setVagasOcupadas(rs.getInt("vagas_ocupadas"));
        curso.setAtivo(rs.getBoolean("ativo"));
        return curso;
    }
} 