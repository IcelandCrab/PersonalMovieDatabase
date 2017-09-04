package com.vienna.pmd.omdb.impl;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.vienna.pmd.omdb.*;
import com.vienna.pmd.omdb.json.Movie;
import com.vienna.pmd.omdb.json.Movies;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ApiSearchRequest implements ISearchService<Movies> {

    private static final String SEARCH_SCHEME = "http";
    private static final String SEARCH_URL = "www.theimdbapi.org";
    private static final String SEARCH_PATH = "/api/find/movie";
    private static final String SEARCH_PATH_ID = "/api/movie";

    private static final String ID_PARAMETER = "movie_id";
    private static final String TITLE_PARAMETER = "title";

    private CloseableHttpClient client = null;

    public ApiSearchRequest() { client = HttpClients.createDefault(); }

    @Override
    public Movies search(ISearchRequest request) throws SearchException {
        URIBuilder uriBuilder;

        if(request.getSearchType() == SearchType.ID)
            uriBuilder = idSearch((IIDSearchRequest) request);
        else if(request.getSearchType() == SearchType.TITLE)
            uriBuilder = titleSearch((ITitleSearchRequest) request);
        else
            throw new SearchException("Unbekannter Suchtyp '" + request.getClass().getName() + "', Suche kann nicht durchgeführt werden!");

        return sendSearch(uriBuilder, request.getSearchType());
    }

    @Override
    public String getSearchScheme() {
        return SEARCH_SCHEME;
    }

    @Override
    public String getSearchUrl() {
        return SEARCH_URL;
    }

    @Override
    public String getSearchPath() {
        return SEARCH_PATH;
    }

    @Override
    public String getIdParameter() {
        return ID_PARAMETER;
    }

    @Override
    public String getTitleParameter() {
        return TITLE_PARAMETER;
    }

    private Movies sendSearch(URIBuilder uriBuilder, SearchType searchType) throws SearchException {
        HttpEntity entity = null;

        try (CloseableHttpResponse response = client.execute(new HttpGet(uriBuilder.build()));
             InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());) {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Movies movie = new Movies();
            if(searchType == SearchType.TITLE)
                movie.setMovies(Arrays.asList(gson.fromJson(reader, Movie[].class)));
            else if(searchType == SearchType.ID)
                movie.setMovies(Arrays.asList(gson.fromJson(reader, Movie.class)));

            return movie;
        } catch (URISyntaxException e1) {
            throw new SearchException("Fehler bei Suche: Suchanfrage konnte nicht erstellt werden.", e1);
        } catch (IOException e2) {
            throw new SearchException("Unbekannter Fehler bei Suche.", e2);
        } catch (XStreamException e2) {
            throw new SearchException("Suchergebnis konnte nicht umgewandelt werden.", e2);
        }
    }

    private URIBuilder idSearch(IIDSearchRequest request) {
        Preconditions.checkArgument(request.getImdbId() != null, "Für einen ID Suche muss eine IMDB ID angegeben werden!");

        URIBuilder uriBuilder = new URIBuilder().setScheme(SEARCH_SCHEME)
                .setHost(SEARCH_URL)
                .setPath(SEARCH_PATH_ID)
                .setParameter(ID_PARAMETER, request.getImdbId());

        return uriBuilder;
    }

    private URIBuilder titleSearch(ITitleSearchRequest request) {
        Preconditions.checkArgument(request.getTitle() != null, "Für eine Titelsuche muss ein Titel angegeben werden!");

        URIBuilder uriBuilder = new URIBuilder().setScheme(SEARCH_SCHEME)
                .setHost(SEARCH_URL)
                .setPath(SEARCH_PATH)
                .setParameter(TITLE_PARAMETER, request.getTitle());

        return uriBuilder;
    }
}
