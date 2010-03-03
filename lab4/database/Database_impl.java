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

class Database_impl extends DatabasePOA {
	//
	// The servants default POA
	//
	private org.omg.PortableServer.POA poa_;

	Database_impl(org.omg.PortableServer.POA poa) {
		poa_ = poa;
	}

	public void say_hello() {
		System.out.println("Hello World!");
	}

	public void shutdown() {
		_orb().shutdown(false);
	}

   public String getCardName(short cardNumber) {
		return new String("a");
	}

   public String getCardFile(short cardNumber) {
		return new String("a");
	}

   public String getCardURL(short cardNumber) {
		return new String("a");
	}

   public String getCardThumb(short cardNumber) {
		return new String("a");
	}

   public boolean getCardArea(	short cardNumber,
								org.omg.CORBA.ShortHolder x,
								org.omg.CORBA.ShortHolder y,
								org.omg.CORBA.ShortHolder width,
								org.omg.CORBA.ShortHolder height) {
		return false;
	}
	
	public short getCardsNumber() {
		short n = 1;
		return n;
	}

	public org.omg.PortableServer.POA _default_POA() {
		return poa_;
	}
}
