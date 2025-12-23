package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserForm extends JFrame {

    private JTextField txtNom, txtEmail;
    private JTable table;
    private DefaultTableModel model;

    private UserDAO dao = new UserDAO();
    private int selectedId = -1;

    public UserForm() {
        setTitle("Gestion des Utilisateurs - CRUD Swing/JDBC");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // -------- FORMULAIRE --------
        // -------- FORMULAIRE --------
JPanel form = new JPanel(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.fill = GridBagConstraints.HORIZONTAL;

// Ligne 1 : Nom
gbc.gridx = 0;
gbc.gridy = 0;
form.add(new JLabel("Nom :"), gbc);

gbc.gridx = 1;
txtNom = new JTextField(20);
form.add(txtNom, gbc);

// Ligne 2 : Email
gbc.gridx = 0;
gbc.gridy = 1;
form.add(new JLabel("Email :"), gbc);

gbc.gridx = 1;
txtEmail = new JTextField(20);
form.add(txtEmail, gbc);

// Ligne 3 : Boutons
JButton btnAdd = new JButton("Ajouter");
JButton btnUpdate = new JButton("Modifier");
JButton btnDelete = new JButton("Supprimer");

gbc.gridx = 0;
gbc.gridy = 2;
form.add(btnAdd, gbc);

gbc.gridx = 1;
form.add(btnUpdate, gbc);

gbc.gridx = 2;
form.add(btnDelete, gbc);


        // -------- TABLE --------
        model = new DefaultTableModel(
                new String[]{"ID", "Nom", "Email"}, 0
        );
        table = new JTable(model);
        loadUsers();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                txtNom.setText(model.getValueAt(row, 1).toString());
                txtEmail.setText(model.getValueAt(row, 2).toString());
            }
        });

        // -------- ACTIONS --------
        btnAdd.addActionListener(e -> {
            User u = new User(0, txtNom.getText(), txtEmail.getText());
            if (dao.insertUser(u)) {
                JOptionPane.showMessageDialog(this, "Ajout réussi !");
                clearForm();
                loadUsers();
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedId != -1) {
                User u = new User(selectedId, txtNom.getText(), txtEmail.getText());
                if (dao.updateUser(u)) {
                    JOptionPane.showMessageDialog(this, "Modification réussie !");
                    clearForm();
                    loadUsers();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur !");
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedId != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Confirmer la suppression ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.deleteUser(selectedId)) {
                        JOptionPane.showMessageDialog(this, "Suppression réussie !");
                        clearForm();
                        loadUsers();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur !");
            }
        });

        // -------- ASSEMBLAGE --------
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadUsers() {
        model.setRowCount(0);
        for (User u : dao.getAllUsers()) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getNom(),
                    u.getEmail()
            });
        }
    }

    private void clearForm() {
        txtNom.setText("");
        txtEmail.setText("");
        selectedId = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserForm().setVisible(true);
        });
    }
}

