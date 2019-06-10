package service;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import com.google.gson.JsonObject;
import dao.ComandaDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import filtragemDeDados.ComandasStream;
import filtragemDeDados.ProdutosStream;
import general.Comanda;
import general.Pedido;
import general.Produto;

public class PedidoService {
  PedidoDAO pedidosDao = new PedidoDAO();
  ProdutoDAO produtosDao = new ProdutoDAO();
  ComandaDAO comandasDao = new ComandaDAO();

  public JsonObject abrePedido(Request request, Pedido pedido) {
    JsonObject obj = new JsonObject();
    Query query = request.getQuery();

    try {
      int mesa = query.getInteger("mesa");
      String produtos = query.get("produtos");
      String solicitantes = query.get("pessoas");

      String[] produtosSeparados = produtos.split("/");
      List<Produto> produtosList = new ArrayList<Produto>();

      for (String str : produtosSeparados) {
        if (!str.equals("")) {
          int id = Integer.parseInt(str);
          Produto atual = ProdutosStream.produtoDeId(produtosDao.getAll(), id);
          if (atual == null) {
            obj.addProperty("status", 3);
            return obj;
          } else {
            produtosList.add(atual);
          }
        }
      }

      String[] solicitantesSeparados = solicitantes.split("/");
      List<String> solicitantesList = new ArrayList<String>();

      List<String> solicitantesCadastrados = comandasDao.get(mesa).getSolicitantes();

      for (String solicitante : solicitantesSeparados) {
        if (!solicitante.equals(""))
          solicitantesList.add(solicitante);
      }

      List<String> solicitantesNovos =
          ComandasStream.solicitantesNaoCadastrados(solicitantesCadastrados, solicitantesList);


      pedido.setProdutos(produtosList);
      pedido.setSolicitantes(solicitantesList);

      pedidosDao.add(pedido);

      Comanda mudada = comandasDao.get(mesa);
      if (mudada == null) {
        obj.addProperty("status", 2);
        return obj;
      } else {
        List<Pedido> pedidosTotais = mudada.getPedidos();
        pedidosTotais.add(pedido);
        mudada.setPedidos(pedidosTotais);
        comandasDao.update(mudada);
      }

      for (String solicitante : solicitantesNovos) {
        List<String> novosSolicitantes = mudada.getSolicitantes();
        novosSolicitantes.add(solicitante);
        mudada.setSolicitantes(novosSolicitantes);
      }

      comandasDao.update(mudada);

      obj.addProperty("status", 1);

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
      obj.addProperty("type", e.getClass().toGenericString());
    }

    return obj;
  }
}
