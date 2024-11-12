package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}	
	
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userRepository.findById(id).orElse(null);
		
		if (user== null) {
			throw new UserNotFoundException("id: " + id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).retrieveAllPostsForUser(id));
		entityModel.add(link1.withRel("all-users"));
		entityModel.add(link2.withRel("all-posts"));
		
		return entityModel;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsForUser(@PathVariable int id) {
		User user = userRepository.findById(id).orElse(null);
		
		if (user== null) {
			throw new UserNotFoundException("id: " + id);
		}
		
		return user.getPosts();
	}
	
	@GetMapping("/users/{id}/posts/{postId}")
	public EntityModel<Post> retrievePostForUser(@PathVariable int id, @PathVariable int postId) {
		
		User user = userRepository.findById(id).orElse(null);
		if (user== null) {
			throw new UserNotFoundException("id: " + id);
		}
		
		Post post = postRepository.findById(postId).orElse(null);
		if (post == null) {
			throw new PostNotFoundException("postId: " + postId);
		}
		
		EntityModel<Post> entityModel = EntityModel.of(post);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllPostsForUser(id));
		entityModel.add(link.withRel("all-posts"));
		
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Post> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		User user = userRepository.findById(id).orElse(null);
		
		if (user== null) {
			throw new UserNotFoundException("id: " + id);
		}
		
		post.setUser(user);
		Post savedPost = postRepository.save(post);
		
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
}
