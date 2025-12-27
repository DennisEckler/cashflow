package dev.eckler.cashflow.util;

import java.util.Arrays;
import static dev.eckler.cashflow.constants.CashflowConst.DEFAULT;

public class DefaultLabelBuilder {
    public static String createLabelFor(Object o) {
        return Arrays.asList(o.getClass().getName().split("\\.")).getLast() + "_" + DEFAULT;
    }
}
