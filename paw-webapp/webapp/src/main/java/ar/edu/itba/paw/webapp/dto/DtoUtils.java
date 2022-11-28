package ar.edu.itba.paw.webapp.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DtoUtils {

    public static String firstWordsToUpper(String s) {
        StringBuilder sb = new StringBuilder();
        for (String word : s.split(" ")) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static String dateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
