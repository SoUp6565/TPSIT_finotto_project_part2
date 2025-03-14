package org.finotto;

public class BankAccount {
    private double balance;

    public BankAccount() {
        this.balance = 0.0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Errore: Non puoi depositare un importo negativo o nullo");
            return false;
        }
        if (balance < 0) {
            double debitoRimanente = Math.min(amount, -balance);
            balance += debitoRimanente;
            amount -= debitoRimanente;
            System.out.println("Il debito residuo è stato ridotto di: " + debitoRimanente);
        }
        balance += amount;
        System.out.println("Deposito effettuato. Nuovo saldo: " + balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Saldo insufficiente per il prelievo.");
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }
}