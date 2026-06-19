package com.rentalkendaraan.domain;

import com.rentalkendaraan.domain.enums.StatusKendaraan;

public class Mobil extends Kendaraan {
    private String transmisi;
    private int jumlahPintu;

    public Mobil() { this.jenisKendaraan = "MOBIL"; }

    public Mobil(String platNomor, String merek, long hargaSewa, String transmisi, int jumlahPintu) {
        super("MOBIL", platNomor, merek, hargaSewa, StatusKendaraan.TERSEDIA);
        this.transmisi = transmisi;
        this.jumlahPintu = jumlahPintu;
    }

    @Override public long hitungDenda(int hariTerlambat) { return Math.max(0, hariTerlambat) * 50000L; }
    @Override public String getInfoTambahan() { return transmisi + ", " + jumlahPintu + " pintu"; }
    public String getTransmisi() { return transmisi; }
    public int getJumlahPintu() { return jumlahPintu; }
    public void setTransmisi(String transmisi) { this.transmisi = transmisi; }
    public void setJumlahPintu(int jumlahPintu) { this.jumlahPintu = jumlahPintu; }
}
