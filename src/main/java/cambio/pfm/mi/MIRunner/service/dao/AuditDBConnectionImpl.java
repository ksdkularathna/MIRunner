package cambio.pfm.mi.MIRunner.service.dao;

import uk.cambio.pfm.configurationservice.dbConnections.AuditDBConnection;
import uk.cambio.pfm.configurationservice.dbConnections.DBConnectionsParameters;
import uk.cambio.pfm.configurationservice.util.ResourceStringService;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * class is used to prepare a connection to the audit database
 *
 * @author SampathD
 * @created 16/07/2018
 */
public class AuditDBConnectionImpl implements AuditDBConnection, DBConnectionsParameters
{
  private static AuditDBConnectionImpl instance;

  private AuditDBConnectionImpl()
  {
  }

  public static AuditDBConnectionImpl getInstance()
  {
    if (instance == null)
    {
      instance = new AuditDBConnectionImpl();
    }
    return instance;
  }

  private Connection connection = null;

  public Connection getConnection()
  {
    if (connection == null)
    {
      try
      {
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        connection = DriverManager
            .getConnection(ResourceStringService.getInstance().getResourceString("openward.db.url"),
                ResourceStringService.getInstance().getResourceString("db.username"),
                ResourceStringService.getInstance().getResourceString("db.password"));
      }
      catch (Exception except)
      {
        except.printStackTrace();
      }
    }
    return connection;
  }
}
