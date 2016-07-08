package com.rd.kv.shriharifin.helper;

import android.provider.BaseColumns;

/**
 * Created by Data Crawl 6 on 31-03-2016.
 */
public class SpendDBFields {

    public static abstract class SpendAdd implements BaseColumns {
        public static final String TABLE_NAME = "tblSpend";
        public static final String ID = "id";
        public static final String AMOUNT = "amount";
        public static final String DATE = "date";
        public static final String NAME = "nname";
        public static final String ADDRESS = "address";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String STATUS = "status";
        public static final String NAME2 = "name2";
        public static final String INTEREST = "interest";
        public static final String DAYS = "days";
        public static final String CREATED_DATE = "createddate";

    }
}
