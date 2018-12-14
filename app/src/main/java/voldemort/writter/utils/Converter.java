package voldemort.writter.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converter {


    // Source: https://developer.android.com/training/data-storage/room/referencing-data
    public class DateConverter {

        @TypeConverter
        public static Date fromLong(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long toLong(Date date) {
            return date == null ? null : date.getTime();
        }

    }
