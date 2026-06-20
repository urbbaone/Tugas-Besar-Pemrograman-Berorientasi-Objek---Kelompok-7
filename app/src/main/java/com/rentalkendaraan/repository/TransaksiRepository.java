package com.rentalkendaraan.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.StatusTransaksi;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * SOLID — Single Responsibility (S): hanya mengurus baca/tulis transaksi.json
 * SOLID — Dependency Inversion (D): implements ITransaksiRepository
 */
public class TransaksiRepository implements ITransaksiRepository {

    private static final String FILE_PATH = "data/transaksi.json";
    private static final Gson   GSON      = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Transaksi> loadAll() {
        File f = new File(FILE_PATH);
        if (!f.exists() || f.length() == 0) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type t = new TypeToken<List<Transaksi>>(){}.getType();
            List<Transaksi> l = GSON.fromJson(r, t);
            return l != null ? l : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[ERROR] " + FILE_PATH + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Transaksi findById(String id) {
        return loadAll().stream()
                .filter(t -> t.getIdTransaksi().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    @Override
    public Transaksi findAktifByPlat(String plat) {
        return loadAll().stream()
                .filter(t -> t.getPlatNomorKendaraan().equalsIgnoreCase(plat)
                          && t.getStatusTransaksi() == StatusTransaksi.BERJALAN)
                .findFirst().orElse(null);
    }

    @Override
    public void tambah(Transaksi t) {
        List<Transaksi> list = loadAll(); list.add(t); saveAll(list);
    }

    @Override
    public void update(Transaksi updated) {
        List<Transaksi> list = loadAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdTransaksi().equals(updated.getIdTransaksi())) {
                list.set(i, updated); break;
            }
        }
        saveAll(list);
    }

    @Override
    public String generateNextId() {
        return String.format("TRX-%03d", loadAll().size() + 1);
    }

    private void saveAll(List<Transaksi> list) {
        try (Writer w = new FileWriter(FILE_PATH)) { GSON.toJson(list, w); }
        catch (IOException e) { System.err.println("[ERROR] Gagal simpan transaksi: " + e.getMessage()); }
    }
}
