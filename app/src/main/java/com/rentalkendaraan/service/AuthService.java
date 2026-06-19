package com.rentalkendaraan.service;

import com.rentalkendaraan.domain.User;
import com.rentalkendaraan.repository.IUserRepository;

/**
 * SOLID — Single Responsibility (S): hanya menangani logika autentikasi
 * SOLID — Dependency Inversion (D): bergantung pada IUserRepository, bukan konkret
 */
public class AuthService {

    private static final int MAX_ATTEMPTS = 3;
    private final IUserRepository userRepository;

    /** Dependency Injection — repository disuntik dari luar */
    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public int getMaxAttempts() { return MAX_ATTEMPTS; }
}
