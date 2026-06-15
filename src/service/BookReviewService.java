package service;

import dao.BookReviewDAO;
import model.BookReview;
import model.User;

import java.util.List;

public class BookReviewService {

    private BookReviewDAO dao = new BookReviewDAO();

    public boolean addReview(User user, int bookId, int rating, String comment) {

        if (user == null) {
            return false;
        }

        if (comment == null || comment.trim().isEmpty()) {
            return false;
        }

        if (rating < 1 || rating > 5) {
            return false;
        }

        return dao.addReview(
                user.getUserId(),
                bookId,
                rating,
                comment.trim()
        );
    }

    public List<BookReview> getAllReviews() {
        return dao.getAllReviews();
    }

    public List<BookReview> getReviewsByBookId(int bookId) {
        return dao.getReviewsByBookId(bookId);
    }

    public boolean deleteReviewByUser(int reviewId, User user) {

        if (user == null) {
            return false;
        }

        return dao.deleteReviewByUser(
                reviewId,
                user.getUserId()
        );
    }

    public boolean deleteReviewByAdmin(int reviewId) {
        return dao.deleteReviewByAdmin(reviewId);
    }
    public double getAverageRating(int bookId) {
        return dao.getAverageRating(bookId);
    }
}