package com.rentalkendaraan.strategy;

public interface HargaStrategy {
    long hitungBiaya(long hargaPerHari, int durasi);
    String getNamaPaket();
}
