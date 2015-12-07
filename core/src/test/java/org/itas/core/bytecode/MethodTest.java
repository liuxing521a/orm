package org.itas.core.bytecode;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
	MDCloneProviderTest.class,
	MDDeleteSQLProviderTest.class,
	MDDoAlterProviderTest.class,
	MDDoCreateProviderTest.class,
	MDDoDeleteProviderTest.class,
	MDDoFillProviderTest.class,
	MDDoInsertProviderTest.class,
	MDDoSelectProviderTest.class,
	MDDoUpdateProviderTest.class,
	MDInsertSQLProviderTest.class,
	MDSelectSQLProviderTest.class,
	MDTableNameProviderTest.class,
	MDUpdateSQLProviderTest.class,
})
public class MethodTest {
	
}
