package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;


@WebServlet("/servletPesquisa")
public class servletPesquisa extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	private DaoUsuario daoUsuario = DaoUsuario();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String descricaoPesquisa = request.getParameter("descricaoconsulta");
		if(descricaoPesquisa != null && !descricaoPesquisa.trim().isEmpty()){
			try {
				List<BeanCursoJsp> listaPesquisa = daoUsuario.listar(descricaoPesquisa);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", listaPesquisa);
				view.forward(request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}


	private DaoUsuario DaoUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

}
