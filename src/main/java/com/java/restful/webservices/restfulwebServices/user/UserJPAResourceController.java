package com.java.restful.webservices.restfulwebServices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


//for creating REST service need to follow the Richardson model
//three level
//level 1- Expose resource with proper URI
//level 2 - level1+proper use of hhtp method
//level3 - level2+ Hateoas


//Best Practice of Restful Ws

//1. consumer first -who are the consumer, what they want, make sure consumer undearstnd restful WS (documentain)*****
//2. Best use of the http method, proper response with proper message
//3. Ensure no secure info will send in URI
//4 always use plurals
//5 use nouns for resources -- for exception scenarrios have the consistant approch

//

@RestController
public class UserJPAResourceController {
	@Autowired
	private UserDaoService userDaoService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	

	// @CrossOrigin(origins = "*")
	@GetMapping("jpa/users")
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@GetMapping("jpa/users/id")
	public User getUserById(@RequestParam(value = "id", required = true) int id) {
		return userDaoService.findOne(id);

	}

	// Due to clash of version between swagger and hateoas below code is commented.
	// when need to add hyper link
	// will use the below code

	/*
	 * @GetMapping("/users/{id}") public EntityModel<User>
	 * getUserByIdAgain(@PathVariable int id) { User user =
	 * userDaoService.findOne(id); if (user == null) { throw new
	 * UserNotFoundException("ID Not Found - " + id); } // Hateoas for Link addition
	 * 
	 * EntityModel<User> model = new EntityModel<>(user); WebMvcLinkBuilder linkTo =
	 * WebMvcLinkBuilder
	 * .linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUser());
	 * model.add(linkTo.withRel("all-users")); return model;
	 * 
	 * // Resource<User> resource = new Resource<User>(user); //
	 * ControllerLinkBuilder linkTo = //
	 * linkTo(methodOn(this.getClass()).retrieveAllUsers()); //
	 * resource.add(linkTo.withRel("all-users")); // return resource;
	 * 
	 * }
	 */

	@GetMapping("jpa/users/{id}")
	public Optional<User> getUserByIdParam(@PathVariable int id) {
		// User user =userDaoService.findOne(id);
		Optional<User> user = userRepository.findById(id);

		if (!(user.isPresent())) {
			throw new UserNotFoundException("ID Not Found - " + id);
		}
		return user;
	}

	@PostMapping("jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("jpa/users/{id}")
	public ResponseEntity<User> delete(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		if (user == null) {
			throw new UserNotFoundException("User Not Found Id : " + id);
		}
		return new ResponseEntity(user, HttpStatus.NO_CONTENT);
	}

	@GetMapping("jpa/users/{id}/posts")
	public List<Post> retriveAllUser(@PathVariable int id) {
		Optional<User> users = userRepository.findById(id);
		if (!(users.isPresent())) {
			throw new UserNotFoundException("User Not Found Id : " + id);
		}
		return users.get().getPosts();
	}
	
	
	@PostMapping("jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> users = userRepository.findById(id);
		if (!(users.isPresent())) {
			throw new UserNotFoundException("User Not Found Id : " + id);
		}
		
		User user = users.get();
		post.setUser(user);
		
		Post savedpost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedpost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
		
		
	}

}
