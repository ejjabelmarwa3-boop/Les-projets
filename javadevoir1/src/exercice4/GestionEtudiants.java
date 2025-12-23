package exercice4;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
public class GestionEtudiants {
    private List<Etudiant> etudiants;
    private static final String FICHIER_ETUDIANTS = "etudiants.txt";
    private static final String FICHIER_CSV = "etudiants.csv";
    private static final String FICHIER_JSON = "etudiants.json";
    private int prochainId;
    public GestionEtudiants() {
        etudiants = new ArrayList<>();
        chargerEtudiants();
    }
    public static class Etudiant {
        private int id;
        private String nom;
        private int age;
        private String niveau;
        public Etudiant(int id, String nom, int age, String niveau) {
            this.id = id;
            this.nom = nom;
            this.age = age;
            this.niveau = niveau;
        }
        public int getId() {
            return id;
        }
        public String getNom() {
            return nom;
        }
        public int getAge() {
            return age;
        }
        public String getNiveau() {
            return niveau;
        }
        public String toFileFormat() {
            return id + ";" + nom + ";" + age + ";" + niveau;
        }
        public String toCSVFormat() {
            return id + "," + nom + "," + age + "," + niveau;
        }
        public String toJSONFormat() {
            return String.format(
                    "  {\"id\":%d, \"nom\":\"%s\", \"age\":%d, \"niveau\":\"%s\"}",
                    id, nom, age, niveau
            );
        }
        @Override
        public String toString() {
            return String.format("ID: %d, Nom: %s, Âge: %d, Niveau: %s",
                    id, nom, age, niveau);
        }
    }
    public void chargerEtudiants() {
        try {
            if (!Files.exists(Paths.get(FICHIER_ETUDIANTS))) {
                System.out.println("Fichier " + FICHIER_ETUDIANTS + " non trouvé. Création d'une liste vide.");
                prochainId = 1;
                return;
            }
            List<String> lignes = Files.readAllLines(Paths.get(FICHIER_ETUDIANTS));
            etudiants.clear();
            for (String ligne : lignes) {
                if (ligne.trim().isEmpty()) continue;
                String[] parts = ligne.split(";");
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String nom = parts[1].trim();
                        int age = Integer.parseInt(parts[2].trim());
                        String niveau = parts[3].trim();
                        Etudiant etudiant = new Etudiant(id, nom, age, niveau);
                        etudiants.add(etudiant);
                        if (id >= prochainId) {
                            prochainId = id + 1;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de format dans la ligne: " + ligne);
                    }
                }
            }
            System.out.println(etudiants.size() + " étudiants chargés depuis " + FICHIER_ETUDIANTS);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier: " + e.getMessage());
            prochainId = 1;
        }
    }
    public void sauvegarderEtudiants() {
        try {
            List<String> lignes = etudiants.stream()
                    .map(Etudiant::toFileFormat)
                    .collect(Collectors.toList());
            Files.write(Paths.get(FICHIER_ETUDIANTS), lignes);
            System.out.println(etudiants.size() + " étudiants sauvegardés dans " + FICHIER_ETUDIANTS);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde: " + e.getMessage());
        }
    }
    public void afficherEtudiantsAgeSuperieur20() {
        System.out.println("\n Etudiants âgés de plus de 20 ans :");
        List<Etudiant> etudiantsFiltres = etudiants.stream()
                .filter(e -> e.getAge() > 20)
                .collect(Collectors.toList());
        if (etudiantsFiltres.isEmpty()) {
            System.out.println("Aucun étudiant trouvé.");
        } else {
            etudiantsFiltres.forEach(System.out::println);
        }
    }
    public void afficherEtudiantsTriesParNom() {
        System.out.println("\n Etudiants triéss par nom : ");
        List<Etudiant> etudiantsTries = etudiants.stream()
                .sorted(Comparator.comparing(Etudiant::getNom))
                .collect(Collectors.toList());
        if (etudiantsTries.isEmpty()) {
            System.out.println("Aucun étudiant dans la liste.");
        } else {
            etudiantsTries.forEach(System.out::println);
        }
    }
    public void ajouterEtudiant(String nom, int age, String niveau) {
        Etudiant nouvelEtudiant = new Etudiant(prochainId++, nom, age, niveau);
        etudiants.add(nouvelEtudiant);
        System.out.println("Étudiant ajouté: " + nouvelEtudiant);
    }
    public void exporterCSV() {
        try {
            List<String> lignes = new ArrayList<>();
            lignes.add("ID,Nom,Age,Niveau"); // En-tête

            etudiants.stream()
                    .map(Etudiant::toCSVFormat)
                    .forEach(lignes::add);

            Files.write(Paths.get(FICHIER_CSV), lignes);
            System.out.println("Données exportées dans " + FICHIER_CSV);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export CSV: " + e.getMessage());
        }
    }
    public void exporterJSON() {
        try {
            List<String> lignes = new ArrayList<>();
            lignes.add("[");
            String jsonEtudiants = etudiants.stream()
                    .map(Etudiant::toJSONFormat)
                    .collect(Collectors.joining(",\n"));
            lignes.add(jsonEtudiants);
            lignes.add("]");
            Files.write(Paths.get(FICHIER_JSON), lignes);
            System.out.println("Données exportées dans " + FICHIER_JSON);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'export JSON: " + e.getMessage());
        }
    }
    public void afficherTousLesEtudiants() {
        System.out.println("\n Tous les étudiants :");
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans la liste.");
        } else {
            etudiants.forEach(System.out::println);
        }
    }
    public static void main(String[] args) {
        GestionEtudiants gestion = new GestionEtudiants();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n Gestion des étudiants :");
            System.out.println("1. Afficher tous les étudiants");
            System.out.println("2. Afficher étudiants > 20 ans");
            System.out.println("3. Afficher étudiants triés par nom");
            System.out.println("4. Ajouter un étudiant");
            System.out.println("5. Sauvegarder et exporter");
            System.out.println("6. Quitter");
            System.out.print("Choix: ");
            try {
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix) {
                    case 1:
                        gestion.afficherTousLesEtudiants();
                        break;
                    case 2:
                        gestion.afficherEtudiantsAgeSuperieur20();
                        break;
                    case 3:
                        gestion.afficherEtudiantsTriesParNom();
                        break;
                    case 4:
                        System.out.print("Nom: ");
                        String nom = scanner.nextLine();
                        System.out.print("Âge: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Niveau: ");
                        String niveau = scanner.nextLine();
                        gestion.ajouterEtudiant(nom, age, niveau);
                        break;
                    case 5:
                        gestion.sauvegarderEtudiants();
                        gestion.exporterCSV();
                        gestion.exporterJSON();
                        break;
                    case 6:
                        System.out.println("Au revoir!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Choix invalide!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }
}