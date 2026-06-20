package com.rentalkendaraan.ui.transaksi;

import com.rentalkendaraan.domain.Kendaraan;
import com.rentalkendaraan.service.KendaraanService;
import com.rentalkendaraan.util.ConsoleFormatter;
import java.util.ArrayList; import java.util.List; import java.util.Scanner;

public class CekKendaraanTersediaView {
    private final KendaraanService service; private final Scanner scanner;
    public CekKendaraanTersediaView(KendaraanService s, Scanner sc) { service=s; scanner=sc; }
    public void show() {
        ConsoleFormatter.printHeader("DAFTAR KENDARAAN TERSEDIA");
        System.out.println("* Hanya kendaraan siap disewa yang ditampilkan."); ConsoleFormatter.printBlank();
        List<Kendaraan> list = service.getKendaraanTersedia();
        String[] h = {"Plat Nomor","Jenis","Harga/Hari","Merek","Info Tambahan","Status"};
        List<String[]> rows = new ArrayList<>();
        for (Kendaraan k : list) rows.add(new String[]{
            k.getPlatNomor(), k.getJenisKendaraan(), ConsoleFormatter.formatRupiah(k.getHargaSewa()),
            k.getMerek(), k.getInfoTambahan(), k.getStatus().toDisplay()});
        ConsoleFormatter.printTable(h, rows);
        ConsoleFormatter.printBlank();
        System.out.println("*Catatan: Kendaraan yang sedang disewa tidak ditampilkan.");
        ConsoleFormatter.printBlank(); ConsoleFormatter.pressEnterToContinue(scanner);
    }
}
