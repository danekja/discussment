# Discussment-example
## Getting started
 - create an empty MySQL database with the name `discussment`

 - run command `mvn tomcat7:run` in the dicussment-example folder where __pom.xml__ is located
   !must be compiled under java8, in 9 you will get errors!

 - this command runs compile source codes and creates a __war__ file that deploy in Tomcat. The example runs on web address: `localhost:8080/discussment-example/`

 - if you have done everything right, you'll see an empty dashboard, so it's time to register first user
   ![Empty dashboard](./docs/images/dashboard-empty.png)
   
   ![Registration](./docs/images/registration.png) 
   
   ![Admin permissions](./docs/images/reg-admin.png)
   
   ![Some users added](./docs/images/dashboard.png)

## Discussion
 - the discussion is empty, for now :)
 ![Empty forum](./docs/images/forum-empty.png)

### Forum controls
   - Creating a category
    ![Creating a category](./docs/images/forum-hl-ccat.png)
     - creates a new category
       ![Adding a new category](./docs/images/category-new.png)
       ![Category added](./docs/images/category.png)
   - Creating a topic
    ![Creating a topic](./docs/images/forum-hl-ctop.png)
     - creates a new topic that isn't under any category
       ![Adding a new topic](./docs/images/topic-new.png)
       ![Topic without category added](./docs/images/topic-standalone.png)
   - Creating topic under a category
    ![Adding a new topic under the category](./docs/images/category-hl-ctop.png)
     - creates a new topic under that category
       ![Topic added under the category](./docs/images/topic-undercat.png)
   - Deleting a category
    ![Deleting a category](./docs/images/category-hl-delete.png)
     - deletes a category and all it's topics, discussions and posts
   - Hiding topics in a category
    ![Collapsing a category](./docs/images/category-hl-ce.png)
     - collapses/expands topics in the category 
      ![Collapsed category](./docs/images/category-collapsed.png)
  

### Discussion management
    
   - Creating a new discussion 
    ![Creating a new discussion](./docs/images/topic-hl-cdisc.png)
    
     ![Adding a new discussion](./docs/images/discussion-new.png) 
     - creates a new discussion in the topic
     - checking private creates password protected discussion
       ![Discussion added](./docs/images/discussion.png)
   - Making a new post 
    ![Adding a new post to the discussion](./docs/images/post-new.png)
     - creates a new post in the discussion
       ![Post added](./docs/images/post-done.png)
   - Making a reply to a post 
    ![Making a reply](./docs/images/post-hl-reply.png)
     
     ![Adding a reply](./docs/images/post-new-reply.png) 
     - makes a reply to this post
       ![Reply added](./docs/images/post-reply.png)
   - Disabling a post
    ![Disabling a post](./docs/images/post-hl-disable.png)
     - hides the post without deleting it 
   - Restoring a post 
    ![Post disabled](./docs/images/post-hl-enable.png)
     - restores the post
       ![Reply added](./docs/images/post-reply.png)
   - Deleting a post
    ![Deleting a post](./docs/images/post-hl-delete.png)
     - deletes the post and all it's replies
       ![Post and replies deleted](./docs/images/post-deleted.png)

