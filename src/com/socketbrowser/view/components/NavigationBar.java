package com.socketbrowser.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Barre de navigation avec boutons Retour/Avant/Actualiser
public class NavigationBar extends JPanel {
    private JButton backButton;       // Bouton Retour
    private JButton forwardButton;    // Bouton Avant
    private JButton refreshButton;    // Bouton Actualiser
    private JButton homeButton;       // Bouton Accueil

    public NavigationBar() {
        initUI();
    }

    private void initUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setBackground(Color.WHITE);

        // Bouton Retour
        backButton = createButton("←", "Retour");
        backButton.setEnabled(false); // Désactivé par défaut

        // Bouton Avant
        forwardButton = createButton("→", "Avant");
        forwardButton.setEnabled(false); // Désactivé par défaut

        // Bouton Actualiser
        refreshButton = createButton("↻", "Actualiser");

        // Bouton Accueil
        homeButton = createButton("⌂", "Accueil");

        add(backButton);
        add(forwardButton);
        add(refreshButton);
        add(homeButton);
    }

    // Bouton avec style uniforme
    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(45, 35));
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        return button;
    }

    // Active/désactive le bouton Retour
    public void setBackEnabled(boolean enabled) {
        backButton.setEnabled(enabled);
    }

    // Active/désactive le bouton Avant
    public void setForwardEnabled(boolean enabled) {
        forwardButton.setEnabled(enabled);
    }

    // Ajoute un listener au bouton Retour
    public void addBackActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    // Ajoute un listener au bouton Avant
    public void addForwardActionListener(ActionListener listener) {
        forwardButton.addActionListener(listener);
    }

    // Ajoute un listener au bouton Actualiser
    public void addRefreshActionListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    // Ajoute un listener au bouton Accueil
    public void addHomeActionListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }
}