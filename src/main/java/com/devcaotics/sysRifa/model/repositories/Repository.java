package com.devcaotics.sysRifa.model.repositories;

import java.sql.SQLException;
import java.util.List;

public interface Repository<E, ID> {// Recebe uma Entidade (E) + um ID

	public E create(E e) throws SQLException; // cada método vai retornar o objeto da própria entidade criado

	public E update(E e, ID id) throws SQLException;

	public E read(int codigo) throws SQLException;

	public void delete(int codigo) throws SQLException;

	public List<E> readAll() throws SQLException;

}