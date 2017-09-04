package com.vienna.pmd.omdb.impl;

import com.vienna.pmd.omdb.ITitleSearchRequest;
import com.vienna.pmd.omdb.PlotType;
import com.vienna.pmd.omdb.ResponseType;
import com.vienna.pmd.omdb.SearchType;

/**
 * Created by bobmo on 23.10.2016.
 */
public class TitleSearchRequest extends SearchRequest implements ITitleSearchRequest {

    protected String title;
    protected Integer year;
    protected PlotType plotType;

    public TitleSearchRequest(String title, Integer year, PlotType plotType, ResponseType responseType) {
        this.title = title;
        this.year = year;
        this.plotType = plotType;
        this.responseType = responseType;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public PlotType getPlotType() {
        return plotType;
    }

    @Override
    public SearchType getSearchType() {
        return SearchType.TITLE;
    }
}
