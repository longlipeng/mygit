package com.huateng.hstserver.communicate.mina.comm.common.filter.connectmaintain.keepalive;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutException;
import org.apache.log4j.Logger;

/**
 * Tells {@link KeepAliveFilter} what to do when a keep-alive response message
 * was not received within a certain timeout.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 */
public interface KeepAliveRequestTimeoutHandler {
    /**
     * Do nothing.
     */
    static KeepAliveRequestTimeoutHandler NOOP = new KeepAliveRequestTimeoutHandler() {
        public void keepAliveRequestTimedOut(
                KeepAliveFilter filter, IoSession session) throws Exception {
            // Do nothing.
        }
    };

    /**
     * Logs a warning message, but doesn't do anything else.
     */
    static KeepAliveRequestTimeoutHandler LOG = new KeepAliveRequestTimeoutHandler() {
        private final Logger LOGGER =
        	Logger.getLogger(KeepAliveFilter.class);

        public void keepAliveRequestTimedOut(
                KeepAliveFilter filter, IoSession session) throws Exception {
            LOGGER.warn("A keep-alive response message was not received within " +
                    "{"+filter.getRequestTimeout()+"} second(s).");
        }
    };

    /**
     * Throws a {@link KeepAliveRequestTimeoutException}.
     */
    static KeepAliveRequestTimeoutHandler EXCEPTION = new KeepAliveRequestTimeoutHandler() {
        public void keepAliveRequestTimedOut(
                KeepAliveFilter filter, IoSession session) throws Exception {
            throw new KeepAliveRequestTimeoutException(
                    "A keep-alive response message was not received within " +
                    filter.getRequestTimeout() + " second(s).");
        }
    };

    /**
     * Closes the connection after logging.
     */
    static KeepAliveRequestTimeoutHandler CLOSE = new KeepAliveRequestTimeoutHandler() {
        private final Logger LOGGER =
        	Logger.getLogger(KeepAliveFilter.class);

        public void keepAliveRequestTimedOut(
                KeepAliveFilter filter, IoSession session) throws Exception {
//        	String warnMessage = new String();
//        	
//        	warnMessage = "Haven't recieve message for {} seconds, do timeout process,";
//        	
//        	if (filter.getResendTimes() == 0)
//        	{
//        		warnMessage = warnMessage + "close the session.";
//        		LOGGER.warn(warnMessage,filter.getRequestInterval());
//        	}
//        	else
//        	{
//        		warnMessage = warnMessage + "after {} times retry for keep-alive request but no response(each request interval is {} seconds), close the session.";
//        		LOGGER.warn(warnMessage,filter.getRequestInterval(),filter.getResendTimes(), filter.getRequestTimeout());
//        	}
        	
            LOGGER.warn("Closing the session because a keep-alive response " +
                    "message was not received within {"+filter.getRequestTimeout()+"} second(s).");
            session.close(true);
        }
    };

    /**
     * A special handler for the 'deaf speaker' mode.
     */
    static KeepAliveRequestTimeoutHandler DEAF_SPEAKER = new KeepAliveRequestTimeoutHandler() {
        public void keepAliveRequestTimedOut(
                KeepAliveFilter filter, IoSession session) throws Exception {
            throw new Error("Shouldn't be invoked.  Please file a bug report.");
        }
    };

    /**
     * Invoked when {@link KeepAliveFilter} couldn't receive the response for
     * the sent keep alive message.
     */
    void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session) throws Exception;
}
