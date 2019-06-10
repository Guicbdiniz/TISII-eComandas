package service;

import java.util.List;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.ProdutoDAO;
import general.Produto;

public class ProdutoService {

  ProdutoDAO produtosDao = new ProdutoDAO();

  public JsonObject adiciona(Request request, Produto produto) {
    Query query = request.getQuery();
    JsonObject obj = new JsonObject();

    try {
      produto.setNome(query.get("nome"));
      produto.setDescricao(query.get("descricao"));
      produto.setDisponivel(query.getBoolean("disponivel"));
      produto.setCodigo(query.getInteger("codigo"));
      produto.setPreco(Double.parseDouble(query.get("preco")));
      produto.setCategoria(query.get("categoria"));
      produtosDao.add(produto);
      obj.addProperty("status", 1);
      obj.addProperty("message", "Produto adicionado com sucesso");

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
      obj.addProperty("type", e.getClass().toGenericString());
    }

    return obj;
  }

  public JsonObject remover(Request request, Produto produto) {
    Query query = request.getQuery();
    JsonObject obj = new JsonObject();

    try {
      produto = produtosDao.get(query.get("nome"));
      produtosDao.delete(produto);

      @SuppressWarnings("unused")
      Produto aux;

      if ((aux = produtosDao.get(query.get("nome"))) != null)
        throw new Exception();
      else {
        obj.addProperty("status", 1);
        obj.addProperty("message", "Produto removido com sucesso!");
      }

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
    }


    return obj;
  }

  public JsonObject alterar(Request request, Produto produto) {
    Query query = request.getQuery();
    JsonObject obj = new JsonObject();

    try {
      produto.setNome(query.get("nome"));
      produto.setDescricao(query.get("descricao"));
      produto.setDisponivel(true);
      produto.setCodigo(query.getInteger("codigo"));
      produto.setPreco(query.getFloat("preco"));
      produto.setCategoria(query.get("categoria"));

      produtosDao.update(produto);

      Produto aux = produtosDao.get(query.get("nome"));

      if (aux.getDescricao().equals(produto.getDescricao())) {
        obj.addProperty("status", 1);
        obj.addProperty("message", "Produto alterado com sucesso!");
      } else
        throw new Exception();

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stacktrace", e.getStackTrace().toString());
    }

    return obj;
  }

  public JsonObject imprimir(Request request, Produto produto) {
    JsonObject obj = new JsonObject();

    try {

      List<Produto> produtos = produtosDao.getAll();
      if (produtos.size() == 0) {
        obj.addProperty("status", 2);
        obj.addProperty("message", "Não existem produtos em arquivo");
        return obj;
      }

      JsonArray vetorProdutos = new JsonArray();

      for (Produto p : produtos) {
        JsonObject atual = new JsonObject();
        atual.addProperty("nome", p.getNome());
        atual.addProperty("descricao", p.getDescricao());
        atual.addProperty("codigo", p.getCodigo());
        atual.addProperty("preco", p.getPreco());
        atual.addProperty("categoria", p.getCategoria());
        vetorProdutos.add(atual);
      }

      obj.add("produtos", vetorProdutos);
      obj.addProperty("status", 1);
      obj.addProperty("message", "Produtos impressos com sucesso!");

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stacktrace", e.getStackTrace().toString());
    }

    return obj;
  }
}
