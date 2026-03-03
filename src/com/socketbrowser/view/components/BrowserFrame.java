package com.socketbrowser.view;

import com.socketbrowser.controller.BrowserController;
import com.socketbrowser.model.Page;
import com.socketbrowser.view.components.AddressBar;
import com.socketbrowser.view.components.ContentPanel;
import com.socketbrowser.view.components.NavigationBar;
import javax.swing.*;
import java.awt.*;

// Fenêtre principale du navigateur SocketBrowser
public class BrowserFrame extends JFrame {
    private NavigationBar navigationBar;   // Barre de navigation
    private AddressBar addressBar;         // Barre d'adresse
    private ContentPanel contentPanel;     // Panneau de contenu
    private BrowserController controller;  // Controller

    public BrowserFrame() {
        initUI();
        initController();
    }

    private void initUI() {
        setTitle("🌐 SocketBrowser - Navigateur Local");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));

        // Layout principal
        setLayout(new BorderLayout());

        // Panel supérieur (navigation + adresse)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        navigationBar = new NavigationBar();
        addressBar = new AddressBar();

        topPanel.add(navigationBar, BorderLayout.WEST);
        topPanel.add(addressBar, BorderLayout.CENTER);

        // Panel de contenu
        contentPanel = new ContentPanel();

        // Barre de statut
        JPanel statusBar = createStatusBar();

        // Ajouter les composants
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Menu Bar
        createMenuBar();

        setVisible(true);
    }

    private void initController() {
        // Créer le controller
        controller = new BrowserController(this);

        // Connecter les événements

        // Bouton "Aller" de la barre d'adresse
        addressBar.addGoActionListener(e -> {
            String url = addressBar.getUrl();
            controller.navigateTo(url);
        });

        // Boutons de navigation
        navigationBar.addBackActionListener(e -> controller.goBack());
        navigationBar.addForwardActionListener(e -> controller.goForward());
        navigationBar.addRefreshActionListener(e -> controller.refresh());
        navigationBar.addHomeActionListener(e -> controller.loadHomePage());
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");

        JMenuItem openItem = new JMenuItem("Ouvrir une URL");
        openItem.addActionListener(e -> addressBar.selectAll());
        fileMenu.add(openItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Menu Navigation
        JMenu navMenu = new JMenu("Navigation");

        JMenuItem backItem = new JMenuItem("← Retour");
        backItem.addActionListener(e -> controller.goBack());
        navMenu.add(backItem);

        JMenuItem forwardItem = new JMenuItem("→ Avant");
        forwardItem.addActionListener(e -> controller.goForward());
        navMenu.add(forwardItem);

        JMenuItem refreshItem = new JMenuItem("↻ Actualiser");
        refreshItem.addActionListener(e -> controller.refresh());
        navMenu.add(refreshItem);

        navMenu.addSeparator();

        JMenuItem homeItem = new JMenuItem("⌂ Accueil");
        homeItem.addActionListener(e -> controller.loadHomePage());
        navMenu.add(homeItem);

        // Menu Aide
        JMenu helpMenu = new JMenu("Aide");

        JMenuItem aboutItem = new JMenuItem("À propos");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(navMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JLabel statusLabel = new JLabel("📡 Prêt");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.add(statusLabel);

        return statusBar;
    }

    private void showAboutDialog() {
        String message = "🌐 SocketBrowser v1.0\n\n" +
                "Un navigateur web local simple utilisant :\n" +
                "• Java Swing pour l'interface\n" +
                "• Sockets pour la communication HTTP\n" +
                "• Architecture MVC + Service\n\n" +
                "Fonctionne avec XAMPP (Apache) sur localhost.\n\n" +
                "© 2026 - Projet pédagogique Java";

        JOptionPane.showMessageDialog(this, message, "À propos", JOptionPane.INFORMATION_MESSAGE);
    }

    // Affiche une page dans le navigateur
    public void displayPage(Page page) {
        if (page != null) {
            contentPanel.displayHtml(page.getHtmlContent());
            addressBar.setUrl(page.getUrl());
            setTitle(page.getTitle() + " - SocketBrowser");
        }
    }

    // Affiche un message de chargement
    public void showLoading() {
        contentPanel.showLoading();
        setTitle("Chargement... - SocketBrowser");
    }

    // Affiche un message d'erreur
    public void showError(String errorMessage) {
        contentPanel.displayError(errorMessage);
        JOptionPane.showMessageDialog(this, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Met à jour les boutons de navigation (Retour/Avant)
    public void updateNavigationButtons(boolean canGoBack, boolean canGoForward) {
        navigationBar.setBackEnabled(canGoBack);
        navigationBar.setForwardEnabled(canGoForward);
    }
}