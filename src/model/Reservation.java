package model;

public class Reservation {

    private int reservationId;
    private int userId;
    private int bookId;
    private String bookTitle;
    private String reservedAt;
    private String status;

    public Reservation(int reservationId, int userId, int bookId,
                       String bookTitle, String reservedAt, String status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.reservedAt = reservedAt;
        this.status = status;
    }

    public int getReservationId() { return reservationId; }
    public int getUserId() { return userId; }
    public int getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getReservedAt() { return reservedAt; }
    public String getStatus() { return status; }
}