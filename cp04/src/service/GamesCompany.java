package service;/*

@Author: Sistema de Gerenciamento de Biblioteca de Jogos
CP04 - JDBC + CRUD + Swing
Requisitos: Java 8+ e biblioteca sqlite-jdbc.jar no classpath.

*/

import db.DatabaseInitializer;
import view.JogoGUI;

import javax.swing.*;
import java.sql.SQLException;

public class GamesCompany {
    public static void main(String[] args) {
        try {
            DatabaseInitializer.ensureCreated();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | SQLException ignored) {}

        SwingUtilities.invokeLater(() -> new JogoGUI().setVisible(true));
    }
}

