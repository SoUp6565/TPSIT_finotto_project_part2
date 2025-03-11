package org.finotto;

import java.io.*;

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }
}