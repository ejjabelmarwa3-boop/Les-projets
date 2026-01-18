package exercice4;

import java.util.concurrent.*;

public class TestExercice4 {
    
    public static void testerTaillePool(int taillePool, int nombreTaches, String description) 
            throws InterruptedException {
        
        System.out.println("\n" + description);
        System.out.println("Pool: " + taillePool + " thread(s), Tâches: " + nombreTaches);
        
        Tache.resetCompteurs();
        ExecutorService pool = Executors.newFixedThreadPool(taillePool);
        long debut = System.currentTimeMillis();
 
        for (int i = 1; i <= nombreTaches; i++) {
            pool.execute(new Tache(i));
        }
        
        pool.shutdown(); 
        

        boolean termine = pool.awaitTermination(30, TimeUnit.SECONDS);
        long fin = System.currentTimeMillis();
        long duree = fin - debut;
        
        if (termine) {
            System.out.println(String.format("✓ Terminé en %d ms", duree));
            System.out.println(String.format("  Tâches terminées: %d/%d", 
                Tache.getNombreTermine(), nombreTaches));
        } else {
            System.out.println("✗ Timeout ! Tâches non terminées");
            pool.shutdownNow();
        }
    }
    
    public static void testerDifferentesTailles() throws InterruptedException {
        System.out.println("\n Test 1: Comparaison tailles de pool ");
        
        int nombreTaches = 10;
        
        testerTaillePool(1, nombreTaches, " 1 thread (séquentiel) ");
        Thread.sleep(1000);
        
        testerTaillePool(2, nombreTaches, " 2 threads ");
        Thread.sleep(1000);
        
        testerTaillePool(3, nombreTaches, " 3 threads ");
        Thread.sleep(1000);
        
        testerTaillePool(5, nombreTaches, " 5 threads ");
        Thread.sleep(1000);
        
        testerTaillePool(10, nombreTaches, " 10 threads (autant que de tâches) ");
        
        System.out.println("\n✓ Observation: Plus le pool est grand, plus c'est rapide");
        System.out.println("  Mais trop de threads peut nuire aux performances\n");
    }
    
    public static void testerAwaitTermination() throws InterruptedException {
        System.out.println("Test 2: awaitTermination avec délai ");
        
        ExecutorService pool = Executors.newFixedThreadPool(3);
        int nombreTaches = 7;
        
        System.out.println("Soumission de " + nombreTaches + " tâches...");
        for (int i = 1; i <= nombreTaches; i++) {
            pool.execute(new Tache(i));
        }
        
        pool.shutdown();
        
        System.out.println("Attente max 5 secondes pour la fin...");
        boolean termine = pool.awaitTermination(5, TimeUnit.SECONDS);
        
        if (termine) {
            System.out.println("✓ Toutes les tâches terminées dans le délai");
        } else {
            System.out.println("✗ Délai dépassé, arrêt forcé...");
            pool.shutdownNow();
            System.out.println("Tâches terminées: " + Tache.getNombreTermine() + "/" + nombreTaches);
        }
        
        System.out.println("✓ Test 2 terminé\n");
    }
    
    public static void testerCachedThreadPool() throws InterruptedException {
        System.out.println(" Test 3: CachedThreadPool ");
        System.out.println("Crée des threads à la demande, réutilise les threads inactifs");
        
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        int nombreTaches = 15;
        
        System.out.println("Soumission de " + nombreTaches + " tâches courtes...");
        for (int i = 1; i <= nombreTaches; i++) {
            final int id = i;
            cachedPool.execute(() -> {
                System.out.println(String.format("[%s] Tâche rapide %02d", 
                    Thread.currentThread().getName(), id));
                try {
                    Thread.sleep(200); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        cachedPool.shutdown();
        cachedPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("✓ CachedThreadPool testé\n");
    }
    
    public static void testerScheduledExecutor() throws InterruptedException {
        System.out.println("Test 4: ScheduledExecutorService ");
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        System.out.println("Planification de tâches:");
        System.out.println("1. Tâche unique dans 2 secondes");
        System.out.println("2. Tâche périodique toutes les 3 secondes");
        System.out.println("3. Arrêt automatique dans 12 secondes");
        
        
        scheduler.schedule(() -> {
            System.out.println("[Scheduled] Tâche unique exécutée après délai");
        }, 2, TimeUnit.SECONDS);
        

        ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("[Scheduled] Tâche périodique exécutée");
        }, 1, 3, TimeUnit.SECONDS);
        
     
        scheduler.schedule(() -> {
            System.out.println("[Scheduled] Arrêt du scheduler...");
            periodicTask.cancel(false);
            scheduler.shutdown();
        }, 12, TimeUnit.SECONDS);
        
   
        scheduler.awaitTermination(15, TimeUnit.SECONDS);
        System.out.println("✓ ScheduledExecutorService testé\n");
    }
    
    public static void main(String[] args) {

        System.out.println(" EXERCICE 4 - EXECUTORSERVICE ET THREADPOOL");

        
        try {
            testerDifferentesTailles();
            Thread.sleep(2000);
            
            testerAwaitTermination();
            Thread.sleep(2000);
            
            testerCachedThreadPool();
            Thread.sleep(2000);
            
            testerScheduledExecutor();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
 
        System.out.println("EXERCICE 4 TERMINÉ AVEC SUCCÈS");

    }
}