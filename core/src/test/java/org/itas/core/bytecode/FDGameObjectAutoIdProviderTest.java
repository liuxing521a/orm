package org.itas.core.bytecode;

import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class FDGameObjectAutoIdProviderTest extends AbstreactFieldProvider {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = FDGameObjectAutoProvider.PROVIDER;
		field = clazz.getDeclaredField("hero");
	}
	
	@Override
	public void setStatementTest() throws Exception {
		thrown.expect(UnsupportException.class);
		thrown.expectMessage("auto game Object not supported setStatement");
		provider.setStatement(1, field);
	}

	@Override
	public void getResultSetTest() throws Exception {
		thrown.expect(UnsupportException.class);
		thrown.expectMessage("auto game Object not supported getResultSet");
		provider.getResultSet(field);
	}
	
}
