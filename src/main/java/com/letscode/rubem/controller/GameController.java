package com.letscode.rubem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.rubem.firebase.GameFirebase;
import com.letscode.rubem.firebase.RankFirebase;
import com.letscode.rubem.model.Game;
import com.letscode.rubem.model.Rank;
import com.letscode.rubem.model.Round;

@RestController
@RequestMapping({ "/game" })
public class GameController {

	GameFirebase firebase;

	public GameController() {
		firebase = new GameFirebase();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Game read(@PathVariable String id) {
		return firebase.read(id);
	}
 

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String delete(@PathVariable String id) {
		return firebase.delete(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Game> listAll() {
		return firebase.listAll();
	}

	@PutMapping("/finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String finish(@PathVariable String id) {
		return firebase.finish(id);
	}
	
	@PutMapping("/round/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String insertRound(@PathVariable String id,@RequestBody Round request) {
		return firebase.insertRound(id,request);
		
	}@GetMapping("/rank")
	@ResponseStatus(HttpStatus.OK)
	public List<Rank> listRank() {
		return new RankFirebase().listAll();
	}
	
	
}
