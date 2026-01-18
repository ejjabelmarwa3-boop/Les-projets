package exercice2;

public class TacheRunnable implements Runnable {
    private String nom;
    private int nombreEtapes;

    public TacheRunnable(String nom, int nombreEtapes) {
        this.nom = nom;
        this.nombreEtapes = nombreEtapes;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] " 
                         + nom + " commence son exécution");
        
        for (int i = 1; i <= nombreEtapes; i++) {
            System.out.println(nom + " - Étape " + i + "/" + nombreEtapes);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println(nom + " interrompu à l'étape " + i);
                return;
            }
        }
        
        System.out.println("[" + Thread.currentThread().getName() + "] " 
                         + nom + " a terminé");
    }
}
