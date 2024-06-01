package com.courier.tracking.constant;


public final class Constants {
    private Constants() {
    }

    public static final class GlobalConstants{
        public static final String NO_ENTRY_INFO = "Herhangi bir mağazaya kurye ulaşmadı.";
        public static final String COURIER_NAME_INFO = " isimli kurye ";
        public static final String STORE_ENTER_INFO = " mağazasına giriş yaptı.";
    }

    public static final class ExceptionConstants{
        public static final String COURIER_NOT_FOUND = "Courier not found with id ";
        public static final String SYSTEM_ERROR = "System error, please try again. ";

    }

    public static final class ResponseStatus{
        public static final String SUCCESS_ERROR_CODE = "0";
        public static final String FAIL_ERROR_CODE = "1";
        public static final String SUCCESS_ERROR_DESCRIPTION = "İşleminiz başarı ile gerçekleşmiştir.";
        public static final String SUCCESS_STATUS = "SUCCESS";
        public static final String FAIL_STATUS = "FAIL";
    }
}
