package ex2;

public class Etudiant extends Personne {

    private String niveau;

    public Etudiant(int id, String nom, int age, String niveau) {
        super(id, nom, age);
        this.niveau = niveau;
    }

    public String getNiveau() { return niveau; }

    @Override
    public String toString() {
        return super.toString() + " - Niveau : " + niveau;
    }
}
