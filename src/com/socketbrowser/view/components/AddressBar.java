package com.socketbrowser.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Barre d'adresse du navigateur
public class AddressBar extends JPanel {
    private JTextField urlField;      // Champ de saisie URL
    private JButton goButton;         // Bouton "Aller"

    public AddressBar() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Champ de saisie d'URL
        urlField = new JTextField();
        urlField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        urlField.setText("http://localhost/");

        // Action sur Entrée
        urlField.addActionListener(e -> {
            // Simuler un clic sur le bouton "Aller"
            if (goButton.getActionListeners().length > 0) {
                goButton.getActionListeners()[0].actionPerformed(e);
            }
        });

        // Bouton "Aller"
        goButton = new JButton("→");
        goButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        goButton.setPreferredSize(new Dimension(50, 35));
        goButton.setFocusPainted(false);

        add(urlField, BorderLayout.CENTER);
        add(goButton, BorderLayout.EAST);
    }

    // Récupère l'URL saisie
    public String getUrl() {
        return urlField.getText().trim();
    }

    // Définit l'URL affichée
    public void setUrl(String url) {
        urlField.setText(url);
    }

    // Ajoute un listener au bouton "Aller"
    public void addGoActionListener(ActionListener listener) {
        goButton.addActionListener(listener);
    }

    // Sélectionne tout le texte de l'URL
    public void selectAll() {
        urlField.selectAll();
        urlField.requestFocus();
    }
}