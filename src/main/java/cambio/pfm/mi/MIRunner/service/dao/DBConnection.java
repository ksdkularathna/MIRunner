package cambio.pfm.mi.MIRunner.service.dao;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * class is used for the implementations of getting the database connections from the tomcat context file
 *
 * @author SampathD
 * @created 28/08/2018
 */
public class DBConnection
{
  public static void main(String[] args)
  {
    DBConnection connection = new DBConnection();
    try
    {
      java.sql.Connection connection1 = connection.getConnection("audit");
      System.out.println("End" +connection1.getSchema());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public java.sql.Connection getConnection(String dbName) throws Exception
  {
    java.sql.Connection connection = null;

    Context initCtx = null;
    Context envCtx = null;
    DataSource dataSource = null;

    initCtx = getInitialContext();
    envCtx = getEnvironmentContext(initCtx);
    dataSource = getDataSource(envCtx, dbName);

    if (dataSource != null)
    {
      try
      {
        connection = dataSource.getConnection();
      }
      catch (SQLException e)
      {
        System.out.println("Connection Exception : " + e.getMessage());
        throw new Exception(e);
      }
    }
    return connection;
  }

  /**
   * returns the initial context of the tomcat server
   *
   * @return
   * @throws Exception
   */
  private Context getInitialContext() throws Exception
  {
    Context initCtx = null;
    try
    {
      initCtx = new InitialContext();
    }
    catch (NamingException e)
    {
      System.out.println("Initial Context Exception : " + e.getMessage());
      throw new Exception(e);
    }
    return initCtx;
  }

  /**
   * get environment context
   *
   * @param initCtx
   * @return
   */
  private Context getEnvironmentContext(Context initCtx) throws Exception
  {
    Context envCtx = null;
    try
    {
      envCtx = (Context) initCtx.lookup("java:comp/env");
    }
    catch (NamingException e)
    {
      System.out.println("Environment Context Exception : " + e.getMessage());
      throw new Exception(e);
    }
    return envCtx;
  }

  /**
   * returns the data source from the environment context
   *
   * @param envCtx
   * @param dbName
   * @return
   */
  private DataSource getDataSource(Context envCtx, String dbName) throws Exception
  {
    DataSource dataSource;
    try
    {
      dataSource = (DataSource) envCtx.lookup("jdbc/" + dbName);
    }
    catch (NamingException e)
    {
      System.out.println("Data Source Exception : " + e.getMessage());
      throw new Exception(e);
    }
    return dataSource;
  }
}
