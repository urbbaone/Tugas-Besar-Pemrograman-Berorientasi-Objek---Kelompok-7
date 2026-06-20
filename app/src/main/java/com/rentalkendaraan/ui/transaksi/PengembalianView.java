package com.rentalkendaraan.ui.transaksi;

import com.rentalkendaraan.domain.Kendaraan;
import com.rentalkendaraan.domain.Pelanggan;
import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.JenisPaket;
import com.rentalkendaraan.service.TransaksiService;
import com.rentalkendaraan.util.*;

import java.util.Scanner;

public class PengembalianView {

    private final TransaksiService transaksiService;
    private final Scanner scanner;

    public PengembalianView(TransaksiService s, Scanner sc) {
        this.transaksiService = s;
        this.scanner = sc;
    }

    public void show() {
        ConsoleFormatter.printHeader("MENU PENGEMBALIAN KENDARAAN");
        System.out.println("(ketik 0 untuk batal)");
        ConsoleFormatter.printLine();
        ConsoleFormatter.printBlank();

        String input = InputHelper.readNonEmpty(scanner,
            "Masukkan ID Transaksi atau Plat Nomor : ");
        if ("0".equals(input)) return;

        int hariTerlambat = InputHelper.readInt(scanner,
            "Durasi Keterlambatan (Hari, isi 0 jika tepat waktu) : ");

        if (hariTerlambat < 0) {
            ConsoleFormatter.printError("Hari keterlambatan tidak boleh negatif.");
            ConsoleFormatter.pressEnterToContinue(scanner);
            return;
        }

        try {
            System.out.println("\nMenghitung tagihan...");
            Transaksi t  = transaksiService.prosespengembalian(input, hariTerlambat);
            Pelanggan p  = transaksiService.getPelanggan(t.getNomorKTPPelanggan());
            Kendaraan k  = transaksiService.getKendaraan(t.getPlatNomorKendaraan());

            cetakStrukAkhir(t, p, k, hariTerlambat);
            ConsoleFormatter.printBlank();
            ConsoleFormatter.printSuccess(
                "Pengembalian berhasil. Status kendaraan \u2192 TERSEDIA.");

        } catch (Exception e) {
            ConsoleFormatter.printBlank();
            ConsoleFormatter.printError(e.getMessage());
        }

        ConsoleFormatter.printBlank();
        ConsoleFormatter.pressEnterToContinue(scanner);
    }

    private void cetakStrukAkhir(Transaksi t, Pelanggan p, Kendaraan k, int hariTerlambat) {
        long hargaNormal  = k.getHargaSewa() * t.getDurasiSewa();
        long biayaSewa    = hargaNormal - t.getDiskon();
        long denda        = k.hitungDenda(hariTerlambat);
        long total        = biayaSewa + denda;
        String tarifDenda = k.getJenisKendaraan().equals("MOBIL")
                            ? "Rp 50.000 khusus Mobil"
                            : "Rp 20.000 khusus Motor";

        ConsoleFormatter.printBlank();
        ConsoleFormatter.printStrukHeader("STRUK TAGIHAN AKHIR");
        ConsoleFormatter.printStrukRow("ID Transaksi", t.getIdTransaksi());
        ConsoleFormatter.printStrukRow("Pelanggan",    p.getNamaLengkap());
        ConsoleFormatter.printStrukRow("Kendaraan",
            k.getJenisKendaraan() + " (" + k.getPlatNomor() + ")");

        if (t.getJenisPaket() == JenisPaket.MINGGUAN) {
            ConsoleFormatter.printStrukRow("Harga Normal",
                ConsoleFormatter.formatRupiah(hargaNormal) + " (" + t.getDurasiSewa() + " Hari)");
            ConsoleFormatter.printStrukRow("Diskon 10%",
                "- " + ConsoleFormatter.formatRupiah(t.getDiskon()));
        }
        ConsoleFormatter.printStrukRow("Biaya Sewa",
            ConsoleFormatter.formatRupiah(biayaSewa) +
            " (" + t.getDurasiSewa() + " Hari, Paket " + t.getJenisPaket().name() + ")");

        if (hariTerlambat > 0) {
            ConsoleFormatter.printStrukRow("Denda Telat",
                ConsoleFormatter.formatRupiah(denda) +
                " (" + hariTerlambat + " Hari x " + tarifDenda + ")");
        } else {
            ConsoleFormatter.printStrukRow("Denda Telat", "Rp 0 (Tepat Waktu)");
        }

        System.out.println("-".repeat(38) + " +");
        ConsoleFormatter.printStrukRow("TOTAL BAYAR", ConsoleFormatter.formatRupiah(total));
        ConsoleFormatter.printStrukFooter();
    }
}
