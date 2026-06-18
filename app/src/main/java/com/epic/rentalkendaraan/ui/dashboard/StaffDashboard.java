package com.rentalkendaraan.ui.dashboard;

import com.rentalkendaraan.domain.User;
import com.rentalkendaraan.service.*;
import com.rentalkendaraan.ui.kendaraan.DaftarKendaraanView;
import com.rentalkendaraan.ui.pelanggan.*;
import com.rentalkendaraan.ui.transaksi.*;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

/** SOLID — Single Responsibility: hanya routing menu Staff */
public class StaffDashboard implements Dashboard {
    private final User user; private final Scanner scanner;
    private final KendaraanService  kendaraanService;
    private final PelangganService  pelangganService;
    private final TransaksiService  transaksiService;

    public StaffDashboard(User user, Scanner scanner,
                          KendaraanService k, PelangganService p, TransaksiService t) {
        this.user = user; this.scanner = scanner;
        this.kendaraanService = k; this.pelangganService = p; this.transaksiService = t;
    }

    @Override public void showMenu() {
        boolean run = true;
        while (run) {
            printMenu();
            switch (InputHelper.readInt(scanner, "Pilihan Anda > ")) {
                case 1 -> new DaftarPelangganView(pelangganService, scanner).show();
                case 2 -> new CariPelangganView(pelangganService, scanner).show();
                case 3 -> new DaftarKendaraanView(kendaraanService, scanner, true).show();
                case 4 -> new PeminjamanView(transaksiService, scanner).show();
                case 5 -> new PengembalianView(transaksiService, scanner).show();
                case 0 -> { ConsoleFormatter.printInfo("Logout. Sampai jumpa, " + user.getUsername() + "!"); run = false; }
                default -> ConsoleFormatter.printError("Pilihan tidak valid (0-5).");
            }
            ConsoleFormatter.printBlank();
        }
    }

    private void printMenu() {
        ConsoleFormatter.printHeader("DASHBOARD - STAFF");
        System.out.println("Selamat Datang, " + user.getUsername() + "!");
        System.out.println("  1. Daftar Pelanggan Baru\n  2. Cari Data Pelanggan\n  3. Cek Kendaraan Tersedia\n  4. Proses Peminjaman (Sewa)\n  5. Proses Pengembalian\n  0. Logout");
    }
}
