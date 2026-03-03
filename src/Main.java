package com.socketbrowser;

import com.socketbrowser.view.BrowserFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuration du Look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Impossible de définir le Look");
            e.printStackTrace();
        }

        // Lancer l'application dans le thread EDT
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("🌐 Démarrage de SocketBrowser...");
                System.out.println("📋 XAMPP (Apache) est vérifié et démarré !");
                System.out.println();

                // Créer et afficher la fenêtre principale
                new BrowserFrame();

                System.out.println("✅ SocketBrowser lancé avec succès !");

            } catch (Exception e) {
                System.err.println("❌ Erreur lors du démarrage de l'application !");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Erreur lors du démarrage de l'application.\n" + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}