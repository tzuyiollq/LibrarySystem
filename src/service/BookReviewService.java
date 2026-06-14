package service;

import dao.BookReviewDAO;
import model.BookReview;
import model.User;
import java.util.List;

public class BookReviewService {

    private BookReviewDAO bookReviewDAO = new BookReviewDAO();

    public boolean addReview(User user, int bookId, int rating, String comment) {

        if (user == null) {
            return false;
        }

        if ("SUSPENDED".equals(user.getStatus())) {
            return false;
        }

        if (rating < 1 || rating > 5) {
            return false;
        }

        if (comment == null || comment.trim().isEmpty()) {
            return false;
        }

        return bookReviewDAO.addReview(
                user.getUserId(),
                bookId,
                rating,
                comment.trim()
        );
    }

    public List<BookReview> getReviewsByBookId(int bookId) {
        return bookReviewDAO.getReviewsByBookId(bookId);
    }

    public List<BookReview> getUserReviews(int userId) {
        return bookReviewDAO.getUserReviews(userId);
    }
}