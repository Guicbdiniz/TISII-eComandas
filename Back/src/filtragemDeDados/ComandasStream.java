package filtragemDeDados;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import general.Comanda;
import general.Pedido;

public class ComandasStream {

  public static List<Integer> mesasDasComandasJaAbertas(List<Comanda> comandas) {
    List<Integer> retorno = comandas.stream() //
        .map(comanda -> comanda.getMesa()) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

  public static List<Comanda> comandasFechadas(List<Comanda> comandas) {
    List<Comanda> retorno = comandas.stream() //
        .filter(comanda -> comanda.estaFechada()) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

  public static double totalAPagar(Comanda comanda) {
    double totalAPagar = 0;

    for (Pedido pedido : comanda.getPedidos()) {
      totalAPagar += PedidosStream.totalAPagar(pedido);
    }

    return totalAPagar;
  }

  public static List<String> solicitantesNaoCadastrados(List<String> solicitantesComanda,
      List<String> solicitantesPedido) {
    List<String> retorno = solicitantesPedido.stream() //
        .filter(solicitante -> !(solicitantesComanda.contains(solicitante))) //
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

}
