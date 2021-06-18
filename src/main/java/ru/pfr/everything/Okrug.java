package ru.pfr.everything;

import java.text.DecimalFormat;

public class Okrug {

    //округдение до двух знаков полсе запятой
    static String okrug(Float f) {
        return f != null ? new DecimalFormat("#0.00").format(f) : null;
    }
    static String okrug(Double f) {
        return f != null ? new DecimalFormat("#0.00").format(f) : null;
    }

}
