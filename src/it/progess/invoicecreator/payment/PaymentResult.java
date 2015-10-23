package it.progess.invoicecreator.payment;

import it.progess.invoicecreator.dao.DraftDao;
import it.progess.invoicecreator.properties.ICParameter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PaymentResult
 */
@WebServlet(description = "return from paypal", urlPatterns = { "/PaymentResult" })
public class PaymentResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		manageResult(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		manageResult(request,response);
	}
	private void manageResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String draft = request.getParameter("did");
		DraftDao d = new DraftDao();
		d.confirmPayment(getServletContext(),request,draft);
		response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ICParameter.SECOND_DOMAIN+"#/ec?po=success");
	}
}
