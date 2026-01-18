package ex3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GestionPersonnesGUI extends JFrame {

    private JTextField txtNom, txtAge;
    private DefaultTableModel model;

    public GestionPersonnesGUI() {

        setTitle("Gestion des personnes");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel(new GridLayout(2, 2));

        panelHaut.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        panelHaut.add(txtNom);

        panelHaut.add(new JLabel("Âge :"));
        txtAge = new JTextField();
        panelHaut.add(txtAge);

        add(panelHaut, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Nom", "Âge"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBas = new JPanel();
        JButton btnAjout = new JButton("Ajouter");
        JButton btnSuppr = new JButton("Supprimer");

        panelBas.add(btnAjout);
        panelBas.add(btnSuppr);

        add(panelBas, BorderLayout.SOUTH);

        btnAjout.addActionListener(e -> ajouter());
        btnSuppr.addActionListener(e -> supprimer(table));

        setVisible(true);
    }

    private void ajouter() {
        String nom = txtNom.getText().trim();
        String ageStr = txtAge.getText().trim();

        if (nom.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires !");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            model.addRow(new Object[]{nom, age});
            txtNom.setText("");
            txtAge.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Âge invalide !");
        }
    }

    private void supprimer(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Aucun élément sélectionné !");
        } else {
            model.removeRow(row);
        }
    }

    public static void main(String[] args) {
        new GestionPersonnesGUI();
    }
}

