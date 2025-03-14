package org.finotto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class UserManager {
    private static final String DIRECTORY_NAME = "usersData";

    public UserManager() {
        createUsersDirectory();
    }

    private void createUsersDirectory() {
        File dir = new File(DIRECTORY_NAME);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void registerUser(String username, String password) {
        File userFile = new File(DIRECTORY_NAME + "/" + username + ".csv");
        if (userFile.exists()) {
            System.out.println("Username già in uso!");
            return;
        }

        User newUser = new User(username, password);
        saveUserToFile(newUser, "at registration: ");
        System.out.println("Registrazione completata.");
    }

    public User authenticate(String username, String password) {
        File userFile = new File(DIRECTORY_NAME + "/" + username + ".csv");
        if (!userFile.exists()) {
            System.out.println("Utente non trovato.");
            return null;
        }

        User user = loadUserFromFile(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }

        System.out.println("Credenziali errate.");
        return null;
    }

    public void saveUserToFile(User user, String where) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DIRECTORY_NAME + "/" + user.getUsername() + ".csv", true))) {
            writer.write(user.toCSV(where));
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio dell'utente.");
        }
    }

    private User loadUserFromFile(String username) {
        File userFile = new File(DIRECTORY_NAME + "/" + username + ".csv");
        if (!userFile.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            String helper = "";
            while ((line = reader.readLine())!= null){
                helper = line;
            }
            return  User.fromCSV(helper);
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento dell'utente.");
        }
        return null;
    }

    public String getHystory(String username){
        File userFile = new File(DIRECTORY_NAME + "/" + username + ".csv");
        if (!userFile.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            StringBuilder text = new StringBuilder();

            while ((line = reader.readLine())!= null){
                String[] parts = line.split(",");
                double balance = Double.parseDouble(parts[3]);
                double walletMoney = Double.parseDouble(parts[4]);
                List<Investment> investments = new ArrayList<>();
                for (int i = 5; i < parts.length; i += 4) {
                    investments.add(Investment.fromCSV(parts[i] + "," + parts[i + 1] + "," + parts[i + 2] + "," + parts[i + 3]));
                }
                text.append("MOMENT: ").append(parts[0]).append("  ")
                        .append("BALANCE: ").append(balance).append("  ")
                        .append("WALLET: ").append(walletMoney).append("  ");
                for (Investment i:investments){
                    text.append(i.getInformationForHystory());
                }
                text.append("\n");
            }

            return text.toString();
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento dell'utente.");
        }
        return null;
    }
}