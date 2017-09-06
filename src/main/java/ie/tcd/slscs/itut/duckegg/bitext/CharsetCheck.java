/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Jim O'Regan
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ie.tcd.slscs.itut.duckegg.bitext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharsetCheck extends Filter {
    private static final Map<String, String> CHARSETS;
    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latin", "\\p{InBasic_Latin}|\\p{InLatin-1_Supplement}|\\p{InLatin_Extended-A}|\\p{InLatin_Extended-B}|\\p{InLatin_Extended_Additional}");
        map.put("greek", "\\p{InGreek}|\\p{InGreek_Extended}");
        map.put("cyrillic", "\\p{InCyrillic}|\\p{InCyrillic_Supplementary}");
        map.put("hebrew", "\\p{InHebrew}");
        map.put("arabic", "\\p{InArabic}");
        CHARSETS = Collections.unmodifiableMap(map);
    }

    public static Pattern getPattern(String s) {
        return Pattern.compile("((?:" + CHARSETS.get(s) + ")+)");
    }
    public static List<Pattern> getPatterns(String src, String trg) {
        List<Pattern> out = new ArrayList<Pattern>();
        for(String s : CHARSETS.keySet()) {
            if(!s.equals(src) && !s.equals(trg)) {
                out.add(getPattern(s));
            }
        }
        return out;
    }

    @Override
    public boolean needsLanguageProperties() {
        return true;
    }

    @Override
    public boolean match(SLTLPair input) {
        List<Pattern> pats = getPatterns(srcProps.script, trgProps.script);
        boolean out = false;
        boolean verbose = true;
        for(Pattern p : pats) {
            Matcher srcm = p.matcher(input.source);
            Matcher trgm = p.matcher(input.target);

            while(srcm.find()) {
                String matched = input.source.substring(srcm.start(), srcm.end());
                if(!input.target.contains(matched)) {
                    if(verbose) {
                        out = true;
                        System.err.println("Source sentence " + input.source + " contains non-native script: \"" + matched + "\"");
                    } else {
                        return true;
                    }
                }
            }
            while(trgm.find()) {
                String matched = input.target.substring(trgm.start(), trgm.end());
                if(!input.source.contains(matched)) {
                    if(verbose) {
                        out = true;
                        System.err.println("Target sentence " + input.target + " contains non-native script: \"" + matched + "\"");
                    } else {
                        return true;
                    }
                }
            }
        }

        return out;
    }
}
