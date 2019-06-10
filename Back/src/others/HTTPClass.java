package others;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;
import com.google.gson.JsonObject;
import general.Comanda;
import general.Conta;
import general.Pedido;
import general.Produto;
import service.ComandaService;
import service.ContaService;
import service.PedidoService;
import service.ProdutoService;


public class HTTPClass implements Container {

  private static Conta conta = new Conta();
  private static ContaService contaServ = new ContaService();
  private static ProdutoService proServ = new ProdutoService();
  private static ComandaService comServ = new ComandaService();
  private static PedidoService pedServ = new PedidoService();


  @Override
  public void handle(Request request, Response response) {
    try {
      // Recupera a URL e o método utilizado.

      String path = request.getPath().getPath();

      if (path.startsWith("/verificarConta")) {
        JsonObject obj = contaServ.validaConta(request, conta);
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/adicionaProduto")) {
        JsonObject obj = proServ.adiciona(request, new Produto());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/criarConta")) {
        JsonObject obj = contaServ.criarCota(request, new Conta());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/excluirProduto")) {
        JsonObject obj = proServ.remover(request, new Produto());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/alterarProduto")) {
        JsonObject obj = proServ.alterar(request, new Produto());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/imprimeTudo")) {
        JsonObject obj = proServ.imprimir(request, new Produto());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/abreComanda")) {
        JsonObject obj = comServ.abreComanda(request, new Comanda());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/fechaComanda")) {
        JsonObject obj = comServ.fechaComanda(request);
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/adicionarPedido")) {
        JsonObject obj = pedServ.abrePedido(request, new Pedido());
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/adicionarSolicitante")) {
        JsonObject obj = comServ.adicionarSolicitante(request);
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/pegaComandasFechadas")) {
        JsonObject obj = comServ.pegaComandasFechadas(request);
        this.enviaResposta(Status.CREATED, response, obj.toString());
      } else if (path.startsWith("/imprimeComanda")) {
        JsonObject obj = comServ.imprimeComandaFechada(request);
        this.enviaResposta(Status.CREATED, response, obj.toString());
      }


    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void enviaResposta(Status status, Response response, String str) throws Exception {


    PrintStream body = response.getPrintStream();
    long time = System.currentTimeMillis();

    response.setValue("Content-Type", "application/json");
    response.setValue("Server", "EComandads (1.0)");
    response.setValue("Access-Control-Allow-Origin", "null");
    response.setDate("Date", time);
    response.setDate("Last-Modified", time);
    response.setStatus(status);

    if (str != null)
      body.println(str);
    body.close();
  }

  public static void main(String[] args) throws IOException {

    int porta = 7200;

    // Configura uma conexão soquete para o servidor HTTP.
    Container container = new HTTPClass();
    ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
    Connection conexao = new SocketConnection(servidor);
    SocketAddress endereco = new InetSocketAddress(porta);
    conexao.connect(endereco);

    System.out.println("Tecle ENTER para interromper o servidor...");
    System.in.read();

    conexao.close();
    servidor.stop();

  }

}
