package ex4;

import ex2.Etudiant;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GestionEtudiants {

    private List<Etudiant> etudiants = new ArrayList<>();
    private final String file = "data/etudiants.txt";

    // Créer automatiquement le fichier s'il n'existe pas
    private void creerFichierSiAbsent() throws IOException {
        Path path = Paths.get(file);

        // Si dossier data n'existe pas -> la créer
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
            System.out.println("📁 Dossier 'data' créé automatiquement.");
        }

        // Si fichier etudiants.txt n'existe pas -> le créer + contenu
        if (!Files.exists(path)) {
            System.out.println("📄 Fichier 'etudiants.txt' manquant → création automatique...");

            List<String> contenu = Arrays.asList(
                    "1;Ali;21;L2",
                    "2;Sara;19;L1",
                    "3;Moussa;23;Master",
                    "4;Lina;20;L2",
                    "5;Yasser;22;Master",
                    "6;Omar;18;L1",
                    "7;Nadia;21;L3"
            );

            Files.write(path, contenu);
            System.out.println("✔ Fichier créé avec succès !");
        }
    }

    public void charger() throws IOException {
        creerFichierSiAbsent();

        List<String> lignes = Files.readAllLines(Paths.get(file));

        for (String l : lignes) {
            if (l.trim().isEmpty()) continue;

            String[] t = l.split(";");
            if (t.length != 4) continue;

            etudiants.add(new Etudiant(
                    Integer.parseInt(t[0]),
                    t[1],
                    Integer.parseInt(t[2]),
                    t[3]
            ));
        }
    }

    public void afficherAgeSup20() {
        etudiants.stream()
                .filter(e -> e.getAge() > 20)
                .forEach(System.out::println);
    }

    public void afficherTriesNom() {
        etudiants.stream()
                .sorted(Comparator.comparing(Etudiant::getNom))
                .forEach(System.out::println);
    }

    public void ajouter(Etudiant e) {
        etudiants.add(e);
    }

    public void sauvegarder() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        for (Etudiant e : etudiants) {
            bw.write(e.getId() + ";" + e.getNom() + ";" + e.getAge() + ";" + e.getNiveau());
            bw.newLine();
        }

        bw.close();
        System.out.println("✔ Données sauvegardées !");
    }

    public void exporterCSV() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/etudiants.csv"));

        bw.write("id,nom,age,niveau");
        bw.newLine();

        for (Etudiant e : etudiants) {
            bw.write(e.getId() + "," + e.getNom() + "," + e.getAge() + "," + e.getNiveau());
            bw.newLine();
        }

        bw.close();
        System.out.println("✔ Export CSV terminé !");
    }

    public static void main(String[] args) throws Exception {
        GestionEtudiants g = new GestionEtudiants();

        g.charger();

        System.out.println("=== Étudiants âge > 20 ===");
        g.afficherAgeSup20();

        System.out.println("\n=== Étudiants triés par nom ===");
        g.afficherTriesNom();

        g.ajouter(new Etudiant(10, "Nadia", 21, "L3"));
        g.sauvegarder();
        g.exporterCSV();
    }
}
