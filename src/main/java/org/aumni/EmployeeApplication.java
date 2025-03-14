package org.aumni;

import org.aumni.auth.AdminAuthorizer;
import org.aumni.auth.BasicAuthenticator;
import org.aumni.core.Employee;
import org.aumni.core.User;
import org.aumni.dao.EmployeeDao;
import org.aumni.dao.UserDao;
import org.aumni.resources.EmployeeResource;
import org.aumni.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeApplication extends Application<EmployeeConfiguration> {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeApplication.class);

    private final HibernateBundle<EmployeeConfiguration> hibernateBundle =
            new HibernateBundle<>(Employee.class, User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(EmployeeConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new EmployeeApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<EmployeeConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(EmployeeConfiguration configuration, Environment environment) {

        logger.info("Starting aumni application inside of run method");
        final UserDao userDAO = new UserDao(hibernateBundle.getSessionFactory());
        final EmployeeDao employeeDAO = new EmployeeDao(hibernateBundle.getSessionFactory());

         final BasicAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(BasicAuthenticator.class,
                        new Class<?>[]{UserDao.class}, new Object[]{userDAO});


        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(new AdminAuthorizer())
                        .setRealm("EMPLOYEE_REALM")
                        .buildAuthFilter()
        ));

        // Health check register
        AumniProjectHealthCheck aumniProjectHealthCheck=new AumniProjectHealthCheck(userDAO);
        environment.healthChecks().register("AUMNI_PROJECT_HEALTH_CHECK",aumniProjectHealthCheck);

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);


        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new EmployeeResource(employeeDAO));
        logger.info("Application started successfully all the resources are registered");

    }


    }