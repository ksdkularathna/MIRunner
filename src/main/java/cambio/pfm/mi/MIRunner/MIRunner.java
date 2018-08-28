package cambio.pfm.mi.MIRunner;

import cambio.pfm.mi.MIRunner.service.AuditRecordService;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SampathD
 * @created 23/08/2018
 */
public class MIRunner extends TimerTask implements MIRunnerProperties
{
  private static MIRunner runner;

  private ScheduledExecutorService executor;

  private AuditRecordService auditRecordService;

  public static MIRunner getRunner()
  {
    if (runner == null)
    {
      runner = new MIRunner();
    }
    return runner;
  }

  private MIRunner()
  {
    executor = Executors.newSingleThreadScheduledExecutor();
  }

  /**
   * The action to be performed by this timer task.
   */
  public void run()
  {
    System.out.println("Audit extract performed on " + new Date());
    try
    {
      auditRecordService.processAuditRecords();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Audit extract failed at " + new Date());
    }
  }

  /**
   * starts the MI
   */
  public void runMI()
  {
    if (executor != null)
      executor.scheduleAtFixedRate(runner, INITIAL_DELAY, DELAY_BETWEEN_EXECUTIONS, TimeUnit.MILLISECONDS);
  }

  /**
   * stops the MI
   */
  public void stopMI()
  {
    if (executor != null)
      executor.shutdown();
  }

  public static void main(String[] args) throws InterruptedException
  {
    MIRunner runner = new MIRunner();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(runner, INITIAL_DELAY, DELAY_BETWEEN_EXECUTIONS, TimeUnit.MILLISECONDS);
  }

  /**
   * returns whether executor is shutdown or not
   *
   * @return
   */
  public boolean isShutDown()
  {
    return executor.isTerminated();
  }
}
