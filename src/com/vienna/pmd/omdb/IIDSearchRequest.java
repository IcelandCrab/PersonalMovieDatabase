package com.vienna.pmd.omdb;

/**
 * Created by bobmo on 23.10.2016.
 */
public interface IIDSearchRequest extends ISearchRequest {

    /**
     * Erlaubt Suche nach einem eindeutigen Film
     * @return Eindeutige IMDB ID zur Suche nach einem bestimmten Film
     */
    public String getImdbId();
}
