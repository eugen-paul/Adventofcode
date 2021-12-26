package net.adventofcode.y2015.day8;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.text.translate.CharSequenceTranslator;

public class HexadecimalUnescaper extends CharSequenceTranslator {

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
        final int remaining = input.length() - index - 1; // how many characters left, ignoring the first \
        final StringBuilder builder = new StringBuilder();
        if (input.charAt(index) == '\\' && remaining > 0 && isXChar(input.charAt(index + 1))) {
            final int next2 = index + 2;
            final int next3 = index + 3;

            if (remaining > 2 && isHexChar(input.charAt(next2)) && isHexChar(input.charAt(next3))) {
                builder.append(input.charAt(next2));
                builder.append(input.charAt(next3));
            }

            out.write((char) Integer.parseInt(builder.toString(), 16));
            return 4;
        }
        return 0;
    }

    private boolean isXChar(final char ch) {
        return ch == 'x' || ch == 'X';
    }

    private boolean isHexChar(final char ch) {
        return (ch >= '0' && ch <= '9') //
                || (ch >= 'a' && ch <= 'f')//
                || (ch >= 'A' && ch <= 'F');
    }
}
