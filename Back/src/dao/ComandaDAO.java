package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import general.Comanda;
import general.Pedido;
import others.NomeDosArquivos;

public class ComandaDAO implements DAO<Comanda, Integer> {

  private PedidoDAO pedDAO = new PedidoDAO();
  private String arquivoNome = NomeDosArquivos.ARQUIVO_COMANDAS;

  public ComandaDAO() {}

  @Override
  public Comanda get(Integer chave) {

    Comanda retorno = null;
    Comanda atual = null;
    Integer chaveAtual = null;

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readInt()) != null) {
        atual = new Comanda();
        atual.setMesa(chaveAtual);

        atual.setFechada(entrada.readBoolean());

        int numeroDeSolicitantes = entrada.readInt();

        List<String> solicitantes = new ArrayList<String>();

        for (int indice = 0; indice < numeroDeSolicitantes; indice++) {
          String solicitante = entrada.readUTF();
          solicitantes.add(solicitante);
        }

        atual.setSolicitantes(solicitantes);

        int numeroDePedidos = entrada.readInt();

        List<Pedido> pedidos = new ArrayList<Pedido>();

        for (int indice = 0; indice < numeroDePedidos; indice++) {
          int codigoDoPedido = entrada.readInt();
          pedidos.add(pedDAO.get(codigoDoPedido));
        }

        atual.setPedidos(pedidos);

        if (chaveAtual.equals(new Integer(atual.getMesa()))) {
          retorno = atual;
          break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  @Override
  public void add(Comanda item) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, true))) {

      saida.writeInt(item.getMesa());
      saida.writeBoolean(item.estaFechada());
      saida.writeInt(item.getSolicitantes().size());
      for (String solicitante : item.getSolicitantes()) {
        saida.writeUTF(solicitante);
      }
      saida.writeInt(item.getPedidos().size());
      for (Pedido p : item.getPedidos()) {
        saida.writeInt(p.getCodigo());
      }


    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  @Override
  public void update(Comanda item) {
    List<Comanda> todas = this.getAll();

    int indiceAtual = todas.indexOf(item);

    if (indiceAtual != -1)
      todas.set(indiceAtual, item);

    this.saveToFile(todas);

  }

  @Override
  public void delete(Comanda item) {
    List<Comanda> todas = this.getAll();

    int indiceAtual = todas.indexOf(item);

    if (indiceAtual != -1)
      todas.remove(indiceAtual);

    this.saveToFile(todas);

  }

  @Override
  public List<Comanda> getAll() {

    List<Comanda> retorno = new ArrayList<Comanda>();
    Comanda atual = null;
    Integer chaveAtual = null;

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readInt()) != null) {
        atual = new Comanda();
        atual.setMesa(chaveAtual);
        atual.setFechada(entrada.readBoolean());

        int numeroDeSolicitantes = entrada.readInt();

        List<String> solicitantes = new ArrayList<String>();

        for (int indice = 0; indice < numeroDeSolicitantes; indice++) {
          String solicitante = entrada.readUTF();
          solicitantes.add(solicitante);
        }

        atual.setSolicitantes(solicitantes);

        int numeroDePedidos = entrada.readInt();

        List<Pedido> pedidos = new ArrayList<Pedido>();

        for (int indice = 0; indice < numeroDePedidos; indice++) {
          int codigoDoPedido = entrada.readInt();
          pedidos.add(pedDAO.get(codigoDoPedido));
        }

        atual.setPedidos(pedidos);
        retorno.add(atual);
      }

    } catch (EOFException e) {

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  private void saveToFile(List<Comanda> comandas) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, false))) {

      for (Comanda com : comandas) {
        saida.writeInt(com.getMesa());
        saida.writeBoolean(com.estaFechada());
        saida.writeInt(com.getSolicitantes().size());
        for (String solicitante : com.getSolicitantes()) {
          saida.writeUTF(solicitante);
        }
        saida.writeInt(com.getPedidos().size());
        for (Pedido ped : com.getPedidos())
          saida.writeInt(ped.getCodigo());
        saida.flush();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
