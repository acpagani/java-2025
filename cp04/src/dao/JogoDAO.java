package dao;/*

@Author: Sistema de Gerenciamento de Biblioteca de Jogos
CP04 - JDBC + CRUD + Swing
Requisitos: Java 8+ e biblioteca sqlite-jdbc.jar no classpath.

*/

import model.Jogo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// --- DAO ---
public class JogoDAO {

    public void inserir(Jogo jogo) {
        String sql = "INSERT INTO jogo (title, genre, platform, releaseYear, rating, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogo.getTitle());
            stmt.setString(2, jogo.getGenre());
            stmt.setString(3, jogo.getPlatform());
            stmt.setInt(4, jogo.getReleaseYear());
            stmt.setInt(5, jogo.getRating());
            stmt.setString(6, jogo.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            mostrarErro("Erro ao inserir jogo", e);
        }
    }

    public List<Jogo> listar() {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogo ORDER BY id";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                jogos.add(new Jogo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("releaseYear"),
                        rs.getInt("rating"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            mostrarErro("Erro ao listar jogos", e);
        }
        return jogos;
    }

    public void atualizar(Jogo jogo) {
        String sql = "UPDATE jogo SET title = ?, genre = ?, platform = ?, releaseYear = ?, rating = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogo.getTitle());
            stmt.setString(2, jogo.getGenre());
            stmt.setString(3, jogo.getPlatform());
            stmt.setInt(4, jogo.getReleaseYear());
            stmt.setInt(5, jogo.getRating());
            stmt.setString(6, jogo.getStatus());
            stmt.setInt(7, jogo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            mostrarErro("Erro ao atualizar jogo", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            mostrarErro("Erro ao deletar jogo", e);
        }
    }

    // Métodos para funcionalidades extras
    public List<Jogo> listarPorGenero(String genero) {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogo WHERE genre LIKE ? ORDER BY title";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + genero + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jogos.add(new Jogo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("releaseYear"),
                        rs.getInt("rating"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            mostrarErro("Erro ao filtrar por gênero", e);
        }
        return jogos;
    }

    public List<Jogo> listarPorPlataforma(String plataforma) {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogo WHERE platform LIKE ? ORDER BY title";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + plataforma + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jogos.add(new Jogo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("releaseYear"),
                        rs.getInt("rating"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            mostrarErro("Erro ao filtrar por plataforma", e);
        }
        return jogos;
    }

    public List<Jogo> listarPorStatus(String status) {
        List<Jogo> jogos = new ArrayList<>();
        String sql = "SELECT * FROM jogo WHERE status = ? ORDER BY title";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                jogos.add(new Jogo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("releaseYear"),
                        rs.getInt("rating"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            mostrarErro("Erro ao filtrar por status", e);
        }
        return jogos;
    }

    private static void mostrarErro(String titulo, Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
    }
}

