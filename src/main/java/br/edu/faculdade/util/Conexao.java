package br.edu.faculdade.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_cursos";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static Connection conexao;
    
    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
} 