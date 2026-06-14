package service;

import dao.FavoriteDAO;
import model.FavoriteBook;
import model.User;
import java.util.List;

public class FavoriteService {

    private FavoriteDAO favoriteDAO = new FavoriteDAO();

    public boolean addFavorite(User user, int bookId) {

        if (user == null) {
            return false;
        }

        if ("SUSPENDED".equals(user.getStatus())) {
            return false;
        }

        return favoriteDAO.addFavorite(user.getUserId(), bookId);
    }

    public boolean removeFavorite(int favoriteId, int userId) {
        return favoriteDAO.removeFavorite(favoriteId, userId);
    }

    public List<FavoriteBook> getUserFavorites(int userId) {
        return favoriteDAO.getUserFavorites(userId);
    }
}