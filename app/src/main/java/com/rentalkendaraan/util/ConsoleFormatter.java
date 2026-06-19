package com.rentalkendaraan.util;
import java.text.NumberFormat; import java.util.List; import java.util.Locale; import java.util.Scanner;
public class ConsoleFormatter {
    private static final int W = 60;
    public static void printHeader(String t) { String b="=".repeat(W); System.out.println(b); System.out.println(" "+t); System.out.println(b); }
    public static void printSubHeader(String t) { String b="-".repeat(W); System.out.println(b); System.out.println(" "+t); System.out.println(b); }
    public static void printTable(String[] headers, List<String[]> rows) {
        int cols=headers.length; int[] w=new int[cols];
        for(int i=0;i<cols;i++) w[i]=headers[i].length();
        for(String[] r:rows) for(int i=0;i<cols&&i<r.length;i++) { String c=r[i]!=null?r[i]:"-"; w[i]=Math.max(w[i],c.length()); }
        String sep=buildSep(w); System.out.println(sep); System.out.println(buildRow(headers,w)); System.out.println(sep);
        if(rows.isEmpty()){ System.out.println("|"+" (Tidak ada data kendaraan)".concat(" ".repeat(Math.max(0,sep.length()-30)))+"|"); }
        else { for(String[] r:rows) System.out.println(buildRow(norm(r,cols),w)); }
        System.out.println(sep);
    }
    private static String buildSep(int[] w){StringBuilder sb=new StringBuilder("+");for(int x:w)sb.append("-".repeat(x+2)).append("+");return sb.toString();}
    private static String buildRow(String[] c,int[] w){StringBuilder sb=new StringBuilder("|");for(int i=0;i<w.length;i++){String v=(i<c.length&&c[i]!=null)?c[i]:"-";sb.append(" ").append(pad(v,w[i])).append(" |");}return sb.toString();}
    private static String[] norm(String[] r,int c){String[] n=new String[c];for(int i=0;i<c;i++)n[i]=(i<r.length&&r[i]!=null)?r[i]:"-";return n;}
    private static String pad(String s,int w){return s.length()>=w?s:s+" ".repeat(w-s.length());}
    public static void printSuccess(String m){System.out.println("[SUKSES] "+m);}
    public static void printError(String m){System.out.println("[GAGAL] "+m);}
    public static void printInfo(String m){System.out.println("[INFO] "+m);}
    public static void printFound(){System.out.println("[DATA DITEMUKAN]");}
    public static String formatRupiah(long a){return "Rp "+NumberFormat.getInstance(new Locale("id","ID")).format(a);}
    public static void pressEnterToContinue(Scanner sc){System.out.print("Tekan ENTER untuk kembali ke menu utama...");sc.nextLine();}
    public static void printStrukHeader(String t){System.out.println("-".repeat(38));System.out.println(" "+t);System.out.println("-".repeat(38));}
    public static void printStrukFooter(){System.out.println("-".repeat(38));}
    public static void printStrukRow(String l,String v){System.out.printf("%-16s: %s%n",l,v);}
    public static void printLine(){System.out.println("-".repeat(W));}
    public static void printBlank(){System.out.println();}
}
