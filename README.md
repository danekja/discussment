# discussment
Library providing discussion/comment functionality to your application with simple interface.

## Readme: discussment-core 

The library creates basic services for the discussion.

### dependencies and environment 
Access to the database is solved via JPA. JPA currently uses the MySQL database, which is set in __persistence.xml__. For this reason, it is necessary to have a MySQL database installed.

Used dependencies:

- Hibernate 5.2.6.Final

- MySQL Connector 6.0.5

### build steps 
- In __persistence.xml__, you must set the database _url_, _user_, and his _password_.

- In the __discussion-core__ folder where the __pom.xml__ file is located, run command `mvn install`.

- The resulting __jar__ file is stored in a local maven repository.

### how-to use core
The __service__ package have interfaces that provides core services. The default implementation of services is in the __service.imp__ package.

The __service__ package contains these interfaces:
- __CategoryService__ contains methods for working with categories in a forum.

- __DiscussionService__ contains methods for working with discussions such as creating a separate discussion or a discussion in a topic.

- __PostService__ contains methods for working with posts in the discussion.

- __TopicService__ contains methods for working with topics in the forum.

- __UserService__ contains methods for working with users who are in the discussion.

The services need an interface to access the database, which is located in the dao package. The default implementation of services is in the __dao.imp__ package. If you need your own access, you need to implement this interface.

The __accesscontrol__ package contains the new implementation of user's permissions. The default implementation of services is in the __service.impl__ package.

Its __service__ package contains these interfaces:
- __DiscussionUserService__ contains basic methods to obtain user object. Application will have to implement this!

- __PermissionManagementService__ contains methods for adding or changing user's permissions.
  - for example using this method will give user permissions for discussion in given __topic__. __PermissionData__ have values sorted _create_, _delete_, _edit_, _view_.
    ```
    configureDiscussionPermissions(IDiscussionUser user, Topic topic, PermissionData permissions);
    ```
  - example usage in application.
    ```
    permissionService.configureDiscussionPermissions(user, topicService.getTopicById(3), new PermissionData(true, false, false, true);
    ```
    - will give user permissions to _create_ and _view_ __discussions__ in __topic__ of id __3__.

- __AccessControlService__ contains methods for checking currently logged user's permissions. Has two main usages.
  - guards the __core services__ so user without sufficient permissions can't use requested method. The method then throws __AccessDeniedException__. 
    ```
     @Override
        public Topic getTopicById(long topicId) throws AccessDeniedException {
            Topic t = topicDao.getById(topicId);
            if (accessControlService.canViewTopics(t.getCategory())) {
                return t;
            } else {
                throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), topicId, PermissionType.TOPIC);
            }
        }
    ```
  - checks if user has sufficient permissions for viewing certain ui components(for example deleting a topic)
    ```
    private Link createRemoveLink(final IModel<Topic> tm) {
    ...
       @Override
       protected void onConfigure() {
          super.onConfigure();
          setVisible(accessControlService.canRemoveTopic(tm.getObject()));
       }
    }
    ```
    
- __AccessControlManagerService__ contains methods for checking another user's permissions.
  - example usage in application 
    ```
    accessControlManagerService.canRemoveDiscussion(userService.getUserById(5), discussionService.getDiscussionById(2))
    ```
    - will check if __user__ of id __5__ has permissions to remove __discussion__ of id __2__.

The services need an interface to access the database, which is located in the dao package. The default implementation of services is in the __dao.imp__ package.

## Readme: discussment-ui-wicket 
The library creates UI for the discussion. The discussion may be under the article or as a separate forum.

### dependencies
The library uses a __discussion-core__ and the wicket library to create UI. The bootstrap library is used for styling html.

Used dependencies:

- discussment-core 1.0-SNAPSHOT

- Wicket Core 7.6.0

### build steps
- Before the translation, the __discussion-core__ library must be stored in the local maven repository.

- In the __discussion-ui-wicket__ folder where the __pom.xml__ file is located, run command `mvn install`.

- The resulting __jar__ file is stored in a local maven repository.

### how-to use ui-wicket
The library contains these packages:

- __form__ contains forms such as adding a new category to the forum.

- __list__ contains panels that create parts of the user interface. The panels use other the panels to create a larger unit. For example, CategoryListPanel, which displays categories and topics, uses TopicListPanel to display topics.

- __model__ contains classes for data acquisition. Classes can be used as a parameter for creating a ListView instance.

- __panel__ contains the __DiscussionPanel__ and __ForumPanel__ classes that are ready to be used in a application.

__DiscussionPanel__ creates a panel which contains a discussion. This panel can be used below a article like a discussion about the article. The panel contains an input field for adding a new post and listing all posts and their replies.

__ForumPanel__ creates a panel that contains a forum. The forum is ready for use on a separate page. The panel manages category and topics with discussions. The user can create categories and topics according to their own needs, where forum users can create discussions.

## Readme: discussment-example
The example shows a simple use of the __discussion-ui-wicket__ library. 

- How to add a user with his permissions to the discussion.
- How to use the __DiscussionPanel__ below a article.
- How to use __ForumPanel__ as a seperate forum.

### dependencies
Used dependencies:

- discussment-ui-wicket 1.0-SNAPSHOT

### build steps
- Before the compile, it is necessary to have __discussion-core__ and __discussions-ui-wicket__ installed in the local maven repository.

- Run command `mvn tomcat7:run` in the __discussment-example__ folder where __pom.xml__ is located.

- Command `mvn tomcat7:run` runs compile source codes and creates a __war__ file that deploy in Tomcat. The example runs on web address: `localhost:8080/discussment-example/`
