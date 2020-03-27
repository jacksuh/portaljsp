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
            
		<title>Cadastro de Usuário</title>
	</head>
<body>
	<!-- Tela de Manipulação da Tabela usuario do BD -->
	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Usuário</h1>
		<h3 style="color:orange">${msg}</h3>
	</center>
	<form action="salvarUsuario" method="post" id="formUser" onsubmit="return validarCampos()? true : false;" enctype="multpart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>ID:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}" class="field-long" /></td>
						<td>CEP:</td>
						<td><input type="text" id="cep" name="cep" value="" onblur="consultarCep()" value="${user.cep}" placeholder="Digite o CEP Valido" /></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${user.login}" /></td>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${user.rua}" /></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${user.senha}" /></td>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${user.bairro}" /></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${user.nome}" placeholder="Digite o nome completo"/></td>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${user.cidade}"/></td>
					</tr>
					<tr>
						<td>Telefone:</td>
						<td><input type="text" id="telefone" name="telefone" value="${user.telefone}" placeholder="Digite o Telefone" /></td>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado" value="${user.estado}" /></td>
					</tr>
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" value="${user.ibge}" /></td>
					</tr>
					
					<tr>
						<td>
						Foto:
						</td>
						<td><input type="file" name="foto" value="Foto"></td>
					</tr>
					
					
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" />  <input type="submit" value="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'" /></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Usuários Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>Rua</th>
				<th>Bairro</th>
				<th>Cidade</th>
				<th>Estado</th>
				<th>IBGE</th>
				<th>Delete</th>
				<th>Update</th>
				<th>Telefones</th>
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td><c:out value="${user.id}" /></td>
					<td><c:out value="${user.login}" /></td>
					<td><c:out value="${user.nome}" /></td>
					<td><c:out value="${user.telefone}" /></td>
					<td><c:out value="${user.rua}" /></td>
					<td><c:out value="${user.bairro}" /></td>
					<td><c:out value="${user.cidade}" /></td>
					<td><c:out value="${user.estado}" /></td>
					<td><c:out value="${user.ibge}" /></td>
					<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/icon.png" alt="Excluir" title="Excluir" width="32px" height="32px" /></a></td>
					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img src="resources/img/editar.png" alt="Editar" title="Editar" width="32px" height="32px" /></a></td>
					<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img src="resources/img/telefone.png" alt="Telefones" title="Telefones" width="32px" height="32px" /></a></td>
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
                    alert("CEP não encontrado.");
                }
            });
		}
	</script>
</body>
</html>