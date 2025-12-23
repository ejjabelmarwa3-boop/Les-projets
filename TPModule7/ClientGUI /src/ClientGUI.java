package clientgui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame {

    private JTextArea zoneMessages;
    private JTextField champMessage;
    private JButton btnEnvoyer;
    private JTextField champAdresse;
    private JTextField champPort;
    private JButton btnConnecter;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean estConnecte = false;

    public ClientGUI() {
        setTitle("Client - Chat TCP");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Configuration de l'interface avec des layouts plus simples
        zoneMessages = new JTextArea();
        zoneMessages.setEditable(false);
        JScrollPane scroll = new JScrollPane(zoneMessages);
        
        // Panel de connexion - SIMPLIFIÉ
        JPanel panelConnexion = new JPanel();
        panelConnexion.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelConnexion.add(new JLabel("Adresse:"));
        champAdresse = new JTextField("localhost", 10);
        panelConnexion.add(champAdresse);
        
        panelConnexion.add(new JLabel("   Port:"));
        champPort = new JTextField("5001", 5);
        panelConnexion.add(champPort);
        
        btnConnecter = new JButton("Se Connecter");
        panelConnexion.add(btnConnecter);
        
        // Panel pour l'envoi de messages
        JPanel panelEnvoi = new JPanel();
        panelEnvoi.setLayout(new BorderLayout());
        
        champMessage = new JTextField();
        champMessage.setEnabled(false);
        
        btnEnvoyer = new JButton("Envoyer");
        btnEnvoyer.setEnabled(false);
        btnEnvoyer.setPreferredSize(new Dimension(100, 30));
        
        panelEnvoi.add(champMessage, BorderLayout.CENTER);
        panelEnvoi.add(btnEnvoyer, BorderLayout.EAST);
        
        // Ajout des écouteurs
        btnConnecter.addActionListener(e -> connecterServeur());
        btnEnvoyer.addActionListener(e -> envoyerMessage());
        
        // Touche Entrée pour envoyer
        champMessage.addActionListener(e -> envoyerMessage());
        
        // Organisation principale de la fenêtre
        setLayout(new BorderLayout());
        add(panelConnexion, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelEnvoi, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private void connecterServeur() {
        if (estConnecte) {
            deconnecter();
            return;
        }
        
        new Thread(() -> {
            try {
                String adresse = champAdresse.getText().trim();
                int port = Integer.parseInt(champPort.getText().trim());
                
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("Connexion à " + adresse + ":" + port + "...\n");
                    btnConnecter.setText("Annuler");
                    champAdresse.setEnabled(false);
                    champPort.setEnabled(false);
                });
                
                socket = new Socket(adresse, port);
                
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("✓ Connecté au serveur!\n");
                    btnConnecter.setText("Se Déconnecter");
                    champMessage.setEnabled(true);
                    btnEnvoyer.setEnabled(true);
                    champMessage.requestFocus();
                    estConnecte = true;
                });
                
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Lire les messages du serveur en continu
                String msg;
                while ((msg = in.readLine()) != null) {
                    final String message = msg;
                    SwingUtilities.invokeLater(() -> {
                        zoneMessages.append("[Serveur]: " + message + "\n");
                        // Scroll automatique vers le bas
                        zoneMessages.setCaretPosition(zoneMessages.getDocument().getLength());
                    });
                }
                
            } catch (NumberFormatException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: Port invalide\n");
                    reinitialiserInterface();
                });
            } catch (ConnectException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: Impossible de se connecter au serveur\n");
                    zoneMessages.append("Vérifiez que le serveur est démarré sur le bon port\n");
                    reinitialiserInterface();
                });
            } catch (UnknownHostException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: Adresse serveur invalide\n");
                    reinitialiserInterface();
                });
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur de connexion: " + e.getMessage() + "\n");
                    reinitialiserInterface();
                });
            } finally {
                if (!estConnecte) {
                    reinitialiserInterface();
                }
            }
        }).start();
    }
    
    private void deconnecter() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            zoneMessages.append("✓ Déconnecté du serveur\n");
            reinitialiserInterface();
        });
    }
    
    private void reinitialiserInterface() {
        champAdresse.setEnabled(true);
        champPort.setEnabled(true);
        champMessage.setEnabled(false);
        btnEnvoyer.setEnabled(false);
        btnConnecter.setText("Se Connecter");
        estConnecte = false;
    }

    private void envoyerMessage() {
        String msg = champMessage.getText().trim();
        if (!msg.isEmpty() && out != null && estConnecte) {
            SwingUtilities.invokeLater(() -> {
                zoneMessages.append("[Vous]: " + msg + "\n");
                zoneMessages.setCaretPosition(zoneMessages.getDocument().getLength());
            });
            out.println(msg);
            champMessage.setText("");
        } else if (!estConnecte) {
            zoneMessages.append("❌ Vous devez d'abord vous connecter au serveur\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientGUI();
        });
    }
}