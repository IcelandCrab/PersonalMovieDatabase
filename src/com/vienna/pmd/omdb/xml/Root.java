package com.vienna.pmd.omdb.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by bobmo on 12.10.2016.
 *
 * Response root eines http://www.omdbapi.com/ querys nach Titel
 */
@XStreamAlias("result")
public class Root {

    @XStreamAsAttribute
    @XStreamAlias("response")
    private Boolean response;

    @XStreamAsAttribute
    @XStreamAlias("totalResults")
    private Integer totalResults;

    @XStreamImplicit
    private List<Result> results;

    public Boolean getResponse() {
        return response;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
