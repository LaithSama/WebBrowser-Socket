package com.socketbrowser.model;

import java.time.LocalDateTime;

// Représente une page web avec son URL et son contenu HTML
public class Page {
    private String url;              // URL de la page
    private String htmlContent;      // Contenu HTML brut
    private String title;            // Titre de la page (extrait du HTML)
    private LocalDateTime visitTime; // Date/heure de visite
    private int statusCode;          // Code HTTP (200, 404, etc.)

    // Constructeur complet
    public Page(String url, String htmlContent, int statusCode) {
        this.url = url;
        this.htmlContent = htmlContent;
        this.statusCode = statusCode;
        this.visitTime = LocalDateTime.now();
        this.title = extractTitle(htmlContent);
    }

    // Constructeur simple (code 200 par défaut)
    public Page(String url, String htmlContent) {
        this(url, htmlContent, 200);
    }

    // Extrait le titre du HTML (entre balises <title>)
    private String extractTitle(String html) {
        if (html == null) return "Page sans titre";

        int startIndex = html.toLowerCase().indexOf("<title>");
        int endIndex = html.toLowerCase().indexOf("</title>");

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return html.substring(startIndex + 7, endIndex).trim();
        }

        return "Page sans titre";
    }

    // Getters et Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
        this.title = extractTitle(htmlContent);
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return title + " (" + url + ")";
    }
}