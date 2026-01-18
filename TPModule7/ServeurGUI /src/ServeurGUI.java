package serveurgui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ServeurGUI extends JFrame {

    private JTextArea zoneMessages;
    private JTextField champMessage;
    private JButton btnEnvoyer;
    private JTextField champPort;
    private JButton btnDemarrer;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean serveurEnMarche = false;

    public ServeurGUI() {
        setTitle("Serveur - Chat TCP");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Configuration avec layout simple
        zoneMessages = new JTextArea();
        zoneMessages.setEditable(false);
        JScrollPane scroll = new JScrollPane(zoneMessages);
        
        // Panel de configuration
        JPanel panelConfig = new JPanel();
        panelConfig.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelConfig.add(new JLabel("Port:"));
        champPort = new JTextField("5001", 5);
        panelConfig.add(champPort);
        
        btnDemarrer = new JButton("Démarrer Serveur");
        panelConfig.add(btnDemarrer);
        
        // Panel pour l'envoi
        JPanel panelEnvoi = new JPanel();
        panelEnvoi.setLayout(new BorderLayout());
        
        champMessage = new JTextField();
        champMessage.setEnabled(false);
        
        btnEnvoyer = new JButton("Envoyer");
        btnEnvoyer.setEnabled(false);
        btnEnvoyer.setPreferredSize(new Dimension(100, 30));
        
        panelEnvoi.add(champMessage, BorderLayout.CENTER);
        panelEnvoi.add(btnEnvoyer, BorderLayout.EAST);
        
        // Écouteurs
        btnDemarrer.addActionListener(e -> demarrerServeur());
        btnEnvoyer.addActionListener(e -> envoyerMessage());
        champMessage.addActionListener(e -> envoyerMessage());
        
        // Organisation principale
        setLayout(new BorderLayout());
        add(panelConfig, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelEnvoi, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private void demarrerServeur() {
        if (serveurEnMarche) {
            arreterServeur();
            return;
        }
        
        new Thread(() -> {
            try {
                int port = Integer.parseInt(champPort.getText());
                
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("Tentative de démarrage sur le port " + port + "...\n");
                    champPort.setEnabled(false);
                    btnDemarrer.setText("Arrêter Serveur");
                    serveurEnMarche = true;
                });
                
                serverSocket = new ServerSocket(port);
                
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("✓ Serveur démarré sur le port " + port + "\n");
                    zoneMessages.append("En attente d'un client...\n");
                });
                
                clientSocket = serverSocket.accept();
                
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("✓ Client connecté!\n");
                    champMessage.setEnabled(true);
                    btnEnvoyer.setEnabled(true);
                    champMessage.requestFocus();
                });
                
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Lire les messages
                String msg;
                while ((msg = in.readLine()) != null) {
                    final String message = msg;
                    SwingUtilities.invokeLater(() -> {
                        zoneMessages.append("[Client]: " + message + "\n");
                        zoneMessages.setCaretPosition(zoneMessages.getDocument().getLength());
                    });
                }
                
            } catch (NumberFormatException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: Port invalide\n");
                    reinitialiserInterface();
                });
            } catch (BindException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: Le port est déjà utilisé!\n");
                    zoneMessages.append("Essayez avec un autre port\n");
                    reinitialiserInterface();
                });
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    zoneMessages.append("❌ Erreur: " + e.getMessage() + "\n");
                    reinitialiserInterface();
                });
            } finally {
                serveurEnMarche = false;
            }
        }).start();
    }
    
    private void arreterServeur() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            zoneMessages.append("✓ Serveur arrêté\n");
            reinitialiserInterface();
        });
    }
    
    private void reinitialiserInterface() {
        champPort.setEnabled(true);
        champMessage.setEnabled(false);
        btnEnvoyer.setEnabled(false);
        btnDemarrer.setText("Démarrer Serveur");
        serveurEnMarche = false;
    }

    private void envoyerMessage() {
        String msg = champMessage.getText().trim();
        if (!msg.isEmpty() && out != null && serveurEnMarche) {
            SwingUtilities.invokeLater(() -> {
                zoneMessages.append("[Vous]: " + msg + "\n");
                zoneMessages.setCaretPosition(zoneMessages.getDocument().getLength());
            });
            out.println(msg);
            champMessage.setText("");
        } else if (!serveurEnMarche) {
            zoneMessages.append("❌ Le serveur n'est pas démarré\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServeurGUI();
        });
    }
}