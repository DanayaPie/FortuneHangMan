package constant;

import java.util.HashSet;
import java.util.Set;

public class WordConstant {

    public static final Set<Character> symbolsNoSpaceAfter() {

        Set<Character> symbolsNoSpaceAfterSet = new HashSet<>();
        symbolsNoSpaceAfterSet.add('\'');
        symbolsNoSpaceAfterSet.add('-');

        return symbolsNoSpaceAfterSet;
    }

    public static final Set<Character> symbolsWithSpaceAfter() {

        Set<Character> symbolsWithSpaceAfterSet = new HashSet<>();
        symbolsWithSpaceAfterSet.add('!');
        symbolsWithSpaceAfterSet.add('%');
        symbolsWithSpaceAfterSet.add('&');
        symbolsWithSpaceAfterSet.add(':');
        symbolsWithSpaceAfterSet.add(';');
        symbolsWithSpaceAfterSet.add('$');
        symbolsWithSpaceAfterSet.add(',');

        return symbolsWithSpaceAfterSet;
    }


    public static final Set<Character> vowel() {

        Set<Character> vowelsSet = new HashSet<>();
        vowelsSet.add('A');
        vowelsSet.add('E');
        vowelsSet.add('I');
        vowelsSet.add('O');
        vowelsSet.add('U');

        return vowelsSet;
    }
}
