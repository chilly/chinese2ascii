import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.JTextArea;

/**
*  Copyright 2002 Erik Peterson
*  Code and program free for non-commercial use.
*  Contact erik@mandarintools.com for fees and
*  licenses for commercial use.
*/

class zhcode2 extends Encoding {
    // Simplfied/Traditional character equivalence hashes
    protected Hashtable s2thash, t2shash;
    private JTextArea ja;


    // Constructor
    public zhcode2(JTextArea ja_) {
		super();
		String dataline;
		ja = ja_;
		// Initialize and load in the simplified/traditional character hashses
		s2thash = new Hashtable();
		t2shash = new Hashtable();
		int index = 0;
		try {
			InputStream pydata = getClass().getResourceAsStream("hcutf8.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(pydata, "UTF8"));

			while ((dataline = in.readLine()) != null) {
				// Skip empty and commented lines
				if (dataline.length() == 0 || dataline.charAt(0) == '#') {
					continue;
				}

				// Simplified to Traditional, (one to many, but pick only one)
				s2thash.put(dataline.substring(0,1).intern(), dataline.substring(1,2));

				// Traditional to Simplified, (many to one)
				for (int i = 1; i < dataline.length(); i++) {
					t2shash.put(dataline.substring(i,i+1).intern(), dataline.substring(0,1));
				}
				index++;
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
			//System.err.println(e);
			//ja.append("probs in reading: " + e);
			//ja.show();
		}
		//ja.append("processed: " + index);
    }


    public String hz2gb(String hzstring) {
		byte[] hzbytes = new byte[2];
		byte[] gbchar = new byte[2];
		int byteindex = 0;
		StringBuffer gbstring = new StringBuffer("");

		try {
			hzbytes = hzstring.getBytes("8859_1");
		}
		catch (Exception usee) { System.err.println("Exception " + usee.toString()); return hzstring; }

		// Convert to look like equivalent Unicode of GB
		for (byteindex = 0; byteindex < hzbytes.length; byteindex++) {
			if (hzbytes[byteindex] == 0x7e) {
			if (hzbytes[byteindex+1] == 0x7b) {
				byteindex+=2;
				while (byteindex < hzbytes.length) {
				if (hzbytes[byteindex] == 0x7e && hzbytes[byteindex+1] == 0x7d) {
					byteindex++;
					break;
				} else if (hzbytes[byteindex] == 0x0a || hzbytes[byteindex] == 0x0d) {
					gbstring.append((char)hzbytes[byteindex]);
					break;
				}
				gbchar[0] = (byte)(hzbytes[byteindex] + 0x80);
				gbchar[1] = (byte)(hzbytes[byteindex+1] + 0x80);
				try {
					gbstring.append(new String(gbchar, "GB2312"));
				}  catch (Exception usee) { System.err.println("Exception " + usee.toString()); }
				byteindex+=2;
				}
			} else if (hzbytes[byteindex+1] == 0x7e) { // ~~ becomes ~
				gbstring.append('~');
			} else {  // false alarm
				gbstring.append((char)hzbytes[byteindex]);
			}
			} else {
			gbstring.append((char)hzbytes[byteindex]);
			}
		}
		return gbstring.toString();
    }

    public String gb2hz(String gbstring) {
		StringBuffer hzbuffer;
		byte[] gbbytes = new byte[2];
		int i;
		boolean terminated = false;

		hzbuffer = new StringBuffer("");
		try {
			gbbytes = gbstring.getBytes("GB2312");
		}
		catch (Exception usee) { System.err.println(usee.toString()); return gbstring; }

		for (i = 0; i < gbbytes.length; i++) {
			if (gbbytes[i] < 0) {
			//hzbuffer.append("~{");
			terminated = false;
			while (i < gbbytes.length) {
				if (gbbytes[i] == 0x0a || gbbytes[i] == 0x0d) {
				hzbuffer.append("~}" + (char)gbbytes[i]);
				terminated = true;
				break;
				} else if (gbbytes[i] >= 0) {
				hzbuffer.append(/*"~}" +*/ (char)gbbytes[i]);
				terminated = true;
				break;
				}
				hzbuffer.append((char)(gbbytes[i] + 256 - 0x80));
				hzbuffer.append((char)(gbbytes[i+1] + 256 - 0x80));
				i+=2;
			}
			if (terminated == false) {
				//hzbuffer.append("~}");
			}
			} else {
			if (gbbytes[i] == 0x7e) {
				//hzbuffer.append("~~");
			} else {
				hzbuffer.append((char)gbbytes[i]);
			}
			}
		}
		return new String(hzbuffer);
    }
}
