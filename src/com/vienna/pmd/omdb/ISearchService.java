package com.vienna.pmd.omdb;

import com.google.inject.ImplementedBy;
import com.vienna.pmd.omdb.impl.ApiSearchRequest;
import com.vienna.pmd.omdb.impl.OmdbSearchService;
import com.vienna.pmd.omdb.xml.Root;

/**
 * Erlaubt Suche nach Filmen und Serien auf http://www.omdbapi.com
 *
 * Created by bobmo on 23.10.2016.
 */
@ImplementedBy(ApiSearchRequest.class)
public interface ISearchService<T> {

    /**
     * Feuert eine Suche auf omdb.
     * Wird kein {@link ResponseType} im request angegeben wird per default {@link ResponseType#XML} benutzt
     *
     * @param request
     * @return
     */
    public T search(ISearchRequest request) throws SearchException;

    public String getSearchScheme();
    public String getSearchUrl();
    public String getSearchPath();

    public String getIdParameter();
    public String getTitleParameter();
}
