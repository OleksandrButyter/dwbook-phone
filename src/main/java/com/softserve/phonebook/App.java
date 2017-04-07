
package com.softserve.phonebook;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.phonebook.api.User;
import com.softserve.phonebook.config.ApplicationContextProvider;
import com.softserve.phonebook.config.SpringConfiguration;
import com.softserve.phonebook.resources.ContactResource;
import com.softserve.phonebook.resources.UserResource;
import com.softserve.phonebook.security.PhoneBookAuthenticator;
import com.softserve.phonebook.security.PhoneBookAuthorizer;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<PhonebookConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> b) {
    }

    @Override
    public void run(PhonebookConfiguration configuration,
            Environment environment) throws Exception {

        ApplicationContext context = getApplicationContext(configuration,
                environment);
        ApplicationContextProvider.setApplicationContext(context);

        environment.jersey().register(context.getBean(ContactResource.class));
        environment.jersey().register(context.getBean(UserResource.class));

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(
                                context.getBean(PhoneBookAuthenticator.class))
                        .setAuthorizer(
                                context.getBean(PhoneBookAuthorizer.class))
                        .setRealm("SUPER SECRET STUFF").buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }

    private ApplicationContext getApplicationContext(
            PhonebookConfiguration configuration, Environment environment) {

        // Get DataSource from DropWizard
        DataSourceFactory dataSourceFactory = configuration
                .getDataSourceFactory();
        ManagedDataSource dataSource = dataSourceFactory
                .build(environment.metrics(), "dataSource");

        // Build parent Context with DropWizard Beans
        AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
        parent.refresh();
        ConfigurableListableBeanFactory beanFactory = parent.getBeanFactory();
        beanFactory.registerSingleton("dataSource", dataSource);
        beanFactory.registerSingleton("configuration", configuration);
        parent.registerShutdownHook();
        parent.start();

        // Build child Context with Spring Beans
        AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();
        annotationContext.setParent(parent);
        annotationContext.register(SpringConfiguration.class);
        annotationContext.refresh();
        annotationContext.registerShutdownHook();
        annotationContext.start();
        return annotationContext;
    }
}
