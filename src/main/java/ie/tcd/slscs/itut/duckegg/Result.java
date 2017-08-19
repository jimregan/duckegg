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
import java.lang.Integer;
import java.lang.Double;
import java.util.ArrayList;
import java.util.List;

public class Result {
    ResultType type;
    int int_result;
    double double_result;
    String string_result;
    Currency currency_result;
    String raw;
    List<String> rawparts;
    List<Result> fuzzy_results;
    public Result() {
        this.rawparts = new ArrayList<String>();
        this.fuzzy_results = new ArrayList<Result>();
    }
    public Result(String raw) {
        this();
        this.raw = raw;
    }
    public Result(ResultType rt) {
        this();
        this.type = rt;
    }
    public void addRawResult(String s) {
        this.rawparts.add(s);
    }
    public List<String> getRawparts() {
        return rawparts;
    }
    public String getRaw() {
        return this.raw;
    }
    public ResultType getType() {
        return type;
    }
    public void setResult(int i) {
        type = ResultType.INT;
        this.int_result = i;
    }
    public void addFuzzyResult(Result r) {
        fuzzy_results.add(r);
    }
    public List<Result> getFuzzy_results() {
        return fuzzy_results;
    }
    public void setType(ResultType rt) {
        this.type = rt;
    }
    public void setResult(double d) {
        type = ResultType.DOUBLE;
        this.double_result = d;
    }
    public void setResult(String s) {
        type = ResultType.STRING;
        this.string_result = s;
    }
    public void setResult(Currency c) {
        type = ResultType.CURRENCY;
        this.currency_result = c;
    }
    public int getInt() {
        return int_result;
    }
    public double getDouble() {
        return double_result;
    }
    public String getString() {
        if(this.type == ResultType.STRING) {
            return this.string_result;
        } else if(this.type == ResultType.INT) {
            return Integer.toString(this.int_result);
        } else if(this.type == ResultType.DOUBLE) {
            return Double.toString(this.double_result);
        } else {
            return null;
        }
    }
    public Currency getCurrency() {
        return currency_result;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(this == o) {
            return true;
        }
        if(!(o instanceof Result)) {
            return false;
        }
        final Result res = (Result) o;
        if(res.getType() != getType()) {
            return false;
        }
        if(getType() == ResultType.DOUBLE && getDouble() == res.getDouble()) {
            return true;
        } else if(getType() == ResultType.INT && getInt() == res.getInt()) {
            return true;
        } else if(getType() == ResultType.STRING && getString().equals(res.getString())) {
            return true;
        } else if(getType() == ResultType.CURRENCY && getCurrency().equals(res.getCurrency())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fuzzyEquals(List<Result> list) {
        for(Result r : list) {
            if(this.equals(r)) {
                return true;
            }
        }
        return false;
    }
}
