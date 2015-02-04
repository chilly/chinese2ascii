import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.*;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.util.Vector;
import java.applet.*;

public class ChineseASCIIApplet extends JApplet implements ActionListener {
	JTextArea inputArea = null;
	JTextArea outputArea = null;
	JScrollPane inPane = null;
	JScrollPane outPane = null;
	JLabel inLabel, outLabel;
	JComboBox inBox, outBox;
	JButton convertButton;
	JPanel southPanel;
	JPanel northPanel;
	Container contentPane;
	CharsetEncoder ce,cecns,ceas;
	Hashtable ht;
	zhcode2 zh;

	public void init () {
		// first: layout stuff
		contentPane = getContentPane();
	    contentPane.setLayout (new BorderLayout ());

	    // Create an instance of JTextArea
	    inputArea = new JTextArea ();
	    outputArea = new JTextArea ();
	    inputArea.setEditable(true);
	    outputArea.setEditable (true);

	    inPane = new JScrollPane(inputArea);
	    outPane = new JScrollPane(outputArea);

	    northPanel = new JPanel(new GridLayout(2,1));
	    northPanel.add(inPane);
	    northPanel.add(outPane);

	    southPanel = new JPanel(new GridLayout(1,5));
	    inLabel = new JLabel("Input: ");
	    outLabel = new JLabel("Output: ");
	    inBox = new JComboBox();
	    outBox = new JComboBox();
	    inBox.addItem(makeObj("Unicode"));

	    //inBox.addItem(makeObj("CNS"));
	    //inBox.addItem(makeObj("GB"));
	    //outBox.addItem(makeObj("Unicode"));
	    //outBox.addItem(makeObj("CNS"));

	    outBox.addItem(makeObj("GB 1"));
	    outBox.addItem(makeObj("GB 2"));
	    outBox.addItem(makeObj("GB 3"));
	    outBox.addItem(makeObj("GB 4"));
	    outBox.addItem(makeObj("GB 5"));
	    outBox.addItem(makeObj("GB 6"));
	    outBox.setSelectedIndex(2);
	    convertButton = new JButton("Convert");
	    convertButton.setActionCommand("Convert");
	    convertButton.addActionListener(this);

	    southPanel.add(inLabel);
	    southPanel.add(inBox);
	    southPanel.add(outLabel);
	    southPanel.add(outBox);
	    southPanel.add(convertButton);

	    //add(inPane,"North");
	    contentPane.add(northPanel,"Center");
	    contentPane.add(southPanel,"South");

		//outputArea.append("??????");

	    //setSize(300,300);
		// 2nd: encoding stuff
	    outputArea.setFont(new Font("Monospaced", Font.BOLD, 8));
		zh = new zhcode2(outputArea);
		try {
			Charset cs = Charset.forName("EUC_CN");
		}catch (Exception e) {
			outputArea.append("Your browser's JRE doesn't support GB-2312 or EUC_CN encoding.");
			o(e+"");
		}
		//Charset cscns = Charset.forName("x-EUC-TW");
		Charset as = Charset.forName("US-ASCII");
		//ce = cs.newEncoder();
		ceas = as.newEncoder();
		//cecns = cscns.newEncoder();

		loadFig();
		//String[] test = (String[])ht.get(new Integer(13613));
		//for (int i=0; i<16; i++) {
		//	o(test[i]);
		//}
	}

	public void actionPerformed(ActionEvent e) {
		//if (e.getActionCommand().equals("Convert")) {
			o("convert button pressed");
			//outputArea.append("test"+"\n");
			//show();
			String stuff = inputArea.getText();
			//o(""+Character.isDefined(stuff.charAt(2)));
			//o(""+ce.canEncode(stuff.charAt(2)));
			//o(""+cecns.canEncode(stuff.charAt(2)));
			//o("input: " + convertToHexString(stuff));
			//o("total length: " + stuff.length());
			char[] pre = stuff.toCharArray();
			//o("total chars: " + pre.length);
			//String emptyString = new String("");
			//CharBuffer cb_pre = CharBuffer.wrap(pre);
			StringBuffer sb_new = new StringBuffer();
			for (int i=0; i<pre.length; i++) {
				if (!ceas.canEncode(pre[i]))	sb_new.append(pre[i]);
			}
			String new_arry = sb_new.toString();
			o("new length: " + sb_new.length());
			o("trimmed: " + new_arry);

			// trimmed ascii..

			/*ByteBuffer bb = null;
			ByteBuffer bbcns = null;
			char[] ca = stuff.toCharArray();
			CharBuffer cb = CharBuffer.wrap(ca);
			try{
				bb = ce.encode(cb);
		//		bbcns = cecns.encode(cb);
			}
			catch (Exception ef) { o(ef.toString()); }


			byte[] ba = bb.array();
		//	byte[] bacns = bbcns.array();
		//	byte[] baold = stuff.getBytes();
			//for (int i=0; i<ba.length; i++) {
			//	o(""+ba[i]);
			//}
			//for (int i=0; i<bacns.length; i++) {
			//	o(""+bacns[i]);
			//}
			String newString = new String(ba);
			o(""+newString);
			o("as hex: " + convertToHexString(newString));
*/


			String newTest = zh.gb2hz(new_arry);  // yay!!
			char[] chars = newTest.toCharArray();
			Integer[] tchars = trim(chars);
			// that's what we're looking for!

			int numPerLine = 0;
			int si = outBox.getSelectedIndex();
			o("outbox: " + si);
			if (si==0) numPerLine = 1;
			else if (si==1) numPerLine = 2;
			else if (si==2) numPerLine = 3;
			else if (si==3) numPerLine = 4;
			else if (si==3) numPerLine = 5;
			else if (si==3) numPerLine = 6;

			o("numPerLine " + numPerLine);
			//String[][] illin = new String[16][numPerLine];
			int index = 0;
			StringBuffer finalq = new StringBuffer();
			while (index < tchars.length) {
				StringBuffer[] output = new StringBuffer[16];
				for (int k=0; k<16; k++) output[k]=new StringBuffer();
				for (int i=0; i<numPerLine; i++) {
					if (index<tchars.length) {
						String[] ill = (String[])ht.get(tchars[index]);
						for (int j=0; j<16; j++) {
							output[j].append(ill[j]+"  ");
						}
						index++;
					}
				}
				for (int j=0; j<16; j++) {
					finalq.append(output[j]+"\n");
				}
				finalq.append("\n");
			}

			outputArea.append(finalq.toString());
			//show();
			//String test = new String(ba);
			//o(convertToHexString(test));
			//String test2 = new String(bacns);
			//o(convertToHexString(test2));
			//String cnsStuff = zh.convertString(stuff,Encoding.UNICODE,Encoding.CNS11643);
			//o(convertToHexString(cnsStuff));
			//}
	}

	public Integer[] trim(char[] chars) {
		Vector v = new Vector();
		for (int i = 0; i<chars.length; i++) {
			o(""+(int)chars[i]);
		}
		int index = 0;
		while (index<chars.length) {
			StringBuffer sb = new StringBuffer();
			sb.append(Integer.toHexString((int)chars[index]));
			sb.append(Integer.toHexString((int)chars[index+1]));
			String q = sb.toString();
			o("test q: " + q);
			int ab = Integer.parseInt(q,16);
			o("test ab: " + ab);
			if (ht.containsKey(new Integer(ab))) {
				v.addElement(new Integer(ab));
			}
			index+=2;
		}
		Integer[] newChars = new Integer[v.size()];
		for (int i=0; i<newChars.length; i++) {
			newChars[i]=(Integer)v.elementAt(i);
		}
		//o("found: " + newChars.length);
		return newChars;
	}

	public void loadFig() {
		 ht = new Hashtable();

		 int comments = 3866;
		 int numLines = 16;
		 int length = 16;
		 //try {
		 //     conn = aURL.openStream();
		 //     data = new BufferedReader(new InputStreamReader(new BufferedInputStream(conn)));
		//file f = new file("gb16fs.flf");
		//f.initRead();
		try {
			InputStream pydata = getClass().getResourceAsStream("gb16fs.flf");
			BufferedReader f = new BufferedReader(new InputStreamReader(pydata));
			String line = "";
			StringTokenizer st;
			for (int i=0; i<comments; i++) {
				line = f.readLine();
			}
			while ((line=f.readLine())!=null) {
				st = new StringTokenizer(line);
				Integer num = new Integer(Integer.parseInt(st.nextToken()));
				String[] fig = new String[numLines];
				for (int i=0; i<numLines; i++) {
					line = f.readLine();
					fig[i] = line.substring(0,length);
				}
				ht.put(num,fig);
			}
			//f.closeRead();
		}
		catch (Exception e) {e.printStackTrace();}
    }

	public void o(String s) {
		//outputArea.append(s+"\n");
		//System.out.println(s);
	}

	/**
	* for testing
	*/
	public String convertToHexString(String unicodeString) {
		char[] chars = unicodeString.toCharArray();
		o("converting: " + chars.length);
		StringBuffer output = new StringBuffer();
		for(int i = 0; i < chars.length; i++) {
			output.append(Integer.toHexString((int)chars[i]));
		}
		return output.toString();
	}


	private Object makeObj(final String item)  {
	     return new Object() { public String toString() { return item; } };
    }

}



