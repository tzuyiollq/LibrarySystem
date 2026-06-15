package model;

public enum BorrowResult {

    SUCCESS("借閱成功，祝您閱讀愉快！"),

    NOT_LOGIN("請先登入後再借書。"),

    SUSPENDED("您的帳號目前已停權，無法借書。"),

    NORMAL_DAYS_LIMIT("NORMAL 會員最多只能借 7 天。"),

    VIP_DAYS_LIMIT("VIP 會員最多只能借 14 天。"),

    BORROW_LIMIT("您已達借閱上限，請先歸還書籍。"),

    BOOK_NOT_FOUND("查無此 Book ID。"),

    BOOK_NOT_AVAILABLE("此書目前已被借出或不可借閱。"),

    SYSTEM_ERROR("系統發生錯誤，請稍後再試。");

    private final String message;

    BorrowResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}