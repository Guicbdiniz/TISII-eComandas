package general;

public class Conta {
	
	public static final byte PERMISSAO_GARCOM = 1;
	public static final byte PERMISSAO_CAIXA = 2;
	public static final byte PERMISSAO_MANUTENCAO = 3;
	
	private String user;
	private String senha;
	private int permissao;
	
	public Conta() {
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setPermissao(int permissao) {
		if(permissao > 0 && permissao < 4)
			this.permissao = permissao;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getSenha() {
		return this.senha;
	}
	
	public int getPermissao() {
		return this.permissao;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Conta) && (this.getUser().equals(((Conta)obj).getUser()));
	}

}
