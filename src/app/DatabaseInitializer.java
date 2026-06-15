package app;

import util.SQLRunner;
import util.DataImporter;

public class DatabaseInitializer {

    public static void main(String[] args) {

        SQLRunner.runSqlFile("sql/create_tables.sql");

        DataImporter.importUsers();
        DataImporter.importBooks();
        DataImporter.importBorrowRecords();
        DataImporter.importDefaultAdmin();

        System.out.println("資料庫已重置，並重新讀取老師給的參考資料！");
    }
}