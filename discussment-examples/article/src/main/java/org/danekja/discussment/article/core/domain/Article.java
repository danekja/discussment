package org.danekja.discussment.article.core.domain;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.LongEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static org.danekja.discussment.article.core.domain.Article.GET_ARTICLES;

/**
 * The class represents the article on the website.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
@Entity
@Table(name = "discussment_article")
@NamedQueries({
        @NamedQuery(name = GET_ARTICLES,
                query = "SELECT a FROM Article a")
})
public class Article extends LongEntity implements Serializable{

    /**
     * The constant contains name of query for getting articles
     */
    public static final String GET_ARTICLES = "FileEntity.getArticles";

    /**
     * Name of the article
     */
    private String name;

    /**
     * Text of the article
     */
    private String articleText;

    /**
     * Discussion of the article. If the article is removed, the discussion is removed too.
     */
    private Discussion discussion;

    public Article() {}

    public Article (String name, String articleText) {
        this.name = name;
        this.articleText = articleText;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getArticleText() { return articleText; }

    public void setArticleText(String articleText) { this.articleText = articleText; }

    @OneToOne(orphanRemoval = true)
    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Article article = (Article) o;
        return Objects.equals(name, article.name) &&
                Objects.equals(articleText, article.articleText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, articleText);
    }
}
