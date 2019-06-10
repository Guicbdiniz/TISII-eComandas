package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import general.Pedido;
import general.Produto;
import others.NomeDosArquivos;

public class PedidoDAO implements DAO<Pedido, Integer> {

  private String arquivoNome = NomeDosArquivos.ARQUIVO_PEDIDOS;
  private ProdutoDAO prodDAO = new ProdutoDAO();

  public PedidoDAO() {

  }

  @Override
  public Pedido get(Integer chave) {

    Pedido retorno = null;
    Pedido atual = null;
    Integer chaveAtual = null;

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readInt()) != null) {
        atual = new Pedido();
        atual.setCodigo(chaveAtual);

        int nmrDeProdutos = entrada.readInt();
        List<Produto> produtos = new ArrayList<Produto>();

        for (int indice = 0; indice < nmrDeProdutos; indice++) {
          String produto = entrada.readUTF();
          produtos.add(prodDAO.get(produto));
        }

        atual.setProdutos(produtos);

        int nmrDeSolicitantes = entrada.readInt();
        List<String> solicitantes = new ArrayList<String>();

        for (int indice = 0; indice < nmrDeSolicitantes; indice++) {
          String solicitante = entrada.readUTF();
          solicitantes.add(solicitante);
        }

        atual.setSolicitantes(solicitantes);

        if (chaveAtual.equals(atual.getCodigo())) {
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
  public void add(Pedido atual) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, true))) {

      saida.writeInt(atual.getCodigo());
      saida.writeInt(atual.getProdutos().size());
      for (Produto p : atual.getProdutos()) {
        saida.writeUTF(p.getNome());
      }
      saida.writeInt(atual.getSolicitantes().size());
      for (String str : atual.getSolicitantes()) {
        saida.writeUTF(str);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void update(Pedido atual) {

    List<Pedido> pedidos = this.getAll();

    int indexAtual = pedidos.indexOf(atual);

    if (indexAtual != -1)
      pedidos.set(indexAtual, atual);

    this.saveToFile(pedidos);

  }

  @Override
  public void delete(Pedido atual) {

    List<Pedido> pedidos = this.getAll();

    int indexAtual = pedidos.indexOf(atual);

    if (indexAtual != -1)
      pedidos.remove(indexAtual);
    this.saveToFile(pedidos);

  }

  @Override
  public List<Pedido> getAll() {

    List<Pedido> retorno = new ArrayList<Pedido>();
    Integer chaveAtual = null;
    Pedido atual = new Pedido();

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readInt()) != null) {
        atual = new Pedido();
        atual.setCodigo(chaveAtual);

        int nmrDeProdutos = entrada.readInt();
        List<Produto> produtos = new ArrayList<Produto>();

        for (int indice = 0; indice < nmrDeProdutos; indice++) {
          String produto = entrada.readUTF();
          produtos.add(prodDAO.get(produto));
        }

        atual.setProdutos(produtos);

        int nmrDeSolicitantes = entrada.readInt();
        List<String> solicitantes = new ArrayList<String>();

        for (int indice = 0; indice < nmrDeSolicitantes; indice++) {
          String solicitante = entrada.readUTF();
          solicitantes.add(solicitante);
        }

        atual.setSolicitantes(solicitantes);

        retorno.add(atual);

      }
    } catch (EOFException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  private void saveToFile(List<Pedido> pedidos) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, false))) {

      for (Pedido atual : pedidos) {
        saida.writeInt(atual.getCodigo());
        saida.writeInt(atual.getProdutos().size());
        for (Produto p : atual.getProdutos()) {
          saida.writeUTF(p.getNome());
        }
        saida.writeInt(atual.getSolicitantes().size());
        for (String str : atual.getSolicitantes()) {
          saida.writeUTF(str);
        }
        saida.flush();

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
