package exercice1;

public class TestExercice1 {
    
    public static void testerDureesDifferentes() {
        System.out.println("\nTest 1: Différentes durées de sleep ");
        System.out.println("Thread A: 200ms, Thread B: 500ms, Thread C: 800ms");
        
        MonThread t1 = new MonThread("Thread A", 200);
        MonThread t2 = new MonThread("Thread B", 500);
        MonThread t3 = new MonThread("Thread C", 800);
        
        t1.start();
        t2.start();
        t3.start();
        
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ Test 1 terminé\n");
    }
    
    public static void testerComportementNonDeterministe() {
        System.out.println("Test 2: Comportement non déterministe");
        System.out.println("10 threads avec la même durée (300ms)");
        
        MonThread[] threads = new MonThread[10];
        
        // Création des threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MonThread("T" + (i+1), 300);
        }
        
        System.out.println("Ordre de démarrage:");
        for (int i = 0; i < threads.length; i++) {
            System.out.print("T" + (i+1) + " ");
            threads[i].start();
            try {
                Thread.sleep(50); // Petit délai pour voir l'ordre
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Attendre la fin de tous les threads
        for (MonThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n✓ Tous les threads sont terminés\n");
    }
    
    public static void testerTroisThreads() {
        System.out.println("Test 3: Trois threads avec priorité ");
        
        MonThread t1 = new MonThread("Thread Rapide", 100);
        MonThread t2 = new MonThread("Thread Normal", 300);
        MonThread t3 = new MonThread("Thread Lent", 600);
        
        // Définir des priorités
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);
        
        System.out.println("Priorités: Rapide(MAX), Normal(NORM), Lent(MIN)");
        
        t3.start(); // Démarrer le lent en premier
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        t2.start(); // Puis le normal
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        t1.start(); // Enfin le rapide
        
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ Test 3 terminé\n");
    }
    
    public static void main(String[] args) {
      
        System.out.println("      EXERCICE 1 - CRÉATION DE THREADS");
       
        
        testerDureesDifferentes();
        testerComportementNonDeterministe();
        testerTroisThreads();
        System.out.println("EXERCICE 1 TERMINÉ AVEC SUCCÈS");
        
    }
}
