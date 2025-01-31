package beans;

public class BeanProduto {
	
	private Long id;
	private String nome;
	private double quantidade;
	private double valor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	//tira o valor com ponto e coloca virgula
	public String getValorEmTexto(){
		return Double.toString(valor).replace('.', ',');
	}
	
}
