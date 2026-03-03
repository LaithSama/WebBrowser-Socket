package com.socketbrowser.model;

import java.util.ArrayList;
import java.util.List;

// Gère l'historique de navigation (comme les boutons Retour/Avant)
public class NavigationHistory {
    private List<Page> history;      // Liste de toutes les pages visitées
    private int currentIndex;        // Index de la page actuelle

    public NavigationHistory() {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
    }

    // Ajoute une nouvelle page à l'historique
    // Supprime l'historique "avant" si on navigue après un retour
    public void addPage(Page page) {
        // Si on est au milieu de l'historique, supprimer ce qui vient après
        if (currentIndex < history.size() - 1) {
            history.subList(currentIndex + 1, history.size()).clear();
        }

        history.add(page);
        currentIndex++;
    }

    // Retourne à la page précédente
    // @return la page précédente, ou null si impossible
    public Page goBack() {
        if (canGoBack()) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null;
    }

    // Avance à la page suivante
    // @return la page suivante, ou null si impossible
    public Page goForward() {
        if (canGoForward()) {
            currentIndex++;
            return history.get(currentIndex);
        }
        return null;
    }

    // Vérifie si on peut retourner en arrière
    public boolean canGoBack() {
        return currentIndex > 0;
    }

    // Vérifie si on peut avancer
    public boolean canGoForward() {
        return currentIndex < history.size() - 1;
    }

    // Récupère la page actuelle
    public Page getCurrentPage() {
        if (currentIndex >= 0 && currentIndex < history.size()) {
            return history.get(currentIndex);
        }
        return null;
    }

    // Récupère tout l'historique
    public List<Page> getHistory() {
        return new ArrayList<>(history); // Retourne une copie
    }

    // Vide l'historique
    public void clear() {
        history.clear();
        currentIndex = -1;
    }

    // Récupère la taille de l'historique
    public int size() {
        return history.size();
    }

    @Override
    public String toString() {
        return "Historique : " + history.size() + " pages, index actuel : " + currentIndex;
    }
}