// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ShareHandle.java

package com.hsmj;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.log4j.Logger;


public class ShareHandle
{
	private Logger logger = Logger.getLogger(ShareHandle.class);
    public final int FLAG_NOTUSE = 0;
    public final int FLAG_USED = 1;
    public final int FLAG_FAULT = 2;
    private Socket iSocketHandle;
    private int iStatus;
    private InputStream iInputStream;
    private OutputStream iOutputStream;
    private String iIP;
    private int iPort;
    private int iTimeout;

    public ShareHandle(String aIP, int aPort, int aTimeout)
    {
        iSocketHandle = null;
        iInputStream = null;
        iOutputStream = null;
        iIP = null;
        iPort = -1;
        iTimeout = -1;
        iIP = aIP;
        iPort = aPort;
        iTimeout = aTimeout;
        connect();
    }

    public void connect()
    {
        try
        {
            iSocketHandle = new Socket();
            InetSocketAddress hsmAddr = new InetSocketAddress(iIP, iPort);
            iSocketHandle.connect(hsmAddr, iTimeout);
            iSocketHandle.setSoTimeout(iTimeout);
            iSocketHandle.setKeepAlive(true);
            iSocketHandle.setReceiveBufferSize(2048);
            iSocketHandle.setTcpNoDelay(true);
            iInputStream = iSocketHandle.getInputStream();
            iOutputStream = iSocketHandle.getOutputStream();
            setNotused();
        }
        catch(IOException e)
        {
        	logger.error(e.getMessage());
            releaseSocketHandle();
        }
    }

    public void releaseSocketHandle()
    {
        setFault();
        if(iInputStream != null)
        {
            try
            {
                iInputStream.close();
            }
            catch(Exception exception) { }
            iInputStream = null;
        }
        if(iOutputStream != null)
        {
            try
            {
                iOutputStream.close();
            }
            catch(Exception exception1) { }
            iOutputStream = null;
        }
        if(iSocketHandle != null)
        {
            try
            {
                iSocketHandle.close();
            }
            catch(Exception exception2) { 
            	
            }
            iSocketHandle = null;
        }
    }

    public void setUsed()
    {
        iStatus = 1;
    }

    public void setNotused()
    {
        iStatus = 0;
    }

    public void setFault()
    {
        iStatus = 2;
    }

    public int getStatus()
    {
        return iStatus;
    }

    public boolean isUsed()
    {
        return iStatus == 1;
    }

    public boolean isUsable()
    {
        return iStatus == 0;
    }

    public boolean isFault()
    {
        return iStatus == 2;
    }

    public void write(byte aByteOut[], int aLength)
        throws IOException
    {
        iOutputStream.write(aByteOut, 0, aLength);
    }

    public int read(byte aByteIn[], int aBufferSize)
        throws IOException
    {
        int tReadLen = iInputStream.read(aByteIn, 0, aBufferSize);
        return tReadLen;
    }
}
