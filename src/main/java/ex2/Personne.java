package ex2;

public class Personne {

    protected int id;
    protected String nom;
    protected int age;

    public Personne(int id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return id + " - " + nom + " (" + age + " ans)";
    }
}
