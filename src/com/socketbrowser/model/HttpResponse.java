package com.socketbrowser.model;

import java.util.HashMap;
import java.util.Map;

// Représente une réponse HTTP complète
public class HttpResponse {
    private int statusCode;           // Code HTTP (200, 404, etc.)
    private String statusMessage;     // Message (OK, Not Found, etc.)
    private Map<String, String> headers; // En-têtes HTTP
    private String body;              // Corps de la réponse (HTML)

    public HttpResponse() {
        this.headers = new HashMap<>();
    }

    // Constructeur avec les éléments essentiels
    public HttpResponse(int statusCode, String statusMessage, String body) {
        this();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.body = body;
    }

    // Getters et Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // Vérifie si la réponse est un succès (code 2xx)
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }

    // Vérifie si c'est une erreur client (4xx)
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    // Vérifie si c'est une erreur serveur (5xx)
    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 600;
    }

    @Override
    public String toString() {
        return "HTTP " + statusCode + " " + statusMessage;
    }
}