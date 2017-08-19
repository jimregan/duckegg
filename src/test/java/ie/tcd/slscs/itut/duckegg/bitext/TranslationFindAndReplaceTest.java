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

import org.junit.Test;

import static org.junit.Assert.*;

public class TranslationFindAndReplaceTest {
    public void testReplace1() throws Exception {
        SLTLPair in = new SLTLPair("689916", "‘relevant date’ means", "ciallaíonn dta iomchuí");
        SLTLPair exp = new SLTLPair("689916", "‘relevant date’ means", "ciallaíonn dáta iomchuí");
        SLTLPair out = TranslationFindAndReplace.replace(in, "relevant date", "dta iomchuí", "dáta iomchuí", false);
        assertEquals(out.target, exp.target);
        SLTLPair out2 = TranslationFindAndReplace.replace(in, "relevant dates?", "dta iomchuí", "dáta iomchuí", true);
        assertEquals(out2.target, exp.target);
        SLTLPair out3 = TranslationFindAndReplace.replace(in, "awesome date", "dta iomchuí", "dáta iomchuí", false);
        assertNotEquals(out.target, exp.target);
        SLTLPair out4 = TranslationFindAndReplace.replace(in, "awesome dates?", "dta iomchuí", "dáta iomchuí", true);
        assertNotEquals(out2.target, exp.target);
    }

}