package general;

import java.util.ArrayList;
import java.util.List;

public class Comanda {

  private int mesa;
  private boolean fechada = false;
  private List<Pedido> pedidos;
  private List<String> solicitantes;

  public Comanda() {
    pedidos = new ArrayList<Pedido>();
  }

  public List<String> getSolicitantes() {
    return this.solicitantes;
  }

  public void setSolicitantes(List<String> solicitantes) {
    this.solicitantes = solicitantes;
  }

  public void setMesa(int mesa) {
    this.mesa = mesa;
  }

  public boolean estaFechada() {
    return this.fechada;
  }

  public void setFechada(boolean fechada) {
    this.fechada = fechada;
  }

  public void setPedidos(List<Pedido> pedidos) {
    this.pedidos = pedidos;
  }


  public List<Pedido> getPedidos() {
    return this.pedidos;
  }

  public int getMesa() {
    return this.mesa;
  }

  @Override
  public String toString() {
    StringBuilder retorno = new StringBuilder();

    retorno.append("\n\tMesa: " + this.mesa + "\n\n");

    retorno.append("\tPedidos:\n");
    for (Pedido pedido : this.pedidos) {
      retorno.append(pedido.toString() + "\n");
    }

    return retorno.toString();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Comanda) && (((Comanda) obj).mesa == this.mesa);
  }

}
