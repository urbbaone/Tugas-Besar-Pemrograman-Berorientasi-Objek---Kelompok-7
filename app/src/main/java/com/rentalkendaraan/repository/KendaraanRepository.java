package com.rentalkendaraan.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentalkendaraan.domain.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class KendaraanRepository implements IKendaraanRepository {
    private static final String FILE_PATH = "data/kendaraan.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Kendaraan.class, (JsonDeserializer<Kendaraan>) (json, type, ctx) -> {
                JsonObject o = json.getAsJsonObject();
                String jenis = o.has("jenisKendaraan") ? o.get("jenisKendaraan").getAsString() : "";
                return ctx.deserialize(json, "MOTOR".equalsIgnoreCase(jenis) ? Motor.class : Mobil.class);
            }).setPrettyPrinting().create();

    @Override public List<Kendaraan> loadAll() {
        File f = new File(FILE_PATH);
        if (!f.exists() || f.length() == 0) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type type = new TypeToken<List<Kendaraan>>(){}.getType();
            List<Kendaraan> list = gson.fromJson(r, type);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) { return new ArrayList<>(); }
    }

    @Override public Kendaraan findByPlat(String platNomor) {
        return loadAll().stream().filter(k -> k.getPlatNomor().equalsIgnoreCase(platNomor)).findFirst().orElse(null);
    }
    @Override public void tambah(Kendaraan kendaraan) { List<Kendaraan> list = loadAll(); list.add(kendaraan); save(list); }
    @Override public void update(Kendaraan kendaraan) {
        List<Kendaraan> list = loadAll();
        for (int i = 0; i < list.size(); i++) if (list.get(i).getPlatNomor().equalsIgnoreCase(kendaraan.getPlatNomor())) { list.set(i, kendaraan); break; }
        save(list);
    }
    @Override public void hapus(String platNomor) { List<Kendaraan> list = loadAll(); list.removeIf(k -> k.getPlatNomor().equalsIgnoreCase(platNomor)); save(list); }
    private void save(List<Kendaraan> list) {
        new File("data").mkdirs();
        try (Writer w = new FileWriter(FILE_PATH)) { gson.toJson(list, w); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
