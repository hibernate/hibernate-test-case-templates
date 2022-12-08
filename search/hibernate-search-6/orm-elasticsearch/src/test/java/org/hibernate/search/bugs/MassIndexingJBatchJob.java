package org.hibernate.search.bugs;

import com.ibm.jbatch.spi.BatchSPIManager;
import com.ibm.jbatch.spi.DatabaseConfigurationBean;
import org.hibernate.Session;
import org.hibernate.search.batch.jsr352.core.massindexing.MassIndexingJob;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;

public class MassIndexingJBatchJob {

  public static void start(Session session) throws Exception {
    configureMassIndexingJBatchProvider(session);

    MassIndexingJob.ParametersBuilder parametersBuilder = MassIndexingJob.parameters()
      .forEntities(YourAnnotatedEntity.class)
      .idFetchSize(Integer.MIN_VALUE)
      .entityFetchSize(Integer.MIN_VALUE);

    Properties parameters = parametersBuilder.build();
    JobOperator jobOperator = BatchRuntime.getJobOperator();
    jobOperator.start(MassIndexingJob.NAME, parameters);
  }

  private static void configureMassIndexingJBatchProvider(Session session) throws Exception {
    DatabaseConfigurationBean dbConfig = new DatabaseConfigurationBean();

    Map<String, Object> dataSourceProperties = session.getSessionFactory().getProperties();
    dbConfig.setSchema("test");
    dbConfig.setJdbcUrl((String) dataSourceProperties.get("hibernate.connection.url"));
    dbConfig.setJdbcDriver(com.mysql.cj.jdbc.Driver.class.getCanonicalName());

    BatchSPIManager.getInstance().registerPlatformMode(BatchSPIManager.PlatformMode.SE);
    BatchSPIManager.getInstance().registerDatabaseConfigurationBean(dbConfig);
    BatchSPIManager.getInstance().registerExecutorServiceProvider(Executors::newCachedThreadPool);
  }
}
