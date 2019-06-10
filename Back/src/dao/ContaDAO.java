package dao;
import others.NomeDosArquivos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import general.Conta;

public class ContaDAO implements DAO<Conta, String> {

	private String arquivoNome = NomeDosArquivos.ARQUIVO_CONTAS;

	public ContaDAO() {

	}

	@Override
	public Conta get(String chave) {

		Conta retorno = null;
		Conta atual = null;
		String chaveAtual = null;

		try(DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))){

			while((chaveAtual = entrada.readUTF()) != null) {
				atual = new Conta();
				atual.setUser(chaveAtual);
				atual.setSenha(entrada.readUTF());
				atual.setPermissao(entrada.readInt());

				if(chave.equals(atual.getUser())) {
					retorno = atual;
					break;
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public void add(Conta item){
		try(DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome,true))){
			saida.writeUTF(item.getUser());
			saida.writeUTF(item.getSenha());
			saida.writeInt(item.getPermissao());
			saida.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(Conta item) {

		List<Conta> contas = this.getAll();

		int index = contas.indexOf(item);

		if(index != -1)
			contas.set(index, item);

		this.saveToFile(contas);
	}

	@Override
	public void delete(Conta item) {

		List<Conta> contas = this.getAll();

		int index = contas.indexOf(item);

		if(index != -1)
			contas.remove(index);

		this.saveToFile(contas);

	}

	@Override
	public List<Conta> getAll(){
		List <Conta> retorno = new ArrayList<Conta>();

		Conta atual = null;
		String chaveAtual = null;

		try(DataInputStream entrada = new DataInputStream(new FileInputStream(arquivoNome))){

			while((chaveAtual = entrada.readUTF()) != null) {
				atual = new Conta();
				atual.setUser(chaveAtual);
				atual.setSenha(entrada.readUTF());
				atual.setPermissao(entrada.readInt());

				retorno.add(atual);
			}

		}catch (EOFException e){

		}catch(Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	private void saveToFile(List <Conta> contas) {

		try(DataOutputStream saida = new DataOutputStream(new FileOutputStream(arquivoNome, false))){

			for(Conta atual : contas) {
				saida.writeUTF(atual.getUser());
				saida.writeUTF(atual.getSenha());
				saida.writeInt(atual.getPermissao());
				saida.flush();

			}

		}catch(Exception e) {
			e.printStackTrace();
		}


	}

}
