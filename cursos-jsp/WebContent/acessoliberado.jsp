<jsp:useBean id="calcula" class="beans.BeanCursoJsp" type="beans.BeanCursoJsp" scope="page" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<!-- Tela de Boas Vindas ao Sistema -->
	<!-- Link de Acesso Para Painel de Controle de Usuários -->
	
	<center style="padding-top: 10%;">
	<jsp:setProperty property="*" name="calcula"/>
	<h1>Bem vindo ao Sistema!</h1>
	
	<table>
		<tr>
		<td><a href="salvarUsuario?acao=listartodos"><img src="resources/img/user.png" width="150px" height="120px" /></a></td>
		<td><a href="salvarProduto?acao=listartodos"><img src="resources/img/produto.png" /></a></td>
		</tr>
		<tr>
		<td>Ca. Usuario</td>
		<td>Ca. Produto</td>
		</tr>
	</table>

	</center>
</body>
</html>