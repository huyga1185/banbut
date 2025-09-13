package com.web.banbut.exception;

public enum ErrorCode {
    USERNAME_EXISTED(1001, "Username existed"),
    USER_NOT_FOUND(1002,  "User not found"),
    PASSWORD_DOES_NOT_MATCH(1003,  "Password does not match"),
    PEN_NOT_FOUND(2001, "Pen not found"),
    PEN_VARIANT_NOT_FOUND(2002, "Pen variant not found"),
    BRAND_NOT_FOUND(2501, "Brand not found"),
    BRAND_EXISTED(2502, "Brand existed"),
    BRAND_NAME_EXISTED(2503,  "Brand name existed"),
    BRAND_HAS_PRODUCTS(2504, "Brand has products"),
    CATEGORY_NOT_FOUND(2751, "Category not found"),
    CATEGORY_EXISTED(2752, "Category existed"),
    CATEGORY_NAME_EXISTED(2753, "Category name existed"),
    CATEGORY_HAS_PRODUCTS(2754, "Category has products"),
    CART_EXISTED(3001, "Cart existed"),
    CART_NOT_FOUND(3004, "Cart not found"),
    OUT_OF_STOCK(3005, "Out of stock"),
    CART_ITEM_NOT_FOUND(3002,  "Cart item not found"),
    COULD_NOT_DELETE_CART_ITEM(3003, "Could not delete cart item"),
    COULD_NOT_CREATE_FOLDER(3500, "Could not initialize folder for upload"),
    INVALID_FILE(3501, "Invalid file"),
    INVALID_EXTENSION(3502, "Invalid extension"),
    FILE_NAME_EXISTED(3503, "File name existed"),
    COULD_NOT_STORE_THE_FILE(3504, "Could not store the file"),
    COULD_NOT_READ_THE_FILE(3505, "Could not read the file"),
    INVALID_FILE_URL(3506, "Invalid file url"),
    FILE_DOES_NOT_EXISTS(3507, "File does not exists"),
    COULD_NOT_DELETE_THE_FILE(3508, "Could not delete the file"),
    IMAGE_NOT_FOUND(3509, "Image not found"),
    IMAGE_EXISTED(3510, "Image existed"),
    AUTH_TOKEN_SIGN_FAILED(4000, "Auth token sign failed"),
    COULD_NOT_VERIFY_TOKEN(4001, "Could not verify token"),
    COULD_NOT_PARSE_TOKEN(4002, "Could not parse token"),
    COULD_NOT_RESET_UPLOAD_FOLDER(9001,"Could not reset upload folder"),
    LACK_OF_REQUEST_BODY(9500, "Lack of request body"),
    UNKNOWN_ERROR(9999, "Unknown error");
    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
