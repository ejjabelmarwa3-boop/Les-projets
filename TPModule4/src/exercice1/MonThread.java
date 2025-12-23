package exercice1;

public class MonThread extends Thread {
    private String nom;
    private int dureeSleep;

    public MonThread(String nom, int dureeSleep) {
        this.nom = nom;
        this.dureeSleep = dureeSleep;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] " + nom + " démarre");
        
        for (int i = 1; i <= 5; i++) {
            System.out.println(nom + " - Compteur : " + i);
            try {
                Thread.sleep(dureeSleep);
            } catch (InterruptedException e) {
                System.out.println(nom + " a été interrompu !");
                return;
            }
        }
        
        System.out.println("[" + Thread.currentThread().getName() + "] " + nom + " se termine");
    }
}