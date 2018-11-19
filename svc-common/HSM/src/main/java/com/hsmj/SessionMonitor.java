// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   SessionMonitor.java

package com.hsmj;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <strong>
 * This class name was SessionMonitor
 * </strong>
 * </p>
 * @author Lay
 * @date 2010-5-11 03:38:08
 * @version 1.0
 */
public class SessionMonitor extends Thread
{
	private  Logger logger = Logger.getLogger(SessionMonitor.class);
    private static ShareHandle sHandle[];

    public SessionMonitor()
    {
    }

    public void addHandle(ShareHandle aHandle[])
    {
        sHandle = aHandle;
    }

    public void run()
    {
        do
            try
            {
                logger.info("SessionMonitor::run()");
                for(int i = 0; i < sHandle.length; i++)
                    if(sHandle[i].isFault())
                    {
                        sHandle[i].releaseSocketHandle();
                        sHandle[i].connect();
                    }

                sleep(10000L);
            }
            catch(Exception e)
            {
                logger.info("SessionMonitor::run() - " + e.getMessage());
            }
            catch(Error err)
            {
                logger.info("SessionMonitor::run() - " + err.getMessage());
            }
        while(true);
    }
}
