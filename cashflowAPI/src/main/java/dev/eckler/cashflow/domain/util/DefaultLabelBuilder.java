package dev.eckler.cashflow.domain.util;

import java.util.Arrays;

import dev.eckler.cashflow.shared.CashflowConst;

public class DefaultLabelBuilder {
    public static String createLabelFor(Object o) {
        return Arrays.asList(o.getClass().getName().split("\\.")).getLast() + "_" + CashflowConst.DEFAULT;
    }
}
