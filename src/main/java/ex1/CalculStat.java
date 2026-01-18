package ex1;

import java.util.Arrays;
import java.util.Scanner;

public class CalculStat {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Combien de nombres voulez-vous saisir ? ");
        int n = sc.nextInt();

        double[] tab = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Entrez le nombre " + (i + 1) + " : ");
            tab[i] = sc.nextDouble();
        }

        double min = tab[0];
        double max = tab[0];
        double somme = 0;

        for (double v : tab) {
            if (v < min) min = v;
            if (v > max) max = v;
            somme += v;
        }

        double moyenne = somme / n;

        System.out.println("\n--- Résultats ---");
        System.out.println("Minimum : " + min);
        System.out.println("Maximum : " + max);
        System.out.println("Somme : " + somme);
        System.out.println("Moyenne : " + moyenne);

        Arrays.sort(tab);
        System.out.println("Tableau trié : " + Arrays.toString(tab));
    }
}
