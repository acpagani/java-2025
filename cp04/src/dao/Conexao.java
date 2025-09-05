package dao;/*

@Author: Sistema de Gerenciamento de Biblioteca de Jogos
CP04 - JDBC + CRUD + Swing
Requisitos: Java 8+ e biblioteca sqlite-jdbc.jar no classpath.

*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// --- Conexão JDBC (SQLite) ---
public class Conexao {
    private static final String USER = "admin";
    private static final String PASS = "admin";
    private static final String URL = "jdbc:sqlite:startup.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

