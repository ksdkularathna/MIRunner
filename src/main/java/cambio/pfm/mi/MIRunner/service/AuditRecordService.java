package cambio.pfm.mi.MIRunner.service;

import cambio.pfm.mi.MIRunner.service.dao.AuditMessageServiceDao;
import cambio.pfm.mi.core.data.AuditData;
import cambio.pfm.service.audit.AuditService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * class is used as a service for the implementation of scheduled audit message reading from the
 * database and processing it to the MI
 *
 * @author SampathD
 * @created 28/08/2018
 */
public class AuditRecordService
{
  private static AuditRecordService service;

  private AuditMessageServiceDao dao;

  private int lastAuditIdProcessed = 0;

  private AuditRecordService()
  {
    dao = AuditMessageServiceDao.getDao();
  }

  public static AuditRecordService getService()
  {
    if (service == null)
    {
      service = new AuditRecordService();
    }
    return service;
  }

  public void processAuditRecords() throws Exception
  {
    try
    {
      int lastAuditIdAvailable = dao.getMaximumAuditIdInAuditTable();

      if (lastAuditIdAvailable > lastAuditIdProcessed)
      {
        System.out.println("Audit extract started on " + new Date());
        List<AuditData> newAuditMessages = dao.getNewAuditData(lastAuditIdProcessed);
        for (AuditData auditData : newAuditMessages)
        {
          AuditService.getInstance().auditRecord(auditData);
          lastAuditIdProcessed = auditData.getAuditId();
        }
        System.out.println("Audit extract ended on " + new Date());
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new Exception("Audit message handling failed");
    }
  }
}
