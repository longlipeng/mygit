package com.huateng.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.FindNotMakeTestClass;
import com.huateng.framework.util.MakeTestType;
import com.huateng.test.flow.issue.IssueTest;
import com.huateng.test.method.MethodTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { MethodTest.class, IssueTest.class,
		FindNotMakeTestClass.class })
@MakeTestType(testType={"MakeTestType"})
public class TestAll {

}
