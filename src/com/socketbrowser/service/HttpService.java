package com.socketbrowser.service;

import com.socketbrowser.model.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Service de communication HTTP via Socket
// Envoie des requêtes HTTP à XAMPP et récupère les réponses
public class HttpService {
    public HttpResponse sendGetRequest(String url) {
        HttpResponse response = new HttpResponse();

        try {
            // Analyser l'URL pour extraire host, port et path
            UrlInfo urlInfo = parseUrl(url);

            // Créer une connexion socket vers le serveur
            try (Socket socket = new Socket(urlInfo.host, urlInfo.port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Construire et envoyer la requête HTTP GET
                String httpRequest = buildHttpGetRequest(urlInfo.path, urlInfo.host);
                out.print(httpRequest);
                out.flush();

                // Lire la réponse HTTP
                response = parseHttpResponse(in);

                System.out.println("✓ Requête réussie : " + url);

            }

        } catch (Exception e) {
            System.err.println("✗ Erreur lors de la requête : " + e.getMessage());
            e.printStackTrace();

            // Créer une réponse d'erreur
            response.setStatusCode(500);
            response.setStatusMessage("Erreur de connexion");
            response.setBody(generateErrorHtml(500, "Erreur de connexion au serveur", e.getMessage()));
        }

        return response;
    }

    // Parse une URL et extrait host, port et path
    private UrlInfo parseUrl(String url) throws Exception {
        UrlInfo info = new UrlInfo();

        // Retirer le protocole http://
        String urlWithoutProtocol = url.replaceFirst("^https?://", "");

        // Séparer host et path
        int slashIndex = urlWithoutProtocol.indexOf('/');
        String hostPart;

        if (slashIndex != -1) {
            hostPart = urlWithoutProtocol.substring(0, slashIndex);
            info.path = urlWithoutProtocol.substring(slashIndex);
        } else {
            hostPart = urlWithoutProtocol;
            info.path = "/";
        }

        // Séparer host et port
        int colonIndex = hostPart.indexOf(':');
        if (colonIndex != -1) {
            info.host = hostPart.substring(0, colonIndex);
            info.port = Integer.parseInt(hostPart.substring(colonIndex + 1));
        } else {
            info.host = hostPart;
            info.port = 80; // Port HTTP par défaut
        }

        return info;
    }

    // Construit une requête HTTP GET
    private String buildHttpGetRequest(String path, String host) {
        StringBuilder request = new StringBuilder();

        // Ligne de requête
        request.append("GET ").append(path).append(" HTTP/1.1\r\n");

        // En-têtes
        request.append("Host: ").append(host).append("\r\n");
        request.append("User-Agent: SocketBrowser/1.0\r\n");
        request.append("Accept: text/html\r\n");
        request.append("Connection: close\r\n");

        // Ligne vide pour terminer les en-têtes
        request.append("\r\n");

        return request.toString();
    }

    // Parse la réponse HTTP reçue du serveur
    private HttpResponse parseHttpResponse(BufferedReader in) throws Exception {
        HttpResponse response = new HttpResponse();

        // Lire la première ligne (Status Line)
        String statusLine = in.readLine();
        if (statusLine == null) {
            throw new Exception("Réponse vide du serveur");
        }

        // Analyser : HTTP/1.1 200 OK
        String[] statusParts = statusLine.split(" ", 3);
        if (statusParts.length >= 2) {
            response.setStatusCode(Integer.parseInt(statusParts[1]));
            response.setStatusMessage(statusParts.length >= 3 ? statusParts[2] : "");
        }

        // Lire les en-têtes HTTP
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
                String key = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                response.addHeader(key, value);
            }
        }

        // Lire le corps de la réponse (HTML)
        StringBuilder body = new StringBuilder();
        while ((line = in.readLine()) != null) {
            body.append(line).append("\n");
        }
        response.setBody(body.toString());

        // Si erreur, générer une page HTML d'erreur
        if (!response.isSuccess()) {
            response.setBody(generateErrorHtml(
                    response.getStatusCode(),
                    response.getStatusMessage(),
                    "Le serveur a retourné une erreur."
            ));
        }

        return response;
    }

    // Génère une page HTML d'erreur
    private String generateErrorHtml(int code, String message, String details) {
        return "<!DOCTYPE html>" +
                "<html><head><title>Erreur " + code + "</title>" +
                "<style>body{font-family:Arial;padding:50px;background:#f5f5f5;}" +
                "h1{color:#d32f2f;}p{color:#555;}</style></head>" +
                "<body><h1>Erreur " + code + "</h1>" +
                "<h2>" + message + "</h2>" +
                "<p>" + details + "</p>" +
                "<p><a href='javascript:history.back()'>← Retour</a></p>" +
                "</body></html>";
    }

    // Classe interne pour stocker les infos d'URL
    private static class UrlInfo {
        String host;
        int port;
        String path;
    }
}