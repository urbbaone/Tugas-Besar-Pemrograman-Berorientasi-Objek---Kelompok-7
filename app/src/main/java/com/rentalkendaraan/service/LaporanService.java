package com.rentalkendaraan.service;

import com.rentalkendaraan.domain.Transaksi;
import com.rentalkendaraan.domain.enums.StatusTransaksi;
import com.rentalkendaraan.repository.ITransaksiRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SOLID — Single Responsibility (S): hanya logika laporan & kalkulasi pendapatan
 * SOLID — Dependency Inversion (D): bergantung pada ITransaksiRepository
 */
public class LaporanService {

    private final ITransaksiRepository repository;

    public LaporanService(ITransaksiRepository repository) {
        this.repository = repository;
    }

    public List<Transaksi> getRiwayatTransaksi()    { return repository.loadAll(); }

    public List<Transaksi> getTransaksiSelesai() {
        return repository.loadAll().stream()
                .filter(t -> t.getStatusTransaksi() == StatusTransaksi.SELESAI)
                .collect(Collectors.toList());
    }

    public long hitungTotalPendapatan() {
        return getTransaksiSelesai().stream()
                .mapToLong(Transaksi::getTotalBayar).sum();
    }
}
