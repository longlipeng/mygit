package com.allinfinance.prepay.message;

public interface IMessage {
	public IMessage createResp() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException;

	public String toXml();
}