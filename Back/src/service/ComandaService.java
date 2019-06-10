package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.ComandaDAO;
import dao.PedidoDAO;
import filtragemDeDados.ComandasStream;
import filtragemDeDados.PedidosStream;
import general.Comanda;
import general.Pedido;
import general.Produto;
import others.NomeDosArquivos;

public class ComandaService {
  ComandaDAO comandaDao = new ComandaDAO();
  PedidoDAO pedidoDao = new PedidoDAO();

  public JsonObject abreComanda(Request request, Comanda comanda) {
    JsonObject obj = new JsonObject();
    Query query = request.getQuery();

    try {
      comanda.setMesa(query.getInteger("mesa"));
      comanda.setSolicitantes(new ArrayList<String>());
      comanda.setPedidos(new ArrayList<Pedido>());
      comandaDao.add(comanda);
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

  public JsonObject fechaComanda(Request request) {
    JsonObject obj = new JsonObject();
    Query query = request.getQuery();

    try {
      Comanda comanda = comandaDao.get(query.getInteger("mesa"));
      comanda.setFechada(true);
      comandaDao.update(comanda);
      if (comandaDao.get(query.getInteger("mesa")).estaFechada())
        obj.addProperty("status", 1);
      else
        throw new Exception("Não foi achada a comanda");
    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
      obj.addProperty("type", e.getClass().toGenericString());
    }

    return obj;
  }

  public JsonObject adicionarSolicitante(Request request) {
    JsonObject obj = new JsonObject();
    Query query = request.getQuery();

    try {
      Comanda comanda = comandaDao.get(query.getInteger("mesa"));
      if (comanda != null) {
        List<String> solicitantes = comanda.getSolicitantes();
        solicitantes.add(query.get("solicitante"));
        comanda.setSolicitantes(solicitantes);
        comandaDao.update(comanda);
        obj.addProperty("status", 1);
      } else {
        obj.addProperty("status", 2);
      }
    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
      obj.addProperty("type", e.getClass().toGenericString());
    }

    return obj;
  }

  public JsonObject pegaComandasFechadas(Request request) {
    JsonObject obj = new JsonObject();

    try {
      JsonArray mesas = new JsonArray();
      List<Comanda> comandasTotais = comandaDao.getAll();
      if (comandasTotais != null) {
        List<Comanda> comandasFechadas = ComandasStream.comandasFechadas(comandasTotais);
        if (comandasFechadas != null) {
          for (Comanda comanda : comandasFechadas) {
            mesas.add(comanda.getMesa());
          }
        }
      }

      obj.add("mesas", mesas);
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

  public JsonObject imprimeComandaFechada(Request request) {
    JsonObject obj = new JsonObject();
    Query query = request.getQuery();
    int codigoComanda = query.getInteger("mesa");
    try {
      Comanda comandaAImprimir = comandaDao.get(codigoComanda);
      comandaDao.delete(comandaAImprimir);

      for (Pedido pedido : comandaAImprimir.getPedidos()) {
        pedidoDao.delete(pedido);
      }

      StringBuilder textoAImprimir = new StringBuilder();

      textoAImprimir.append("Comanda - Mesa " + comandaAImprimir.getMesa() + "\n");
      textoAImprimir
          .append("Total a pagar: R$" + ComandasStream.totalAPagar(comandaAImprimir) + " \n");
      textoAImprimir
          .append("\tNúmero de pedidos: " + comandaAImprimir.getPedidos().size() + "\n\n");
      for (Pedido pedido : comandaAImprimir.getPedidos()) {
        textoAImprimir.append("\tPedido - Número " + pedido.getCodigo() + ":\n");
        textoAImprimir.append("\tSolicitantes:\n");
        for (String solicitante : pedido.getSolicitantes()) {
          textoAImprimir.append("\t\tSolicitante - " + solicitante + "\n");
        }
        textoAImprimir.append("\tProdutos:\n");
        for (Produto produto : pedido.getProdutos()) {
          textoAImprimir.append("\t\tProduto - " + produto.getNome() + "\n");
        }
      }
      textoAImprimir.append("Quanto cada solicitante deve pagar:\n");
      for (String solicitante : comandaAImprimir.getSolicitantes()) {
        textoAImprimir.append("\tSolicitante - " + solicitante + ": R$"
            + PedidosStream.totalAPagar(solicitante, comandaAImprimir.getPedidos()) + "\n");
      }

      obj.addProperty("status", 1);
      obj.addProperty("arquivo", NomeDosArquivos.ARQUIVO_COMANDA_IMPRESSA);

      colocaComandaEmArquivo(textoAImprimir.toString());

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
      obj.addProperty("type", e.getClass().toGenericString());
    }

    return obj;
  }

  private void colocaComandaEmArquivo(String comanda) {
    try (BufferedWriter saida =
        new BufferedWriter(new FileWriter(NomeDosArquivos.ARQUIVO_COMANDA_IMPRESSA))) {

      saida.write(comanda);

    } catch (Exception e) {
      System.out.println("Teve um erro ao tentar escrever a comanda impressa no arquivo");
      e.printStackTrace();
    }
  }

}
