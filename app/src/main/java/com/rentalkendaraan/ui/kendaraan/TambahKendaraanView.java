package com.rentalkendaraan.ui.kendaraan;

import com.rentalkendaraan.domain.*;
import com.rentalkendaraan.service.KendaraanService;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

public class TambahKendaraanView {
    private final KendaraanService service;
    private final Scanner scanner;

    public TambahKendaraanView(KendaraanService service, Scanner scanner) { this.service = service; this.scanner = scanner; }

    public void show() {
        ConsoleFormatter.printHeader("TAMBAH KENDARAAN");
        int jenis = InputHelper.readInt(scanner, "Jenis (1 Mobil, 2 Motor, 0 Batal) > ");
        if (jenis == 0) return;
        String plat = InputHelper.readNonEmpty(scanner, "Plat Nomor (0 batal) > ");
        if ("0".equals(plat)) return;
        long harga = InputHelper.readLong(scanner, "Harga Sewa/Hari > ");
        String merek = InputHelper.readNonEmpty(scanner, "Merek > ");
        try {
            if (jenis == 1) service.tambahKendaraan(new Mobil(plat, merek, harga, InputHelper.readNonEmpty(scanner, "Transmisi > "), InputHelper.readInt(scanner, "Jumlah Pintu > ")));
            else if (jenis == 2) service.tambahKendaraan(new Motor(plat, merek, harga, InputHelper.readNonEmpty(scanner, "Jenis Transmisi > ")));
            else { ConsoleFormatter.printError("Jenis tidak valid."); return; }
            ConsoleFormatter.printSuccess("Kendaraan berhasil ditambahkan.");
        } catch (DuplicateDataException e) { ConsoleFormatter.printError(e.getMessage()); }
    }
}
