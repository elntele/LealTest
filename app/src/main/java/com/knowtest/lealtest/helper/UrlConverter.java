package com.knowtest.lealtest.helper;

import androidx.room.TypeConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlConverter {
    @TypeConverter
    public static URL stringtoDate(String url)  {
        try {
            URL u= new URL(url);
            return u;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @TypeConverter
    public static String urlTostring(URL url) {
        return url==null? null: url.toString();
    }
}
