package com.example.springorient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springorient.domain.Article;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

@Service
public class ArticleService {
    
    private OrientDB orientDB;
    private ODatabaseSession db;

    public ODatabaseSession database() {
        orientDB = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        db = orientDB.open("article", "root", "root");
        
        if (db.getClass("Article") == null) {
            db.command("CREATE CLASS Article");
        }
        
        return db;
    }

    public Article save(Article article) {

        OElement element = database().newElement("Article");
        element.setProperty("title", article.getTitle());
        element.setProperty("content", article.getContent());
        element.setProperty("author", article.getAuthor());
        element.save();
        return article;
    }

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();

        for (OElement articleElement : database().browseClass("Article")) {
            Article article = Article.fromOElement(articleElement);

            articles.add(article);
        }
        return articles;
    }


    public Article findOne(String title) {
        OResultSet results = database().command("select * from Article where title = :title", title);

        if(results.hasNext()) {
            OElement oElement= results.next().toElement();
            return Article.fromOElement(oElement);
        }

        return null;
    }

    public Article update(String title, Article newArticle) {
        String query = "select from Article where title = :title";
        OResultSet result = database().command(query, title);
    
        if(result.hasNext()) {
            OElement oElement = result.next().toElement();
            oElement.setProperty("title", newArticle.getTitle());
            oElement.setProperty("content", newArticle.getContent());
            oElement.setProperty("author", newArticle.getAuthor());
            oElement.save();
            Article updatedArticle = Article.fromOElement(oElement);
            return updatedArticle;
        }
    
        System.out.println("Artigo não encontrado com o título: " + title);
        return null;
    }

    public boolean delete(String title) {
        
        title = title.trim();
        OResultSet result = database().command("DELETE FROM Article WHERE title = :title", title);
        long recordsDeleted = result.stream().count();
        result.close(); 
        if (recordsDeleted > 0) return true;
        return false;
    }
    
}
