package org.finotto;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankPlotter {
    private LocalDateTime date = LocalDateTime.now();
    private static final String DIRECTORY_NAME = "usersData";

    public void setUp(User user){
        File userFile = new File(DIRECTORY_NAME + "/" + user.getUsername() + "BankPlotter" + ".csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            String helper = "";
            while ((line = reader.readLine())!= null){
                helper = line;
            }
            String[] parts = helper.split(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            date = LocalDateTime.parse(parts[0], formatter);
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del BankPlotter.");
        }
    }

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
