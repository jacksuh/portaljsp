package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import beans.BeanProduto;
import connection.SingleConnection;

/*
 * Classe DaoProduto
 * Classe Que Provê os Métodos e Validações Para Manipular Dados, e Acesso e Manipulação do BD
 */
public class DaoProduto {

	private Connection connection;
		
		/*
		 * Construtor DaoProduto()
		 * Recebe um Objeto connection da Classe SingleConnection
		 */
		public DaoProduto() {
			connection = SingleConnection.getConnection();
		}
		
		/*
		 * Método salvar()
		 * Responsável Por Fazer a Inserção de Dados (INSERT) no BD
		 * @param BeanProduto produto = Objeto Produto da Classe BeanProduto
		 */
		public void salvar(BeanProduto produto) {
			try {
				String sql = "INSERT INTO produto(nome, quantidade, valor) VALUES(?, ?, ?)";
				PreparedStatement insert = connection.prepareStatement(sql);
				insert.setString(1, produto.getNome());
				insert.setDouble(2, produto.getQuantidade());
				insert.setDouble(3, produto.getValor());
				insert.execute();
				connection.commit();
			} catch(Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		/*
		 * Método listar()
		 * Responsável Por Listar Todos os Produtos do Sistema
		 */
		public List<BeanProduto> listar() throws Exception {
			List<BeanProduto> listar = new ArrayList<BeanProduto>();
			String sql = "SELECT * FROM produto";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					BeanProduto produto = new BeanProduto();
					produto.setId(resultSet.getLong("id"));
					produto.setNome(resultSet.getString("nome"));
					produto.setQuantidade(resultSet.getInt("quantidade"));
					produto.setValor(resultSet.getDouble("valor"));
					listar.add(produto);
				}
				return listar;
		}
		
		/*
		 * Método delete()
		 * Responsável Por Fazer a Exclusão (Delete) no BD
		 * @param String id = Atributo ID do Produto
		 */
		public void delete(String id) {
			try {
				String sql = "DELETE FROM produto WHERE id = '"+ id +"'";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.execute();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		/*
		 * Método consultar()
		 * Responsável Por Fazer Consultas (SELECT) no BD
		 * @param String id = Atributo ID do Produto
		 */
		public BeanProduto consultar(String id) throws Exception {
			String sql = "SELECT * FROM produto WHERE id = '"+ id +"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					BeanProduto produto = new BeanProduto();
					produto.setId(resultSet.getLong("id"));
					produto.setNome(resultSet.getString("nome"));
					produto.setQuantidade(resultSet.getInt("quantidade"));
					produto.setValor(resultSet.getDouble("valor"));
					return produto;
				}
			return null;
		}
		
		/*
		 * Método validarNome()
		 * Responsável Por Validar o Nome (Não Pode Existir 1 Mesmo Nome Para 2 Produtos Diferentes)
		 * @param String nome = Atributo Nome do Produto
		 */
		public boolean validarNome(String nome) throws Exception {
			String sql = "SELECT COUNT(1) as qtde FROM produto WHERE nome = '"+ nome +"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					return resultSet.getInt("qtde") <= 0;
				}
			return false;
		}
		
		/*
		 * Método atualizar()
		 * Método Responsável Por Atualizar os Dados (UPDATE) no BD
		 * @param BeanProduto produto = Objeto Produto da Classe BeanProduto
		 */
		public void atualizar(BeanProduto produto) {
			try {
				String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = "+ produto.getId();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, produto.getNome());
				preparedStatement.setDouble(2, produto.getQuantidade());
				preparedStatement.setDouble(3, produto.getValor());
				preparedStatement.executeUpdate();
				connection.commit();
			} catch(Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
}

