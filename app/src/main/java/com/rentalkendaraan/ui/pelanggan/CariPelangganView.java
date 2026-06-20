package com.rentalkendaraan.ui.pelanggan;

import com.rentalkendaraan.domain.Pelanggan;
import com.rentalkendaraan.service.PelangganService;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

public class CariPelangganView {
    private final PelangganService service; private final Scanner scanner;
    public CariPelangganView(PelangganService s, Scanner sc) { service=s; scanner=sc; }
    public void show() {
        ConsoleFormatter.printHeader("MENU PENCARIAN PELANGGAN");
        System.out.println("(ketik 0 untuk batal)"); ConsoleFormatter.printLine(); ConsoleFormatter.printBlank();
        String ktp = InputHelper.readNonEmpty(scanner, "Masukkan Nomor KTP: ");
        if ("0".equals(ktp)) return;
        try {
            Pelanggan p = service.cariByKTP(ktp);
            ConsoleFormatter.printBlank(); ConsoleFormatter.printFound();
            ConsoleFormatter.printStrukRow("Nama Lengkap", p.getNamaLengkap());
            ConsoleFormatter.printStrukRow("Nomor KTP",   p.getNomorKTP());
            ConsoleFormatter.printStrukRow("No Telepon",  p.getNoTelepon());
        } catch (Exception e) { ConsoleFormatter.printBlank(); ConsoleFormatter.printError(e.getMessage()); }
        ConsoleFormatter.printBlank(); ConsoleFormatter.pressEnterToContinue(scanner);
    }
}
