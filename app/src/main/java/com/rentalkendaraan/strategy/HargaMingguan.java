package com.rentalkendaraan.strategy;

public class HargaMingguan implements HargaStrategy {
    @Override public long hitungBiaya(long hargaPerHari, int durasi) { return Math.round(hargaPerHari * durasi * 0.9); }
    @Override public String getNamaPaket() { return "MINGGUAN (diskon 10%)"; }
}
