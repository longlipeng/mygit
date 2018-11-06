package com.allinfinance.prepay.encryption;

import java.nio.ByteBuffer;

public interface Client
{
	byte[] request(ByteBuffer data) throws Exception;
}
