package com.rentalkendaraan.ui.kendaraan;

import com.rentalkendaraan.service.KendaraanService;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

public class HapusKendaraanView {
    private final KendaraanService service;
    private final Scanner scanner;

    public HapusKendaraanView(KendaraanService service, Scanner scanner) { this.service = service; this.scanner = scanner; }

    public void show() {
        ConsoleFormatter.printHeader("HAPUS KENDARAAN");
        while (true) {
            String plat = InputHelper.readNonEmpty(scanner, "Plat Nomor (0 batal) > ");
            if ("0".equals(plat)) return;
            try {
                service.hapusKendaraan(plat);
                ConsoleFormatter.printSuccess("Kendaraan berhasil dihapus.");
            } catch (DataNotFoundException | KendaraanTidakTersediaException e) { ConsoleFormatter.printError(e.getMessage()); }
        }
    }
}
