package com.vienna.pmd.omdb.impl;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.vienna.pmd.omdb.*;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by bobmo on 23.10.2016.
 */
public class OmdbSearchService implements ISearchService<Root> {

    private static final String SEARCH_SCHEME = "http";
    private static final String SEARCH_URL = "www.omdbapi.com";
    private static final String SEARCH_PATH = "/";

    private static final String ID_PARAMETER = "i";
    private static final String TITLE_PARAMETER = "s";
    private static final String RESPONSETYPE_PARAMETER = "r";
    private static final String PLOT_PARAMETER = "plot";

    private CloseableHttpClient client = null;

    public OmdbSearchService() {
        client = HttpClients.createDefault();
    }

    @Override
    public Root search(ISearchRequest request) throws SearchException {
        URIBuilder uriBuilder;

        if(request instanceof IIDSearchRequest)
            uriBuilder = idSearch((IIDSearchRequest) request);
        else if(request instanceof ITitleSearchRequest)
            uriBuilder = titleSearch((ITitleSearchRequest) request);
        else
            throw new SearchException("Unbekannter Suchtyp '" + request.getClass().getName() + "', Suche kann nicht durchgeführt werden!");

        return sendSearch(uriBuilder);
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

    private Root sendSearch(URIBuilder uriBuilder) throws SearchException {
        HttpEntity entity = null;

        try (CloseableHttpResponse response = client.execute(new HttpGet(uriBuilder.build()));) {
            entity = response.getEntity();

            XStream stream = new XStream();
            stream.processAnnotations(new Class[]{Result.class, Root.class});
            Root root = (Root) stream.fromXML(entity.getContent());

            return root;
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
            .setPath(SEARCH_PATH)
            .setParameter(ID_PARAMETER, request.getImdbId())
            .setParameter(PLOT_PARAMETER, PlotType.LONG.getEncodedName())
            .setParameter(RESPONSETYPE_PARAMETER, request.getResponseType() != null ? request.getResponseType().name() : ResponseType.XML.name());

        return uriBuilder;
    }

    private URIBuilder titleSearch(ITitleSearchRequest request) {
        Preconditions.checkArgument(request.getTitle() != null, "Für eine Titelsuche muss ein Titel angegeben werden!");

        URIBuilder uriBuilder = new URIBuilder().setScheme(SEARCH_SCHEME)
            .setHost(SEARCH_URL)
            .setPath(SEARCH_PATH)
            .setParameter(TITLE_PARAMETER, request.getTitle())
            .setParameter(RESPONSETYPE_PARAMETER, request.getResponseType() != null ? request.getResponseType().name() : ResponseType.XML.name());

        return uriBuilder;
    }
}
