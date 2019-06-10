package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import general.Pedido;
import general.Produto;

class PedidoDAOTest {

	Pedido pedido;
	Pedido outroPedido;
	PedidoDAO testadoDAO;
	ProdutoDAO produtoDAO;
	Produto adicionado;
	Produto outro;
	List<Produto> produtos;
	List<Produto> outros;
	
	@BeforeEach
	void setUp() throws Exception {
		pedido = new Pedido();
		outroPedido = new Pedido();
		testadoDAO = new PedidoDAO();
		produtoDAO = new ProdutoDAO();
		produtos = new ArrayList<Produto>();
		outros = new ArrayList<Produto>();
		adicionado = new Produto();
		adicionado.setNome("queijo");
		adicionado.setDescricao("integral");
		adicionado.setDisponivel(true);
		outro = new Produto();
		outro.setNome("Sanduiche");
		outro.setDisponivel(true);
		outro.setNome("Gostoso");
		produtoDAO.add(outro);
		produtoDAO.add(adicionado);
		produtos.add(adicionado);
		outros.add(outro);
		
	}

	@Test
	void testAddEGet() {
		pedido.setCodigo(1);
		pedido.setProdutos(produtos);
		
		testadoDAO.add(pedido);
		
		int chave = pedido.getCodigo();
		pedido = null;
		
		pedido = testadoDAO.get(chave);
		
		assertEquals(pedido.getProdutos().contains(adicionado), "O get não retornou os produtos corretos.");
	}
	
	@Test
	void testUpdate() {
		
		pedido.setCodigo(1);
		pedido.setProdutos(outros);
		
		testadoDAO.update(pedido);
		
		int chave = pedido.getCodigo();
		pedido = null;
		pedido = testadoDAO.get(chave);
		
		assertEquals(outros, pedido.getProdutos(), "O update não atualizou os produtos da forma correta.");
		
	}
	
	@Test
	void testDeleteEGetAll() {
		
		outroPedido.setCodigo(2);
		outroPedido.setProdutos(produtos);
		
		testadoDAO.delete(pedido);
		
		testadoDAO.add(outroPedido);
		
		List<Pedido> lista = testadoDAO.getAll();
		
		assertEquals(1, lista.size(), "O delete não deletou a conta da forma correta.");
	}

}
