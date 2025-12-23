package exercice2;
public class Personne {
    private int id;
    private String nom;
    private int age;
    public Personne() {
    }
    public Personne(int id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void afficher() {
        System.out.println("ID: " + id + ", Nom: " + nom + ", Âge: " + age);
    }
    @Override
    public String toString() {
        return "Personne{id=" + id + ", nom='" + nom + "', age=" + age + "}";
    }
}