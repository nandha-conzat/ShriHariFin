package com.rd.kv.shriharifin.helper;

import android.provider.BaseColumns;

/**
 * Created by Data Crawl 6 on 31-03-2016.
 */
public class InvestorsDBFileds {

    public static abstract class InvestorsAdd implements BaseColumns {
        public static final String TABLE_NAME = "tblInvesters";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AMOUNT = "amount";
        public static final String DATE = "date";

    }
}
