package com.rentalkendaraan.domain;

import com.rentalkendaraan.domain.enums.JenisPaket;
import com.rentalkendaraan.domain.enums.StatusTransaksi;

/**
 * Entitas transaksi peminjaman kendaraan.
 *
 * Fields jenisPaket & diskon adalah bagian dari Modul K7
 * (Paket Sewa Harian vs Mingguan — diskon 10% jika >= 7 hari).
 */
public class Transaksi {

    private String          idTransaksi;
    private String          nomorKTPPelanggan;
    private String          platNomorKendaraan;
    private int             durasiSewa;
    private String          tanggalMulai;
    private StatusTransaksi statusTransaksi;
    private long            totalBayar;
    private JenisPaket      jenisPaket;
    private long            diskon;

    // No-arg constructor — untuk Gson & DB mapping
    public Transaksi() {}

    public Transaksi(String id, String ktp, String plat, int durasi,
                     String tanggal, JenisPaket paket, long diskon) {
        this.idTransaksi        = id;
        this.nomorKTPPelanggan  = ktp;
        this.platNomorKendaraan = plat;
        this.durasiSewa         = durasi;
        this.tanggalMulai       = tanggal;
        this.statusTransaksi    = StatusTransaksi.BERJALAN;
        this.jenisPaket         = paket;
        this.diskon             = diskon;
        this.totalBayar         = 0;
    }

    // ── Getters 
    public String          getIdTransaksi()         { return idTransaksi; }
    public String          getNomorKTPPelanggan()    { return nomorKTPPelanggan; }
    public String          getPlatNomorKendaraan()   { return platNomorKendaraan; }
    public int             getDurasiSewa()           { return durasiSewa; }
    public String          getTanggalMulai()         { return tanggalMulai; }
    public StatusTransaksi getStatusTransaksi()      { return statusTransaksi; }
    public long            getTotalBayar()           { return totalBayar; }
    public JenisPaket      getJenisPaket()           { return jenisPaket; }
    public long            getDiskon()               { return diskon; }

    // ── Setters 
    public void setIdTransaksi(String v)            { idTransaksi = v; }
    public void setNomorKTPPelanggan(String v)      { nomorKTPPelanggan = v; }
    public void setPlatNomorKendaraan(String v)     { platNomorKendaraan = v; }
    public void setDurasiSewa(int v)                { durasiSewa = v; }
    public void setTanggalMulai(String v)           { tanggalMulai = v; }
    public void setStatusTransaksi(StatusTransaksi s){ statusTransaksi = s; }
    public void setTotalBayar(long v)               { totalBayar = v; }
    public void setJenisPaket(JenisPaket v)         { jenisPaket = v; }
    public void setDiskon(long v)                   { diskon = v; }
}
