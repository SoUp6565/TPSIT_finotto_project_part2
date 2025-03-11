package org.finotto;

import java.io.*;
import java.util.ArrayList;

class UserManager {
    private final ArrayList<User> users;
    private static final String FILE_NAME = "users.csv";

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public void registerUser(String username, String password) {
        if (findUser(username) != null) {
            System.out.println("Username già in uso!");
            return;
        }
        users.add(new User(username, password));
        InitialSaveUsers();
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

    public void InitialSaveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.toCSV() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void finalSaver(User user) {
        File file = new File(FILE_NAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(user.getUsername())&&user.checkPassword(parts[1])){
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                        writer.write(user.toCSV());
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCSV(line);
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento degli utenti.");
        }
    }
}