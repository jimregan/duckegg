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

package ie.tcd.slscs.itut.duckegg;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class Integer extends Rule {
    Pattern pat = null;
    public Integer() throws Exception {
        super();
        name = "integer";
        pattern = new PatternContainer
                .Builder()
                .addPattern(new Regex("[0-9]+"))
                .build();
        pat = Pattern.compile(getPattern());
    }
    @Override
    public Result setResult(Result res) throws Exception {
        if(res.rawparts.size() == 0 || res.rawparts.get(0) == null) {
            throw new Exception("Empty result");
        } else {
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            Number tmp = format.parse(res.rawparts.get(0));
            res.setResult(tmp.intValue());
        }
        return res;
    }
}
