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

package lock.LockPackage;

//
// IDL:Lock/token_type:1.0
//
final public class token_typeHolder implements org.omg.CORBA.portable.Streamable
{
    public int[] value;

    public
    token_typeHolder()
    {
    }

    public
    token_typeHolder(int[] initial)
    {
        value = initial;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        value = token_typeHelper.read(in);
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        token_typeHelper.write(out, value);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return token_typeHelper.type();
    }
}
