// **********************************************************************
//
// Generated by the ORBacus IDL to Java Translator
//
// Copyright (c) 2002
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

// Version: 4.1.2

package writer;

//
// IDL:Writer:1.0
//
/***/

public interface WriterOperations
{
    //
    // IDL:Writer/setCard:1.0
    //
    /***/

    void
    setCard(String inputFileName,
            short x,
            short y,
            short width,
            short height);

    //
    // IDL:Writer/setMessage:1.0
    //
    /***/

    void
    setMessage(String message);

    //
    // IDL:Writer/writeCard:1.0
    //
    /***/

    String
    writeCard(String outputFileName);
}