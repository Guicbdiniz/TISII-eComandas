package filtragemDeDados;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import general.Produto;

public class ProdutosStream {

  public static List<Produto> bebidas(List<Produto> produtos) {
    List<Produto> retorno = produtos.stream() //
        .filter(produto -> produto.getCategoria().equals("Bebida")) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

  public static List<Produto> sobremesas(List<Produto> produtos) {
    List<Produto> retorno = produtos.stream() //
        .filter(produto -> produto.getCategoria().equals("Sobremesa")) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

  public static List<Produto> outros(List<Produto> produtos) {
    List<Produto> retorno = produtos.stream() //
        .filter(produto -> produto.getCategoria().equals("Outros")) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

  public static double valorTotal(List<Produto> produtos) {
    double retorno = produtos.stream() //
        .mapToDouble(produto -> produto.getPreco()) //
        .reduce(0, (x, y) -> x + y);
    return retorno;
  }

  public static Produto produtoDeId(List<Produto> produtos, int id) {
    List<Produto> produtosFiltrados = produtos.stream() //
        .filter(produto -> produto.getCodigo() == id) //
        .collect(Collectors.toCollection(ArrayList::new));
    if (produtosFiltrados.size() != 1) {
      return null;
    } else
      return produtosFiltrados.get(0);
  }
}
