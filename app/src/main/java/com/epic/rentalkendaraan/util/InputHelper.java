package com.rentalkendaraan.util;
import java.util.Scanner;
public class InputHelper {
    public static int readInt(Scanner sc, String prompt) {
        while(true){System.out.print(prompt);try{return Integer.parseInt(sc.nextLine().trim());}catch(NumberFormatException e){ConsoleFormatter.printError("Input harus berupa angka.");}}
    }
    public static long readLong(Scanner sc, String prompt) {
        while(true){System.out.print(prompt);try{long v=Long.parseLong(sc.nextLine().trim());if(v<0){ConsoleFormatter.printError("Tidak boleh negatif.");continue;}return v;}catch(NumberFormatException e){ConsoleFormatter.printError("Input harus berupa angka.");}}
    }
    public static String readNonEmpty(Scanner sc, String prompt) {
        while(true){System.out.print(prompt);String s=sc.nextLine().trim();if(!s.isEmpty())return s;ConsoleFormatter.printError("Tidak boleh kosong.");}
    }
    public static String readLine(Scanner sc, String prompt){System.out.print(prompt);return sc.nextLine().trim();}
    public static String readPassword(Scanner sc, String prompt){
        System.out.print(prompt);java.io.Console c=System.console();if(c!=null)return new String(c.readPassword());return sc.nextLine().trim();
    }
    public static boolean isNumericOnly(String s){return s!=null&&!s.isEmpty()&&s.chars().allMatch(Character::isDigit);}
}
