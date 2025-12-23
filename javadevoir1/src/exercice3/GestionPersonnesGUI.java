package exercice3;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GestionPersonnesGUI extends JFrame {
    private JTextField textFieldNom;
    private JTextField textFieldAge;
    private JButton buttonAjouter;
    private JButton buttonSupprimer;
    private JTable tablePersonnes;
    private DefaultTableModel tableModel;
    private JLabel labelStatut;
    private int compteurId = 1;
    public GestionPersonnesGUI() {
        setTitle("Gestion des Personnes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        initialiserComposants();
        organiserInterface();
        configurerEcouteurs();
    }
    private void initialiserComposants() {
        textFieldNom = new JTextField(15);
        textFieldAge = new JTextField(5);
        buttonAjouter = new JButton("Ajouter");
        buttonSupprimer = new JButton("Supprimer la sélection");
        String[] colonnes = {"ID", "Nom", "Âge"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePersonnes = new JTable(tableModel);
        tablePersonnes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        labelStatut = new JLabel("Prêt");
        labelStatut.setBorder(BorderFactory.createLoweredBevelBorder());
    }
    private void organiserInterface() {
        JPanel panelSaisie = new JPanel(new FlowLayout());
        panelSaisie.setBorder(BorderFactory.createTitledBorder("Saisie des informations"));
        panelSaisie.add(new JLabel("Nom:"));
        panelSaisie.add(textFieldNom);
        panelSaisie.add(new JLabel("Âge:"));
        panelSaisie.add(textFieldAge);
        panelSaisie.add(buttonAjouter);
        JPanel panelActions = new JPanel(new FlowLayout());
        panelActions.add(buttonSupprimer);
        JScrollPane scrollPane = new JScrollPane(tablePersonnes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des personnes"));
        scrollPane.setPreferredSize(new Dimension(550, 200));
        setLayout(new BorderLayout(10, 10));
        JPanel panelNord = new JPanel(new BorderLayout());
        panelNord.add(panelSaisie, BorderLayout.NORTH);
        panelNord.add(panelActions, BorderLayout.CENTER);
        add(panelNord, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(labelStatut, BorderLayout.SOUTH);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void configurerEcouteurs() {
        buttonAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterPersonne();
            }
        });
        buttonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerPersonneSelectionnee();
            }
        });
        textFieldAge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterPersonne();
            }
        });
    }
    private void ajouterPersonne() {
        String nom = textFieldNom.getText().trim();
        String ageText = textFieldAge.getText().trim();
        if (nom.isEmpty()) {
            afficherErreur("Le nom ne peut pas être vide");
            textFieldNom.requestFocus();
            return;
        }
        if (ageText.isEmpty()) {
            afficherErreur("L'âge ne peut pas être vide");
            textFieldAge.requestFocus();
            return;
        }
        try {
            int age = Integer.parseInt(ageText);

            if (age < 0 || age > 150) {
                afficherErreur("L'âge doit être compris entre 0 et 150");
                textFieldAge.requestFocus();
                return;
            }
            Object[] ligne = {compteurId++, nom, age};
            tableModel.addRow(ligne);
            textFieldNom.setText("");
            textFieldAge.setText("");
            textFieldNom.requestFocus();
            labelStatut.setText("Personne ajoutée avec succès - ID: " + (compteurId - 1));
        } catch (NumberFormatException ex) {
            afficherErreur("L'âge doit être un nombre entier valide");
            textFieldAge.requestFocus();
        }
    }
    private void supprimerPersonneSelectionnee() {
        int ligneSelectionnee = tablePersonnes.getSelectedRow();
        if (ligneSelectionnee == -1) {
            afficherErreur("Veuillez sélectionner une personne à supprimer");
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir supprimer cette personne ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(ligneSelectionnee, 0);
            tableModel.removeRow(ligneSelectionnee);
            labelStatut.setText("Personne avec ID " + id + " supprimée avec succès");
        }
    }
    private void afficherErreur(String message) {
        labelStatut.setText("Erreur: " + message);
        JOptionPane.showMessageDialog(
                this,
                message,
                "Erreur de saisie",
                JOptionPane.ERROR_MESSAGE
        );
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new GestionPersonnesGUI().setVisible(true);
            }
        });
    }
}