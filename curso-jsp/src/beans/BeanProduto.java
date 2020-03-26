package beans;

/*
 * Classe BeanProduto
 * Classe Que Provê o Modelo de Objeto
 */
public class BeanProduto {

	private Long id;
	private String nome;
	private double quantidade;
	private double valor;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getQuantidade() {
		return this.quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getValor() {
		return this.valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}
