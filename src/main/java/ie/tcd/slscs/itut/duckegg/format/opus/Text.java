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
package ie.tcd.slscs.itut.duckegg.format.opus;

import ie.tcd.slscs.itut.duckegg.bitext.SLTLPair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Text {
    public static File tryFile(String base, String lang) throws Exception {
        String filename = base;
        if(filename.endsWith(".")) {
            base += lang;
        } else {
            base += ".";
            base += lang;
        }
        File f = new File(base + lang);
        if(f.exists() && f.canRead()) {
            return f;
        } else {
            throw new Exception("Cannot open " + filename + " for reading");
        }
    }
    public static List<String> fileToStringList(File f) throws IOException {
        List<String> out = new ArrayList<String>();
        int lineno = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                lineno++;
                out.add(line);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file " + f.getName() + " at line " + lineno + ":" + e.getMessage());
        }
        return out;
    }
    public static List<SLTLPair> read(String base, String src, String trg) throws Exception {
        File source = tryFile(base, src);
        File target = tryFile(base, trg);
        List<SLTLPair> out = new ArrayList<SLTLPair>();
        List<String> srcTxt = fileToStringList(source);
        List<String> trgTxt = fileToStringList(target);

        if(srcTxt.size() != trgTxt.size()) {
            throw new Exception("File mismatch: " + src + " had " + srcTxt.size() + " lines, " + trg + " had " + trgTxt.size());
        }
        for(int i = 0; i < srcTxt.size(); i++) {
            out.add(new SLTLPair(srcTxt.get(i), trgTxt.get(i)));
        }

        return out;
    }
}
