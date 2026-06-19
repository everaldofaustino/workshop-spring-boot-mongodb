package com.everaldo.workshopmongo.services;

import com.everaldo.workshopmongo.domain.Post;
import com.everaldo.workshopmongo.domain.User;
import com.everaldo.workshopmongo.dto.UserDTO;
import com.everaldo.workshopmongo.repository.PostRepository;
import com.everaldo.workshopmongo.repository.UserRepository;
import com.everaldo.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

        @Autowired
        private PostRepository repo;

        public Post findById(String id){
            Optional<Post> user = repo.findById(id);
            if(user.isEmpty()){
                throw new ObjectNotFoundException("Objeto não encontrado");
            }

            return user.get();

        }

        public List<Post> findByTitle(String text){
            return repo.searchTitle(text);
        }

}
