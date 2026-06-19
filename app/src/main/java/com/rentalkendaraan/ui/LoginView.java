package com.rentalkendaraan.ui;
import com.rentalkendaraan.domain.User; import com.rentalkendaraan.service.AuthService;
import com.rentalkendaraan.util.ConsoleFormatter; import com.rentalkendaraan.util.InputHelper;
import java.util.Scanner;
public class LoginView {
    private static final String NAMA_KELOMPOK = "SISTEM RENTAL KENDARAAN - KELOMPOK 7";
    private final AuthService authService; private final Scanner scanner;
    public LoginView(AuthService a, Scanner s){ authService=a; scanner=s; }
    public User show(){
        for(int i=1;i<=authService.getMaxAttempts();i++){
            ConsoleFormatter.printHeader(NAMA_KELOMPOK);
            String u=InputHelper.readNonEmpty(scanner,"Username : > ");
            String p=InputHelper.readPassword(scanner,"Password : > ");
            User user=authService.login(u,p);
            ConsoleFormatter.printBlank();
            if(user!=null){
                ConsoleFormatter.printSuccess("Login berhasil sebagai "+user.getRole().name()+".");
                System.out.print("Tekan ENTER untuk masuk ke Dashboard..."); scanner.nextLine();
                ConsoleFormatter.printBlank(); return user;
            }
            int sisa=authService.getMaxAttempts()-i;
            if(sisa>0) ConsoleFormatter.printError("Username atau password salah. Sisa percobaan: "+sisa);
            else ConsoleFormatter.printError("Login gagal. Batas percobaan habis.");
            ConsoleFormatter.printBlank();
        }
        return null;
    }
}
