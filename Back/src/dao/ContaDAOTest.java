package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import general.Conta;

class ContaDAOTest {

	ContaDAO testadoDAO;
	Conta contaQualquer;
	Conta outraConta;
	
	
	@BeforeEach
	void setUp() throws Exception {
		contaQualquer = new Conta();
		outraConta = new Conta();
		testadoDAO = new ContaDAO();
	}

	@Test
	void testAddEGet() {
		contaQualquer.setUser("hugo");
		contaQualquer.setSenha("senha");
		contaQualquer.setPermissao(2);
		
		testadoDAO.add(contaQualquer);
		
		String chave = contaQualquer.getUser();
		contaQualquer = null;
		
		contaQualquer = testadoDAO.get(chave);
		
		assertEquals("hugo", contaQualquer.getUser(), "O get não retornou o usuário correto.");
		assertEquals("senha", contaQualquer.getSenha(), "O get não retornou a senha correta.");
		assertEquals(2, contaQualquer.getPermissao(),"O get não retornou a permissão correta.");
	}
	
	@Test
	void testUpdate() {
		
		contaQualquer.setUser("hugo");
		contaQualquer.setSenha("novasenha");
		contaQualquer.setPermissao(1);
		
		testadoDAO.update(contaQualquer);
		
		String chave = contaQualquer.getUser();
		contaQualquer = null;
		contaQualquer = testadoDAO.get(chave);
		
		assertEquals("novasenha", contaQualquer.getSenha(), "O update não mudou a senha da forma correta.");
		assertEquals(1, contaQualquer.getPermissao(), "O update não mudou a permissão da forma correta.");
		
	}
	
	@Test
	void testDeleteEGetAll() {
		
		outraConta.setUser("joao");
		outraConta.setSenha("oloco");
		outraConta.setPermissao(1);
		
		contaQualquer.setUser("hugo");
		
		testadoDAO.delete(contaQualquer);
		
		testadoDAO.add(outraConta);
		
		List<Conta> lista = testadoDAO.getAll();
		
		assertEquals(1, lista.size(), "O delete não deletou a conta da forma correta.");
	}

}
