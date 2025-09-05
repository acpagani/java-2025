package model;/*

@Author: Sistema de Gerenciamento de Biblioteca de Jogos
CP04 - JDBC + CRUD + Swing
Requisitos: Java 8+ e biblioteca sqlite-jdbc.jar no classpath.

*/

// --- Modelo ---
public class Jogo {
    private int id;
    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private int rating;
    private String status;

    public Jogo(int id, String title, String genre, String platform, int releaseYear, int rating, String status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.status = status;
    }

    public Jogo(String title, String genre, String platform, int releaseYear, int rating, String status) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
    public int getReleaseYear() { return releaseYear; }
    public int getRating() { return rating; }
    public String getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPlatform(String platform) { this.platform = platform; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public void setRating(int rating) { this.rating = rating; }
    public void setStatus(String status) { this.status = status; }
}

