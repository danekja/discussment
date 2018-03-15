package org.danekja.discussment.spring;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlManagerService;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.*;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.spring.core.dao.UserDao;
import org.danekja.discussment.spring.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.core.service.imp.DefaultUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableTransactionManagement
public class CoreConfiguration {

    @Bean
    public CategoryDao categoryDao(){
        return new CategoryDaoJPA();
    }

    @Bean
    public TopicDao topicDao(){
        return new TopicDaoJPA();
    }

    @Bean
    public DiscussionDao discussionDao(){
        return new DiscussionDaoJPA();
    }

    @Bean
    public PostDao postDao(){
        return new PostDaoJPA();
    }

    @Bean
    public UserPostReputationDao userPostReputationDao(){
        return new UserPostReputationDaoJPA();
    }

    @Bean
    public PermissionDao permissionDao(){
        return new PermissionDaoJPA();
    }

    @Bean
    public UserDao userDao(){
        return new UserDaoJPA();
    }

    @Bean
    public UserService userService(){
        return new DefaultUserService(userDao());
    }

    @Bean(name = "accessControlService")
    public AccessControlService accessControlService(){
        return new PermissionService(permissionDao(), userService());
    }

    @Bean(name = "permissionManagementService")
    public PermissionManagementService permissionManagementService(){
        return new PermissionService(permissionDao(), userService());
    }

    @Bean(name = "accessControlManagerService")
    public AccessControlManagerService accessControlManagerService(){
        return new PermissionService(permissionDao(), userService());
    }

    @Bean
    public CategoryService categoryService(){
        return new DefaultCategoryService(categoryDao(), accessControlService(), userService());
    }

    @Bean
    public TopicService topicService(){
        return new DefaultTopicService(topicDao(), categoryService(), accessControlService(), userService());
    }

    @Bean
    public DiscussionService discussionService(){
        return new DefaultDiscussionService(discussionDao(), postDao(), topicService(), accessControlService(), userService());
    }

    @Bean
    public PostService postService(){
        return new DefaultPostService(postDao(), userService(), accessControlService());
    }

    @Bean
    public PostReputationService postReputationService(){
        return new DefaultPostReputationService(userPostReputationDao(), postDao(), userService(), accessControlService());
    }
}
