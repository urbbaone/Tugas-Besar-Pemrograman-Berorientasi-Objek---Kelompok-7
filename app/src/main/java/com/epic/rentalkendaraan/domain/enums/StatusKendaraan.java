package com.epic.rentalkendaraan.domain.enums;
public enum StatusKendaraan {
    TERSEDIA, SEDANG_DISEWA;
    public String toDisplay() { return this == TERSEDIA ? "TERSEDIA" : "SEDANG DISEWA"; }
}
