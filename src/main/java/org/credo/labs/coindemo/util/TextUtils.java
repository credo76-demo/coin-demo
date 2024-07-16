package org.credo.labs.coindemo.util;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.data.util.Pair;

public final class TextUtils {
    public static final String DEFAULT_PREFIX = "%{";
    public static final String DEFAULT_SUFFIX = "}";
    public static final String DEFAULT_SEPARATOR = ",";

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    @SafeVarargs
    public static String substitute(String source, String prefix, Pair<String, Object>... values) {
        Map<String, ?> map = Arrays.stream(values).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        return StringSubstitutor.replace(source, map, prefix, DEFAULT_SUFFIX);
    }

    @SafeVarargs
    public static final String substitute(String source,  Pair<String, Object>... values) {
        return substitute(source, DEFAULT_PREFIX, values);
    }
}
