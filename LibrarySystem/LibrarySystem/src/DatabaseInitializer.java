import util.SQLRunner;
import util.DataImporter;

public class DatabaseInitializer {

    public static void main(String[] args) {

        SQLRunner.runSqlFile("sql/create_tables.sql");

        SQLRunner.resetDatabase();

        DataImporter.importDefaultAdmin();
        DataImporter.importUsers();
        DataImporter.importBooks();
        DataImporter.importBorrowRecords();

        System.out.println("資料庫初始化完成！");
    }
}