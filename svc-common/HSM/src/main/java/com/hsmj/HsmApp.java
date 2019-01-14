// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   HsmApp.java

package com.hsmj;

import org.apache.log4j.Logger;


/**
 * 
 * <p>
 * <strong>
 * This class name was HsmApp
 * </strong>
 * </p>
 * @author Lay
 * @date 2010-5-11 03:38:23
 * @version 1.0
 */
public class HsmApp
{
	private  Logger logger = Logger.getLogger(HsmApp.class);
    public static final int SECBUF_MAX_SIZE = 6000;
    private byte iSecBufferIn[];
    private byte iSecBufferOut[];

    public HsmApp()
    {
        iSecBufferIn = new byte[6000];
        iSecBufferOut = new byte[6000];
    }

/*    public void OutputDataHex(String sInfo, byte byteIn[], int nDataLen)
    {
        logger.info("[" + sInfo + "]" + "length" + "[" + nDataLen + "]");
        int n;
        int prev = n = 0;
        int i;
        for(i = 0; i < nDataLen; i++)
        {
            if(i == prev + 16)
            {
                logger.info("    ;");
                for(int j = prev; j < prev + 16; j++)
                    if(Character.isLetter((char)(byteIn[j] & 0xff)))
                        logger.info((char)byteIn[j]);
                    else
                        logger.info(" ");

                prev += 16;
            }
            if(Integer.toHexString(byteIn[i] & 0xff).length() == 1)
                logger.info("0" + Integer.toHexString(byteIn[i] & 0xff) + " ");
            else
                logger.info(Integer.toHexString(byteIn[i] & 0xff) + " ");
        }

        if(i != prev)
        {
            n = i;
            for(; i < prev + 16; i++)
                logger.info("   ");

        }
        logger.info("    ;");
        for(i = prev; i < n; i++)
            if(Character.isLetter((char)byteIn[i]))
                logger.info((char)byteIn[i]);
            else
                logger.info(" ");

    }*/

    static boolean Str2Hex(byte in[], byte out[], int len)
    {
        byte asciiCode[] = {
            10, 11, 12, 13, 14, 15
        };
        if(len > in.length)
            return false;
        if(len % 2 != 0)
            return false;
        byte temp[] = new byte[len];
        for(int i = 0; i < len; i++)
            if(in[i] >= 48 && in[i] <= 57)
                temp[i] = (byte)(in[i] - 48);
            else
            if(in[i] >= 65 && in[i] <= 70)
                temp[i] = asciiCode[in[i] - 65];
            else
            if(in[i] >= 97 && in[i] <= 102)
                temp[i] = asciiCode[in[i] - 97];
            else
                return false;

        for(int i = 0; i < len / 2; i++)
            out[i] = (byte)(temp[2 * i] * 16 + temp[2 * i + 1]);

        return true;
    }

    static boolean Hex2Str(byte in[], byte out[], int len)
    {
        byte asciiCode[] = {
            65, 66, 67, 68, 69, 70
        };
        if(len > in.length)
            return false;
        byte temp[] = new byte[2 * len];
        for(int i = 0; i < len; i++)
        {
            temp[2 * i] = (byte)((in[i] & 0xf0) / 16);
            temp[2 * i + 1] = (byte)(in[i] & 0xf);
        }

        for(int i = 0; i < 2 * len; i++)
            if(temp[i] <= 9 && temp[i] >= 0)
                out[i] = (byte)(temp[i] + 48);
            else
                out[i] = asciiCode[temp[i] - 10];

        return true;
    }

    public static String byte2hex(byte b[])
    {
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 0xff);
            if(stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if(n < b.length - 1)
                hs = hs + " ";
        }

        return hs.toUpperCase();
    }

   public int Bank_GenMac(HsmSession hSession, Bank_GenMacIN pmsg_in, Bank_GenMacOUT pmsg_out)
    {
        int iSndLen = 0;
        int nResult = 0;
        byte retCode[] = new byte[1];
        iSecBufferIn[0] = 4;
        iSecBufferIn[1] = 16;
        iSndLen += 2;
        iSecBufferIn[iSndLen++] = 1;
        String temp = new String(pmsg_in.bk_inx);
        int i = Integer.parseInt(temp);
        if(i > 999)
        {
            retCode[0] = 12;
            Hex2Str(retCode, pmsg_out.reply_code, 1);
            return 1;
        }
        iSecBufferIn[iSndLen++] = (byte)(i >> 8 & 0xff);
        iSecBufferIn[iSndLen++] = (byte)(i & 0xff);
        temp = new String(pmsg_in.mak_len);
        i = Integer.parseInt(temp);
        iSecBufferIn[iSndLen++] = (byte)i;
        iSecBufferIn[iSndLen++] = 1;
        byte mak[] = new byte[i];
        Str2Hex(pmsg_in.mak, mak, i * 2);
        System.arraycopy(mak, 0, iSecBufferIn, iSndLen, i);
        iSndLen += i;
        for(int j = 0; j < 8; j++)
            iSecBufferIn[iSndLen + j] = 0;
        iSndLen += 8;
        temp = new String(pmsg_in.mac_element_len);
        i = Integer.parseInt(temp);
        iSecBufferIn[iSndLen++] = (byte)(i >> 8 & 0xff);
        iSecBufferIn[iSndLen++] = (byte)(i & 0xff);
        System.arraycopy(pmsg_in.mac_element, 0, iSecBufferIn, iSndLen, i);
        iSndLen += i;
        
       // byte[] out = new byte[200];
        //CodeUtil.Hex2Str(iSecBufferIn, out, iSndLen);
        //String cc = new String(out).trim();
        
        //cc = "041001002808024718BBCB715F00B4000000000000000000113132333435363738203132333435363738";
        
        //iSndLen = cc.length()/2;
        //CodeUtil.Str2Hex(cc.getBytes(), iSecBufferIn, cc.length());
        
        	nResult = hSession.SendData(iSecBufferIn, iSndLen);
        
        if(nResult != 0)
        {
            retCode[0] = (byte)nResult;
            Hex2Str(retCode, pmsg_out.reply_code, 1);
            return 1;
        }
        nResult = hSession.RecvData(iSecBufferOut);
        if(nResult != 0)
        {
            retCode[0] = (byte)nResult;
            Hex2Str(retCode, pmsg_out.reply_code, 1);
            return 1;
        }
        if(iSecBufferOut[0] == 65)
        {
            byte tempMac[] = new byte[8];
            System.arraycopy(iSecBufferOut, 1, tempMac, 0, 8);
            byte [] temp1 = new byte[16];
            Hex2Str(tempMac, temp1, 8);
            logger.info("temp1 = " + new String(temp1));
            Hex2Str(tempMac, pmsg_out.mac, 4);
            pmsg_out.reply_code[0] = 48;
            pmsg_out.reply_code[1] = 48;
        } else
        {
            retCode[0] = iSecBufferOut[1];
            Hex2Str(retCode, pmsg_out.reply_code, 1);
            return 1;
        }
        return 0;
    }

   
   
}
