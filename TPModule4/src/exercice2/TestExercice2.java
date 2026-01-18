package exercice2;

public class TestExercice2 {
    
    public static void testerCinqTachesParalleles() {
        System.out.println("\nTest 1: 5 tâches en parallèle ");
        
        Thread[] threads = new Thread[5];
        
        for (int i = 0; i < 5; i++) {
            String nomTache = "Tâche-" + (i + 1);
            TacheRunnable tache = new TacheRunnable(nomTache, 3);
            threads[i] = new Thread(tache);
            threads[i].start();
        }
        
        System.out.println("✓ Les 5 threads ont été démarrés");
        
        // Attendre la fin des threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("✓ Toutes les tâches sont terminées\n");
    }
    
    public static void testerThreadPrincipalContinue() {
        System.out.println(" Test 2: Thread principal continue ");
        System.out.println("Le thread principal continue pendant l'exécution des threads");
        
        // Créer 3 threads
        Thread t1 = new Thread(new TacheRunnable("T1", 4));
        Thread t2 = new Thread(new TacheRunnable("T2", 4));
        Thread t3 = new Thread(new TacheRunnable("T3", 4));
        
        t1.start();
        t2.start();
        t3.start();
        
        // Le thread principal continue son travail
        for (int i = 1; i <= 6; i++) {
            System.out.println("THREAD PRINCIPAL - Message " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Attendre la fin
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✓ Thread principal: Tâches terminées\n");
    }
    
    public static void testerThreadVsRunnable() {
        System.out.println(" Test 3: Comparaison Thread vs Runnable ");
        
      
        System.out.println("\n Méthode Runnable ");
        Runnable tache1 = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Runnable Tâche - Étape " + i);
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        };
        
        Thread thread1 = new Thread(tache1, "Thread-Runnable");
        thread1.start();
        
        // Méthode 2: En utilisant héritage de Thread
        System.out.println("\n Méthode Thread ");
        Thread thread2 = new Thread("Thread-Heritage") {
            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("Thread Tâche - Étape " + i);
                    try { Thread.sleep(200); } catch (InterruptedException e) {}
                }
            }
        };
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n✓ Comparaison terminée");
        System.out.println("Runnable est préféré car permet l'héritage d'une autre classe\n");
    }
    
    public static void main(String[] args) {
        
        System.out.println("EXERCICE 2 - INTERFACE RUNNABLE");
        
        
        testerCinqTachesParalleles();
        testerThreadPrincipalContinue();
        testerThreadVsRunnable();
        
        
        System.out.println("EXERCICE 2 TERMINÉ AVEC SUCCÈS");
        
    }
}