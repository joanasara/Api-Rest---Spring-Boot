package com.springboot.api.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.api.model.Category;
import com.springboot.api.repositorys.CategoryRepository;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

	@Autowired
	private CategoryRepository repo;

	@GetMapping
	public List<Category> listar() {
		return repo.findAll();

	}

	@PostMapping
	public ResponseEntity<Category> criar(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category categorySalve = repo.save(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categorySalve.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(categorySalve);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Category> buscarPeloPedido(@PathVariable Long codigo) {

		Category category = repo.findById(codigo).orElse(null);

		return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();

	}
	
	

}
