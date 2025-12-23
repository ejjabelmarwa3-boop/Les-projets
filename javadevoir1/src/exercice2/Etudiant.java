package exercice2;
public class Etudiant extends Personne {
    private String niveau;
    public Etudiant() {
        super();
    }
    public Etudiant(int id, String nom, int age, String niveau) {
        super(id, nom, age);
        this.niveau = niveau;
    }
    public String getNiveau() {
        return niveau;
    }
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    @Override
    public void afficher() {
        System.out.println("ID: " + getId() + ", Nom: " + getNom() +
                ", Âge: " + getAge() + ", Niveau: " + niveau);
    }
    @Override
    public String toString() {
        return "Etudiant{id=" + getId() + ", nom='" + getNom() +
                "', age=" + getAge() + ", niveau='" + niveau + "'}";
    }
}