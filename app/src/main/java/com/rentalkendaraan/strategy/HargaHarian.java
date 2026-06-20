package com.rentalkendaraan.strategy;

public class HargaHarian implements HargaStrategy {
    @Override public long hitungBiaya(long hargaPerHari, int durasi) { return hargaPerHari * durasi; }
    @Override public String getNamaPaket() { return "HARIAN"; }
}
