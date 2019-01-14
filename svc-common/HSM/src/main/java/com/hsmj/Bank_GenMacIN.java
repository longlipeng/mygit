package com.hsmj;

public class Bank_GenMacIN
{

    public byte bk_inx[];
    public byte mak_len[];
    public byte mak[];
    public byte mac_element_len[];
    public byte mac_element[];

    public Bank_GenMacIN(byte index[], byte mak_len[], byte mak[], byte mac_data_len[], byte mac_data[])
    {
        bk_inx = new byte[3];
        this.mak_len = new byte[2];
        this.mak = new byte[48];
        mac_element_len = new byte[3];
        mac_element = new byte[400];
        System.arraycopy(index, 0, bk_inx, 0, 3);
        System.arraycopy(mak_len, 0, this.mak_len, 0, 2);
        System.arraycopy(mak, 0, this.mak, 0, mak.length);
        System.arraycopy(mac_data_len, 0, mac_element_len, 0, 3);
        System.arraycopy(mac_data, 0, mac_element, 0, mac_data.length);
    }
}

