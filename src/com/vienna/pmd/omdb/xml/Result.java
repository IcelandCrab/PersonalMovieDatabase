package com.vienna.pmd.omdb.xml;

import com.sun.org.apache.xpath.internal.operations.String;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by bobmo on 12.10.2016.
 */
@XStreamAlias(value = "result")
public class Result {

    @XStreamAsAttribute
    @XStreamAlias("title")
    private String title;

    @XStreamAsAttribute
    @XStreamAlias("year")
    private String year;

    @XStreamAsAttribute
    @XStreamAlias("imdbID")
    private String imdbID;

    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;

    @XStreamAsAttribute
    @XStreamAlias("poster")
    private String poster;

    @XStreamAsAttribute
    @XStreamAlias("rated")
    private String rated;

    @XStreamAsAttribute
    @XStreamAlias("release")
    private String released;

    @XStreamAsAttribute
    @XStreamAlias("runtime")
    private String runtime;

    @XStreamAsAttribute
    @XStreamAlias("genre")
    private String genre;

    @XStreamAsAttribute
    @XStreamAlias("director")
    private String director;

    @XStreamAsAttribute
    @XStreamAlias("writer")
    private String writer;

    @XStreamAsAttribute
    @XStreamAlias("actors")
    private String actors;

    @XStreamAsAttribute
    @XStreamAlias("plot")
    private String plot;

    @XStreamAsAttribute
    @XStreamAlias("language")
    private String language;

    @XStreamAsAttribute
    @XStreamAlias("country")
    private String country;

    @XStreamAsAttribute
    @XStreamAlias("awards")
    private String awards;

    @XStreamAsAttribute
    @XStreamAlias("metascore")
    private String metascore;

    @XStreamAsAttribute
    @XStreamAlias("imdbRating")
    private String imdbRating;

    @XStreamAsAttribute
    @XStreamAlias("imdbVotes")
    private String imdbVotes;
}
