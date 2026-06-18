package com.everaldo.workshopmongo.services;

import com.everaldo.workshopmongo.domain.User;
import com.everaldo.workshopmongo.repository.UserRepository;
import com.everaldo.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

        @Autowired
        private UserRepository repo;

        public List<User> findAll(){
          return repo.findAll();
        }

        public User findById(String id){
            Optional<User> user = repo.findById(id);
            if(user.isEmpty()){
                throw new ObjectNotFoundException("Objeto não encontrado");
            }

            return user.get();

        }

}
