package org.finotto;

public class Investment {
    private double amount;
    private int duration;
    private int elapsedMonths;
    private int risk;

    public Investment(double amount, int duration, int risk) {
        this.amount = amount;
        this.duration = duration;
        this.elapsedMonths = 0;
        this.risk = risk;
    }

    public void advanceTime(int months) {
        this.elapsedMonths += months;
    }

    public boolean isMatured() {
        return elapsedMonths >= duration;
    }

    public double getReturn() {
        double multiplier = 1.0;

        if (risk == 1) { // basso
            if (Math.random() < 0.9) {
                multiplier = 1.15;
            }else{
                multiplier = 0.90;
            }
        } else if (risk == 2) { // medio
            if (Math.random() < 0.7) {
                multiplier = 1.25;
            }else {
                multiplier = 0.75;
            }
        } else if (risk == 3) { // alto
            if (Math.random() < 0.5) {
                multiplier = 2.0;
            }else {
                multiplier = 0.5;
            }
        }

        return amount * multiplier;
    }
}

