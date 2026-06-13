package model;

public class BookISBN {
    private int bookId;
    private String isbn;
    public BookISBN(int bookId, String isbn) { this.bookId = bookId; this.isbn = isbn; }
    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
}
