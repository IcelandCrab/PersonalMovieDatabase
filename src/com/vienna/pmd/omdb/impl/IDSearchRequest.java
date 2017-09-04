package com.vienna.pmd.omdb.impl;

import com.vienna.pmd.omdb.IIDSearchRequest;
import com.vienna.pmd.omdb.ISearchRequest;
import com.vienna.pmd.omdb.ResponseType;
import com.vienna.pmd.omdb.SearchType;

/**
 * Created by bobmo on 23.10.2016.
 */
public class IDSearchRequest extends SearchRequest implements IIDSearchRequest {

    private String imdbId;

    public IDSearchRequest(String imdbId, ResponseType responseType) {
        this.imdbId = imdbId;
        this.responseType = responseType;
    }

    @Override
    public String getImdbId() {
        return imdbId;
    }


    @Override
    public SearchType getSearchType() {
        return SearchType.ID;
    }
}
