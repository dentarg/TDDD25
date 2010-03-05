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

import java.awt.*;
import java.awt.event.*;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Client extends java.applet.Applet implements ActionListener {

   static int run(org.omg.CORBA.ORB orb, String[] args)
   throws org.omg.CORBA.UserException {

      //
      // Get "database" object
      //
      org.omg.CORBA.Object rootObj =
         orb.resolve_initial_references("NameService");
      if(rootObj == null) {
         System.err.println("database.Client: " +
            "cannot read IOR from Database.ref");
         return 1;
      }
      org.omg.CosNaming.NamingContext root =
         org.omg.CosNaming.NamingContextHelper.narrow(rootObj);

      NameComponent[] nc = new NameComponent[1];
//    nc[0] = new NameComponent("Database","trapo");
      nc[0] = new NameComponent("Database","foo123");
      org.omg.CORBA.Object obj = ((NamingContext)root).resolve(nc);
      Database database = DatabaseHelper.narrow(obj);

      NameComponent[] nc2 = new NameComponent[1];
      nc2[0] = new NameComponent("Writer","trapo");
      org.omg.CORBA.Object obj2 = ((NamingContext)root).resolve(nc2);
      Writer writer = WriterHelper.narrow(obj2);

      //
      // Main loop
      //
      System.out.println("Enter a number or 'x' for exit:");
      System.out.println("  1: getCardsNumber()");
      System.out.println("  2: getCardName()");
      System.out.println("  3: getCardFile()");
      System.out.println("  4: getCardURL()");
      System.out.println("  5: getCardThumb()");
      System.out.println("  6: getCardArea()");
      System.out.println("  7: writeCard()");

      try {
         String input;
         int cmd = 0;
         do {
            System.out.print("> ");
            java.io.BufferedReader in = new java.io.BufferedReader(
               new java.io.InputStreamReader(System.in));
            input = in.readLine();
            cmd = Integer.parseInt(input);

            switch(cmd) {
               case 1:
                  System.out.println("getCardsNumber(): " +
                     database.getCardsNumber());
                  break;
               case 2:
                  System.out.println("getCardName: " +
                     database.getCardName(readCardNumber(in)));
                  break;
               case 3:
                  System.out.println("getCardFile: " +
                     database.getCardFile(readCardNumber(in)));
                  break;
               case 4:
                  System.out.println("getCardURL: " +
                     database.getCardURL(readCardNumber(in)));
                  break;
               case 5:
                  System.out.println("getCardThumb: " +
                     database.getCardThumb(readCardNumber(in)));
                  break;
               case 6:
                  ShortHolder xHolder        = new ShortHolder();
                  ShortHolder yHolder        = new ShortHolder();
                  ShortHolder widthHolder    = new ShortHolder();
                  ShortHolder heightHolder   = new ShortHolder();
                  short x                    = xHolder.value;
                  short y                    = yHolder.value;
                  short width                = widthHolder.value;
                  short height               = heightHolder.value;

                  System.out.println("getCardArea: " +
                     database.getCardArea(readCardNumber(in), xHolder, yHolder,
                     widthHolder, heightHolder));
                  break;
               case 7:
                  short number = readCardNumber(in);
                  System.out.print("Message? ");
                  String message = in.readLine();
                  writer.setCard(database.getCardFile(number),
                     (short) 10, (short) 10, (short) 100, (short) 100);
                  writer.setMessage(message);
                  String outputFileName = "foo";
                  String url = writer.writeCard(outputFileName);
                  System.out.println("writeCard(): " + url);
                  break;
               default:
                  System.out.println("error");
               }
            } while(!input.equals("x"));
         }
         catch(java.io.IOException ex) {
            System.err.println("Can't read from `" + ex.getMessage() + "'");
            return 1;
         }
         catch(java.lang.NumberFormatException ex) {
            System.err.println("Invalid input (1)");
            return 1;
         }
         return 0;
      }

      private static short readCardNumber(java.io.BufferedReader in) {
         try {
            System.out.print("cardNumber? ");
            String input = in.readLine();
            short number = Short.parseShort(input);
            return number;
         }
         catch(java.io.IOException ex) {
            System.err.println("Can't read from `" + ex.getMessage() + "'");
            return 1;
         }
         catch(java.lang.NumberFormatException ex) {
            System.err.println("Invalid input (2)");
            return 0;
         }
      }

      //
      // Standalone program initialization
      //
      public static void main(String args[]) {
         int status = 0;
         org.omg.CORBA.ORB orb = null;

         java.util.Properties props = System.getProperties();
         props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
         props.put("org.omg.CORBA.ORBSingletonClass",
            "com.ooc.CORBA.ORBSingleton");
         props.put("ooc.orb.id", "Database-Client");

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

      //
      // Members only needed for applet
      //
      private Database database_;
      private Button button_;

      //
      // Applet initialization
      //
      public void init() {
         String ior = getParameter("ior");

         //
         // Create ORB
         //
         org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(this, null);

         //
         // Create client object
         //
         org.omg.CORBA.Object obj = orb.string_to_object(ior);
         if(obj == null)
            throw new RuntimeException();

         database_ = DatabaseHelper.narrow(obj);

         //
         // Add database button
         //
         button_ = new Button("Database");
         button_.addActionListener(this);
         this.add(button_);
      }

      //
      // Handle events
      //
      public void actionPerformed(ActionEvent event) {
         //	database_.say_database();
      }
   }
