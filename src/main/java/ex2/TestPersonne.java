package ex2;

public class TestPersonne {

    public static void main(String[] args) {

        Etudiant[] etudiants = {
                new Etudiant(1, "Ali", 21, "L2"),
                new Etudiant(2, "Sara", 19, "L1"),
                new Etudiant(3, "Moussa", 23, "Master"),
                new Etudiant(4, "Lina", 20, "L2"),
                new Etudiant(5, "Yasser", 22, "Master")
        };

        System.out.println("Liste des étudiants :");
        for (Etudiant e : etudiants) {
            System.out.println(e);
        }

        // Étudiant le plus âgé
        Etudiant max = etudiants[0];
        for (Etudiant e : etudiants) {
            if (e.getAge() > max.getAge()) max = e;
        }

        System.out.println("\nÉtudiant le plus âgé : " + max);

        // Recherche par nom
        System.out.println("\nRecherche ‘Lina’ :");
        rechercher(etudiants, "Lina");
    }

    public static void rechercher(Etudiant[] tab, String nom) {
        for (Etudiant e : tab) {
            if (e.getNom().equalsIgnoreCase(nom)) {
                System.out.println("Trouvé : " + e);
                return;
            }
        }
        System.out.println("Aucun étudiant trouvé avec ce nom.");
    }
}
