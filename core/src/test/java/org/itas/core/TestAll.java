package org.itas.core;

import org.itas.core.bytecode.FieldTest;
import org.itas.core.bytecode.MethodTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
	FieldTest.class,
	MethodTest.class,
})
public class TestAll {
	
}
