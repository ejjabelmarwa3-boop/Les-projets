package exercice3;

import java.util.*;
import java.util.concurrent.*;

public class TestExercice3 {
    
    public static void testerCalculSimple() throws Exception {
        System.out.println("\nTest 1: Calcul simple avec Future ");
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        System.out.println("Création du calcul de somme 1 à 10");
        Calcul calcul = new Calcul("Somme-10", 10);
        Future<Integer> future = executor.submit(calcul);
        
        System.out.println("Le thread principal continue son travail...");
        for (int i = 1; i <= 3; i++) {
            System.out.println("Principal - Travail " + i);
            Thread.sleep(200);
        }
        
        System.out.println("Attente du résultat...");
        if (future.isDone()) {
            System.out.println("Le calcul est terminé !");
        } else {
            System.out.println("Le calcul est toujours en cours...");
        }
        
     
        Integer resultat = future.get();
        System.out.println("✓ Résultat obtenu: " + resultat);
        
        executor.shutdown();
        System.out.println("✓ Test 1 terminé\n");
    }
    
    public static void testerPlusieursCalculs() throws Exception {
        System.out.println(" Test 2: Plusieurs calculs en parallèle ");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Créer plusieurs calculs
        List<Calcul> calculs = Arrays.asList(
            new Calcul("A-5", 5),
            new Calcul("B-10", 10),
            new Calcul("C-15", 15),
            new Calcul("D-20", 20)
        );
        
        System.out.println("Calculs à effectuer: " + calculs);
        
        // Soumettre tous les calculs
        List<Future<Integer>> futures = new ArrayList<>();
        for (Calcul calcul : calculs) {
            futures.add(executor.submit(calcul));
        }
        
        System.out.println("\nRécupération des résultats:");
        for (int i = 0; i < futures.size(); i++) {
            Integer resultat = futures.get(i).get();
            System.out.println("  " + calculs.get(i) + " = " + resultat);
        }
        
        executor.shutdown();
        System.out.println("✓ Test 2 terminé\n");
    }
    
    public static void testerInvokeAll() throws Exception {
        System.out.println("Test 3: invokeAll() pour plusieurs Callable ");
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        List<Calcul> calculs = Arrays.asList(
            new Calcul("Calcul-1", 7),
            new Calcul("Calcul-2", 12),
            new Calcul("Calcul-3", 9),
            new Calcul("Calcul-4", 15),
            new Calcul("Calcul-5", 5)
        );
        
        System.out.println("Exécution de " + calculs.size() + " calculs avec invokeAll()");
        
      
        List<Future<Integer>> futures = executor.invokeAll(calculs);
        
        System.out.println("\nTous les calculs sont terminés !");
        System.out.println("Affichage des résultats:");
        
        int total = 0;
        for (int i = 0; i < futures.size(); i++) {
            Integer resultat = futures.get(i).get();
            System.out.println("  " + calculs.get(i) + " = " + resultat);
            total += resultat;
        }
        
        System.out.println("Somme totale: " + total);
        
        executor.shutdown();
        System.out.println("✓ Test 3 terminé\n");
    }
    
    public static void testerInvokeAny() throws Exception {
        System.out.println("Test 4: invokeAny() - Premier résultat ");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Calcul> calculs = Arrays.asList(
            new Calcul("Rapide-3", 3),
            new Calcul("Moyen-8", 8),
            new Calcul("Lent-12", 12)
        );
        
        System.out.println("Lancement de 3 calculs avec invokeAny()");
        System.out.println("Le premier qui termine gagne !");
        
        Integer premierResultat = executor.invokeAny(calculs);
        System.out.println("✓ Premier résultat disponible: " + premierResultat);
        
        // Arrêter les autres calculs
        executor.shutdownNow();
        System.out.println("✓ Test 4 terminé\n");
    }
    
    public static void main(String[] args) {
       
        System.out.println(" EXERCICE 3 - CALLABLE ET FUTURE");
      
        
        try {
            testerCalculSimple();
            Thread.sleep(1000);
            
            testerPlusieursCalculs();
            Thread.sleep(1000);
            
            testerInvokeAll();
            Thread.sleep(1000);
            
            testerInvokeAny();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        System.out.println(" EXERCICE 3 TERMINÉ AVEC SUCCÈS");
       
    }
}