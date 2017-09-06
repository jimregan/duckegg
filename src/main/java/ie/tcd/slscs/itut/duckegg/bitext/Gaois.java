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

import ie.tcd.slscs.itut.duckegg.format.gaois.TMX;
import ie.tcd.slscs.itut.duckegg.format.gaois.TU;
import ie.tcd.slscs.itut.duckegg.format.opus.Text;

import java.util.ArrayList;
import java.util.List;

public class Gaois {
    public static void main(String[] args) throws Exception {
        if(args.length != 3) {
            throw new Exception("Usage: filename source-language target-language");
        }
        String filename = args[0];
        String srcLang = args[1];
        String trgLang = args[2];
        String outputFile = filename;
        if(outputFile.endsWith(".tmx")) {
            outputFile = outputFile.substring(0, outputFile.length() - 4);
        }

        TMX tmx = TMX.readFile(filename);
        List<SLTLPair> segs = TUsToSLTLPairs(tmx.getTUs(), srcLang, trgLang);
        List<SLTLPair> changed = new ArrayList<SLTLPair>();

        GaoisFinalNumberAddition numrule = new GaoisFinalNumberAddition();

        for(SLTLPair seg : segs) {
            SLTLPair replace = numrule.replace(seg);
            if(numrule.hadReplacement()) {
                System.err.println("Replacement in segment " + replace.getId());
            }
            changed.add(replace);
        }

        Text.write(changed, outputFile, srcLang, trgLang);
    }

    public static List<SLTLPair> TUsToSLTLPairs(List<TU> tus, String src, String trg) {
        List<SLTLPair> out = new ArrayList<SLTLPair>();
        for(TU tu : tus) {
            String srcseg = tu.getSegments().get(src);
            String trgseg = tu.getSegments().get(trg);
            if(srcseg != null && trgseg != null) {
                out.add(new SLTLPair(tu.getId(), srcseg, trgseg));
            }
        }
        return out;
    }
}