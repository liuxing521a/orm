package org.itas.core;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ServerModule implements Module {

  ServerModule() {
  }
  
  @Override
  public final void configure(Binder binder) {
//		final Config config = ConfigFactory.load("application.conf");
//		
//		final Config dbConfig = config.getConfig("mainDB");
//		final DBPool dbPool  = DataBaseManager.makdDBPoolBuilder()
//			.setConfig(dbConfig).builder();
//			
//		final DBSync dbSync = DataBaseManager.makeDBSyncBuilder()
//			.setDBPool(dbPool).builder();
//			
//		final Config sharedConfig = config.getConfig("shared");
//		final DBSyncService syncService = DataBaseManager.makdDBSyncThreadBuilder()
//			.setSync(dbSync).setInterval(sharedConfig.getLong("interval"))
//			.builder();
//		
//		final DataPool dataPool = DataPoolImpl.makeBuilder()
//			.setShared(sharedConfig).setDbSync(dbSync).builder();
//		
//		final ResPool resPool = ResPoolImpl.makeBuilder().builder();
//		
//		binder.bind(DBSync.class).toInstance(dbSync);
//    binder.bind(DBPool.class).toInstance(dbPool);
//    binder.bind(DBSyncService.class).toInstance(syncService);
//
//    binder.bind(DataPool.class).toInstance(dataPool);
//    binder.bind(ResPool.class).toInstance(resPool);
//    
//    binder.bind(Config.class).toInstance(config);
  }
	
}
