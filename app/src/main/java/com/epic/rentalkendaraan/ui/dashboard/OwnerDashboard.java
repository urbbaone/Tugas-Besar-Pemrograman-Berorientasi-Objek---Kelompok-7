package com.rentalkendaraan.ui.dashboard;

import com.rentalkendaraan.domain.User;
import com.rentalkendaraan.service.*;
import com.rentalkendaraan.ui.laporan.LaporanView;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

/** SOLID — Single Responsibility: hanya routing menu Owner */
public class OwnerDashboard implements Dashboard {
    private final User user; private final Scanner scanner;
    private final LaporanService   laporanService;
    private final PelangganService pelangganService;

    public OwnerDashboard(User user, Scanner scanner,
                          LaporanService l, PelangganService p) {
        this.user = user; this.scanner = scanner;
        this.laporanService = l; this.pelangganService = p;
    }

    @Override public void showMenu() {
        boolean run = true;
        while (run) {
            printMenu();
            switch (InputHelper.readInt(scanner, "Pilihan Anda > ")) {
                case 1 -> new LaporanView(laporanService, pelangganService, scanner).show();
                case 0 -> { ConsoleFormatter.printInfo("Logout. Sampai jumpa, " + user.getUsername() + "!"); run = false; }
                default -> ConsoleFormatter.printError("Pilihan tidak valid (0-1).");
            }
            ConsoleFormatter.printBlank();
        }
    }

    private void printMenu() {
        ConsoleFormatter.printHeader("DASHBOARD - OWNER");
        System.out.println("Selamat Datang, " + user.getUsername() + ".");
        System.out.println("  1. Lihat Laporan Pendapatan & Riwayat\n  0. Logout");
    }
}
