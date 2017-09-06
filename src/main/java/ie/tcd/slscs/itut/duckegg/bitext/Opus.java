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

import ie.tcd.slscs.itut.duckegg.format.opus.Text;

import java.util.ArrayList;
import java.util.List;

public class Opus {
    public static void main(String[] args) throws Exception {
        if(args.length != 3) {
            throw new Exception("Usage: basename source-language target-language");
        }
        String base = args[0];
        String sl = args[1];
        String tl = args[2];
        LanguageProperties enlp = new LanguageProperties("english", "latin");
        LanguageProperties galp = new LanguageProperties("irish", "latin");

        List<SLTLPair> orig = Text.read(base, sl, tl);
        List<SLTLPair> out = new ArrayList<SLTLPair>();
        List<SLTLPair> del = new ArrayList<SLTLPair>();

        CharsetCheck ck = new CharsetCheck();
        ck.setLanguageProperties(enlp, galp);

        for (SLTLPair sent : orig) {
            if(!ck.match(sent)) {
                out.add(sent);
            } else {
                del.add(sent);
            }
        }

        Text.write(out, base + "-filt", sl, tl);
        Text.write(del, base + "-removed", sl, tl);
    }
}
