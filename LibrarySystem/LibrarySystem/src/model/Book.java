package model;

public class Book {
	  private int bookId;
	    private String title;
	    private String authors;
	    private String subjects;
	    private String publisher;
	    private String publishYear;
	    private String edition;
	    private String formatDesc;
	    private String source;
	    private String note;
	    private String status;
	    private String isbns;

	    public Book(int bookId, String title, String authors, String subjects,
	                String publisher, String publishYear, String edition,
	                String formatDesc, String source, String note, String status) {
	        this.bookId = bookId;
	        this.title = title;
	        this.authors = authors;
	        this.subjects = subjects;
	        this.publisher = publisher;
	        this.publishYear = publishYear;
	        this.edition = edition;
	        this.formatDesc = formatDesc;
	        this.source = source;
	        this.note = note;
	        this.status = status;
	    }

	    public int getBookId() { return bookId; }
	    public String getTitle() { return title; }
	    public String getAuthors() { return authors; }
	    public String getSubjects() { return subjects; }
	    public String getPublisher() { return publisher; }
	    public String getPublishYear() { return publishYear; }
	    public String getEdition() { return edition; }
	    public String getFormatDesc() { return formatDesc; }
	    public String getSource() { return source; }
	    public String getNote() { return note; }
	    public String getStatus() { return status; }
	    public String getIsbns() {return isbns; }
	    public void setIsbns(String isbns) {this.isbns = isbns; }
}