package com.vienna.pmd.omdb;

/**
 * Created by bobmo on 13.10.2016.
 */
public enum PlotType {
    SHORT,
    LONG;

    public String getEncodedName() {
        switch (this) {
            case LONG: return "full";
            case SHORT: return "short";
            default: return "";
        }
    }

    public static PlotType convertFromString(String enc) {
        if(enc.equals("full"))
            return PlotType.LONG;
        else if(enc.equals("short"))
            return PlotType.SHORT;

        return null;
    }
}
