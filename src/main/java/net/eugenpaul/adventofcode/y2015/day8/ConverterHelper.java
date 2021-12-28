package net.eugenpaul.adventofcode.y2015.day8;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;
import org.apache.commons.text.translate.OctalUnescaper;
import org.apache.commons.text.translate.UnicodeUnescaper;

public class ConverterHelper {
    private ConverterHelper() {
    }

    public static final CharSequenceTranslator UNESCAPE_JAVA;
    static {
        final Map<CharSequence, CharSequence> unescapeJavaMap = new HashMap<>();
        unescapeJavaMap.put("\\\\", "\\");
        unescapeJavaMap.put("\\\"", "\"");
        unescapeJavaMap.put("\\'", "'");
        unescapeJavaMap.put("\\", StringUtils.EMPTY);
        UNESCAPE_JAVA = new AggregateTranslator(//
                new OctalUnescaper(), // .between('\1', '\377'),
                new UnicodeUnescaper(), //
                new HexadecimalUnescaper(), //
                new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE), //
                new LookupTranslator(Collections.unmodifiableMap(unescapeJavaMap)) //
        );
    }
}
