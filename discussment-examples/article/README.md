# Articles Example

**Update 2023:** This example doesn't work.

## Getting started
 - create an empty MySQL database with the name `discussment`

 - run command `mvn tomcat7:run` in the __article__ folder where __pom.xml__ is located
   !must be compiled under java8, in 9 you will get errors!

 - this command runs compile source codes and creates a __war__ file that deploy in Tomcat. The example runs on web address: `localhost:8080/discussment-article/`

 - if you have done everything right, you'll see an empty dashboard, so it's time to register first user
    ![Empty dashboard](./docs/images/dashboard-empty.png)
       
    ![Registration](./docs/images/registration.png) 
       
    ![Admin permissions](./docs/images/reg-admin.png)
       
    ![Some users added](./docs/images/dashboard.png)
    
## Articles
 - !articles use the same access rights as categories!
 - there aren't any articles so let's make some
### Article management
 - Creating an article
   ![Creating of article](./docs/images/article-page-hl-create.png)
   - Creates a new article
     ![Adding the article](./docs/images/article-new.png) 
     ![Article added](./docs/images/article-list.png)
 - Deleting an article
   ![Deleting an article](./docs/images/article-list-dl-hl.png)
   - Deletes an article and its discussion
     ![Article deleted](./docs/images/article-deleted.png)
 - Opening an article
   - After opening of the article you will see it's text and discussion to which you can post
     ![Article opened](./docs/images/article-text.png)
 - Making a post 
   ![Adding a post](./docs/images/post-new.png)
   - Adds a new post under the article
     ![Post added](./docs/images/post-done.png)
 - Making a reply 
   ![Adding a reply](./docs/images/post-hl-reply.png)
   - Adds a new reply to the discussion
     ![Reply form](./docs/images/post-new-reply.png)
     ![Reply done](./docs/images/post-reply.png)
 - Disabling a post 
   ![Disabling a post](./docs/images/post-hl-disable.png)
   - Disables a post without deleting it, can be later restored
     ![Post disabled](./docs/images/post-hl-enable.png)
 - Deleting a post
  ![Deleting a post](./docs/images/post-hl-delete.png)
   - Deletes a post and all its replies 
     ![Post deleted](./docs/images/post-deleted.png)