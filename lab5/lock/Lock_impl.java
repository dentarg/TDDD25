// **********************************************************************
//
// Copyright (c) 2002
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

package lock;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

import java.lang.Math;
import java.lang.Thread;

public class Lock_impl extends LockPOA
{
	// Declaration of the array that stores the remote references
	Lock[] objArray;

	//
	// TODO:
	// Declare necessary variables
	private short  my_id;
	private int    state;
	private int    number_of_clients;
	private int[]  token;
	private int[]  request;
	public static final int NO_TOKEN       = 0;
   public static final int TOKEN_PRESENT  = 1;
   public static final int TOKEN_HELD     = 2;
   public static final boolean DEBUG      = false;


    // The servants default POA
    private org.omg.PortableServer.POA poa_;

// ----------------------------------------------------------------------
// Lock_impl constructor
// ----------------------------------------------------------------------
    public Lock_impl(org.omg.PortableServer.POA poa)
    {
	poa_ = poa;
    }


// ----------------------------------------------------------------------
// Methods necessary for the CORBA framework
// ----------------------------------------------------------------------
    public org.omg.PortableServer.POA
    _default_POA()
    {
	return poa_;
    }


// ----------------------------------------------------------------------
// Methods that can be called from other CORBA objects
// ----------------------------------------------------------------------

/*
	This method is used to request the token from a remote object.
*/
	public void request_token(short id, int logical_time) {
		System.out.print("Token was requested by client ");
		System.out.println(id+" at remote logical time "+logical_time);

		// TODO: Implement code for handling token requests from remote objects
      request[id] = Math.max(request[id], logical_time);
      if(state == TOKEN_PRESENT) {
         release_token();
      }
	}

/*
	This method is called by a remote object when it wants to hand the token to us.
*/
	public void acquire_token(int[] token) {
		System.out.println("Token acquired!");

		// TODO: Implement code for acquiring the token from a remote object
		this.token = token;
      state = TOKEN_HELD;
	}

/*
	This function is called from any remote object that wants us to print
	some text on the screen (for instance to check if we are alive).
*/
	public void print_message(short id) {
		System.out.println("Received a message from client "+id);	
	}

// ----------------------------------------------------------------------
// Local methods that can be called only from the program that created
// this object
// ----------------------------------------------------------------------

/*
	This method must be called when all clients are started. It finds the references
	of remote objects and puts them into an array. It also initializes all variables
	necesary for the distributed mutual exclusion algorithm to work.
*/
	public void initialize(int my_id, int number_of_clients, boolean has_token, String username) throws org.omg.CORBA.UserException {

		System.out.println("Initializing "+number_of_clients+" clients...");

		// Initialize object array
		objArray=new Lock[number_of_clients];

		// Get the NamingContext
		org.omg.CORBA.Object rootObj = _orb().resolve_initial_references("NameService");
		org.omg.CosNaming.NamingContext root = org.omg.CosNaming.NamingContextHelper.narrow(rootObj);
		
		for (int i=0; i<number_of_clients; i++) {
			// Only search for remote objects
			if (i!=my_id) {
				// Use the CORBA name service to find the object
				System.out.println("Searching for client: "+username+i);
				NameComponent[] nc = new NameComponent[1];
				nc[0] = new NameComponent("Lock",username+i);
				org.omg.CORBA.Object obj = ((NamingContext)root).resolve(nc);

				// Store it into an array containing references to all
				// remote objects
				objArray[i] = LockHelper.narrow(obj);

				// Function method() of the remote client i can now be accessed by:
				// objArray[i].method();
				objArray[i].print_message((short)my_id);
			}
		}
		
		// The reference corresponding to this, local, object should be null
		// since we are not going to access our own member functions using CORBA
		objArray[my_id]=null;		
		System.out.println("All remote objects are located.");
		
		//
		// TODO: Initialization of variables, such as the token.
      this.my_id = (short) my_id;
      this.number_of_clients = number_of_clients;
      token = new int[number_of_clients];
      request = new int[number_of_clients];

      for(int i = 0; i < number_of_clients; i++) {
         token[i] = 0;
         request[i] = 0;
      }

      if(has_token){
         state = TOKEN_PRESENT;
      }
      else {
         state = NO_TOKEN;
      }

	}


/*
	This method is called from the local client whenever it wants to acquire
	the lock.
*/    
    public void acquire() {
		System.out.println("Acquiring lock...");

		//
		// TODO:
		// Implement code for acquiring the lock. This means requesting the token
		// from all other clients, if we do not already have it.
		if(state == NO_TOKEN) {
		   for(int i = 0; i < number_of_clients; i++) {
		      if(i != my_id) { // do not request from myself
		         objArray[i].request_token(my_id, token[my_id] + 1);
	         }
		   }
		   // wait until we receive token
		   while(true) {
		      if(state == TOKEN_HELD) {
		         break;
		      }
		      else {
		         try {
		            Thread.sleep(500);
		         }
		         catch(InterruptedException e) {
		            System.err.println("sleep error");
		         }
		      }
		   }
	   }
	   state = TOKEN_HELD;
	   // enter critical section
    }
    
/*
	This method is called from the local client whenever it wants to release
	an acquired lock.
*/
    public void release() {

		//
		// TODO:
		// Implement code for releasing the lock.
		// If a remote object wants the token, send it!
		state = TOKEN_PRESENT;
		release_token();
	
		System.out.println("Lock released!");
    }


	//
	// TODO:
	// Declare necessary (if any) local methods
	public void release_token() {
	   if(DEBUG) {
         System.out.println("release_token called from my_id: " + my_id);
         System.out.println("state: " + state);
         for(int i = 0; i < number_of_clients; i++) {
            System.out.println("token["+i+"]: " + token[i]);
            System.out.println("request["+i+"]: " + request[i]);
         }
      }

	   for(int i = 1; i <= number_of_clients; i++) {
	      int k = (my_id + i) % number_of_clients;
         if(request[k] > token[k]) {
            state = NO_TOKEN;
            token[my_id] = token[my_id] + 1;
            objArray[k].acquire_token(token);
            break;
         }
	   }
	}

}
