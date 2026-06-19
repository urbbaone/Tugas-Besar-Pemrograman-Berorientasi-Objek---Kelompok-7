package com.rentalkendaraan.ui.kendaraan;

import com.rentalkendaraan.service.KendaraanService;
import com.rentalkendaraan.util.ConsoleFormatter;
import java.util.*;

public class DaftarKendaraanView {
    private final KendaraanService service;
    private final Scanner scanner;
    private final boolean hanyaTersedia;

    public DaftarKendaraanView(KendaraanService service, Scanner scanner, boolean hanyaTersedia) {
        this.service = service;
        this.scanner = scanner;
        this.hanyaTersedia = hanyaTersedia;
    }

    public void show() {
        ConsoleFormatter.printHeader(hanyaTersedia ? "KENDARAAN TERSEDIA" : "DAFTAR KENDARAAN");
        List<String[]> rows = (hanyaTersedia ? service.getKendaraanTersedia() : service.getAllKendaraan()).stream()
                .map(k -> new String[]{k.getPlatNomor(), k.getJenisKendaraan(), ConsoleFormatter.formatRupiah(k.getHargaSewa()), k.getMerek(), k.getInfoTambahan(), k.getStatus().toDisplay()})
                .toList();
        ConsoleFormatter.printTable(new String[]{"Plat Nomor", "Jenis", "Harga/Hari", "Merek", "Info Tambahan", "Status"}, rows);
    }
}
