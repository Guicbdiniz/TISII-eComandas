package service;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import com.google.gson.JsonObject;
import dao.ContaDAO;
import general.Conta;

public class ContaService {

  ContaDAO contasDao = new ContaDAO();

  public JsonObject validaConta(Request request, Conta conta) {
    Query query = request.getQuery();
    JsonObject obj = new JsonObject();

    try {
      conta = contasDao.get(query.get("user"));
      if (conta != null) {
        if (conta.getSenha().equals(query.get("senha"))) {
          obj.addProperty("status", 1);
          obj.addProperty("permissao", conta.getPermissao());
        } else {
          obj.addProperty("status", 2);
          obj.addProperty("message", "Senha incorreta");
        }
      } else {
        obj.addProperty("status", 3);
        obj.addProperty("message", "Essa conta não consta nos nossos dados.");
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

  public JsonObject criarCota(Request request, Conta conta) {
    Query query = request.getQuery();
    JsonObject obj = new JsonObject();

    try {
      conta.setUser(query.get("user"));
      conta.setSenha(query.get("senha"));
      conta.setPermissao(query.getInteger("permissao"));
      contasDao.add(conta);
      obj.addProperty("status", 1);
      obj.addProperty("message", "Conta adicionada com sucesso!");

    } catch (Exception e) {
      e.printStackTrace();
      obj.addProperty("status", 0);
      obj.addProperty("message", e.getMessage());
      obj.addProperty("stackTrace", e.getStackTrace().toString());
    }

    return obj;


  }

}
