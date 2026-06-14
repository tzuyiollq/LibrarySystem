package model;

public class FavoriteBook {

    private int favoriteId;
    private int bookId;
    private String title;
    private String authors;
    private String createdAt;

    public FavoriteBook(int favoriteId, int bookId, String title,
                        String authors, String createdAt) {
        this.favoriteId = favoriteId;
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.createdAt = createdAt;
    }

    public int getFavoriteId() { return favoriteId; }
    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public String getCreatedAt() { return createdAt; }
}