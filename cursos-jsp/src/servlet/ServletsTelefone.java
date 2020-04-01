package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanTelefone;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")

public class ServletsTelefone extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefone daoTelefone = new DaoTelefone();
       
    public ServletsTelefone() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
			
				if(acao.equalsIgnoreCase("addFone")) {
			
					request.getSession().setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("userEscolhido", beanCursoJsp);
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefone", daoTelefone.listar(beanCursoJsp.getId()));
					request.setAttribute("msg", "Salvo Com Sucesso!");
					view.forward(request, response);
				} else if(acao.equalsIgnoreCase("deleteFone")) {
					String foneId = request.getParameter("foneId");
					daoTelefone.delete(foneId);
					
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("telefone", daoTelefone.listar(Long.parseLong(user)));
					request.setAttribute("msg", "Excluído Com Sucesso!");
					view.forward(request, response);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try {
			
			
			
			BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String acao = request.getParameter("acao");
			
			if(acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))){
			
			if(numero == null || (numero) != null && numero.isEmpty()){
				
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefone", daoTelefone.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "Informe o numero!");
				view.forward(request, response);
			}else{
			
			BeanTelefone beanTelefone = new BeanTelefone();
			beanTelefone.setNumero(numero);
			beanTelefone.setTipo(tipo);
			beanTelefone.setUsuario(beanCursoJsp.getId());
			daoTelefone.salvar(beanTelefone);
			request.getSession().setAttribute("userEscolhido", beanCursoJsp);
			request.setAttribute("userEscolhido", beanCursoJsp);
		
			RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
			request.setAttribute("telefone", daoTelefone.listar(beanCursoJsp.getId()));
			request.setAttribute("msg", "Salvo Com Sucesso!");
			view.forward(request, response);
			
			}
			
			}else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}