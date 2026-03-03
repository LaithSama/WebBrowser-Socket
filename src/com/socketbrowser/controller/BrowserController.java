package com.socketbrowser.controller;

import com.socketbrowser.model.HttpResponse;
import com.socketbrowser.model.NavigationHistory;
import com.socketbrowser.model.Page;
import com.socketbrowser.service.HttpService;
import com.socketbrowser.view.BrowserFrame;
import javax.swing.SwingWorker;

// Controller principal du navigateur
// Gère la logique de navigation et fait le lien entre View et Service
public class BrowserController {
    private BrowserFrame view;           // Interface graphique
    private HttpService httpService;     // Service de communication HTTP
    private NavigationHistory history;   // Historique de navigation

    public BrowserController(BrowserFrame view) {
        this.view = view;
        this.httpService = new HttpService();
        this.history = new NavigationHistory();
    }

    // Navigue vers une URL donnée
    // @param url URL à charger
    public void navigateTo(String url) {
        // Validation de l'URL
        if (url == null || url.trim().isEmpty()) {
            view.showError("Veuillez entrer une URL valide");
            return;
        }

        // Ajouter http:// si absent
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        final String finalUrl = url;

        // Afficher le chargement
        view.showLoading();

        // Exécuter la requête dans un thread séparé pour ne pas bloquer l'UI
        new SwingWorker<HttpResponse, Void>() {
            @Override
            protected HttpResponse doInBackground() {
                // Envoyer la requête HTTP
                return httpService.sendGetRequest(finalUrl);
            }

            @Override
            protected void done() {
                try {
                    HttpResponse response = get();

                    // Créer un objet Page
                    Page page = new Page(finalUrl, response.getBody(), response.getStatusCode());

                    // Ajouter à l'historique
                    history.addPage(page);

                    // Afficher la page
                    view.displayPage(page);

                    // Mettre à jour les boutons de navigation
                    view.updateNavigationButtons(history.canGoBack(), history.canGoForward());

                } catch (Exception e) {
                    view.showError("Erreur lors du chargement de la page : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // Retour à la page précédente
    public void goBack() {
        if (history.canGoBack()) {
            Page previousPage = history.goBack();
            if (previousPage != null) {
                view.displayPage(previousPage);
                view.updateNavigationButtons(history.canGoBack(), history.canGoForward());
            }
        }
    }

    // Avance à la page suivante
    public void goForward() {
        if (history.canGoForward()) {
            Page nextPage = history.goForward();
            if (nextPage != null) {
                view.displayPage(nextPage);
                view.updateNavigationButtons(history.canGoBack(), history.canGoForward());
            }
        }
    }

    // Actualise la page actuelle
    public void refresh() {
        Page currentPage = history.getCurrentPage();
        if (currentPage != null) {
            // Supprimer la page actuelle de l'historique
            // et la recharger (cela va l'ajouter à nouveau)
            navigateTo(currentPage.getUrl());
        }
    }

    // Récupère l'historique complet
    public NavigationHistory getHistory() {
        return history;
    }

    // Charge la page d'accueil par défaut
    public void loadHomePage() {
        String homePage = "http://localhost/index.html";
        navigateTo(homePage);
    }
}