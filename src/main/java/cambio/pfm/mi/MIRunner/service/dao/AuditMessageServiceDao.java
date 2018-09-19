package cambio.pfm.mi.MIRunner.service.dao;

import cambio.pfm.mi.core.data.AuditData;
import org.apache.log4j.Logger;
import cambio.pfm.mi.configurationservicemodule.data.enums.DatabaseNames;
import cambio.pfm.mi.configurationservicemodule.dbConnections.DBConnections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class is used for the dao implementations of AuditRecordService
 *
 * @author SampathD
 * @created 28/08/2018
 */
public class AuditMessageServiceDao
{
  private static AuditMessageServiceDao dao;

  private final static Logger logger = Logger.getLogger(AuditMessageServiceDao.class);

  private AuditMessageServiceDao()
  {
  }

  public static AuditMessageServiceDao getDao()
  {
    if (dao == null)
    {
      dao = new AuditMessageServiceDao();
    }
    return dao;
  }

  /**
   * returns all the audit list which the id is greater than the given one
   *
   * @param auditId
   * @return
   */
  public List<AuditData> getNewAuditData(int auditId) throws Exception
  {
    List<AuditData> auditDataList = new ArrayList<AuditData>();

    Connection connection = DBConnections.Factory.getInstance().getConnection(DatabaseNames.OPENWARD);
    if (connection == null)
    {
      logger.error("database connection is not available for " + DatabaseNames.OPENWARD);
      throw new SQLException("database connection is not available for " + DatabaseNames.OPENWARD);
    }

    Statement statement = null;
    try
    {
      statement = connection.createStatement();
      ResultSet results = statement.executeQuery("select * from AUDIT where id>" + auditId);
      while (results.next())
      {
        AuditData auditData = new AuditData();
        auditData.setAuditId(results.getInt(1));
        auditData.setAuditTime(results.getTimestamp(2).toString());
        auditData.setUsername(results.getString(3));
        auditData.setEntityId(results.getString(4));
        auditData.setService(results.getString(5));
        auditData.setAction(results.getString(6));
        auditData.setResponse(results.getString(7));
        auditData.setMessage(results.getString(8));
        auditDataList.add(auditData);
      }
      results.close();
      statement.close();
    }
    catch (SQLException sqlExcept)
    {
      sqlExcept.printStackTrace();
    }
    return auditDataList;
  }

  /**
   * returns the maximm audit id in audit table
   *
   * @return
   */
  public int getMaximumAuditIdInAuditTable() throws Exception
  {
    Connection connection = DBConnections.Factory.getInstance().getConnection(DatabaseNames.AUDIT);
    if (connection == null)
    {
      logger.error("database connection is not available for " + DatabaseNames.AUDIT);
      throw new SQLException("database connection is not available");
    }

    PreparedStatement auditSelectStmt = connection
        .prepareStatement("select max(id) from audit for read only with ur", ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY);
    ResultSet auditRs = auditSelectStmt.executeQuery();

    if (auditRs.next())
    {
      String intString = auditRs.getString(1);

      if (intString != null)
      {
        return Integer.parseInt(intString);
      }
    }
    return 0;
  }
}
