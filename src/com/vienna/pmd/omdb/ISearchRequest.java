package com.vienna.pmd.omdb;

/**
 * Created by bobmo on 13.10.2016.
 *
 * Interface für Suche auf http://www.omdbapi.com/
 */
public interface ISearchRequest {

    /**
     * Legt fest, in welchem Format das Ergebnis geliefert wird
     * @return den spezifizierten Formattyp (z.B. XML)
     */
    public ResponseType getResponseType();
}
