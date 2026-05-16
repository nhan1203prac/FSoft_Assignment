package com.Project.General_Assignment_ATJB_opt2.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validation {
    private static final String KHACH_HANG_ID_REGEX = "^KH\\d{5}$";

    private static final String DICH_VU_ID_REGEX = "^DV\\d{3}$";
    private static final String MA_MAY_REGEX = "^MAY\\d{3}$";

    private static final String PHONE_REGEX = "^(090\\d{7}|091\\d{7}|\\(84\\)\\+90\\d{7}|\\(84\\)\\+91\\d{7})$";

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

    public static boolean validateMayId(String id) { return Pattern.matches(MA_MAY_REGEX, id); }
    public static boolean isPositiveInteger(int value) {
        try {
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isPositiveDouble(double value) {
        try {

            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean validateDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = date.format(formatter);

            LocalDate.parse(formattedDate, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
