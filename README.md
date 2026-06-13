# LibrarySystem

Java Swing + MySQL 圖書館借還書系統。

架構：
GUI(View) → Controller → Service → DAO → MySQL

## 執行前設定
1. 匯入 Eclipse Java Project。
2. 將 MySQL Connector JAR 加到 Build Path。
3. 修改 `src/dao/DBConnection.java` 的 MySQL 密碼。
4. 在 MySQL 執行 `sql/create_tables.sql`，或執行 `DatabaseInitializer.java`。
5. 執行 `Main.java`。

## 預設帳號
使用者：王小明 / 1234
管理者：admin / 1234

## 管理者入口
登入畫面右下角：拖鑰匙到書本上，會開啟管理者登入視窗。
