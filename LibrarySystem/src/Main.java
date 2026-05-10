import util.DataImporter;

public class Main {
    public static void main(String[] args) {
        DataImporter.importUsers();
        DataImporter.importBooks();
        DataImporter.importBorrowRecords();

        System.out.println("全部資料匯入完成！");
    }
}