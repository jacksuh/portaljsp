<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="resources/css/cadastro.css" />
		<title>Cadastro de Telefones</title>
	</head>
<body>
	<!-- Tela de Manipulação de Telefones do Usuário -->
	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<center>
		<h1>Cadastro de Telefones</h1>
		<h3 style="color:orange"></h3>
	</center>
	<form action="salvarTelefones" method="post" id="formUser" >
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" id="id" name="id" value="${userEscolhido.id}" readonly="true" /></td>
						<td><input type="text" id="nome" name="nome" value="${userEscolhido.nome}" readonly="true" /></td>
					</tr>
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" value="" /></td>
						<td>
							<select id="tipo" name="tipo">
								<option>Celular</option>
								<option>Casa</option>
								<option>Trabalho</option>
								<option>Recado</option>
								<option>Outros</option>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Salvar" />
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Telefones Cadastrados</caption>
			<tr>
				<th>Id:</th>
				<th>Número:</th>
				<th>Tipo:</th>
				<th>Delete</th>
			</tr>
			<c:forEach items="${telefone}" var="fone">
				<tr>
					<td><c:out value="${fone.id}" /></td>
					<td><c:out value="${fone.numero}" /></td>
					<td><c:out value="${fone.tipo}" /></td>
					<td><a href="salvarTelefones?acao=deleteFone&foneId=${fone.id}"><img src="resources/img/icon.png" alt="Excluir" title="Excluir" width="32px" height="32px" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if(document.getElementById("numero").value == '') {
				alert("Informe o Número!");
				return false;
			} else if(document.getElementById("tipo").value == '') {
				alert("Informe o Tipo!");
			}
			return true;
		}
	</script>
</body>
</html>