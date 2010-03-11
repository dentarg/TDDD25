// **********************************************************************
//
// Copyright (c) 2002
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

import java.awt.*;
import java.awt.event.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import lock.*;

public class Testclient {
	
	static int id;
	static int numberOfClients;
	static boolean hasToken;
	static String username;

	static int run(org.omg.CORBA.ORB orb, String[] args) throws org.omg.CORBA.UserException, java.io.IOException {

		// Get the NamingContext
		org.omg.CORBA.Object rootObj = orb.resolve_initial_references("NameService");
		org.omg.CosNaming.NamingContext root = org.omg.CosNaming.NamingContextHelper.narrow(rootObj);
	
	
		// Register Server Object
		org.omg.PortableServer.POA rootPOA =
		    org.omg.PortableServer.POAHelper.narrow(
			orb.resolve_initial_references("RootPOA"));
	
		// Get a reference to the POA manager
		org.omg.PortableServer.POAManager manager = rootPOA.the_POAManager();
	
		// Create implementation object
		Lock_impl lockImpl = new Lock_impl(rootPOA);
		Lock lock = lockImpl._this(orb);
		NameComponent[] nc = new NameComponent[1];
		nc[0]=new NameComponent("Lock",username+id);
		System.out.println("Registering client: "+username+id); 
		((NamingContext)root).rebind(nc, rootPOA.servant_to_reference(lockImpl));
	
		// Activate the POA manager
		manager.activate();
	
		// Create the BufferedReader for reading from the console
		String input;
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
	
		// Wait until all remote objects are activated.
		System.out.println("Wait until all clients show this message, and press enter to initialize");
		input = in.readLine();
	
		// Now all remote clients should be available. Let's find them!
		lockImpl.initialize(id, numberOfClients, hasToken, username);
	
	    System.out.println("Type \"lock\" to acquire the lock, and \"release\" to release it again.");
	    System.out.println("Type \"exit\" to exit.");
	
	    // The main loop of the test client
	    while(true) {
	
		   	// Wait until the user wants to acquire the lock
		    do {
		    	System.out.print("unlocked> ");
				input = in.readLine();
				if (input.equals("release")) System.out.println("The lock must be acquired before it can be released");
				if (input.equals("exit")) return 0;
			} while (!input.equals("lock"));
	
			// acquire the lock
			lockImpl.acquire();
		
		   	// Wait until the user wants to release the lock
		    do {
		    	System.out.print("LOCKED> ");
				input = in.readLine();
				if (input.equals("lock")) System.out.println("The lock is already acquired!");
				if (input.equals("exit")) return 0;
			} while (!input.equals("release"));
		
			// Release the lock
			lockImpl.release();
		}
	
    }

    //
    // Standalone program initialization
    //
    public static void
    main(String args[])
    {

	if (args.length<10) {
		System.err.println("Error: not enough parameters");
		return;		
	}	

	// Extract the -username parameter
	username=getParam(args,"username");

	// Extract the -id parameter
	id=Integer.parseInt(getParam(args,"id"));

	// Extract the -n parameter
	numberOfClients=Integer.parseInt(getParam(args,"n"));

	// Extract the -token parameter
	if (getParam(args,"token").equals("true"))
		hasToken=true;
	else hasToken=false;
	
	int status = 0;
	org.omg.CORBA.ORB orb = null;

	java.util.Properties props = System.getProperties();
	props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
	props.put("org.omg.CORBA.ORBSingletonClass",
		  "com.ooc.CORBA.ORBSingleton");
	props.put("ooc.orb.id", "Database-Client");

	try
	{
        orb = org.omg.CORBA.ORB.init(args, props);
	    status = run(orb, args);
	}
	catch(Exception ex)
	{
	    ex.printStackTrace();
	    status = 1;
	}

	if(orb != null)
	{
	    try
	    {
		orb.destroy();
	    }
	    catch(Exception ex)
	    {
		ex.printStackTrace();
		status = 1;
	    }
	}

	System.exit(status);
    }

/*
	Extract command line parameters
*/
    static public String getParam(String[] args, String arg) {
    	String result=null;
        for (int i=0; i<args.length; i++) {
        	if (args[i].equals("-"+arg) || args[i].equals("--"+arg)) {
        		result=args[i+1];
        		break;
        	}
        }
        return result;
    }
}
