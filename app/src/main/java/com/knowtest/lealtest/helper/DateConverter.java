package com.knowtest.lealtest.helper;

import androidx.room.TypeConverter;

import com.google.firebase.Timestamp;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Timestamp toDate(String l) {
        Long lon = new Long(l);
        Timestamp t = new Timestamp(lon, 0);

        return l == null ? null : t;
    }
    @TypeConverter
    public static String fromDate(Timestamp timestamp) {
        String s = String.valueOf(timestamp.getSeconds());
        return timestamp == null ? null : s;
    }

}
