package com.Project.General_Assignment_ATJB_opt2.utils;

import java.util.regex.Pattern;

public class Validation {
    private static final String KHACH_HANG_ID_REGEX = "^KH\\d{5}$";

    private static final String DICH_VU_ID_REGEX = "^DV\\d{3}$";

    private static final String PHONE_REGEX = "^0[35789]\\d{8}$";

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static final String TIME_24H_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

    public static boolean validateKhachHangId(String id) {
        return Pattern.matches(KHACH_HANG_ID_REGEX, id);
    }

    public static boolean validateDichVuId(String id) {
        return Pattern.matches(DICH_VU_ID_REGEX, id);
    }

    public static boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean validateTime(String time) {
        return Pattern.matches(TIME_24H_REGEX, time);
    }

    public static boolean isPositiveInteger(String value) {
        try {
            int val = Integer.parseInt(value);
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isPositiveDouble(String value) {
        try {
            double val = Double.parseDouble(value);
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
