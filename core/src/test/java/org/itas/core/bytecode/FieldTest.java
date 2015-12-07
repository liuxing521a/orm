package org.itas.core.bytecode;

import org.itas.core.util.FirstCharTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
	FirstCharTest.class,
  FDBooleanProviderTest.class,
  FDByteProviderTest.class,
  FDCharProviderTest.class,
  FDDoubleArrayProviderTest.class,
  FDDoubleProviderTest.class,
  FDEnumByteProviderTest.class,
  FDEnumIntProviderTest.class,
  FDEnumProviderTest.class,
  FDEnumStringProviderTest.class,
  FDFloatProviderTest.class,
  FDGameObjectProviderTest.class,
  FDGameObjectAutoIdProviderTest.class,
//  FDGameObjectNoCacheProviderTest.class,
  FDIntProviderTest.class,
  FDListProviderTest.class,
  FDLongProviderTest.class,
  FDMapProviderTest.class,
  FDResourceProviderTest.class,
  FDSetProviderTest.class,
  FDSingleArrayProviderTest.class,
  FDShortProviderTest.class,
  FDSimpleProviderTest.class,
  FDStringProviderTest.class,
  FDTimestampProviderTest.class,
})
public class FieldTest {
	
}
