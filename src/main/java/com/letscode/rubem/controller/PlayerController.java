package com.letscode.rubem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.rubem.firebase.PlayerFirebase;
import com.letscode.rubem.model.Player;

@RestController
@RequestMapping({ "/player" })
public class PlayerController {

	PlayerFirebase firebase;

	public PlayerController() {
		firebase = new PlayerFirebase();
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public String login(@RequestBody Player request) {
		return firebase.login(request);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody Player request) {
		return firebase.create(request);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Player read(@PathVariable String id) {
		return firebase.read(id);
	}
	

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String update(@PathVariable String id, @RequestBody Player request) {
		return firebase.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String delete(@PathVariable String id) {
		return firebase.delete(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Player> listAll() {
		return firebase.listAll();
	}
}
