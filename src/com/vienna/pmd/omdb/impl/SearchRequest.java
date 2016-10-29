package com.vienna.pmd.omdb.impl;

import com.vienna.pmd.omdb.ISearchRequest;
import com.vienna.pmd.omdb.ResponseType;

/**
 * Created by bobmo on 29.10.2016.
 */
public abstract class SearchRequest implements ISearchRequest {
    protected ResponseType responseType;

    @Override
    public ResponseType getResponseType() {
        return responseType;
    }
}
