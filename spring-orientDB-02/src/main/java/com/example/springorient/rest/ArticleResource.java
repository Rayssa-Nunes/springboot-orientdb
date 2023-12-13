package com.example.springorient.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springorient.domain.Article;
import com.example.springorient.service.ArticleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class ArticleResource {
    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/article")
    public Article create(@RequestBody @Valid Article article) {
        log.debug("Create an article with the properties {}", article);
        return articleService.save(article);
    }

    @GetMapping("/article")
    public List<Article> list() {
        log.debug("Essa é a lista de Arquivos.");
        return articleService.findAll();
    }

    @GetMapping("/article/{title}")
    public Article findByTitle(@PathVariable @NotNull String title) {
        log.debug("Carregado arquivo de título: {}", title);
        return articleService.findOne(title);
    }
    
    @PutMapping("/article/{title}")
public Article update(@PathVariable @NotNull String title, @RequestBody Article newArticle) {
    log.debug("Atualização do artigo de título {}", title);
    
    return articleService.update(title, newArticle);
}

    @DeleteMapping("/article/{title}")
    public boolean deleteById(@PathVariable @NotNull String title) {
        log.debug("dDlete o artigo de título: {}", title);
        return articleService.delete(title);
    }


}
