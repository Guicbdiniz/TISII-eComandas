package dao;

import java.util.List;

public interface DAO<tipoItem, tipoChave> {

	public tipoItem get(tipoChave chave);
	public void add(tipoItem item);
	public void update(tipoItem item);
	public void delete(tipoItem item);
	public List<tipoItem> getAll();
	
}
