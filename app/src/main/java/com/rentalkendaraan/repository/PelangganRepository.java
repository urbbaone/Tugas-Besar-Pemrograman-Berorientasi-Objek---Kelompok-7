package com.rentalkendaraan.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentalkendaraan.domain.Pelanggan;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * SOLID — Single Responsibility (S): hanya mengurus baca/tulis pelanggan.json
 * SOLID — Dependency Inversion (D): implements IPelangganRepository
 */
public class PelangganRepository implements IPelangganRepository {

    private static final String FILE_PATH = "data/pelanggan.json";
    private static final Gson   GSON      = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Pelanggan> loadAll() {
        File f = new File(FILE_PATH);
        if (!f.exists() || f.length() == 0) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type t = new TypeToken<List<Pelanggan>>(){}.getType();
            List<Pelanggan> l = GSON.fromJson(r, t);
            return l != null ? l : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[ERROR] " + FILE_PATH + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Pelanggan findByKTP(String ktp) {
        return loadAll().stream()
                .filter(p -> p.getNomorKTP().equals(ktp))
                .findFirst().orElse(null);
    }

    @Override
    public void tambah(Pelanggan p) {
        List<Pelanggan> list = loadAll(); list.add(p);
        try (Writer w = new FileWriter(FILE_PATH)) { GSON.toJson(list, w); }
        catch (IOException e) { System.err.println("[ERROR] Gagal simpan pelanggan: " + e.getMessage()); }
    }
}
