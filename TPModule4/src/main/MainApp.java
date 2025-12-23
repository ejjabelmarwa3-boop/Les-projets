package main;

import exercice1.TestExercice1;
import exercice2.TestExercice2;
import exercice3.TestExercice3;
import exercice4.TestCompletableFuture;
import exercice4.TestExercice4;

import java.util.Scanner;

public class MainApp {
    
    private static void clearScreen() {
       
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private static void afficherMenu() {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║    PROGRAMMATION CONCURRENTE - MENU PRINCIPAL  ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                                                ║");
        System.out.println("║  1. Exercice 1 - Création de Threads           ║");
        System.out.println("║  2. Exercice 2 - Interface Runnable            ║");
        System.out.println("║  3. Exercice 3 - Callable et Future            ║");
        System.out.println("║  4. Exercice 4 - ExecutorService et ThreadPool ║");
        System.out.println("║  5. Bonus - CompletableFuture                  ║");
        System.out.println("║  6. Exécuter TOUS les exercices                ║");
        System.out.println("║  0. Quitter                                    ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("\nVotre choix : ");
    }
    
    private static void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        try {
            System.in.read();
            System.in.skip(System.in.available());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;
        
        while (!quitter) {
            clearScreen();
            afficherMenu();
            
            try {
                int choix = scanner.nextInt();
                scanner.nextLine(); 
                
                clearScreen();
                
                switch (choix) {
                    case 0:
                        quitter = true;
                        System.out.println("Au revoir ! 👋");
                        break;
                        
                    case 1:
                        System.out.println("🚀 Lancement de l'Exercice 1...\n");
                        TestExercice1.main(new String[]{});
                        pause();
                        break;
                        
                    case 2:
                        System.out.println("🚀 Lancement de l'Exercice 2...\n");
                        TestExercice2.main(new String[]{});
                        pause();
                        break;
                        
                    case 3:
                        System.out.println("🚀 Lancement de l'Exercice 3...\n");
                        TestExercice3.main(new String[]{});
                        pause();
                        break;
                        
                    case 4:
                        System.out.println("🚀 Lancement de l'Exercice 4...\n");
                        TestExercice4.main(new String[]{});
                        pause();
                        break;
                        
                    case 5:
                        System.out.println("🚀 Lancement du Bonus...\n");
                        try {
                            TestCompletableFuture.main(new String[]{});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pause();
                        break;
                        
                    case 6:
                        System.out.println("🚀 EXÉCUTION COMPLÈTE DE TOUS LES EXERCICES\n");
                       
                        
                        System.out.println("EXERCICE 1 :");
                        TestExercice1.main(new String[]{});
                        
                        
                        System.out.println("EXERCICE 2 :");
                        TestExercice2.main(new String[]{});
                       
                        
                        System.out.println("EXERCICE 3 :");
                        TestExercice3.main(new String[]{});
                        
                        
                        System.out.println("EXERCICE 4 :");
                        TestExercice4.main(new String[]{});
                        
                        
                        System.out.println("BONUS :");
                        try {
                            TestCompletableFuture.main(new String[]{});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        System.out.println("\n🎉 TOUS LES EXERCICES ONT ÉTÉ EXÉCUTÉS AVEC SUCCÈS !");
                        pause();
                        break;
                        
                    default:
                        System.out.println("❌ Choix invalide ! Veuillez choisir un nombre entre 0 et 6.");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("❌ Veuillez entrer un nombre valide !");
                scanner.nextLine(); 
                pause();
            }
        }
        
        scanner.close();
    }
}
