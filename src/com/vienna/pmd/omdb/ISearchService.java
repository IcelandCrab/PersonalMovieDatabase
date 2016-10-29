package com.vienna.pmd.omdb;

import com.google.inject.ImplementedBy;
import com.vienna.pmd.omdb.impl.SearchService;
import com.vienna.pmd.omdb.xml.Root;

/**
 * Erlaubt Suche nach Filmen und Serien auf http://www.omdbapi.com
 *
 * Created by bobmo on 23.10.2016.
 */
@ImplementedBy(SearchService.class)
public interface ISearchService {

    public static final String SEARCH_SCHEME = "http";
    public static final String SEARCH_URL = "www.omdbapi.com";
    public static final String SEARCH_PATH = "/";

    public static final String ID_PARAMETER = "i";
    public static final String TITLE_PARAMETER = "s";
    public static final String RESPONSETYPE_PARAMETER = "r";

    /**
     * Feuert eine Suche auf omdb.
     * Wird kein {@link ResponseType} im request angegeben wird per default {@link ResponseType#XML} benutzt
     *
     * @param request
     * @return
     */
    public Root search(ISearchRequest request) throws SearchException;
}
