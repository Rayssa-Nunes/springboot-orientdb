package com.example.springorient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springorient.config.OrientDBTemplate;
import com.example.springorient.domain.Article;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

@Service
public class ArticleService {
    
    @Autowired
    private OrientDBTemplate template;

    public Article save(Article article) {
        template.execute(db -> {
            OElement element = db.newElement("Article");
            element.setProperty("title", article.getTitle());
            element.setProperty("content", article.getContent());
            element.setProperty("author", article.getAuthor());
            element.save();
        });
        return article;
    }

    public List<Article> findAll() {
        final List<Article> articles = new ArrayList<>();
    
        template.execute(db -> {
            for (OElement articleElement : db.browseClass("Article")) {
                Article article = Article.fromOElement(articleElement);
                articles.add(article);
            }
        });
    
        return articles;
    }
    
    public Article findOne(String title) {
        final Article[] article = new Article[1];
    
        template.execute(db -> {
            OResultSet results = db.command("select * from Article where title = :title", title);
    
            if(results.hasNext()) {
                OElement oElement= results.next().toElement();
                article[0] = Article.fromOElement(oElement);
            }
        });
    
        return article[0];
    }
    
    public Article update(String title, Article newArticle) {
        final Article[] updatedArticle = new Article[1];
    
        template.execute(db -> {
            String query = "select from Article where title = :title";
            OResultSet result = db.command(query, title);
    
            if(result.hasNext()) {
                OElement oElement = result.next().toElement();
                oElement.setProperty("title", newArticle.getTitle());
                oElement.setProperty("content", newArticle.getContent());
                oElement.setProperty("author", newArticle.getAuthor());
                oElement.save();
                updatedArticle[0] = Article.fromOElement(oElement);
            } else {
                System.out.println("Artigo não encontrado com o título: " + title);
            }
        });
    
        return updatedArticle[0];
    }
    
    public boolean delete(String title) {
        final boolean[] isDeleted = new boolean[1];
    
        template.execute(db -> {
            String trimmedTitle = title.trim();
            OResultSet result = db.command("DELETE FROM Article WHERE title = :title", trimmedTitle);
            long recordsDeleted = result.stream().count();
            result.close(); 
            isDeleted[0] = recordsDeleted > 0;
        });
    
        return isDeleted[0];
    }
    
    
 
}
