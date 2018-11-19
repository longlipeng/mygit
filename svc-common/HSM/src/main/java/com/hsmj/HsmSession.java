// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   HsmSession.java

package com.hsmj;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

// Referenced classes of package hsmj:
//            SessionMonitor, ShareHandle

public class HsmSession
{
	private  Logger logger = Logger.getLogger(HsmSession.class);
    private static final SessionMonitor sSessionMonitor = new SessionMonitor();
//    private static final int ERR_CONFIG_FILE = 8;
//    private static final int ERR_CONNECT_HSM = 10;
//    private static final int ERR_SENDTO_HSM = 12;
//    private static final int ERR_RECVFORM_HSM = 13;
//    private static final int ERR_SESSION_END = 32;
//    private static final int ERR_HANDLE_FAULT = 48;
    private static ShareHandle sHandles[];
    private static String sIPs[];
    private static int sHsmNumber;
    private static int sBalance;
    private static int sPort;
    private static int sTimeOut;
    private static String sProfileFile;
    private static int sPreIndex = -1;
    private int iCurrentIndex;
    private int iLastErr;

    public HsmSession(String aProfileFile)
    {
        iCurrentIndex = -1;
        //iLastErr = -1;
        iLastErr = 0;
        iCurrentIndex = -1;
        try
        {
            initial(aProfileFile);
        }
        catch(Exception e)
        {
            iLastErr = 8;
            return;
        }
        for(int loop = 0; loop < sTimeOut / 20; loop++)
        {
            iCurrentIndex = getSession();
            if(iCurrentIndex != -1)
                break;
            try
            {
                Thread.sleep(20L);
                continue;
            }
            catch(InterruptedException e) { }
            break;
        }

        if(iCurrentIndex == -1)
            iLastErr = 48;
    }

    public static synchronized void initial(String aProfileFile)
        throws Exception
    {
        int nError = 0;
        if(sHandles != null)
            return;
        try
        {
            FileInputStream raf = new FileInputStream(aProfileFile);
            Properties pr = new Properties();
            pr.load(raf);
            sProfileFile = aProfileFile;
            String sDigit = pr.getProperty("NUMBER");
            sHsmNumber = Integer.parseInt(sDigit);
            sDigit = pr.getProperty("BALANCE");
            sBalance = Integer.parseInt(sDigit);
            sDigit = pr.getProperty("PORT");
            sPort = Integer.parseInt(sDigit);
            sDigit = pr.getProperty("TIMEOUT");
            sTimeOut = Integer.parseInt(sDigit);
            sIPs = new String[sHsmNumber];
            for(int i = 0; i < sHsmNumber; i++)
            {
                String str = Integer.toString(i + 1);
                sIPs[i] = pr.getProperty(str);
            }

        }
        catch(Exception e)
        {
            throw new Exception("��ȡ���ܻ����������ļ�[" + aProfileFile + "]�������");
        }
        catch(Error err)
        {
            throw new Exception("��ȡ���ܻ����������ļ�[" + aProfileFile + "]�������");
        }
        ShareHandle tHandle[] = new ShareHandle[sHsmNumber * sBalance];
        for(int i = 0; i < sBalance; i++)
        {
            for(int j = 0; j < sHsmNumber; j++)
            {
                tHandle[i * sHsmNumber + j] = new ShareHandle(sIPs[j], sPort, sTimeOut);
                if(tHandle[i * sHsmNumber + j].isFault())
                    nError++;
            }

        }

        if(nError == sHsmNumber * sBalance)
        {
            throw new Exception("�޷�����ܻ������ӡ�");
        } else
        {
            sHandles = tHandle;
            sSessionMonitor.addHandle(sHandles);
            sSessionMonitor.start();
            return;
        }
    }

    public static String getHsmSessionInfo()
    {
        StringBuffer tInfo = new StringBuffer();
        tInfo.append("Initial File = [" + sProfileFile + "]<br>").append("Balance = [" + sBalance + "]<br>").append("Number = [" + sHsmNumber + "]<br>").append("IP = [" + sIPs[0] + "]<br>").append("Port = [" + sPort + "]<br>").append("Timeout = [" + sTimeOut + "]<br>").append("Handle = [" + sHandles + "]<br>").append("Connection Info:<br>\n");
        for(int i = 0; i < sBalance * sHsmNumber; i++)
        {
            ShareHandle tHandle = sHandles[i];
            tInfo.append("Connection[" + i + "] Status is " + tHandle.getStatus() + "\n");
        }

        return tInfo.toString();
    }

    private static synchronized int getSession()
    {
        int i = 0;
        boolean tStatus = false;
        int tNumOfSession = sHsmNumber * sBalance;
        for(i = sPreIndex + 1; i < tNumOfSession; i++)
        {
            ShareHandle tHandle = sHandles[i];
            if(!tHandle.isUsable())
                continue;
            tHandle.setUsed();
            sPreIndex = i;
            tStatus = true;
            break;
        }

        if(!tStatus)
            for(i = 0; i < sPreIndex; i++)
            {
                ShareHandle tHandle = sHandles[i];
                if(!tHandle.isUsable())
                    continue;
                tHandle.setUsed();
                sPreIndex = i;
                tStatus = true;
                break;
            }

        if(!tStatus)
            i = -1;
        return i;
    }

    public int GetPortConf()
    {
        return sPort;
    }

    public int GetLastError()
    {
        return iLastErr;
    }

    public void SetErrCode(int nErrCode)
    {
        iLastErr = nErrCode;
    }

    public String ParseErrCode(int nErrCode)
    {
        String sMeaning;
        switch(nErrCode)
        {
        case 0: // '\0'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":������ȷ,״̬��";
            break;

        case 8: // '\b'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":�����ļ��쳣";
            break;

        case 10: // '\n'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":���������ʧ��";
            break;

        case 12: // '\f'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":��������������ʧ��";
            break;

        case 13: // '\r'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":������������ʧ��";
            break;

        case 32: // ' '
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":�����ѹر�";
            break;

        case 48: // '0'
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":���Ӿ��״̬�쳣";
            break;

        default:
            sMeaning = "0x" + Integer.toHexString(nErrCode) + ":�쳣����,����������־";
            break;
        }
        return sMeaning;
    }

    public int SendData(byte byteOut[], int nLength)
    {
        ShareHandle tHandle = sHandles[iCurrentIndex];
        if(tHandle.isFault())
        {
            iLastErr = 48;
            return iLastErr;
        }
        try
        {
            tHandle.write(byteOut, nLength);
            iLastErr = 0;
        }
        catch(Exception e)
        {
            tHandle.setFault();
            iLastErr = 12;
            logger.info("HsmSession::SendData() - " + e.getMessage());
        }
        catch(Error err)
        {
            tHandle.setFault();
            iLastErr = 12;
            logger.info("HsmSession::SendData() - " + err.getMessage());
        }
        return iLastErr;
    }

    public int RecvData(byte byteIn[])
    {
        ShareHandle tHandle = sHandles[iCurrentIndex];
        if(tHandle.isFault())
        {
            iLastErr = 48;
            return iLastErr;
        }
        try
        {
            tHandle.read(byteIn, 2048);
            if(byteIn[0] != 65)
                iLastErr = byteIn[1] & 0xff;
            else
                iLastErr = 0;
        }
        catch(Exception e)
        {
            tHandle.setFault();
            iLastErr = 13;
            logger.info("HsmSession::RecvData() - " + e.getMessage());
        }
        catch(Error err)
        {
            tHandle.setFault();
            iLastErr = 13;
            logger.info("HsmSession::RecvData() - " + err.getMessage());
        }
        return iLastErr;
    }

    public int HsmSessionEnd()
    {
        if(iCurrentIndex != -1)
        {
            ShareHandle tHandle = sHandles[iCurrentIndex];
            if(tHandle.isUsed())
            {
                tHandle.setNotused();
                iLastErr = 0;
            }
        }
        return iLastErr;
    }

}
