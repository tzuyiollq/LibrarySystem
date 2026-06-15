package model;

public class BookReview {

    private int reviewId;
    private int userId;
    private int bookId;
    private String userName;
    private String bookTitle;
    private int rating;
    private String comment;
    private String createdAt;

    public BookReview(
            int reviewId,
            int userId,
            int bookId,
            String userName,
            String bookTitle,
            int rating,
            String comment,
            String createdAt
    ) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.bookId = bookId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getUserName() {
        return userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}