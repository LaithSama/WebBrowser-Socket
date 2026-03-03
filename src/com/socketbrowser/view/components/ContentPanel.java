package com.socketbrowser.view.components;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

// Panneau d'affichage du contenu HTML
public class ContentPanel extends JPanel {
    private JEditorPane editorPane;   // Composant pour afficher le HTML
    private JScrollPane scrollPane;   // Barre de défilement
    private JLabel loadingLabel;      // Label de chargement

    public ContentPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // JEditorPane pour afficher le HTML
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setEditorKit(new HTMLEditorKit());

        // ScrollPane pour permettre le défilement
        scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Label de chargement
        loadingLabel = new JLabel("Chargement...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loadingLabel.setForeground(Color.GRAY);
        loadingLabel.setVisible(false);

        add(scrollPane, BorderLayout.CENTER);
        add(loadingLabel, BorderLayout.NORTH);
    }

    // Affiche du contenu HTML
    public void displayHtml(String html) {
        editorPane.setText(html);
        editorPane.setCaretPosition(0); // Scroll vers le haut
        hideLoading();
    }

    // Affiche un message de chargement
    public void showLoading() {
        loadingLabel.setVisible(true);
        editorPane.setText("");
    }

    // Cache le message de chargement
    public void hideLoading() {
        loadingLabel.setVisible(false);
    }

    // Affiche un message d'erreur
    public void displayError(String errorMessage) {
        String errorHtml = "<!DOCTYPE html>" +
                "<html><head><title>Erreur</title>" +
                "<style>body{font-family:Arial;padding:50px;background:#f5f5f5;}" +
                "h1{color:#d32f2f;}</style></head>" +
                "<body><h1>⚠ Erreur</h1>" +
                "<p>" + errorMessage + "</p>" +
                "</body></html>";
        displayHtml(errorHtml);
    }

    // Vide le contenu
    public void clear() {
        editorPane.setText("");
    }
}