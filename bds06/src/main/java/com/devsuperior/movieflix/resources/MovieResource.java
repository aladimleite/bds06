package com.devsuperior.movieflix.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieGenreDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

	@Autowired
	private MovieService service;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieGenreDTO> findById(@PathVariable Long id) {
		MovieGenreDTO movieDTO = service.findById(id);
		return ResponseEntity.ok().body(movieDTO);
	}
	
	@GetMapping
	public ResponseEntity<Page<MovieMinDTO>> findByGenre(Pageable pageable,
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId,
			@RequestParam(value = "name", defaultValue = "") String title) {
		
		Page<MovieMinDTO> page = service.findByGenre(genreId, title.trim(), pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{movieId}/reviews")
	public ResponseEntity<List<ReviewDTO>> findMovieReviews(@PathVariable Long movieId) {
		List<ReviewDTO> list = reviewService.findByMovie(movieId);
		return ResponseEntity.ok().body(list);
	}
}
