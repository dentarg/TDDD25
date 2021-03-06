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

package database;

//
// IDL:Database:1.0
//
public abstract class DatabasePOA
    extends org.omg.PortableServer.Servant
    implements org.omg.CORBA.portable.InvokeHandler,
               DatabaseOperations
{
    static final String[] _ob_ids_ =
    {
        "IDL:Database:1.0",
    };

    public Database
    _this()
    {
        return DatabaseHelper.narrow(super._this_object());
    }

    public Database
    _this(org.omg.CORBA.ORB orb)
    {
        return DatabaseHelper.narrow(super._this_object(orb));
    }

    public String[]
    _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId)
    {
        return _ob_ids_;
    }

    public org.omg.CORBA.portable.OutputStream
    _invoke(String opName,
            org.omg.CORBA.portable.InputStream in,
            org.omg.CORBA.portable.ResponseHandler handler)
    {
        final String[] _ob_names =
        {
            "getCardArea",
            "getCardFile",
            "getCardName",
            "getCardThumb",
            "getCardURL",
            "getCardsNumber"
        };

        int _ob_left = 0;
        int _ob_right = _ob_names.length;
        int _ob_index = -1;

        while(_ob_left < _ob_right)
        {
            int _ob_m = (_ob_left + _ob_right) / 2;
            int _ob_res = _ob_names[_ob_m].compareTo(opName);
            if(_ob_res == 0)
            {
                _ob_index = _ob_m;
                break;
            }
            else if(_ob_res > 0)
                _ob_right = _ob_m;
            else
                _ob_left = _ob_m + 1;
        }

        switch(_ob_index)
        {
        case 0: // getCardArea
            return _OB_op_getCardArea(in, handler);

        case 1: // getCardFile
            return _OB_op_getCardFile(in, handler);

        case 2: // getCardName
            return _OB_op_getCardName(in, handler);

        case 3: // getCardThumb
            return _OB_op_getCardThumb(in, handler);

        case 4: // getCardURL
            return _OB_op_getCardURL(in, handler);

        case 5: // getCardsNumber
            return _OB_op_getCardsNumber(in, handler);
        }

        throw new org.omg.CORBA.BAD_OPERATION();
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardArea(org.omg.CORBA.portable.InputStream in,
                       org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_a0 = in.read_short();
        org.omg.CORBA.ShortHolder _ob_ah1 = new org.omg.CORBA.ShortHolder();
        org.omg.CORBA.ShortHolder _ob_ah2 = new org.omg.CORBA.ShortHolder();
        org.omg.CORBA.ShortHolder _ob_ah3 = new org.omg.CORBA.ShortHolder();
        org.omg.CORBA.ShortHolder _ob_ah4 = new org.omg.CORBA.ShortHolder();
        boolean _ob_r = getCardArea(_ob_a0, _ob_ah1, _ob_ah2, _ob_ah3, _ob_ah4);
        out = handler.createReply();
        out.write_boolean(_ob_r);
        out.write_short(_ob_ah1.value);
        out.write_short(_ob_ah2.value);
        out.write_short(_ob_ah3.value);
        out.write_short(_ob_ah4.value);
        return out;
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardFile(org.omg.CORBA.portable.InputStream in,
                       org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_a0 = in.read_short();
        String _ob_r = getCardFile(_ob_a0);
        out = handler.createReply();
        out.write_string(_ob_r);
        return out;
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardName(org.omg.CORBA.portable.InputStream in,
                       org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_a0 = in.read_short();
        String _ob_r = getCardName(_ob_a0);
        out = handler.createReply();
        out.write_string(_ob_r);
        return out;
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardThumb(org.omg.CORBA.portable.InputStream in,
                        org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_a0 = in.read_short();
        String _ob_r = getCardThumb(_ob_a0);
        out = handler.createReply();
        out.write_string(_ob_r);
        return out;
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardURL(org.omg.CORBA.portable.InputStream in,
                      org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_a0 = in.read_short();
        String _ob_r = getCardURL(_ob_a0);
        out = handler.createReply();
        out.write_string(_ob_r);
        return out;
    }

    private org.omg.CORBA.portable.OutputStream
    _OB_op_getCardsNumber(org.omg.CORBA.portable.InputStream in,
                          org.omg.CORBA.portable.ResponseHandler handler)
    {
        org.omg.CORBA.portable.OutputStream out = null;
        short _ob_r = getCardsNumber();
        out = handler.createReply();
        out.write_short(_ob_r);
        return out;
    }
}
