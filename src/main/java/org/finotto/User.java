package org.finotto;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private BankAccount account;
    private Wallet wallet;
    private List<Investment> investments;

    public User(String name) {
        this.name = name;
        this.account = new BankAccount(name);
        this.wallet = new Wallet();
        this.investments = new ArrayList<>();
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

    public void addMonthlyIncome() {
        wallet.addMonthlyIncome();
        for (Investment investment : investments) {
            investment.advanceTime(1);
        }
    }

    public void invest(double amount, int duration, int risk) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            investments.add(new Investment(amount, duration, risk));
            System.out.println("Investimento effettuato con successo.");
        } else {
            System.out.println("Fondi insufficienti per investire.");
        }
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
            }else {
                System.out.println("l' investimento non è ancora maturato");
            }
        }
    }
}


