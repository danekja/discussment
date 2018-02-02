# Making own module
This tutorial will show you how to make you own module using `discussment-core` and `discusment-ui-wicket` modules.

## Core features
First of all we will make our own `domain`, `Jpa` and `service` classes and interfaces for working with articles.

### New Data Class
- Create new class for our entity in the **domain** package.
  ```
     public class Article extends BaseEntity implements Serializable{
  ```
  - Query to get all articles from the database
     ```    
        @NamedQueries({
        @NamedQuery(name = GET_ARTICLES,
           query = "SELECT a FROM Article a")
        })
        public class Article extends BaseEntity implements Serializable{
     
           /**
            * The constant contains name of query for getting articles
            */
            public static final String GET_ARTICLES = "FileEntity.getArticles";
     ```
  - Fields of our article 
     ```    
        /**
         * Name of the article
         */
        private String name;
     
        /**
          * Text of the article
          */
        private String articleText;
     ```
  - And its discussion
     ```
        /**
         * Discussion of the article. If the article is removed, the discussion is removed too.
         */
        @OneToOne(orphanRemoval = true)
        private Discussion discussion;
     ```
     - `@OneToOne` is enough, cause we'll need only one discussion per article
### Jpa
- Create new interface in the **dao** package.
  ```
     package org.danekja.discussment.article.core.dao;
     
     import org.danekja.discussment.core.dao.GenericDao;
     import org.danekja.discussment.article.core.domain.Article;
     
     import java.util.List;
     
     /**
      * The interface extends GenericDao on methods for working with articles in a database
      *
      * Date: 30.1.18
      *
      * @author Jiri Kryda
      */
     public interface ArticleDao extends GenericDao<Article> {
     
         /**
          * Get all articles in a database.
          *
          * @return list of Articles
          */
         List<Article> getArticles();
     
     }
  ```
- Create new class in the **jpa** package to implement the interface
  ```
    package org.danekja.discussment.article.core.dao.jpa;
    
    import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;
    import org.danekja.discussment.article.core.dao.ArticleDao;
    import org.danekja.discussment.article.core.domain.Article;
    
    import javax.persistence.EntityManager;
    import javax.persistence.TypedQuery;
    import java.util.List;
    
    /**
     * JPA implementation of the UserDao interface.
     *
     * Date: 30.1.18
     *
     * @author Jiri Kryda
     */
    public class ArticleDaoJPA extends GenericDaoJPA<Article> implements ArticleDao{
    
        /**
         * @param em entity manager to be used by this dao
         */
        public ArticleDaoJPA(EntityManager em) {
            super(Article.class, em);
        }
    
        public List<Article> getArticles() {
            TypedQuery<Article> q = em.createNamedQuery(Article.GET_ARTICLES, Article.class);
            return q.getResultList();
        }
    }
  ```
### New Service for Data Class
- Create new interface in the **service** package
  ```
  package org.danekja.discussment.article.core.service;
  
  import org.danekja.discussment.article.core.domain.Article;
  import java.util.List;
  
  /**
   * The interface contains the service methods for working with the articles.
   *
   * Date: 30.1.18
   *
   * @author Jiri Kryda
   */
  public interface ArticleService {
  
  
      /**
       * Creates a new article and its discussion.
       *
       * @param entity new article
       * @return new article
       */
      Article createArticle(Article entity);
  
      /**
       * Get an article based on its id.
       *
       * @param articleId article id
       * @return article by id
       */
      Article getArticleById(long articleId);
  
      /**
       * Get all articles
       *
       * @return list of Articles
       */
      List<Article> getArticles();
  
      /**
       * Remove an article
       *
       * @param entity article to remove
       */
      void removeArticle(Article entity);
  }
  ``` 
- And implement it
  ```
  package org.danekja.discussment.article.core.service.imp;
  
  import org.danekja.discussment.core.domain.Discussion;
  import org.danekja.discussment.article.core.dao.ArticleDao;
  import org.danekja.discussment.article.core.domain.Article;
  import org.danekja.discussment.article.core.service.ArticleService;
  import org.danekja.discussment.core.service.DiscussionService;
  
  import java.util.List;
  
  /**
   * Implementation of the ArticleSevice interface.
   *
   * Date: 30.1.18
   *
   * @author Jiri Kryda
   */
  public class DefaultArticleService implements ArticleService {
      private ArticleDao articleDao;
      private DiscussionService discussionService;
  
      public DefaultArticleService(ArticleDao articleDao, DiscussionService discussionService){
          this.articleDao = articleDao;
          this.discussionService = discussionService;
      }
  
      public Article createArticle(Article entity) {
  
          Discussion discussion = discussionService.createDiscussion(new Discussion(entity.getName(), (null)));
          entity.setDiscussion(discussion);
  
          return articleDao.save(entity);
      }
  
      public Article getArticleById(long articleId){
          return articleDao.getById(articleId);
      }
  
      public List<Article> getArticles() {
          return articleDao.getArticles();
      }
  
      public void removeArticle(Article entity){
          articleDao.remove(entity);
      }
  }
  ```

## UI Wicket features 
### Model 
- Create new model class for our article in the **model** package so wicket can handle our articles.
  ```
     package org.danekja.discussment.article.ui.wicket.model;
     
     import org.apache.wicket.model.LoadableDetachableModel;
     import org.danekja.discussment.article.core.domain.Article;
     import org.danekja.discussment.article.core.service.ArticleService;
     
     import java.util.List;
     
     /**
      * The class for getting the articles via the article service.
      *
      * Date: 30.1.18
      *
      * @author Jiri Kryda
      */
     public class ArticleWicketModel extends LoadableDetachableModel<List<Article>> {
     
         private ArticleService articleService;
     
         /**
          * Constructor for creating a instance of getting the articles.
          *
          * @param articleService instance of the category article
          */
         public ArticleWicketModel(ArticleService articleService){ this.articleService = articleService; }
     
         @Override
         protected List<Article> load(){ return articleService.getArticles(); }
     }
  ```

### Panels 
- Panels help us make the final page smaller and less cluttered.
- One panel usually handles one thing and passes data to another.

### Article panel 
- Let's create panel with a link for creating a new article and calling a panel for showing their list. 
  - Only requires articleService as its parameter, we'll get the other things later via the service. 
     ```
        public class ArticlePanel extends Panel{
  
           private ArticleService articleService;
     ```
  - Adds clickable link on the website.
     ```
        add(createArticleAjaxLink());
             add(new ArticleListPanel(
     ``` 
  - Which is implemented in the method.
     ```
        private AjaxLink createArticleAjaxLink() {
           return new AjaxLink("createArticle") {
              @Override
              public void onClick(AjaxRequestTarget target) {
                 modalWindow.show(target);
              }
     
              @Override
              protected void onConfigure() {
                 super.onConfigure();
     
                 User user = (User) getSession().getAttribute("user");
                     this.setVisible(user != null && user.getPermissions().isCreateCategory());
              }
           };
        }
     ```
     - Calls for a modal window that contains form for creating a new article.
     - Is only visible if user has sufficient permissions. 
  - Calls for a panel which shows list of all articles in the database.
     ```
        add(new ArticleListPanel("content",
            new ArticleWicketModel(articleService), articleService));
     ```  
  - Creates a modal window that will contain panel with form for making a new article.
     ```
        modalWindow = new ModalWindow("articleModal");
        modalWindow.setContent(new ModalPanel(modalWindow.getContentId(), modalWindow, articleService));
     ```
  - Example html, has to have defined all wicked components by the right id. 
     ```
        <wicket:panel xmlns:wicket="http://www.w3.org/1999/xhtml">
     
           <div class="modal" role="dialog" wicket:id="articleModal" />
     
           <h1>Article</h1>
           <div class="actions_create">
              <a><span wicket:id="createArticle">Create article</span></a>
           </div>
     
           <span wicket:id="content"></span>
        </wicket:panel>
     ``` 
     
### Article list panel
- Fills the list with all available articles and handles their opening and deleting
  - This populates the list with articles.
     ```
        @Override
           protected void onInitialize() {
              super.onInitialize();
     
              add(new ListView<Article>("articleList", articleListModel) {
                 protected void populateItem(final ListItem<Article> listItem){
     
                     listItem.add(createOpenArticleLink(listItem.getModel()));
                     listItem.add(createRemoveArticleLink(listItem.getModel()));
                 }
           });
        }
     ```
  - Opens the article when it name is clicked on.
     ```
         private Link createOpenArticleLink(final IModel<Article> am) {
             return new Link("openArticle") {
                 @Override
                 public void onClick() {
                     PageParameters pageParameters = new PageParameters();
                     pageParameters.add("articleId", am.getObject().getId());
      
                     setResponsePage(ArticleTextPage.class, pageParameters);
                 }
      
                 @Override
                 protected void onConfigure() {
                     super.onConfigure();
      
                     setBody(Model.of(am.getObject().getName()));
                 }
             };
         }
     ```
     - Article is opened in a new page, which is set in `setResponsePage()`
     - `PageParameters` contain the id of the clicked article and will allow the page to call for it. 
  - Deletes the article and its discussion. Button is visible only if user has sufficient permissions.
     ```
        private Link createRemoveArticleLink(final IModel<Article> am) {
           return new Link("remove") {
              @Override
              public void onClick() {
                 articleService.removeArticle(am.getObject());
                 setResponsePage(getWebPage().getClass());
              }
      
              @Override
              protected void onConfigure() {
                 super.onConfigure();
      
                 User user = (User) getSession().getAttribute("user");
                 this.setVisible(user != null && user.getPermissions().isRemoveCategory());
              }
           };
        }
     ```
     

### Article text panel
- Shows article name, its text and adds the right discussion under it.
  
  - Gets the right article name and text to show from article model.
     ```
        Label articleName = new Label ("articleName", new PropertyModel<String>(articleModel, "name"));
        add(articleName);
     
        Label articleText = new Label ("articleText", new PropertyModel<String>(articleModel, "articleText"));
        add(articleText);
     ``` 
  - Adds the linked discussion under the article.
     ```
        add(new DiscussionPanel("discussionPanel", new PropertyModel(articleModel, "discussion"), postService, new Model<Post>()));
     ```
  
### Modal Panel
- Fills modal window from `ArticlePanel` with `ArticleForm`.
  - Calls for a new Article Form to which we can later add buttons.
     ```
        Form articleForm = new ArticleForm("articleForm", new Model<Article>(new Article()), articleService);
     ```
  - Creates a button that closes the modal window when submitting the form.
     ```
        AjaxButton createArticle = new AjaxButton("createArticle") {
           @Override
           protected void onSubmit(AjaxRequestTarget target, Form articleForm) {
              modalWindow.close(target);
           }
        };
     ```
  - Refreshes the page after creating an article so user doesn't have to do that manually.
     ```
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
        @Override
        public void onClose(AjaxRequestTarget target)
           {
           setResponsePage(getPage());
           }
        });
     ```

  
### Article Form 
- Creates the form for creating a new article
- Requires 'ArticleModel' and 'ArticleService' as its parameters
  ```
   public ArticleForm(String id, IModel<Article> articleModel, ArticleService articleService){
  ```
  
  - Adds panel with form components.
     ```
        @Override
        protected void onInitialize(){
           super.onInitialize();
           add(new ArticleFormComponent("articleFormComponent", articleModel));
        }
     ```
  - Keeps the entered fields of the article in the form.
     ```
        @Override
        protected void onSubmit(){
           if(articleService != null){
              articleModel.setObject(articleService.createArticle(articleModel.getObject()));
           }
        }
     ``` 
     
### Article Form Component
- Contains input fields for getting a name and text of the article.
  - Only requires 'ArticleModel' as its parameter.
     ```
        public ArticleFormComponent (String id, IModel<Article> articleModel) {
     ``` 
  - Has the fields for filling the form.
     ```
        @Override
        protected void onInitialize() {
           super.onInitialize();
       
           TextField<String> name = new TextField<String>("name", new PropertyModel<String>(getDefaultModel(), "name"));
           name.setRequired(true);
           add(name);
       
           TextArea<String> articleText = new TextArea<String>("articleText", new PropertyModel<String>(getDefaultModel(), "articleText"));
           articleText.setRequired(true);
           add(articleText);
        }
     ```

## Pages 

### Wicket Application
- Application object for web application
- Creates `EntityManagerFactory` with references from __Persistence.xml__ for making `EntityManagers`
   ```
      public class WicketApplication extends WebApplication
      {
      	private static final String PERSISTENCE_UNIT = "discussment-learning";
      	public static EntityManagerFactory factory;
      
      	/**
      	 * Constructor
      	 */
      	public WicketApplication()
      	{
      		if (factory == (null)) factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
      	}
   ```
   
### Base Page 
- Embeds other pages and adds header with links for other pages and registration, login or logout.

  - Fills the header menu with our other pages.
     ```
        private void setMenu() {
           Link dashboardPagePageLink = new Link("dashboardPage") {
              @Override
              public void onClick() {
                 setResponsePage(DashboardPage.class);
              }
           };
           add(dashboardPagePageLink);
     
           Link articlePageLink = new Link("articlePage") {
              @Override
              public void onClick() {
                 setResponsePage(ArticlePage.class);
              }
           };
           add(articlePageLink);
        }
     ```
  - Adds form for registration and login. Creates services from their respective JPA.
     ```
        add(new LoginForm("loginForm", new Model<User>(new User()), new DefaultUserService(new UserDaoJPA(em), new PermissionDaoJPA(em))));
        add(new RegistrationForm("registrationForm", new DefaultUserService(new UserDaoJPA(em), new PermissionDaoJPA(em)), new Model<User>(new User())));
     ``` 

### Dashboard Page
- Shows list of users and their permissions.
  - Add the list of users and permissions and fills it.
     ```
        add(new ListView<User>("usersListView", userService.getUsers()) {
     
           public void populateItem(final ListItem<User> item) {
     
              final User user = item.getModelObject();
     
              item.add(new Label("username", user.getUsername()));
     
              item.add(new Label("cc", user.getPermissions().isCreateCategory()));
              item.add(new Label("rc", user.getPermissions().isRemoveCategory()));
     
              item.add(new Label("cp", user.getPermissions().isCreatePost()));
              item.add(new Label("rp", user.getPermissions().isRemovePost()));
              item.add(new Label("dp", user.getPermissions().isDisablePost()));
           }
        });
     ``` 

### Article Page
- Shows list of articles and creates services for getting the list from panels.
  - Adds `ArticlePanel` chain to the page.
     ```
        @Override
        protected void onInitialize() {
           super.onInitialize();
           add(new ArticlePanel("content", articleService));
        }
     ```

### Article Text Page
- Gets the right article from its `parameters` and adds for `ArticleTextPanel` to show it and its discussion.
  ```
     @Override
     protected void onInitialize() {
        super.onInitialize();
        articleModel.setObject(articleService.getArticleById(Integer.parseInt(parameters.get("articleId").toString())));
        add(new ArticleTextPanel("content", articleModel, postService));
     }
  ```
## Persistence 
- We have to add entities from the core to the __Persistence.xml__.
  ```
     <persistence-unit name="discussment-learning" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>org.danekja.discussment.core.domain.BaseEntity</class>
        <class>org.danekja.discussment.core.domain.Category</class>
        <class>org.danekja.discussment.core.domain.Discussion</class>
        <class>org.danekja.discussment.core.domain.Permission</class>
        <class>org.danekja.discussment.core.domain.Post</class>
        <class>org.danekja.discussment.core.domain.Topic</class>
        <class>org.danekja.discussment.core.domain.User</class>
  ```
  