package com.rentalkendaraan.service;

import com.rentalkendaraan.domain.Pelanggan;
import com.rentalkendaraan.repository.IPelangganRepository;
import com.rentalkendaraan.util.DataNotFoundException;
import com.rentalkendaraan.util.DuplicateDataException;

/**
 * SOLID — Single Responsibility (S): hanya logika bisnis pelanggan
 * SOLID — Dependency Inversion (D): bergantung pada IPelangganRepository
 */
public class PelangganService {

    private final IPelangganRepository repository;

    public PelangganService(IPelangganRepository repository) {
        this.repository = repository;
    }

    public void daftarkanPelanggan(Pelanggan p) {
        if (p.getNomorKTP() == null || p.getNomorKTP().isEmpty() ||
            !p.getNomorKTP().chars().allMatch(Character::isDigit))
            throw new IllegalArgumentException("Nomor KTP harus berupa angka dan tidak boleh kosong.");
        if (repository.findByKTP(p.getNomorKTP()) != null)
            throw new DuplicateDataException("Pelanggan dengan KTP " + p.getNomorKTP() + " sudah terdaftar.");
        repository.tambah(p);
    }

    public Pelanggan cariByKTP(String ktp) {
        Pelanggan p = repository.findByKTP(ktp);
        if (p == null) throw new DataNotFoundException("Pelanggan dengan KTP \"" + ktp + "\" tidak ditemukan.");
        return p;
    }
}
