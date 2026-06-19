package com.rentalkendaraan.domain;

import com.rentalkendaraan.domain.enums.StatusKendaraan;

public abstract class Kendaraan {
    protected String jenisKendaraan;
    protected String platNomor;
    protected String merek;
    protected long hargaSewa;
    protected StatusKendaraan status;

    protected Kendaraan() {}

    protected Kendaraan(String jenisKendaraan, String platNomor, String merek, long hargaSewa, StatusKendaraan status) {
        this.jenisKendaraan = jenisKendaraan;
        this.platNomor = platNomor;
        this.merek = merek;
        this.hargaSewa = hargaSewa;
        this.status = status;
    }

    public abstract long hitungDenda(int hariTerlambat);
    public abstract String getInfoTambahan();

    public String getJenisKendaraan() { return jenisKendaraan; }
    public String getPlatNomor() { return platNomor; }
    public String getMerek() { return merek; }
    public long getHargaSewa() { return hargaSewa; }
    public StatusKendaraan getStatus() { return status; }
    public void setJenisKendaraan(String jenisKendaraan) { this.jenisKendaraan = jenisKendaraan; }
    public void setPlatNomor(String platNomor) { this.platNomor = platNomor; }
    public void setMerek(String merek) { this.merek = merek; }
    public void setHargaSewa(long hargaSewa) { this.hargaSewa = hargaSewa; }
    public void setStatus(StatusKendaraan status) { this.status = status; }
}
