import java.lang.*;
import java.io.*;
import java.util.*;

class Encoding {
    // Supported Encoding Types
    public static int GB2312        = 0;
    public static int GBK           = 1;
    public static int HZ            = 2;
    public static int BIG5          = 3;
    public static int CNS11643      = 4;
    public static int UTF8          = 5;
    public static int UTF8T         = 6;
    public static int UTF8S         = 7;
    public static int UNICODE       = 8;
    public static int UNICODET      = 9;
    public static int UNICODES      = 10;
    public static int ISO2022CN     = 11;
    public static int ISO2022CN_CNS = 12;
    public static int ISO2022CN_GB  = 13;
    public static int EUC_KR  = 14;

    public static int ASCII         = 15;
    public static int OTHER         = 16;


    public static int TOTALTYPES    = 17;

    // Names of the encodings as understood by Java
    public static String[] javaname;
    // Names of the encodings for human viewing
    public static String[] nicename;
    // Names of charsets as used in charset parameter of HTML Meta tag
    public static String[] htmlname;


    // Constructor
    public Encoding() {
	javaname = new String[TOTALTYPES];
	nicename = new String[TOTALTYPES];
	htmlname = new String[TOTALTYPES];

	// Assign encoding names
	javaname[GB2312] = "GB2312";
	javaname[HZ] = "ASCII";  // What to put here?  Sun doesn't support HZ
	javaname[GBK] = "GBK";
	javaname[ISO2022CN_GB] = "ISO2022CN_GB";
	javaname[BIG5] = "BIG5";
	javaname[CNS11643] = "EUC-TW";
	javaname[ISO2022CN_CNS] = "ISO2022CN_CNS";
	javaname[ISO2022CN] = "ISO2022CN";
	javaname[UTF8] = "UTF8";
	javaname[UTF8T] = "UTF8";
	javaname[UTF8S] = "UTF8";
	javaname[UNICODE] = "Unicode";
	javaname[UNICODET] = "Unicode";
	javaname[UNICODES] = "Unicode";
	javaname[EUC_KR] = "EUC_KR";
	javaname[ASCII] = "ASCII";
	javaname[OTHER] = "ISO8859_1";

	// Assign encoding names
	htmlname[GB2312] = "GB2312";
	htmlname[HZ] = "HZ-GB-2312";  
	htmlname[GBK] = "GB2312";
	htmlname[ISO2022CN_GB] = "ISO-2022-CN-EXT";
	htmlname[BIG5] = "BIG5";
	htmlname[CNS11643] = "EUC-TW";
	htmlname[ISO2022CN_CNS] = "ISO-2022-CN-EXT";
	htmlname[ISO2022CN] = "ISO-2022-CN";
	htmlname[UTF8] = "UTF-8";
	htmlname[UTF8T] = "UTF-8";
	htmlname[UTF8S] = "UTF-8";
	htmlname[UNICODE] = "UTF-16";
	htmlname[UNICODET] = "UTF-16";
	htmlname[UNICODES] = "UTF-16";
	htmlname[EUC_KR] = "EUC-KR";
	htmlname[ASCII] = "ASCII";
	htmlname[OTHER] = "ISO8859-1";

	// Assign Human readable names
	nicename[GB2312] = "GB-2312";
	nicename[HZ] = "HZ";
	nicename[GBK] = "GBK";
	nicename[ISO2022CN_GB] = "ISO2022CN-GB";
	nicename[BIG5] = "Big5";
	nicename[CNS11643] = "CNS11643";
	nicename[ISO2022CN_CNS] = "ISO2022CN-CNS";
	nicename[ISO2022CN] = "ISO2022 CN";
	nicename[UTF8] = "UTF-8";
	nicename[UTF8T] = "UTF-8 (Trad)";
	nicename[UTF8S] = "UTF-8 (Simp)";
	nicename[UNICODE] = "Unicode";
	nicename[UNICODET] = "Unicode (Trad)";
	nicename[UNICODES] = "Unicode (Simp)";
	nicename[EUC_KR] = "EUC-KR";
	nicename[ASCII] = "ASCII";
	nicename[OTHER] = "OTHER";


    }


    /*		System.out.println("Usage:  java zhcode -[gbc8ui2nk][gbc8uts2nk] in_file out_file");
 		System.out.println("  g = GB2312, b = Big5, c = CNS11643, 8 = UTF-8, u = Unicode,");
	 	System.out.println("  t = Unicode (traditional characters),");
		System.out.println("  s = Unicode (simplified characters),");
 		System.out.println("  i = ISO-2022-CN, 2 = ISO-2022-CN-GB, n = ISO-2022-CN-CNS,");
 		System.out.println("  k = GBK");
    */
    
}
