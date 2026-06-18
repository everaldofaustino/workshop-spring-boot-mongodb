package com.everaldo.workshopmongo.services;

import com.everaldo.workshopmongo.domain.User;
import com.everaldo.workshopmongo.dto.UserDTO;
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

        public User insert(User obj){
            return repo.insert(obj);
        }

        public void delete(String id){
            findById(id);
            repo.deleteById(id);

        }

        public User update(User obj){
            Optional<User> newObj = repo.findById(obj.getId());
            updateData(newObj,obj);

            User user = newObj.get();
            return repo.save(user);


        }

    private void updateData(Optional<User> newObj, User obj) {

            User user = newObj.get();
            user.setName(obj.getName());
            user.setEmail(obj.getEmail());

    }


    public User fromDTO(UserDTO objDTO){
            return new User(objDTO.getId(),objDTO.getName(),objDTO.getEmail());
        }




}
