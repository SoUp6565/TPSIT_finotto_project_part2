package org.finotto;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        initialConfiguration();
    }

    public static void initialConfiguration() {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        User loggedInUser = null;

        while (loggedInUser == null) {
            System.out.println("1. Registrati");
            System.out.println("2. Accedi");
            System.out.print("Scegli un'opzione: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                userManager.registerUser(username, password);
            } else if (choice == 2) {
                loggedInUser = userManager.authenticate(username, password);
            }
        }

        System.out.println("Benvenuto, " + loggedInUser.getUsername() + "!");
        userPrompt(loggedInUser);
    }

    public static void userPrompt(User user) {
        Scanner scanner = new Scanner(System.in);
        UserManager helper = new UserManager();
        boolean run = true;

        while (run) {
            showMenu();
            int scelta = scanner.nextInt();
            switch (scelta) {
                case 1:
                    user.addMonthlyIncome();
                    System.out.println("Entrate mensili aggiunte");
                    helper.saveUserToFile(user, "add monthly income: ");
                    break;
                case 2:
                    System.out.println("nel tuo conto corrente ci sono: " + user.getAccount());
                    helper.saveUserToFile(user, "watch bank account: ");
                    break;
                case 3:
                    System.out.println("Il tuo wallet contiene: " + user.getWallet());
                    helper.saveUserToFile(user, "watch wallet: ");
                    break;
                case 4:
                    System.out.print("Inserisci importo da depositare: ");
                    double depositAmount = scanner.nextDouble();
                    user.depositToAccount(depositAmount);
                    helper.saveUserToFile(user, "deposit: ");
                    break;
                case 5:
                    System.out.print("Inserisci importo da prelevare: ");
                    double withdrawAmount = scanner.nextDouble();
                    user.withdrawFromAccount(withdrawAmount);
                    helper.saveUserToFile(user, "withdraw: ");
                    break;
                case 6:
                    System.out.print("Inserisci importo da investire: ");
                    double investAmount = scanner.nextDouble();
                    System.out.print("Inserisci durata in mesi: ");
                    int duration = scanner.nextInt();
                    System.out.print("Inserisci livello di rischio (1-Basso, 2-Medio, 3-Alto): ");
                    int risk = scanner.nextInt();
                    user.invest(investAmount, duration, risk);
                    helper.saveUserToFile(user, "create an investment: ");
                    break;
                case 7:
                    user.checkInvestments();
                    helper.saveUserToFile(user, "check investment: ");
                    break;
                case 0:
                    run = false;
                    System.out.println("Uscita dal programma.");
                    helper.saveUserToFile(user,"at the end of program: ");
                    break;
                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println("\n1. Aggiungi entrate mensili");
        System.out.println("2. Guarda il tuo conto bancario");
        System.out.println("3. Guarda il tuo wallet");
        System.out.println("4. Deposita sul conto");
        System.out.println("5. Preleva dal conto");
        System.out.println("6. Effettua un investimento");
        System.out.println("7. Controlla investimenti maturati");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }
}