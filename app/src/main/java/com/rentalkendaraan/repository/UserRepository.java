package com.rentalkendaraan.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentalkendaraan.domain.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * SOLID — Single Responsibility (S): hanya mengurus baca/tulis users.json
 * SOLID — Dependency Inversion (D): implements IUserRepository
 * SOLID — Open/Closed (O): swap ke DB → buat UserDbRepository, tidak ubah class ini
 */
public class UserRepository implements IUserRepository {

    private static final String FILE_PATH = "data/users.json";
    private static final Gson   GSON      = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<User> loadAll() {
        File f = new File(FILE_PATH);
        if (!f.exists() || f.length() == 0) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type t = new TypeToken<List<User>>(){}.getType();
            List<User> list = GSON.fromJson(r, t);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[ERROR] " + FILE_PATH + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public User findByUsername(String username) {
        return loadAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}
