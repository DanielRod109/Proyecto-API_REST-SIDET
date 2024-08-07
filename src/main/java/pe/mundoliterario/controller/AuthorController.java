package pe.mundoliterario.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.mundoliterario.entity.Author;
import pe.mundoliterario.entity.Subgenre;
import pe.mundoliterario.service.AuthorService;

@RestController
@RequestMapping("/author")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {
	
	@Autowired
	AuthorService service;
	
	public AuthorController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listAuthor(){
		Collection<Author> collection = service.findAll();
		
		if(collection.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(collection,HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveAuthor(@RequestBody Author author) {

		service.insert(author);

		Map<String, String> response = new HashMap<>();

		response.put("message", "Autor registrado correctamente.");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/findId/{author_id}")
	public ResponseEntity<?> findIdAuthor( @PathVariable Integer author_id)
	{
		Map<String, String> response = new HashMap<>();
		Author Db=service.findById(author_id);
		
		if(Db!=null){		
			
			return new ResponseEntity<>(Db,HttpStatus.OK);
		}
		
		response.put("message", "Autor no encontrado.");
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editAuthor(@RequestBody Author author, @PathVariable Integer id) {

		Author db = service.findById(id);
		
		Map<String, String> response = new HashMap<>();
		
		if(db!=null) {
			db.setAuthor_name(author.getAuthor_name());
			service.update(db);
			
			response.put("message", "Autor editado correctamente.");

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		response.put("message", "404 E");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
