package com.vienna.pmd.omdb.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.vienna.pmd.omdb.PlotType;

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
    @XStreamConverter(TypeConverter.class)
    private Type type;

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

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public Type getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }
}
