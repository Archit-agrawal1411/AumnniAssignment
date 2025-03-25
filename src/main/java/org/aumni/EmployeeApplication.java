package org.aumni;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.aumni.auth.AdminAuthorizer;
import org.aumni.auth.BasicAuthenticator;
import org.aumni.core.Employee;
import org.aumni.core.User;
import org.aumni.dao.UserDao;
import org.aumni.resources.EmployeeResource;
import org.aumni.resources.UserResource;
import org.aumni.services.EmailServiceIntegration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class EmployeeApplication extends Application<EmployeeConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeApplication.class);

    private final HibernateBundle<EmployeeConfiguration> hibernateBundle =
            new HibernateBundle<>(Employee.class, User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(EmployeeConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new EmployeeApplication().run(args);

        // Thread part starts here
        Runnable runnable = new EmailServiceIntegration();

        Thread periodicThread = new Thread(() -> {
            while (true) {
                runnable.run();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("In catch block of Application class Thread sleep is interrupted" + e);
                    break;
                }
            }
        });
        periodicThread.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            System.out.println("Thread has completed its execution");
        }

        periodicThread.interrupt(); // Used to stop thread manually
        // Thread part complete
    }

    @Override
    public void initialize(Bootstrap<EmployeeConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(EmployeeConfiguration configuration, Environment environment) {

        // Injector for Google Guice Dependency injector
        Injector injector = Guice.createInjector(new GuiceModule(hibernateBundle.getSessionFactory()));
        // Registering other classes with Guice
        environment.jersey().register(injector.getInstance(EmployeeResource.class));
        environment.jersey().register(injector.getInstance(UserResource.class));

        logger.info("Starting aumni application, inside of run method");

        // Set up UserDao and authenticator for basic authentication
        UserDao userDao = injector.getInstance(UserDao.class);
        final BasicAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(BasicAuthenticator.class,
                        new Class<?>[]{UserDao.class}, new Object[]{userDao});

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(new AdminAuthorizer())
                        .setRealm("EMPLOYEE_REALM")
                        .buildAuthFilter()
        ));

        // Health check registration
        AumniProjectHealthCheck aumniProjectHealthCheck = new AumniProjectHealthCheck(userDao);
        environment.healthChecks().register("AUMNI_PROJECT_HEALTH_CHECK", aumniProjectHealthCheck);

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        // Liquibase integration to apply database migrations
        runLiquibaseMigrations(configuration);

        logger.info("Application started successfully, all the resources are registered");
    }

    private void runLiquibaseMigrations(EmployeeConfiguration configuration) {
        try {
            // Get the database connection parameters from the configuration
            String url = configuration.getDataSourceFactory().getUrl();
            String username = configuration.getDataSourceFactory().getUser();
            String password = configuration.getDataSourceFactory().getPassword();

            // Establish a JDBC connection
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                logger.info("Successfully connected to the database for Liquibase migration.");

                // Set up Liquibase database with MySQL connection
                Database database = new liquibase.database.core.MySQLDatabase();
                database.setConnection(new JdbcConnection(connection));

                // Initialize Liquibase with the changelog file
                Liquibase liquibase = new Liquibase(
                        configuration.getLiquibaseConfiguration().getChangeLogFile(), // Path to your changelog file
                        new ClassLoaderResourceAccessor(),
                        database
                );

                // Run the migrations
                liquibase.update("");
                logger.info("Liquibase migrations completed successfully.");
            } catch (Exception e) {
                logger.error("Error establishing database connection for Liquibase migration.", e);
            }
        } catch (Exception e) {
            logger.error("Error running Liquibase migrations", e);
        }
    }

}
