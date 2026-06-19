package com.rentalkendaraan.ui.dashboard;

import com.rentalkendaraan.domain.User;
import com.rentalkendaraan.service.KendaraanService;
import com.rentalkendaraan.ui.kendaraan.*;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

/** SOLID — Single Responsibility: hanya routing menu Admin */
public class AdminDashboard implements Dashboard {
    private final User user; private final Scanner scanner;
    private final KendaraanService kendaraanService;

    public AdminDashboard(User user, Scanner scanner, KendaraanService kendaraanService) {
        this.user = user; this.scanner = scanner;
        this.kendaraanService = kendaraanService;
    }

    @Override public void showMenu() {
        boolean run = true;
        while (run) {
            printMenu();
            switch (InputHelper.readInt(scanner, "Pilihan Anda > ")) {
                case 1 -> new TambahKendaraanView(kendaraanService, scanner).show();
                case 2 -> new DaftarKendaraanView(kendaraanService, scanner, false).show();
                case 3 -> new HapusKendaraanView(kendaraanService, scanner).show();
                case 0 -> { ConsoleFormatter.printInfo("Logout. Sampai jumpa, " + user.getUsername() + "!"); run = false; }
                default -> ConsoleFormatter.printError("Pilihan tidak valid (0-3).");
            }
            ConsoleFormatter.printBlank();
        }
    }

    private void printMenu() {
        ConsoleFormatter.printHeader("DASHBOARD - ADMIN");
        System.out.println("Selamat Datang, " + user.getUsername() + "!");
        System.out.println("  1. Tambah Kendaraan Baru\n  2. Lihat Semua Kendaraan\n  3. Hapus Kendaraan\n  0. Logout");
    }
}
