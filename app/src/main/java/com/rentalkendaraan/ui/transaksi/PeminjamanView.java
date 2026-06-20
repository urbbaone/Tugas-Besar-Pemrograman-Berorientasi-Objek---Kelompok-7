package com.rentalkendaraan.ui.transaksi;

import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.JenisPaket;
import com.rentalkendaraan.service.TransaksiService;
import com.rentalkendaraan.util.*;

import java.util.Scanner;

public class PeminjamanView {

    private final TransaksiService transaksiService;
    private final Scanner scanner;

    public PeminjamanView(TransaksiService s, Scanner sc) {
        this.transaksiService = s;
        this.scanner = sc;
    }

    public void show() {
        ConsoleFormatter.printHeader("MENU PEMINJAMAN KENDARAAN");
        System.out.println("(ketik 0 untuk batal)");
        ConsoleFormatter.printLine();
        ConsoleFormatter.printBlank();

        String ktp = InputHelper.readNonEmpty(scanner,
            "Masukkan Nomor KTP Pelanggan : ");
        if ("0".equals(ktp)) return;

        String plat = InputHelper.readNonEmpty(scanner,
            "Masukkan Plat Nomor Kendaraan: ");
        if ("0".equals(plat)) return;

        int durasi = InputHelper.readInt(scanner,
            "Rencana Durasi Sewa (Hari)  : ");

        if (durasi <= 0) {
            ConsoleFormatter.printError("Durasi sewa harus lebih dari 0 hari.");
            ConsoleFormatter.pressEnterToContinue(scanner);
            return;
        }

        try {
            System.out.println("\nMemproses transaksi...");
            Transaksi t = transaksiService.prosesPeminjaman(ktp, plat, durasi);
            cetakStrukSementara(t);
            ConsoleFormatter.printBlank();
            ConsoleFormatter.printSuccess(
                "Transaksi berhasil dicatat. Status kendaraan berubah menjadi SEDANG DISEWA.");

        } catch (Exception e) {
            ConsoleFormatter.printBlank();
            ConsoleFormatter.printError(e.getMessage());
        }

        ConsoleFormatter.printBlank();
        ConsoleFormatter.pressEnterToContinue(scanner);
    }

    private void cetakStrukSementara(Transaksi t) {
        var kendaraan = transaksiService.getKendaraan(t.getPlatNomorKendaraan());
        var pelanggan = transaksiService.getPelanggan(t.getNomorKTPPelanggan());
        long hargaNormal = kendaraan.getHargaSewa() * t.getDurasiSewa();

        ConsoleFormatter.printBlank();
        ConsoleFormatter.printStrukHeader("STRUK PEMINJAMAN SEMENTARA");
        ConsoleFormatter.printStrukRow("ID Transaksi",   t.getIdTransaksi());
        ConsoleFormatter.printStrukRow("Nama Pelanggan", pelanggan.getNamaLengkap());
        ConsoleFormatter.printStrukRow("Kendaraan",
            kendaraan.getJenisKendaraan() + " (" + kendaraan.getPlatNomor() + ")");
        ConsoleFormatter.printStrukRow("Durasi Sewa",    t.getDurasiSewa() + " Hari");

        if (t.getJenisPaket() == JenisPaket.MINGGUAN) {
            ConsoleFormatter.printStrukRow("Paket", "MINGGUAN (diskon 10%)");
            ConsoleFormatter.printStrukRow("Harga Normal",
                ConsoleFormatter.formatRupiah(hargaNormal));
            ConsoleFormatter.printStrukRow("Diskon 10%",
                "- " + ConsoleFormatter.formatRupiah(t.getDiskon()));
        } else {
            ConsoleFormatter.printStrukRow("Paket", "HARIAN");
        }
        ConsoleFormatter.printStrukRow("Estimasi Biaya",
            ConsoleFormatter.formatRupiah(t.getTotalBayar()));
        ConsoleFormatter.printStrukFooter();
    }
}