package com.krakedev.peliculas.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.krakedev.peliculas.Pelicula;
import com.krakedev.peliculas.repository.PeliculaRepository;

@Service
public class ServicioPelicula {

	private final PeliculaRepository repository;

	public ServicioPelicula(PeliculaRepository repository) {
		this.repository = repository;
	}

	public Pelicula crear(Pelicula pelicula) {
		return repository.save(pelicula);
	}

	public List<Pelicula> listar() {
		return repository.findAll();
	}

	public Pelicula buscarPorId(Long id) {
		Optional<Pelicula> resultado = repository.findById(id);
		return resultado.orElse(null);
	}

	public Pelicula actualizar(Long id, Pelicula peliculaActualizada) {
		Pelicula pelicula = buscarPorId(id);

		if (pelicula == null) {
			return null;
		}

		pelicula.setNombre(peliculaActualizada.getNombre());
		pelicula.setDirector(peliculaActualizada.getDirector());
		pelicula.setGenero(peliculaActualizada.getGenero());
		pelicula.setDuracion(peliculaActualizada.getDuracion());
		pelicula.setDisponible(peliculaActualizada.isDisponible());

		return repository.save(pelicula);
	}

	public boolean eliminar(Long id) {
		Pelicula pelicula = buscarPorId(id);

		if (pelicula == null) {
			return false;
		}
		repository.deleteById(id);
		return true;
	}

	public List<Pelicula> buscarPorGenero(String genero) {
		return repository.findByGenero(genero);
	}

	public List<Pelicula> buscarPorDisponible(boolean disponible) {
		return repository.findByDisponible(disponible);
	}
}
