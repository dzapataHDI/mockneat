package com.mockneat.random.unit.financial;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.mockneat.random.Rand;
import com.mockneat.random.interfaces.RandUnitString;
import com.mockneat.types.enums.CurrencySymbolType;

import static com.mockneat.types.enums.DictType.FOREX_PAIRS;

public class Currencies {
    private Rand rand;

    public Currencies(Rand rand) {
        this.rand = rand;
    }

    public RandUnitString forexPair() {
        return () -> rand.dicts().type(FOREX_PAIRS)::val;
    }

    public RandUnitString code() {
        return () -> rand.from(CurrencySymbolType.class)
                            .mapToString(CurrencySymbolType::getCode)::val;
    }

    public RandUnitString symbol() {
        return () -> rand.from(CurrencySymbolType.class)
                            .mapToString(CurrencySymbolType::getSymbol)::val;
    }

    public RandUnitString name() {
        return () -> rand.from(CurrencySymbolType.class)
                            .mapToString(CurrencySymbolType::getName)::val;
    }
}