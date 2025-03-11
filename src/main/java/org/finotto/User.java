package org.finotto;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private BankAccount account;
    private Wallet wallet;
    private List<Investment> investments;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.account = new BankAccount(username);
        this.wallet = new Wallet();
        this.investments = new ArrayList<>();
    }

    public User(String username, String password, double balance, double walletMoney, List<Investment> investments) {
        this.username = username;
        this.password = password;
        this.account = new BankAccount(username);
        this.wallet = new Wallet();
        this.wallet.setCash(walletMoney); // Adjust for initial wallet amount
        this.account.deposit(balance);
        this.investments = investments;
    }

    public String toCSV(String where) {
        StringBuilder sb = new StringBuilder();
        sb.append(where).append(",").append(username).append(",").append(password).append(",").append(account.getBalance()).append(",").append(wallet.getCash());
        for (Investment inv : investments) {
            sb.append(",").append(inv.toCSV());
        }
        sb.append("\n");
        return sb.toString();
    }

    public static User fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        String username = parts[1];
        String password = parts[2];
        double balance = Double.parseDouble(parts[3]);
        double walletMoney = Double.parseDouble(parts[4]);
        List<Investment> investments = new ArrayList<>();
        for (int i = 5; i < parts.length; i += 4) {
            investments.add(Investment.fromCSV(parts[i] + "," + parts[i + 1] + "," + parts[i + 2] + "," + parts[i + 3]));
        }
        return new User(username, password, balance, walletMoney, investments);
    }

    public void depositToAccount(double amount) {
        if (wallet.getCash() >= amount) {
            wallet.spend(amount);
            account.deposit(amount);
        } else {
            System.out.println("Non hai abbastanza denaro nel portafoglio");
        }
    }

    public void withdrawFromAccount(double amount) {
        if (amount <= account.getBalance()) {
            account.withdraw(amount);
            wallet.addCash(amount);
        } else {
            System.out.println("Saldo insufficiente nel conto");
        }
    }

    public double getWallet() {
        return wallet.getCash();
    }

    public double getAccount(){return account.getBalance();}

    public void addMonthlyIncome() {
        wallet.addMonthlyIncome();
        for (Investment investment : investments) {
            investment.advanceTime(1);
        }
    }

    public void invest(double amount, int duration, int risk) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            investments.add(new Investment(amount, duration, risk, 0));
            System.out.println("Investimento effettuato con successo.");
        } else {
            System.out.println("Fondi insufficienti per investire.");
        }
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void checkInvestments() {
        for (int i = 0; i < investments.size(); i++) {
            Investment inv = investments.get(i);
            if (inv.isMatured()) {
                double returnedAmount = inv.getReturn();
                System.out.println("Un investimento è maturato e hai guadagnato " + returnedAmount);
                account.deposit(returnedAmount);
                investments.remove(i);
                i--;
            } else {
                System.out.println("L'investimento non è ancora maturato");
            }
        }
    }
}