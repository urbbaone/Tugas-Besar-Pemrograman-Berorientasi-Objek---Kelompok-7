package com.rentalkendaraan.repository;

import com.rentalkendaraan.domain.Transaksi;
import java.util.List;

/**
 * SOLID — Dependency Inversion Principle (D)
 * TransaksiService dan LaporanService bergantung pada interface ini.
 */
public interface ITransaksiRepository {
    List<Transaksi> loadAll();
    Transaksi findById(String id);
    Transaksi findAktifByPlat(String platNomor);
    void tambah(Transaksi t);
    void update(Transaksi t);
    String generateNextId();
}
