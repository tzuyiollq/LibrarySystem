import util.SQLRunner;
import util.DataImporter;

public class DatabaseInitializer {

    public static void main(String[] args) {

        SQLRunner.runSqlFile("sql/create_tables.sql");

        SQLRunner.resetDatabase();

        DataImporter.importUsers();
        DataImporter.importBooks();
        DataImporter.importBorrowRecords();

        DataImporter.importDefaultAdmin();

        System.out.println("資料庫已重新讀取參考資料！");
    }
}