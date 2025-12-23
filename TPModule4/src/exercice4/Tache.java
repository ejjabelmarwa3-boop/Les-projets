package exercice4;

import java.util.concurrent.atomic.AtomicInteger;

public class Tache implements Runnable {
    private int id;
    private static AtomicInteger compteurTermine = new AtomicInteger(0);
    private static AtomicInteger compteurActif = new AtomicInteger(0);

    public Tache(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int actifs = compteurActif.incrementAndGet();
        System.out.println(String.format("[%s] Tâche %02d START (actives: %d)", 
            Thread.currentThread().getName(), id, actifs));
        
        try {
           
            int duree = 800 + (int)(Math.random() * 800);
            Thread.sleep(duree);
            
        } catch (InterruptedException e) {
            System.out.println("Tâche " + id + " interrompue");
            Thread.currentThread().interrupt();
            return;
            
        } finally {
            compteurActif.decrementAndGet();
        }
        
        compteurTermine.incrementAndGet();
        System.out.println(String.format("[%s] Tâche %02d END", 
            Thread.currentThread().getName(), id));
    }
    
    public static int getNombreTermine() {
        return compteurTermine.get();
    }
    
    public static int getNombreActif() {
        return compteurActif.get();
    }
    
    public static void resetCompteurs() {
        compteurTermine.set(0);
        compteurActif.set(0);
    }
}