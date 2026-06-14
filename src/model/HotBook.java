package model;

public class HotBook {
    private int bookId;
    private String title;
    private String authors;
    private int borrowCount;

    public HotBook(int bookId, String title, String authors, int borrowCount) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.borrowCount = borrowCount;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthors() { return authors; }
    public int getBorrowCount() { return borrowCount; }
}