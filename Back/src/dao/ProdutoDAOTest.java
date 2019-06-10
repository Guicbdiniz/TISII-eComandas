package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import general.Produto;

class ProdutoDAOTest {

	Produto produtoQualquer;
	Produto outroProdutoQualquer;
	ProdutoDAO produtoDAO;
	
	@BeforeEach
	void setUp() throws Exception {
		produtoQualquer = new Produto();
		outroProdutoQualquer = new Produto();
		produtoDAO = new ProdutoDAO();
	}

	@Test
	void testAddEGet() {
		produtoQualquer.setNome("Pao");
		produtoQualquer.setDescricao("Gostoso");
		produtoQualquer.setDisponivel(true);
		
		produtoDAO.add(produtoQualquer);
		
		String chave = produtoQualquer.getNome();
		produtoQualquer = null;
		
		produtoQualquer = produtoDAO.get(chave);
		
		assertEquals("Pao", produtoQualquer.getNome(), "O get não retornou o nome esperado");
		assertEquals("Gostoso", produtoQualquer.getDescricao(), "O get não retornou a descricao esperada");
	}
	
	@Test
	void testUpdate() {
		
		produtoQualquer.setNome("Pao");
		produtoQualquer.setDescricao("Nova");
		produtoQualquer.setDisponivel(true);
		
		produtoDAO.update(produtoQualquer);
		
		String chave = produtoQualquer.getNome();
		produtoQualquer = null;
		produtoQualquer = produtoDAO.get(chave);
		
		assertEquals("Nova", produtoQualquer.getDescricao(), "O update não mudou a descricao da forma correta.");
		
	}
	
	@Test
	void testeDelete() {
			
			outroProdutoQualquer.setNome("agua");
			outroProdutoQualquer.setDescricao("oloco");
			outroProdutoQualquer.setDisponivel(true);
			
			produtoQualquer.setNome("hugo");
			
			produtoDAO.delete(produtoQualquer);
			
			produtoDAO.add(outroProdutoQualquer);
			
			List<Produto> lista = produtoDAO.getAll();
			
			assertEquals(1, lista.size(), "O delete não deletou o produto da forma correta.");
		}

}
