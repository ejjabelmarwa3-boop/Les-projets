package exercice2;
public class TestPersonne {
    public static void main(String[] args) {
        Etudiant[] etudiants = new Etudiant[5];
        etudiants[0] = new Etudiant(1, "Sara Maaroufi", 20, "L2");
        etudiants[1] = new Etudiant(2, "Yassine Salhi", 22, "L3");
        etudiants[2] = new Etudiant(3, "Aymen Farouqi", 19, "L1");
        etudiants[3] = new Etudiant(4, "Aya Nouri", 24, "Master");
        etudiants[4] = new Etudiant(5, "Othmane Lahrech", 21, "L2");
        System.out.println(" Liste des étudiants : ");
        afficherListeEtudiants(etudiants);
        Etudiant plusAge = trouverEtudiantPlusAge(etudiants);
        System.out.println("\n Etudiant plus agé :");
        if (plusAge != null) {
            plusAge.afficher();
        }
        System.out.println("\n Recherche par nom :");
        rechercherEtudiantParNom(etudiants, "Othmane Lahrech");
        rechercherEtudiantParNom(etudiants, "Yassine Salhi");
    }
    public static void afficherListeEtudiants(Etudiant[] etudiants) {
        for (int i = 0; i < etudiants.length; i++) {
            System.out.print((i + 1) + ". ");
            etudiants[i].afficher();
        }
    }
    public static Etudiant trouverEtudiantPlusAge(Etudiant[] etudiants) {
        if (etudiants == null || etudiants.length == 0) {
            return null;
        }

        Etudiant plusAge = etudiants[0];
        for (int i = 1; i < etudiants.length; i++) {
            if (etudiants[i].getAge() > plusAge.getAge()) {
                plusAge = etudiants[i];
            }
        }
        return plusAge;
    }
    public static void rechercherEtudiantParNom(Etudiant[] etudiants, String nom) {
        boolean trouve = false;
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getNom().equalsIgnoreCase(nom)) {
                System.out.println("Étudiant trouvé :");
                etudiant.afficher();
                trouve = true;
                break;
            }
        }
        if (!trouve) {
            System.out.println("Aucun étudiant trouvé avec le nom : " + nom);
        }
    }
}