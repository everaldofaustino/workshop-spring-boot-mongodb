package com.everaldo.workshopmongo;

import com.everaldo.workshopmongo.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class DatabaseChecker {

    @Autowired
    private MongoTemplate mongoTemplate;



    @PostConstruct
    public void check() {

        System.out.println("========================================");
        System.out.println("🔍 TESTE COMPLETO");
        System.out.println("========================================");

        // Nome do banco conectado
        System.out.println("Banco conectado: "
                + mongoTemplate.getDb().getName());

        // Coleções existentes
        System.out.println("Coleções existentes: "
                + mongoTemplate.getCollectionNames());

        // Nome da coleção da entidade User
        System.out.println("Collection User.class: "
                + mongoTemplate.getCollectionName(User.class));

        // Quantidade de documentos
        long total = mongoTemplate.count(
                new Query(),
                User.class
        );

        System.out.println("Quantidade de usuários: "
                + total);

        System.out.println("========================================");
    }
}