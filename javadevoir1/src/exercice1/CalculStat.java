package exercice1;
import java.util.Arrays;
import java.util.Scanner;
public class CalculStat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Combien de nombres voulez-vous saisir ? ");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("Erreur : vous devez entrer un nombre positif (supérieur à 0).");
            scanner.close();
            return;
        }
        double[] nombres = new double[n];
        System.out.println("Veuillez saisir " + n + " nombres :");
        for (int i = 0; i < n; i++) {
            System.out.print("Nombre " + (i + 1) + " : ");
            nombres[i] = scanner.nextDouble();
        }
        Arrays.sort(nombres);
        double minimum = calculerMinimum(nombres);
        double maximum = calculerMaximum(nombres);
        double somme = calculerSomme(nombres);
        double moyenne = calculerMoyenne(somme, n);
        afficherResultats(nombres, minimum, maximum, somme, moyenne);
        scanner.close();
    }
    public static double calculerMinimum(double[] tableau) {
        return tableau[0];
    }
    public static double calculerMaximum(double[] tableau) {
        return tableau[tableau.length - 1];
    }
    public static double calculerSomme(double[] tableau) {
        double somme = 0;
        for (double nombre : tableau) {
            somme += nombre;
        }
        return somme;
    }
    public static double calculerMoyenne(double somme, int nombreElements) {
        return somme / nombreElements;
    }
    public static void afficherResultats(double[] tableau, double min, double max,
                                         double somme, double moyenne) {
        System.out.println("Résultat des calculs statistiques:");
        System.out.println("Tableau trié : " + Arrays.toString(tableau));
        System.out.println("Nombre d'éléments : " + tableau.length);
        System.out.printf("Minimum : %.2f\n", min);
        System.out.printf("Maximum : %.2f\n", max);
        System.out.printf("Somme : %.2f\n", somme);
        System.out.printf("Moyenne : %.2f\n", moyenne);
    }
}