package cambio.pfm.mi.MIRunner.servlets;

import cambio.pfm.mi.MIRunner.MIRunner;
import cambio.pfm.mi.MIRunner.MIRunnerProperties;
import cambio.pfm.service.mi.MIStartService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SampathD
 * @created 23/08/2018
 */
@WebServlet(name = "InitMI", urlPatterns = "/init") public class InitMI extends HttpServlet
    implements MIRunnerProperties
{

  private MIRunner runner;

  @Override public void init() throws ServletException
  {
    super.init();
    runner = MIRunner.getRunner();
    MIStartService.getMIService().startMIProcessors();
    runner.runMI();
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    //    DBConnection connection = new DBConnection();
    //    try
    //    {
    //      java.sql.Connection connection1 = connection.getConnection("audit");
    //      System.out.println("End" +connection1.getSchema());
    //    }
    //    catch (Exception e)
    //    {
    //      e.printStackTrace();
    //    }
    if (runner != null && !runner.isShutDown())
    {
      req.setAttribute("result", "MI Started");
    }
    else
    {
      req.setAttribute("result", "MI starting failed. Restart tomcat");
    }
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    MIStartService.getMIService().startMIProcessors();
    if (runner != null && !runner.isShutDown())
    {
      req.setAttribute("result", "MI Started");
    }
    else
    {
      req.setAttribute("result", "MI starting failed. Restart tomcat");
    }
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }
}
