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

package ie.tcd.slscs.itut.duckegg.format.gaois;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class TU {
    String id;
    Map<String, String> segments;
    TU() {
        this.segments = new HashMap<String, String>();
    }
    TU(String id, Map<String, String> segs) {
        this();
        this.id = id;
        this.segments = segs;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Map<String, String> getSegments() {
        return segments;
    }
    public void setSegments(Map<String, String> segments) {
        this.segments = segments;
    }
    private static String chomp(String s) {
        int start = 0;
        int end = s.length();
        if(s.startsWith("\n")) {
            start++;
        }
        if(s.endsWith("\n")) {
            end--;
        }
        return s.substring(start, end);
    }
    public static TU fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("tu")) {
            String id = n.getAttributes().getNamedItem("tuid").getTextContent();
            Map<String, String> segs = new HashMap<String, String>();
            for (int i = 0; i < n.getChildNodes().getLength(); i++) {
                String lang = "";
                String seg = "";
                Node tuvnode = n.getChildNodes().item(i);
                if (tuvnode.getNodeName().equals("tuv")) {
                    NamedNodeMap attrs = tuvnode.getAttributes();
                    if (attrs == null || attrs.getNamedItem("xml:lang") == null) {
                        throw new Exception("Attributes empty: expected xml:lang");
                    }
                    lang = attrs.getNamedItem("xml:lang").getTextContent();
                    for(int j = 0; j < tuvnode.getChildNodes().getLength(); j++) {
                        Node segnode = tuvnode.getChildNodes().item(j);
                        if(segnode.getNodeName().equals("seg")) {
                            if (segnode.getChildNodes().getLength() == 1 && segnode.getFirstChild().getNodeName().equals("#text")) {
                                seg = chomp(segnode.getFirstChild().getTextContent());
                            } else if(segnode.getChildNodes().getLength() == 0) {
                                System.err.println("Empty <seg>, id: " + id);
                                seg = "";
                            } else {
                                throw new Exception("Unexpected node: expected text, got " + segnode.getFirstChild().getNodeName());
                            }
                        } else if(segnode.getNodeName().equals("#text") && segnode.getTextContent().trim().equals("")) {
                            //
                        } else {
                            throw new Exception("Unexpected node: expected seg, got "  + tuvnode.getNodeName());
                        }
                    }
                } else if (tuvnode.getNodeName().equals("#text") && tuvnode.getTextContent().trim().equals("")) {
                    // Do nothing
                } else {
                    throw new Exception("Unexpected element: expected tuv, got " +  tuvnode.getNodeName());
                }
                if (!lang.equals("") && !seg.equals("")) {
                    segs.put(lang, seg);
                }
            }
            return new TU(id, segs);
        } else {
           throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }
}
