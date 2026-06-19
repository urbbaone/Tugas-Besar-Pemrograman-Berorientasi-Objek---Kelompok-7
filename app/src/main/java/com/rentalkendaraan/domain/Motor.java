package com.rentalkendaraan.domain;

import com.rentalkendaraan.domain.enums.StatusKendaraan;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor() { this.jenisKendaraan = "MOTOR"; }

    public Motor(String platNomor, String merek, long hargaSewa, String jenisTransmisi) {
        super("MOTOR", platNomor, merek, hargaSewa, StatusKendaraan.TERSEDIA);
        this.jenisTransmisi = jenisTransmisi;
    }

    @Override public long hitungDenda(int hariTerlambat) { return Math.max(0, hariTerlambat) * 20000L; }
    @Override public String getInfoTambahan() { return jenisTransmisi; }
    public String getJenisTransmisi() { return jenisTransmisi; }
    public void setJenisTransmisi(String jenisTransmisi) { this.jenisTransmisi = jenisTransmisi; }
}
