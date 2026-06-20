package com.rentalkendaraan.service;

import com.rentalkendaraan.domain.Kendaraan;
import com.rentalkendaraan.domain.Pelanggan;
import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.JenisPaket;
import com.rentalkendaraan.domain.enums.StatusKendaraan;
import com.rentalkendaraan.domain.enums.StatusTransaksi;
import com.rentalkendaraan.repository.ITransaksiRepository;
import com.rentalkendaraan.strategy.HargaHarian;
import com.rentalkendaraan.strategy.HargaMingguan;
import com.rentalkendaraan.strategy.HargaStrategy;
import com.rentalkendaraan.util.DataNotFoundException;
import com.rentalkendaraan.util.KendaraanTidakTersediaException;
import com.rentalkendaraan.util.TransaksiSudahSelesaiException;

import java.time.LocalDate;
import java.util.List;

/**
 * SOLID — Single Responsibility (S): hanya logika bisnis transaksi
 * SOLID — Dependency Inversion (D): bergantung pada interface, bukan konkret
 * SOLID — Open/Closed (O): strategi harga diperluas tanpa ubah service ini
 */
public class TransaksiService {

    private final ITransaksiRepository transaksiRepo;
    private final KendaraanService     kendaraanService;
    private final PelangganService     pelangganService;

    public TransaksiService(ITransaksiRepository transaksiRepo,
                            KendaraanService     kendaraanService,
                            PelangganService     pelangganService) {
        this.transaksiRepo    = transaksiRepo;
        this.kendaraanService = kendaraanService;
        this.pelangganService = pelangganService;
    }

    public Transaksi prosesPeminjaman(String ktp, String plat, int durasi) {
        Pelanggan pelanggan = pelangganService.cariByKTP(ktp);
        Kendaraan kendaraan = kendaraanService.cariByPlat(plat);
        if (kendaraan.getStatus() != StatusKendaraan.TERSEDIA)
            throw new KendaraanTidakTersediaException("Kendaraan " + plat + " sedang tidak tersedia.");

        HargaStrategy strategy     = pilihStrategy(durasi);
        long          hargaNormal  = kendaraan.getHargaSewa() * durasi;
        long          biayaDiskon  = strategy.hitungBiaya(kendaraan.getHargaSewa(), durasi);
        long          diskon       = hargaNormal - biayaDiskon;
        JenisPaket    paket        = durasi >= 7 ? JenisPaket.MINGGUAN : JenisPaket.HARIAN;

        String id = transaksiRepo.generateNextId();
        Transaksi t = new Transaksi(id, ktp, plat.toUpperCase(), durasi,
                                    LocalDate.now().toString(), paket, diskon);
        t.setTotalBayar(biayaDiskon);

        transaksiRepo.tambah(t);
        kendaraanService.updateStatus(plat, StatusKendaraan.SEDANG_DISEWA);
        return t;
    }

    public Transaksi prosespengembalian(String idOrPlat, int hariTerlambat) {
        Transaksi t = transaksiRepo.findById(idOrPlat);
        if (t == null) t = transaksiRepo.findAktifByPlat(idOrPlat);
        if (t == null)
            throw new DataNotFoundException("Transaksi \"" + idOrPlat + "\" tidak ditemukan.");
        if (t.getStatusTransaksi() == StatusTransaksi.SELESAI)
            throw new TransaksiSudahSelesaiException("Transaksi " + t.getIdTransaksi() + " sudah SELESAI.");

        Kendaraan k      = kendaraanService.cariByPlat(t.getPlatNomorKendaraan());
        long biayaSewa   = (k.getHargaSewa() * t.getDurasiSewa()) - t.getDiskon();
        long denda       = k.hitungDenda(hariTerlambat);
        t.setTotalBayar(biayaSewa + denda);
        t.setStatusTransaksi(StatusTransaksi.SELESAI);

        transaksiRepo.update(t);
        kendaraanService.updateStatus(t.getPlatNomorKendaraan(), StatusKendaraan.TERSEDIA);
        return t;
    }

    /** SOLID O: tambah strategi baru tanpa ubah method ini */
    public HargaStrategy pilihStrategy(int durasi) {
        return durasi >= 7 ? new HargaMingguan() : new HargaHarian();
    }

    public List<Transaksi> getAllTransaksi()         { return transaksiRepo.loadAll(); }
    public Kendaraan getKendaraan(String plat)       { return kendaraanService.cariByPlat(plat); }
    public Pelanggan getPelanggan(String ktp)        { return pelangganService.cariByKTP(ktp); }
}
