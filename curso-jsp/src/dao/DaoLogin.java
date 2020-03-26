package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.SingleConnection;

/*
 * Classe DaoLogin
 * Responsável Pela Autenticação do Usuário
 */
public class DaoLogin {

	/*
	 * Objeto connection da Classe SingleConnection
	 * Como Atributo de Classe
	 */
	private Connection connection;
	
	/*
	 * Construtor DaoLogin()
	 * Recebe o Objeto connection da Classe SingleConnection
	 */
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}
	
	/*
	 * Método validarLogin()
	 * Responsável Por Fazer a Verificação de Autenticação no Sistema
	 * @param String login = Login do usuario
	 * @param String senha = Senha do usuario
	 */
	public boolean validarLogin(String login, String senha) throws Exception {
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return true;
			} else {
				return false;
			}
	}
}
