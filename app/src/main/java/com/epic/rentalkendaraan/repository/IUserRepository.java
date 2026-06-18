package com.rentalkendaraan.repository;

import com.rentalkendaraan.domain.User;
import java.util.List;

/**
 * SOLID — Dependency Inversion Principle (D)
 * AuthService bergantung pada interface ini, bukan pada class konkret.
 * Memungkinkan swap JSON ↔ SQLite tanpa mengubah service.
 */
public interface IUserRepository {
    List<User> loadAll();
    User findByUsername(String username);
}
