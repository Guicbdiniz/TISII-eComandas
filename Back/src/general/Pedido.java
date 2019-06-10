package general;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pedido {

	private Random gerador = new Random();
	private int codigo;
	private List<String> solicitantes;
	private List<Produto> produtos;
	
	private static int pedidosDiarios = 0;
	
	public Pedido() {
		codigo = (++pedidosDiarios) + gerador.nextInt(100);
		solicitantes = new ArrayList<String>();
		produtos = new ArrayList<Produto>();
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public void setSolicitantes(List<String> solicitantes) {
		this.solicitantes = solicitantes;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public List<String> getSolicitantes(){
		return this.solicitantes;
	}
	
	public List<Produto> getProdutos(){
		return this.produtos;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder();
		
		retorno.append("\n\tCódigo: " + this.codigo + "\n\n");
		
		retorno.append("\tSolicitantes:\n");
		for(String str : this.solicitantes) {
			retorno.append("\t\t" + str + "\n");
		}
		
		retorno.append("\n\tProdutos:\n");
		for(Produto prod : this.produtos) {
			retorno.append("\t\t" + prod.getNome() + "\n");
		}
		
		return retorno.toString();
	}
	
	public void ratearPedido(String[] solicitantes) {
		this.solicitantes.clear();
		for(int indice = 0; indice < solicitantes.length; indice++)
			this.solicitantes.add(solicitantes[indice]);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Pedido) && (((Pedido)obj).getCodigo() == this.codigo);
	}
}
