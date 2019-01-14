package com.huateng.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;
import com.huateng.test.method.MethodTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( {MethodTest.class})
@MakeTestType(testType={"MakeTestType"})
public class TestAll {

}
