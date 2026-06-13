package model;

public class BorrowRecord {
	private int recordId;
    private String studentNo;
    private String userName;
    private String bookTitle;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private int borrowDays;

    public BorrowRecord(int recordId, String studentNo, String userName,
                        String bookTitle, String borrowDate, String dueDate,
                        String returnDate, int borrowDays) {
        this.recordId = recordId;
        this.studentNo = studentNo;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.borrowDays = borrowDays;
    }

    public int getRecordId() { return recordId; }
    public String getStudentNo() { return studentNo; }
    public String getUserName() { return userName; }
    public String getBookTitle() { return bookTitle; }
    public String getBorrowDate() { return borrowDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public int getBorrowDays() { return borrowDays; }
    
}