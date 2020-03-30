package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

import beans.BeanCursoJsp;
import dao.DaoUsuario;
import sun.nio.cs.ext.ISCII91;

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
				} else if (acao.equalsIgnoreCase("download")){
					BeanCursoJsp usuario = daoUsuario.consultar(user);
					if (usuario != null){
						String contentType = "";
						byte[] fileBytes = null;
						String tipo = request.getParameter("tipo");
						
						if(tipo.equalsIgnoreCase("imagem")){
							contentType = usuario.getContentType();
							fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
						
						}else if (tipo.equalsIgnoreCase("curriculo")){
							contentType = usuario.getContentTypeCurriculo();
							fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
						}
						
						response.setHeader("Content-Disposition", "attachment;filename=arquivo."
					   + contentType.split("\\/")[1]);
						
						
						/*Coloca os bytes em um objeto de entrada para processar*/
						InputStream is = new ByteArrayInputStream(fileBytes);
						
						/*inicio da resposta para o navegador*/
						int read= 0;
						byte[] bytes = new byte[1024];
						OutputStream os = response.getOutputStream();
						
						
						while ((read = is.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}
						
						os.flush();
						os.close();
						
					}
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
				usuario.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
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
					
					/*Inicio File upload de imagems e pdf*/
					
					if (ServletFileUpload.isMultipartContent(request)){

						Part imagemFoto = request.getPart("foto");
						
						String fotoBase64 = new Base64()
						.encodeBase64String(converteStremParabyte(imagemFoto.getInputStream()));
						
						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						
					}else{
						usuario.setFotoBase64(request.getParameter("fotoTemp"));
						usuario.setContentType(request.getParameter("contetTypeTemp"));
					}
					
						Part curriculoPdf = request.getPart("curriculo");
						if(curriculoPdf != null){
							String curriculoBase64 = new Base64().
									encodeBase64String(converteStremParabyte(curriculoPdf.getInputStream()));
							
							usuario.setCurriculoBase64(curriculoBase64);
							usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
							
						}else{
							usuario.setFotoBase64(request.getParameter("fotoTempPDF"));
							usuario.setContentType(request.getParameter("contetTypeTempPDF"));
						}
					
					/*FIM File upload de imagems e pdf*/
					
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
							request.setAttribute("msg", "Este Login Pertence a Um Usuário!");
							podeInserir = false;
						} else if(id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
							request.setAttribute("msg", "Esta Senha Pertence a Um Usuário!");
							podeInserir = false;
						}
						
						if(msg != null) {
							request.setAttribute("msg", msg);
						} else if(id == null || id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha) && podeInserir) {
							daoUsuario.salvar(usuario);
						}
						
						if(id != null && !id.isEmpty() && podeInserir) {
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
	
	/*Converte a entrada de fluxo de dados da imagem para byte[]*/
	private byte[] converteStremParabyte(InputStream imagem) throws Exception{
		
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 int reads = imagem.read();
	 while (reads != -1){
		 baos.write(reads);
		 reads = imagem.read();
	 }
	 
	 return baos.toByteArray();
	
	}

}