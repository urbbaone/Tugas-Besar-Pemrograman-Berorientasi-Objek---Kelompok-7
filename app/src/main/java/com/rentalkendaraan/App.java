package com.rentalkendaraan;

import com.rentalkendaraan.domain.User;
import com.rentalkendaraan.repository.*;
import com.rentalkendaraan.service.*;
import com.rentalkendaraan.ui.LoginView;
import com.rentalkendaraan.ui.dashboard.*;

import java.io.*;
import java.util.Scanner;

/**
 * Entry point aplikasi — Composition Root.
 *
 * SOLID — Dependency Inversion (D):
 *   Semua dependency di-inject di sini, bukan di dalam service.
 *   Service hanya bergantung pada interface repository, tidak tahu
 *   detail implementasinya (JSON).
 *
 * Storage: JSON on-demand (data/*.json), tidak ada database eksternal.
 *
 * Urutan init:
 *   1. Buat file JSON jika belum ada (first-run initializer)
 *   2. Buat semua repository (implementasi JSON)
 *   3. Inject ke service via constructor
 *   4. Loop login → Dashboard → Logout
 */
public class App {

    public static void main(String[] args) {
        // 1. File JSON init
        initDataFiles();

        // 2 & 3. Wiring dependency (Composition Root)
        IUserRepository       userRepo      = new UserRepository();
        IKendaraanRepository  kendaraanRepo = new KendaraanRepository();
        IPelangganRepository  pelangganRepo = new PelangganRepository();
        ITransaksiRepository  transaksiRepo = new TransaksiRepository();

        AuthService      authService      = new AuthService(userRepo);
        KendaraanService kendaraanService = new KendaraanService(kendaraanRepo);
        PelangganService pelangganService = new PelangganService(pelangganRepo);
        TransaksiService transaksiService = new TransaksiService(transaksiRepo, kendaraanService, pelangganService);
        LaporanService   laporanService   = new LaporanService(transaksiRepo);

        // 4. Loop utama
        Scanner scanner = new Scanner(System.in);
        while (true) {
            User user = new LoginView(authService, scanner).show();
            if (user == null) break;
            buildDashboard(user, scanner, kendaraanService, pelangganService, transaksiService, laporanService)
                .showMenu();
        }
        scanner.close();
    }

    private static Dashboard buildDashboard(
            User user, Scanner scanner,
            KendaraanService kendaraanService,
            PelangganService pelangganService,
            TransaksiService transaksiService,
            LaporanService   laporanService) {

        return switch (user.getRole()) {
            case ADMIN -> new AdminDashboard(user, scanner, kendaraanService);
            case STAFF -> new StaffDashboard(user, scanner, kendaraanService, pelangganService, transaksiService);
            case OWNER -> new OwnerDashboard(user, scanner, laporanService, pelangganService);
        };
    }

    private static void initDataFiles() {
        new File("data").mkdirs();
        for (String p : new String[]{"data/kendaraan.json","data/pelanggan.json","data/transaksi.json"})
            createIfNotExists(p, "[]");
        createIfNotExists("data/users.json",
            "[\n  {\"username\":\"admin123\",\"password\":\"admin123\",\"role\":\"ADMIN\"},\n" +
            "  {\"username\":\"staff123\",\"password\":\"staff123\",\"role\":\"STAFF\"},\n" +
            "  {\"username\":\"owner123\",\"password\":\"owner123\",\"role\":\"OWNER\"}\n]");
    }

    private static void createIfNotExists(String path, String content) {
        File f = new File(path);
        if (!f.exists()) {
            try (FileWriter fw = new FileWriter(f)) { fw.write(content); }
            catch (IOException e) { System.err.println("[WARN] Gagal buat: " + path); }
        }
    }
}
