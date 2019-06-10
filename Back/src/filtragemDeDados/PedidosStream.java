package filtragemDeDados;

import java.util.List;
import general.Pedido;

public class PedidosStream {

  public static double totalAPagar(Pedido pedido) {
    double retorno =
        ProdutosStream.valorTotal(pedido.getProdutos()) / pedido.getSolicitantes().size();
    return retorno;
  }

  public static double totalAPagar(String solicitante, List<Pedido> pedidos) {
    double retorno = pedidos.stream() //
        .filter(pedido -> pedido.getSolicitantes().contains(solicitante)) //
        .mapToDouble(
            pedido -> (PedidosStream.totalAPagar(pedido)) / pedido.getSolicitantes().size()) //
        .reduce(0, (x, y) -> x + y);
    return retorno;
  }

}
