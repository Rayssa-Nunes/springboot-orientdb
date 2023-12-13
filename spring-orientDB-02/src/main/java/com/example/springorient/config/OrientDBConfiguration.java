package com.example.springorient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;

@Configuration
public class OrientDBConfiguration {

    private OrientDB orientDB;
    private ODatabaseSession db;


    @Bean
    public ODatabaseSession database() {
        orientDB = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        db = orientDB.open("article", "root", "root");
        

        if (db.getClass("Article") == null) {
            db.command("CREATE CLASS Article");
        }

        return db;
    }

    @Bean
    public OrientDBTemplate orientDBTemplate(ODatabaseSession db) {
        return new OrientDBTemplate(db);
    }
    
}
