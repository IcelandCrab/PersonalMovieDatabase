package com.vienna.pmd.omdb.xml;

import com.sun.org.apache.xpath.internal.operations.String;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by bobmo on 12.10.2016.
 */
@XStreamAlias(value = "result")
public class Result {
    private String title;
    private Integer year;
    private String imdbID;
    private String type;
    private String poster;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
}
