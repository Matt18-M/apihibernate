package com.krakedev.peliculas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.peliculas.Pelicula;
import com.krakedev.peliculas.services.ServicioPelicula;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

	private final ServicioPelicula servicio;

	public PeliculaController(ServicioPelicula servicio) {
		this.servicio = servicio;
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Pelicula pelicula) {
		try {
			Pelicula creada = servicio.crear(pelicula);
			return ResponseEntity.status(HttpStatus.CREATED).body(creada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la película");
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Pelicula> peliculas = servicio.listar();
			return ResponseEntity.ok(peliculas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las películas");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		try {
			Pelicula pelicula = servicio.buscarPorId(id);

			if (pelicula == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La película con ID: " + id + " no fue encontrada");
			}
			return ResponseEntity.ok(pelicula);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la película");
		}
	}

	@GetMapping("/genero")
	public ResponseEntity<?> buscarPorGenero(@RequestParam String genero) {
		try {
			List<Pelicula> peliculas = servicio.buscarPorGenero(genero);

			if (peliculas.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontraron películas del género: " + genero);
			}
			return ResponseEntity.ok(peliculas);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar por género");
		}
	}

	@GetMapping("/disponible")
	public ResponseEntity<?> buscarPorDisponible(@RequestParam boolean disponible) {
		try {
			List<Pelicula> peliculas = servicio.buscarPorDisponible(disponible);

			if (peliculas.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontraron películas con disponibilidad: " + disponible);
			}
			return ResponseEntity.ok(peliculas);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar por disponibilidad");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pelicula pelicula) {
		try {
			Pelicula actualizada = servicio.actualizar(id, pelicula);

			if (actualizada == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La película con ID: " + id + " no fue encontrada");
			}
			return ResponseEntity.ok(actualizada);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la película");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		try {
			boolean eliminado = servicio.eliminar(id);

			if (!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La película con ID: " + id + " no fue encontrada");
			}

			return ResponseEntity.ok("Película eliminada con éxito");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la película");
		}
	}
}
