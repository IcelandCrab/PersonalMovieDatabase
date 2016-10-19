package com.vienna.pmd.omdb.xml;

import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.vienna.pmd.omdb.PlotType;

/**
 * Created by bobmo on 19.10.2016.
 */
public class PlotTypeConverter implements SingleValueConverter {
    @Override
    public boolean canConvert(Class type) {
        return type.equals(String.class);
    }

    @Override
    public String toString(Object obj) {
        PlotType type = (PlotType) obj;

        return type.getEncodedName();
    }

    @Override
    public Object fromString(String str) {
        return PlotType.convertFromString(str);
    }
}
