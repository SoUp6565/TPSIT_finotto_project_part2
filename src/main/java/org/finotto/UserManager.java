package org.finotto;

import java.io.*;
import java.util.ArrayList;

class UserManager {
    private final ArrayList<User> users;
    private static final String FILE_NAME = "users.txt";

    public UserManager() {
        users = new ArrayList<>();
        downloadUsers();
    }

    public void registerUser(String username, String password) {
        if (findUser(username) != null) {
            System.out.println("Username già in uso!");
            return;
        }
        users.add(new User(username, password));
        saveUsers();
        System.out.println("Registrazione completata.");
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        System.out.println("Credenziali errate.");
        return null;
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadUsers() {
        BufferedReader reader = null;
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return; // Se il file non esiste, non facciamo nulla
            }
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante il download degli utenti.");
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) {}
        }
    }
}
