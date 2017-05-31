# discussment
Library providing discussion/comment functionality to your application with simple interface.

## Readme: discussment-core 

The library creates basic services for the discussion.

### dependencies and environment 
Access to the database is solved via JPA. JPA currently uses the MySQL database, which is set in __persistence.xml__. For this reason, it is necessary to have a MySQL database installed.

### build steps 
- In __persistence.xml__, you must set the database _url_, _user_, and his _password_.

- In the __discussion-core__ folder where the __pom.xml__ file is located, run command `mvn install`.

- The resulting __jar__ file is stored in a local maven repository.

### how-to use core
The service package have interfaces that provides core services. The default implementation of services is in the __service.imp__ package.

The services need an interface to access the database, which is located in the dao package. The default implementation of services is in the __dao.imp__ package. If you need your own access, you need to implement this interface.

## Readme: discussment-ui-wicket 
The library creates UI for the discussion. The discussion may be under the article or as a separate forum.

### dependencies
The library uses a __discussion-core__ and the wicket library to create UI. The bootstrap library is used for styling html.

### build steps
- Before the translation, the __discussion-core__ library must be stored in the local maven repository.

- In the __discussion-ui-wicket__ folder where the __pom.xml__ file is located, run command `mvn install`.

- The resulting __jar__ file is stored in a local maven repository.

### how-to use ui-wicket
The library contains the __DiscussionPanel__ and __ForumPanel__ classes that are ready to be used in a application.

__DiscussionPanel__ creates a panel which contains a discussion. This panel can be used below a article like a discussion about the article. 

__ForumPanel__ creates a panel that contains a forum. The forum is ready for use on a separate page.

## Readme: discussment-example
The example shows a simple use of the __discussion-ui-wicket__ library.

### build steps
- Before the compile, it is necessary to have __discussion-core__ and __discussions-ui-wicket__ installed in the local maven repository.

- Run command `mvn tomcat7:run` in the __discussment-example__ folder where __pom.xml__ is located.

- Command `mvn tomcat7:run` runs compile source codes and creates a __war__ file that deploy in Tomcat. Example runs on web address: `localhost:8080/discussment-example/`
