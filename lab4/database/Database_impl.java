// **********************************************************************
//
// Copyright (c) 2002
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.omg.CORBA.ShortHolder;

class Database_impl extends DatabasePOA {
	//
	// The servants default POA
	//
	private org.omg.PortableServer.POA poa_;

	Database_impl(org.omg.PortableServer.POA poa) {
		poa_ = poa;
	}

	public void shutdown() {
		_orb().shutdown(false);
	}

	public String getCardName(short cardNumber) {
		Vector cards = readDB();
		Card c = (Card) cards.get((int)cardNumber);
		return c.getTitle();
	}

	public String getCardFile(short cardNumber) {
		Vector cards = readDB();
		Card c = (Card) cards.get((int)cardNumber);
		return c.getFilename();
	}

	public String getCardURL(short cardNumber) {
		Vector cards = readDB();
		Card c = (Card) cards.get((int)cardNumber);
		return "http://domain.tld/cards/" + c.getFilename();
	}

	public String getCardThumb(short cardNumber) {
		Vector cards = readDB();
		Card c = (Card) cards.get((int)cardNumber);
		return c.getThumbfilename();
	}

	public boolean getCardArea(short cardNumber,
			org.omg.CORBA.ShortHolder x,
			org.omg.CORBA.ShortHolder y,
			org.omg.CORBA.ShortHolder width,
			org.omg.CORBA.ShortHolder height) {
		Vector cards = readDB();
		Card c = (Card) cards.get((int)cardNumber);
		if(x.equals(c.getX()) && y.equals(c.getY()))
			return true;
		else if(width.equals(c.getWidth()) && height.equals(c.getHeight()))
			return true;
		else
			return false;
	}

	public short getCardsNumber() {
		return (short) readDB().size();
	}
	
	// Helper class and function

	class Card {
		private String title, filename, thumbfilename;
		private ShortHolder x, y, height, width;
		public Card() {
		}
		public String getTitle() {
			return title;
		}
		public String getFilename() {
			return filename;
		}
		public String getThumbfilename() {
			return thumbfilename;
		}
		public ShortHolder getX() {
			return x;
		}
		public ShortHolder getY() {
			return y;
		}
		public ShortHolder getHeight() {
			return height;
		}
		public ShortHolder getWidth() {
			return width;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public void setThumbfilename(String thumbfilename) {
			this.thumbfilename = thumbfilename;
		}
		public void setX(ShortHolder x) {
			this.x = x;
		}
		public void setY(ShortHolder y) {
			this.y = y;
		}
		public void setHeight(ShortHolder height) {
			this.height = height;
		}
		public void setWidth(ShortHolder width) {
			this.width = width;
		}
	}
	
	public Vector readDB() {
		Vector cards = new Vector();
		try {
			// Open the file 
			FileInputStream fstream = new FileInputStream("cards.database");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if(!strLine.startsWith("#")) {
					//String[] result = strLine.split(",");
					Vector result = split(strLine, ',');
					Card c = new Card();
					c.setTitle((String)result.get(0));
					c.setFilename((String)result.get(1));
					c.setThumbfilename((String)result.get(2));
					c.setX(new ShortHolder(Short.parseShort((String)
							result.get(3))));
					c.setY(new ShortHolder(Short.parseShort((String)
							result.get(4))));
					c.setWidth(new ShortHolder(Short.parseShort((String)
							result.get(5))));
					c.setHeight(new ShortHolder(Short.parseShort((String)
							result.get(6))));
					cards.add(c);
				}
			}
			//Close the input stream
			in.close();
		}
		catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return cards;
	}

	// Java version <1.4 does not have String.split()
	public Vector split(String str, char delim) {
		Vector v = new Vector();
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i) == delim) {
				// extract everything up to the first occurrence of delim
				v.add(str.substring(0,i));
				// remove what we extracted and the delim
				str = str.substring(i+1);
				// start search from beginning
				i=0;
			}
			// the last part is what is left
			if(i == str.length()-1) {
				v.add(str);
			}
		}
		return v;
	}

	public org.omg.PortableServer.POA _default_POA() {
		return poa_;
	}
}
