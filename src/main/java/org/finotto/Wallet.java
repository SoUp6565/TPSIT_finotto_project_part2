package org.finotto;

public class Wallet {
    private double money;

    public Wallet() {
        this.money = 100.0;
    }

    public void addCash(double total) {
        if (total > 0) {
            money += total;
        }
    }

    public void spend(double total) {
        if (total <= money) {
            money -= total;
        }
    }

    public double getCash() {
        return money;
    }

    public void setCash(double value){
        this.money = value;
    }


    public void addMonthlyIncome() {
        money += 100.0;
    }
}
