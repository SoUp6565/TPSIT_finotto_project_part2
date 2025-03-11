package org.finotto;

import java.util.Random;

public class Investment {
    private double amount;
    private int duration;
    private int elapsedMonths;
    private int risk;

    public Investment(double amount, int duration, int risk, int elapsedMonths) {
        this.amount = amount;
        this.duration = duration;
        this.risk = risk;
        this.elapsedMonths = elapsedMonths;
    }

    public String getInformationForHystory(){
        return "INVESTMENT-AMOUNT: " + amount + "  "
                +"INVESTMENT-DURATION: " + duration + "  "
                +"INVESTMENT-RISK: " + risk + "  "
                + "INVESTMENT-ELAPSED-MONTHS: " + elapsedMonths + "  ";
    }

    public void advanceTime(int months) {
        this.elapsedMonths += months;
    }

    public boolean isMatured() {
        return elapsedMonths >= duration;
    }

    public double getReturn() {
        double multiplier = 1.0;
        Random random = new Random();

        if (risk == 1) { // basso
            multiplier = (random.nextDouble() < 0.9) ? 1.15 : 0.90;
        } else if (risk == 2) { // medio
            multiplier = (random.nextDouble() < 0.7) ? 1.25 : 0.75;
        } else if (risk == 3) { // alto
            multiplier = (random.nextDouble() < 0.5) ? 2.0 : 0.5;
        }

        return amount * multiplier;
    }

    public String toCSV() {
        return amount + "," + duration + "," + risk + "," + elapsedMonths;
    }

    public static Investment fromCSV(String csv) {
        String[] parts = csv.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Formato CSV non valido per Investment");
        }
        double amount = Double.parseDouble(parts[0]);
        int duration = Integer.parseInt(parts[1]);
        int risk = Integer.parseInt(parts[2]);
        int elapsedMonths = Integer.parseInt(parts[3]);
        return new Investment(amount, duration, risk, elapsedMonths);
    }
}

