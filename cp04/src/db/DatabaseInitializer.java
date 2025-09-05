package db;

import dao.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void ensureCreated() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS jogo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "genre TEXT NOT NULL," +
                "platform TEXT NOT NULL," +
                "releaseYear INTEGER NOT NULL," +
                "rating INTEGER NOT NULL," +
                "status TEXT NOT NULL)";
        try (Connection c = Conexao.getConnection();
             Statement st = c.createStatement()) {
            st.execute(sql);
        }
    }
}

