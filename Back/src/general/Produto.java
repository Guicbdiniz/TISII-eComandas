package general;

public class Produto {

  private boolean disponivel;
  private String nome;
  private String descricao;
  private double preco;
  private int codigo;
  private String categoria;

  public Produto() {}

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double preco) {
    this.preco = preco;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public boolean isDisponivel() {
    return disponivel;
  }

  public String getNome() {
    return nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Produto) && (this.nome.equals(((Produto) obj).getNome()));
  }
}
