package filtragemDeDados;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import general.Conta;

public class ContasStream {

  public static List<Conta> administradores(List<Conta> contas) {
    List<Conta> retorno = contas.stream() //
        .filter(conta -> conta.getPermissao() == 3)
        .collect(Collectors.toCollection(ArrayList::new));
    return retorno;
  }

}
