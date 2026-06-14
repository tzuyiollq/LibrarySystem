package service;

import java.util.List;

import dao.ReservationDAO;
import model.Reservation;
import model.User;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    public boolean reserveBook(User user, int bookId) {

        if (user == null) {
            return false;
        }

        if (user.getStatus().equals("SUSPENDED")) {
            System.out.println("停權使用者不能預約！");
            return false;
        }

        return reservationDAO.reserveBook(user.getUserId(), bookId);
    }

    public List<Reservation> getUserReservations(int userId) {
        return reservationDAO.getUserReservations(userId);
    }

    public boolean cancelReservation(int reservationId, int userId) {
        return reservationDAO.cancelReservation(reservationId, userId);
    }
    public int countWaitingReservations(int userId) {
        return reservationDAO.countWaitingReservations(userId);
    }
}