package com.rentalkendaraan.domain;
import com.rentalkendaraan.domain.enums.Role;
public class User {
    private String username; private String password; private Role role;
    public User() {}
    public User(String u, String p, Role r){ username=u; password=p; role=r; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public Role getRole(){ return role; }
    public void setUsername(String u){ username=u; }
    public void setPassword(String p){ password=p; }
    public void setRole(Role r){ role=r; }
}
