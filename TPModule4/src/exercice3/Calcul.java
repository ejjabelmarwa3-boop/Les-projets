package exercice3;

import java.util.concurrent.Callable;

public class Calcul implements Callable<Integer> {
    private int n;
    private String nom;

    public Calcul(String nom, int n) {
        this.nom = nom;
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("[" + Thread.currentThread().getName() + "] " 
                         + nom + ": Calcul de la somme de 1 à " + n);
        
        int somme = 0;
        for (int i = 1; i <= n; i++) {
            somme += i;
            Thread.sleep(100); 
            
           
            if (n >= 10 && i % (n/5) == 0) {
                System.out.println("  " + nom + ": Progression " + i + "/" + n);
            }
        }
        
        System.out.println("  " + nom + ": Résultat = " + somme);
        return somme;
    }
    
    @Override
    public String toString() {
        return nom + "(1.." + n + ")";
    }
}