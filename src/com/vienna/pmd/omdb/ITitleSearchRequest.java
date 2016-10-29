package com.vienna.pmd.omdb;

/**
 * Created by bobmo on 23.10.2016.
 */
public interface ITitleSearchRequest extends ISearchRequest {

    /**
     * Erlaubt Suche nach einem Titel, erlaubt Nutzung der Wildcard '*'
     * @return Suchbegriff für Titel
     */
    public String getTitle();

    /**
     * Erlaubt Einschränkung der Suche nach Jahr der Veröffentlichung des Filmes
     * @return Jahr der Veröffentlichung
     */
    public Integer getYear();

    /**
     * Gibt an, ob eine Kurz- oder Langversion der Zusammenfassung zurückgegeben werden soll.
     * @return
     */
    public PlotType getPlotType();
}
