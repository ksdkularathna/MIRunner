package cambio.pfm.mi.MIRunner;

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
{
  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    MIStartService miStartService = new MIStartService();
    miStartService.startMIProcessors();
    req.setAttribute("result", "MI Started");
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    MIStartService miStartService = new MIStartService();
    miStartService.startMIProcessors();
    req.setAttribute("result", "MI Started");
    RequestDispatcher view = req.getRequestDispatcher("Result.jsp");
    view.forward(req, resp);
  }
}
