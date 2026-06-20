package com.rentalkendaraan.ui.pelanggan;

import com.rentalkendaraan.domain.Pelanggan;
import com.rentalkendaraan.service.PelangganService;
import com.rentalkendaraan.util.*;
import java.util.Scanner;

public class DaftarPelangganView {
    private final PelangganService service; private final Scanner scanner;
    public DaftarPelangganView(PelangganService s, Scanner sc) { service=s; scanner=sc; }
    public void show() {
        ConsoleFormatter.printHeader("MENU PENDAFTARAN PELANGGAN");
        System.out.println("(ketik 0 pada Nomor KTP untuk batal)"); ConsoleFormatter.printLine(); ConsoleFormatter.printBlank();
        String ktp = InputHelper.readNonEmpty(scanner, "Masukkan Nomor KTP   : ");
        if ("0".equals(ktp)) return;
        String nama = InputHelper.readNonEmpty(scanner, "Masukkan Nama Lengkap: ");
        String telp = InputHelper.readNonEmpty(scanner, "Masukkan No Telepon  : ");
        try {
            service.daftarkanPelanggan(new Pelanggan(ktp, nama, telp));
            ConsoleFormatter.printBlank();
            ConsoleFormatter.printSuccess("Pelanggan " + nama + " (KTP: " + ktp + ") berhasil didaftarkan.");
        } catch (Exception e) { ConsoleFormatter.printBlank(); ConsoleFormatter.printError(e.getMessage()); }
        ConsoleFormatter.printBlank(); ConsoleFormatter.pressEnterToContinue(scanner);
    }
}
