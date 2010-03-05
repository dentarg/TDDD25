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

import org.omg.CosNaming.*;

public class Server {
	static int run(org.omg.CORBA.ORB orb, String[] args)
	throws org.omg.CORBA.UserException	{

		//
		// Resolve Root POA
		//
		org.omg.PortableServer.POA rootPOA =
			org.omg.PortableServer.POAHelper.narrow(
		orb.resolve_initial_references("RootPOA"));
   	
		//
		// Get a reference to the POA manager
		//
		org.omg.PortableServer.POAManager manager = rootPOA.the_POAManager();
   	
		//
		// Create implementation object
		//
		Database_impl databaseImpl = new Database_impl(rootPOA);
		Database database = databaseImpl._this(orb);

		// Obtain a reference to the NameService
		org.omg.CORBA.Object rootObj = orb.resolve_initial_references("NameService");
		org.omg.CosNaming.NamingContext root = org.omg.CosNaming.NamingContextHelper.narrow(rootObj);

		// Bind the name of the Database object to the NameService
		NameComponent[] nc = new NameComponent[1];
		nc[0]=new NameComponent("Database","foo123");
		((NamingContext)root).bind(nc, rootPOA.servant_to_reference(databaseImpl));   	

/*		//
		// Save reference
		//
		try {
			String ref = orb.object_to_string(database);
			String refFile = "Database.ref";
			java.io.FileOutputStream file =
				new java.io.FileOutputStream(refFile);
			java.io.PrintWriter out = new java.io.PrintWriter(file);
			out.println(ref);
			out.flush();
			file.close();
		}
		catch(java.io.IOException ex) {
			System.err.println("database.Server: can't write to `" +
				ex.getMessage() + "'");
			return 1;
		}

		//
		// Save reference as html
		//
		try {
			String ref = orb.object_to_string(database);
			String refFile = "Database.html";
			java.io.FileOutputStream file =
				new java.io.FileOutputStream(refFile);
			java.io.PrintWriter out = new java.io.PrintWriter(file);
			out.println("<applet codebase=\"classes\" " +
				"code=\"database/Client.class\" " +
				"archive=\"OB.jar\" " +
				"width=500 height=300>");
			out.println("<param name=ior value=\"" + ref + "\">");
			out.println("<param name=org.omg.CORBA.ORBClass " +
				"value=com.ooc.CORBA.ORB>");
			//
			// No need to specify ORBSingletonClass - it's ignored anyway
			//
			//out.println("<param name=org.omg.CORBA.ORBSingletonClass " +
			//            "value=com.ooc.CORBA.ORBSingleton>");
			out.println("</applet>");
			out.flush();
			file.close();
		}
		catch(java.io.IOException ex) {
			System.err.println("database.Server: can't write to `" +
				ex.getMessage() + "'");
			return 1;
		} */
   	
		//
		// Run implementation
		//
		manager.activate();
		orb.run();
		
		return 0;
	}

    public static void main(String args[]) {
		java.util.Properties props = System.getProperties();
		props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
		props.put("org.omg.CORBA.ORBSingletonClass",
			"com.ooc.CORBA.ORBSingleton");
		props.put("ooc.orb.id", "Database-Server");

		int status = 0;
		org.omg.CORBA.ORB orb = null;

		try {
			orb = org.omg.CORBA.ORB.init(args, props);
			status = run(orb, args);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			status = 1;
		}

		if(orb != null) {
			try {
				orb.destroy();
			}
			catch(Exception ex) {
				ex.printStackTrace();
				status = 1;
			}
		}
		System.exit(status);
	}
}
