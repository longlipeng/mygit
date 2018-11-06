package com.allinfinance.prepay.processor;

import com.allinfinance.prepay.message.BasicMessage;

public interface IProcessor
{
	BasicMessage process(BasicMessage req) throws Exception;
}
