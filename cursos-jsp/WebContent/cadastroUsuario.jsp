<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="resources/css/cadastro.css" />
		<script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
		<title>Cadastro de Usu�rio</title>
	</head>
<body>
	<!-- Tela de Manipula��o da Tabela usuario do BD -->
	<a href="acessoliberado.jsp">In�cio</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Usu�rio</h1>
		<h3 style="color:orange">${msg}</h3>
	</center>
	<form action="salvarUsuario" method="post" id="formUser" 
	   onsubmit="return validarCampos()? true : false;" enctype="multipart/form-data" >
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>ID:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}" /></td>
						<td>CEP:</td>
						<td><input type="text" id="cep" name="cep" value="" onblur="consultarCep()" value="${user.cep}" maxlength="10" /></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${user.login}" maxlength="10"/></td>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${user.rua}" maxlength="50" /></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${user.senha}" maxlength="10" /></td>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${user.bairro}" maxlength="50" /></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${user.nome}" maxlength="50" placeholder ="Informe o nome"/></td>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${user.cidade}" maxlength="50" /></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado" value="${user.estado}" /></td>
					</tr>
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${user.ibge}" maxlength="20"/></td>
					</tr>

					<tr>
						<td>
						   Foto:	
						</td>
						<td><input type="file" name="foto">
						<input type="text" style="display: none;" name="fotoTemp"   readonly="readonly"   value="${user.fotoBase64}"/>
						<input type="text" style="display: none;" name="contetTypeTemp" readonly="readonly" value="${user.contentType}"/>
					 </td>	
					</tr>
					
					<td>
						   Curriculo:	
						</td>
						<td><input type="file" name="curriculo" value="curriculo" > </td>
						<input type="text" style="display: none;" name="fotoTempPDF"  readonly="readonly"   value="${user.curriculoBase64}"/>
						<input type="text" style="display: none;" name="contetTypeTempPDF"  readonly="readonly" value="${user.contentTypeCurriculo}"/>
					</tr>
					
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 173px"/></td>  
						<td></td>
						<td>
						<input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'" style="width: 173px" />
						</td>
					</tr>
					
				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Usu�rios Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Foto</th>
				<th>Curriculo</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>Delete</th>
				<th>Editar</th>

			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td><c:out value="${user.id}" /></td>
					
					<c:if test="${user.fotoBase64.isEmpty() == false }">
					<td><a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img src='<c:out value="${user.tempFotoUser}"/>' alt="Imagem User" title="Imagem User" width="32px" height="32px" /> </a></td>
					</c:if>
					<c:if test="${user.fotoBase64.isEmpty() == true }">
						<td><img alt="Imagem user" src="resources/img/userpadrao.png" width="32px" height="32px" onclick="alert('N�o possui imagem')" ></td>
					</c:if>
					
					<c:if test="${user.curriculoBase64.isEmpty() == false }">
					<td><a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img alt="Curriculo" src="resources/img/pdf.png" width="32px" height="32px"></a></td>
					</c:if>
					<c:if test="${user.curriculoBase64.isEmpty() == true }">
					<td><img alt="Curriculo" src="resources/img/pdf.png" width="32px" height="32px" onclick="alert('N�o possui o PDF')"></td>
					</c:if>
					
					<td><c:out value="${user.nome}" /></td>
					<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img src="resources/img/telefone.png" alt="Telefones" title="Telefones" width="32px" height="32px" /></a></td>
					<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/icon.png" alt="Excluir" title="Excluir" width="32px" height="32px" /></a></td>
					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img src="resources/img/editar.png" alt="Editar" title="Editar" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if(document.getElementById("login").value == '') {
				alert("Informe o Login!");
				return false;
			} else if(document.getElementById("senha").value == '') {
				alert("Informe a Senha!");
				return false;
			} else if(document.getElementById("nome").value == '') {
				alert("Informe o Nome!");
				return false;
			} else if(document.getElementById("telefone").value == '') {
				alert("Informe o Telefone!");
				return false;
			}
			return true;
		}
		
		function consultarCep() {
			var cep = $("#cep").val();
			
			$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                if (!("erro" in dados)) {
                    $("#rua").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#cidade").val(dados.localidade);
                    $("#estado").val(dados.uf);
                    $("#ibge").val(dados.ibge);
                } else {
                	$("#cep").val('');
                	$("#rua").val('');
                    $("#bairro").val('');
                    $("#cidade").val('');
                    $("#estado").val('');
                    $("#ibge").val('');
                    alert("CEP n�o encontrado.");
                }
            });
		}
	</script>
</body>
</html>