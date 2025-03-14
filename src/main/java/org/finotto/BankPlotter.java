package org.finotto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankPlotter {
    private LocalDateTime date = LocalDateTime.now();
    private static final String DIRECTORY_NAME = "usersData";


    public void AddMonthDate(User user){
        date = date.plusMonths(1);
        saveUserToBankPlotter(user);
    }

    public void registerUserForPlotter(String username, String password) {
        User newUser = new User(username, password);
        saveUserToBankPlotter(newUser);
    }

    public void saveUserToBankPlotter(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataFormat = date.format(formatter);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIRECTORY_NAME + "/" + user.getUsername() + "BankPlotter" + ".csv", true))) {
            writer.write(dataFormat+";"+(int)(user.getAccount())+";"+(int)(user.getWallet())+"\n");
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dell'utente.");
        }
    }
}
