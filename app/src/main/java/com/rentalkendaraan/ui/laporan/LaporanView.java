package com.rentalkendaraan.ui.laporan;

import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.StatusTransaksi;
import com.rentalkendaraan.service.LaporanService;
import com.rentalkendaraan.service.PelangganService;
import com.rentalkendaraan.util.ConsoleFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tampilan laporan riwayat & pendapatan untuk Owner.
 */
public class LaporanView {

    private final LaporanService  laporanService;
    private final PelangganService pelangganService;
    private final Scanner         scanner;

    public LaporanView(LaporanService laporanService,
                       PelangganService pelangganService,
                       Scanner scanner) {
        this.laporanService   = laporanService;
        this.pelangganService = pelangganService;
        this.scanner          = scanner;
    }

    public void show() {
        ConsoleFormatter.printHeader("LAPORAN RIWAYAT & PENDAPATAN");

        List<Transaksi> riwayat = laporanService.getRiwayatTransaksi();
        long totalPendapatan    = laporanService.hitungTotalPendapatan();

        String[] headers = { "ID Transaksi", "Pelanggan", "Kendaraan", "Durasi", "Status", "Total Tagihan" };
        List<String[]> rows = new ArrayList<>();

        for (Transaksi t : riwayat) {
            String namaPelanggan = getNamaPelanggan(t.getNomorKTPPelanggan());
            String totalStr = t.getStatusTransaksi() == StatusTransaksi.SELESAI
                    ? ConsoleFormatter.formatRupiah(t.getTotalBayar())
                    : "-";

            rows.add(new String[]{
                t.getIdTransaksi(),
                namaPelanggan,
                t.getPlatNomorKendaraan(),
                t.getDurasiSewa() + " Hari",
                t.getStatusTransaksi().name(),
                totalStr
            });
        }

        ConsoleFormatter.printTable(headers, rows);
        ConsoleFormatter.printBlank();
        System.out.println("TOTAL PENDAPATAN (Transaksi Selesai): "
                + ConsoleFormatter.formatRupiah(totalPendapatan));
        System.out.println("=".repeat(60));
        ConsoleFormatter.printBlank();
        ConsoleFormatter.pressEnterToContinue(scanner);
    }

    private String getNamaPelanggan(String ktp) {
        try {
            return pelangganService.cariByKTP(ktp).getNamaLengkap();
        } catch (Exception e) {
            return ktp; // fallback ke KTP jika pelanggan tidak ditemukan
        }
    }
}
