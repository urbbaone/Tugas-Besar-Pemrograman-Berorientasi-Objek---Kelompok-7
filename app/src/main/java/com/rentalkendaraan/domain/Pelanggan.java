package com.rentalkendaraan.domain;

/**
 * Entitas pelanggan yang mendaftarkan diri untuk menyewa kendaraan.
 */
public class Pelanggan {

    private String nomorKTP;
    private String namaLengkap;
    private String noTelepon;

    public Pelanggan() {}

    public Pelanggan(String nomorKTP, String namaLengkap, String noTelepon) {
        this.nomorKTP    = nomorKTP;
        this.namaLengkap = namaLengkap;
        this.noTelepon   = noTelepon;
    }

    public String getNomorKTP()    { return nomorKTP; }
    public String getNamaLengkap() { return namaLengkap; }
    public String getNoTelepon()   { return noTelepon; }

    public void setNomorKTP(String v)    { nomorKTP = v; }
    public void setNamaLengkap(String v) { namaLengkap = v; }
    public void setNoTelepon(String v)   { noTelepon = v; }
}
