package org.aumni;

import com.codahale.metrics.health.HealthCheck;
import com.mysql.cj.exceptions.UnableToConnectException;
import org.aumni.core.User;
import org.aumni.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AumniProjectHealthCheck extends HealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(AumniProjectHealthCheck.class);
    private final UserDao userDao;

    public AumniProjectHealthCheck(UserDao userDAO) {
        this.userDao = userDAO;
    }

//    Default endpoint for health check -- http://localhost:8081/healthcheck
@Override
protected Result check() throws Exception {
    logger.info("Inside Check method for health check");
    try {
        List<User> userListForHealthCheck = userDao.getAllUsers();
        if (!userListForHealthCheck.isEmpty()) {
            logger.info("Inside Check method for health check, list is not empty, database connection successful");
            return Result.healthy("Health check complete, database connection successful");
        }
    } catch (UnableToConnectException ex) {
        logger.error("Database connection failed during health check: " + ex.getMessage(), ex);
        return Result.unhealthy("Health check failed due to database connection issue");
    } catch (Exception ex) {

        logger.error("Unexpected error occurred during health check: " + ex.getMessage(), ex);
        return Result.unhealthy("Health check failed due to an unexpected error");
    }
    return Result.unhealthy("Health check failed due to empty user list");
}

}
