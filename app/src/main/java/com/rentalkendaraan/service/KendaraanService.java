package com.rentalkendaraan.service;

import com.rentalkendaraan.domain.Kendaraan;
import com.rentalkendaraan.domain.enums.StatusKendaraan;
import com.rentalkendaraan.repository.IKendaraanRepository;
import com.rentalkendaraan.util.*;
import java.util.List;

public class KendaraanService {
    private final IKendaraanRepository repo;
    public KendaraanService(IKendaraanRepository repo) { this.repo = repo; }

    public void tambahKendaraan(Kendaraan kendaraan) {
        if (repo.findByPlat(kendaraan.getPlatNomor()) != null) throw new DuplicateDataException("Plat nomor sudah terdaftar.");
        kendaraan.setStatus(StatusKendaraan.TERSEDIA);
        repo.tambah(kendaraan);
    }

    public Kendaraan cariByPlat(String platNomor) {
        Kendaraan k = repo.findByPlat(platNomor);
        if (k == null) throw new DataNotFoundException("Kendaraan tidak ditemukan.");
        return k;
    }

    public void hapusKendaraan(String platNomor) {
        Kendaraan k = cariByPlat(platNomor);
        if (k.getStatus() == StatusKendaraan.SEDANG_DISEWA) throw new KendaraanTidakTersediaException("Kendaraan sedang disewa.");
        repo.hapus(platNomor);
    }

    public List<Kendaraan> getAllKendaraan() { return repo.loadAll(); }
    public List<Kendaraan> getKendaraanTersedia() { return repo.loadAll().stream().filter(k -> k.getStatus() == StatusKendaraan.TERSEDIA).toList(); }
    public void updateStatus(String platNomor, StatusKendaraan status) { Kendaraan k = cariByPlat(platNomor); k.setStatus(status); repo.update(k); }
}
