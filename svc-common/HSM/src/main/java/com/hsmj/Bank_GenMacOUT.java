package com.hsmj;

public class Bank_GenMacOUT
{

    public byte reply_code[];
    public byte mac[];

    public Bank_GenMacOUT()
    {
        reply_code = new byte[2];
        mac = new byte[8];
    }
}
