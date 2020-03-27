package servlet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
       
	/*
	 * Construtor Usuario()
	 * Recebe o Construtor da Classe HttpServlet
	 */
    public Usuario() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
				if(acao.equalsIgnoreCase("delete") && user != null) {
					daoUsuario.delete(user);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} else if(acao.equalsIgnoreCase("editar")) {
					BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", beanCursoJsp);
					view.forward(request, response);
				} else if(acao.equalsIgnoreCase("listartodos")) {
					daoUsuario.delete(user);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
			if(acao != null && acao.equalsIgnoreCase("reset")) {
				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				String id = request.getParameter("id");
				String login = request.getParameter("login");
				String senha = request.getParameter("senha");
				String nome = request.getParameter("nome");
				String telefone = request.getParameter("telefone");
				String cep = request.getParameter("cep");
				String rua = request.getParameter("rua");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String ibge = request.getParameter("ibge");
		
				BeanCursoJsp usuario = new BeanCursoJsp();
				usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				usuario.setLogin(login);
				usuario.setSenha(senha);
				usuario.setNome(nome);
				usuario.setTelefone(telefone);
				usuario.setCep(cep);
				usuario.setRua(rua);
				usuario.setBairro(bairro);
				usuario.setCidade(cidade);
				usuario.setEstado(estado);
				usuario.setIbge(ibge);
				
				
					try {
						
					/*Inicio file de imagem e pdf upload*/
						
						if(ServletFileUpload.isMultipartContent(request)){
						
						Part imagemFoto = request.getPart("foto");
						
						String fotobase64 = new Base64().
								encodeBase64String(converteStremParabyte(imagemFoto.getInputStream()));
						
						usuario.setFotoBase64(fotoBase64)
						usuario.setContentType(imagemFoto.getContentType());
						
						}
						
						
						/*FIM file de imagem e pdf upload*/
						
						String msg = null;
						boolean podeInserir = true;
						
						if(login == null || login.isEmpty()) {
							msg = "Login Deve Ser Informado!";
							podeInserir = false;
						} else if(senha == null || senha.isEmpty()) {
							msg = "Senha Deve Ser Informada!";
							podeInserir = false;
						} else if(nome == null || nome.isEmpty()) {
							msg = "Nome Deve Ser Informado!";
							podeInserir = false;
						} else if(telefone == null || telefone.isEmpty()) {
							msg = "Telefone Deve Ser Informado!";
							podeInserir = false;
						} else if(id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
							request.setAttribute("msg", "Este Login Pertence a Um Usu�rio!");
							podeInserir = false;
						} else if(id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
							request.setAttribute("msg", "Esta Senha Pertence a Um Usu�rio!");
							podeInserir = false;
						}
						
						if(msg != null) {
							request.setAttribute("msg", msg);
						} else if(id == null || id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha) && podeInserir) {
							daoUsuario.salvar(usuario);
						}
						
						if(id != null || !id.isEmpty() && podeInserir) {
							daoUsuario.atualizar(usuario);
						}
						
						if(!podeInserir) {
							request.setAttribute("user", usuario);
						}
						
						RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
						request.setAttribute("usuarios", daoUsuario.listar());
						request.setAttribute("msg", "Salvo Com Sucesso!");
						view.forward(request, response);
					} catch(Exception e) {
						e.printStackTrace();
					}
			}
	}

	/* CONVERTE A ENTRA DE FLUXO DE IMAGEM PARA BYTE[]*/
	private byte[] converteStremParabyte(InputStream imagem) throws Exception {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (read != -1 ){
			baos.write(reads);
			reads = imagem.read();
		}
		
		return baos.toByteArray();
	}
}