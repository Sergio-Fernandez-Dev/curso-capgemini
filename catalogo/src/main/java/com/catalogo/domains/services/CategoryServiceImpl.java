package com.catalogo.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contracts.repositories.CategoryRepository;
import com.catalogo.domains.contracts.services.CategoryService;
import com.catalogo.domains.entities.Category;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository dao;

	public CategoryServiceImpl(CategoryRepository dao) {
		this.dao = dao;
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Category> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Category> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (item.getCategoryId() != 0 && dao.existsById(item.getCategoryId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (!dao.existsById(item.getCategoryId()))
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		if (item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

}
