package org.finotto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User("Mirko Pavan");

        boolean run = true;

        while (run) {
            showMenu();

            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    user.addMonthlyIncome();
                    System.out.println("Entrate mensili aggiunte");
                    break;
                case 2:
                    System.out.println("il tuo wallet contiene: "+user.getWallet());
                    break;
                case 3:
                    System.out.print("Inserisci importo da depositare: ");
                    double depositAmount = scanner.nextDouble();
                    user.depositToAccount(depositAmount);
                    break;
                case 4:
                    System.out.print("Inserisci importo da prelevare: ");
                    double withdrawAmount = scanner.nextDouble();
                    user.withdrawFromAccount(withdrawAmount);
                    break;
                case 5:
                    System.out.print("Inserisci importo da investire: ");
                    double investAmount = scanner.nextDouble();
                    System.out.print("Inserisci durata in mesi: ");
                    int duration = scanner.nextInt();
                    System.out.print("Inserisci livello di rischio (1-Basso, 2-Medio, 3-Alto): ");
                    int risk = scanner.nextInt();
                    user.invest(investAmount, duration, risk);
                    break;
                case 6:
                    user.checkInvestments();
                    break;
                case 0:
                    run = false;
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
        scanner.close();
    }

    public static void showMenu() {
        System.out.println("\n1. Aggiungi entrate mensili (eseguendo questo comando farai anche avanzare di un mese tutti i tuoi investimenti attivi)");
        System.out.println("2. Guarda il tuo wallet");
        System.out.println("3. Deposita sul conto");
        System.out.println("4. Preleva dal conto");
        System.out.println("5. Effettua un investimento");
        System.out.println("6. Controlla investimenti maturati");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }
}
