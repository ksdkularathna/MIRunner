package cambio.pfm.mi.MIRunner.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cambio.pfm.mi.MIRunner.MIRunner;
import cambio.pfm.mi.MIRunner.MIRunnerProperties;

import java.io.IOException;

/**
 * @author SampathD
 * @created 23/08/2018
 */
@WebServlet(name = "shutdownMI", urlPatterns = "/shutdown") public class shutdownMI extends HttpServlet
    implements MIRunnerProperties
{

  private MIRunner runner;

  @Override public void init() throws ServletException
  {
    super.init();
    runner = MIRunner.getRunner();
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    if (runner != null)
      runner.stopMI();
    req.setAttribute("result", "MI Stopped");
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    if (runner != null)
      runner.stopMI();
    req.setAttribute("result", "MI Stopped");
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }
}
