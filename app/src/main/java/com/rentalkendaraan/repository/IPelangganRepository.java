package com.rentalkendaraan.repository;

import com.rentalkendaraan.domain.Pelanggan;
import java.util.List;

/**
 * SOLID — Dependency Inversion Principle (D)
 * PelangganService bergantung pada interface ini.
 */
public interface IPelangganRepository {
    List<Pelanggan> loadAll();
    Pelanggan findByKTP(String nomorKTP);
    void tambah(Pelanggan p);
}
