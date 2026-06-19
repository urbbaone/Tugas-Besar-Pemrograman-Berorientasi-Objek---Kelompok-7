package com.rentalkendaraan.repository;

import com.rentalkendaraan.domain.Kendaraan;
import java.util.List;

public interface IKendaraanRepository {
    List<Kendaraan> loadAll();
    Kendaraan findByPlat(String platNomor);
    void tambah(Kendaraan kendaraan);
    void update(Kendaraan kendaraan);
    void hapus(String platNomor);
}
