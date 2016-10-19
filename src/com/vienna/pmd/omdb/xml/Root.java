package com.vienna.pmd.omdb.xml;

import com.sun.corba.se.spi.orbutil.fsm.Guard;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobmo on 12.10.2016.
 *
 * Response root eines http://www.omdbapi.com/ querys nach Titel
 */
@XStreamAlias("root")
public class Root {

    @XStreamAsAttribute
    @XStreamAlias("response")
    private Boolean response;

    @XStreamAsAttribute
    @XStreamAlias("totalResults")
    private Integer totalResults;

    @XStreamImplicit(itemFieldName = "result")
    private List<Result> results = new ArrayList<Result>();

    @XStreamImplicit(itemFieldName = "movie")
    private List<Movie> movies = new ArrayList<Movie>();

    public Boolean getResponse() {
        return response;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<Result> getResults() {
        return results;
    }
}
