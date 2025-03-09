package org.finotto;

public class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner) {
        this.owner = owner;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Errore: Non puoi depositare un importo negativo o nullo");
            return;
        }
        if (balance < 0) {
            double debitoRimanente = Math.min(amount, -balance);
            balance += debitoRimanente;
            amount -= debitoRimanente;
            System.out.println("Il debito residuo è stato ridotto di: " + debitoRimanente);
        }
        balance += amount;
        System.out.println("Deposito effettuato. Nuovo saldo: " + balance);
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Saldo insufficiente per il prelievo.");
        }
    }

    public double getBalance() {
        return balance;
    }
}
