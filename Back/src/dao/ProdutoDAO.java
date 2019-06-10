package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import general.Produto;
import others.NomeDosArquivos;

public class ProdutoDAO implements DAO<Produto, String> {

  private String arquivoNome = NomeDosArquivos.ARQUIVO_PRODUTOS;

  public ProdutoDAO() {

  }

  @Override
  public Produto get(String chave) {

    Produto atual = null;
    Produto retorno = null;
    String chaveAtual = null;

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readUTF()) != null) {
        atual = new Produto();
        atual.setNome(chaveAtual);
        atual.setDescricao(entrada.readUTF());
        atual.setDisponivel(entrada.readBoolean());
        atual.setCodigo(entrada.readInt());
        atual.setPreco(entrada.readDouble());
        atual.setCategoria(entrada.readUTF());

        if (chave.equals(chaveAtual)) {
          retorno = atual;
          break;
        }
      }
    } catch (EOFException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;

  }

  @Override
  public void add(Produto item) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, true))) {
      saida.writeUTF(item.getNome());
      saida.writeUTF(item.getDescricao());
      saida.writeBoolean(item.isDisponivel());
      saida.writeInt(item.getCodigo());
      saida.writeDouble(item.getPreco());
      saida.writeUTF(item.getCategoria());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void update(Produto item) {

    List<Produto> lista = this.getAll();

    int index = lista.indexOf(item);

    if (index != -1)
      lista.set(index, item);

    this.saveToFile(lista);

  }

  @Override
  public void delete(Produto item) {

    List<Produto> lista = this.getAll();

    int index = lista.indexOf(item);

    if (index != -1)
      lista.remove(index);

    this.saveToFile(lista);

  }

  @Override
  public List<Produto> getAll() {

    List<Produto> retorno = new ArrayList<Produto>();
    String chaveAtual = null;

    try (DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))) {

      while ((chaveAtual = entrada.readUTF()) != null) {

        Produto atual = new Produto();

        atual.setNome(chaveAtual);
        atual.setDescricao(entrada.readUTF());
        atual.setDisponivel(entrada.readBoolean());
        atual.setCodigo(entrada.readInt());
        atual.setPreco(entrada.readDouble());
        atual.setCategoria(entrada.readUTF());

        retorno.add(atual);
      }

    } catch (FileNotFoundException e) {
    } catch (EOFException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  private void saveToFile(List<Produto> produtos) {

    try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, false))) {

      for (Produto atual : produtos) {
        saida.writeUTF(atual.getNome());
        saida.writeUTF(atual.getDescricao());
        saida.writeBoolean(atual.isDisponivel());
        saida.writeInt(atual.getCodigo());
        saida.writeDouble(atual.getPreco());
        saida.writeUTF(atual.getCategoria());
        saida.flush();

      }

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

}
