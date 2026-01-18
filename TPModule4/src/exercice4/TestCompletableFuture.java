package exercice4;

import java.util.concurrent.*;

public class TestCompletableFuture {
    
    public static void main(String[] args) throws Exception {
        System.out.println("\n BONUS: CompletableFuture ");
        
      
        System.out.println("\n 1. Création asynchrone ");
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("Tâche asynchrone en cours...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("Tâche asynchrone terminée");
        });
        future1.get();
        
        
        System.out.println("\n 2. Tâche avec résultat ");
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Calcul en cours...");
            try { Thread.sleep(800); } catch (InterruptedException e) {}
            return 42;
        });
        System.out.println("Résultat: " + future2.get());
        
        System.out.println("\n✓ Tests CompletableFuture terminés");
    }
}