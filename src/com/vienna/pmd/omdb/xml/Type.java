package com.vienna.pmd.omdb.xml;

/**
 * Created by bobmo on 19.10.2016.
 */
public enum Type {
    MOVIE,
    SERIES,
    GAME,
    EPISODE;

    public String getEncodedName() {
        switch (this) {
            case MOVIE: return "movie";
            case SERIES: return "series";
            case GAME: return "game";
            case EPISODE: return "episode";
            default: return "";
        }
    }

    public static Type convertFromString(String enc) {
        if(enc.equals("movie"))
            return Type.MOVIE;
        else if(enc.equals("series"))
            return Type.SERIES;
        else if(enc.equals("game"))
            return Type.GAME;
        else if(enc.equals("episode"))
            return Type.EPISODE;

        return null;
    }
}
