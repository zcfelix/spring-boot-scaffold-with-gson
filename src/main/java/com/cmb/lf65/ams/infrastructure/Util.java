package com.cmb.lf65.ams.infrastructure;

import java.util.Collection;

public final class Util {
    private Util() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
