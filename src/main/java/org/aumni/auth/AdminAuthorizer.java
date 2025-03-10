
package org.aumni.auth;

import org.aumni.core.User;
import io.dropwizard.auth.Authorizer;
import org.aumni.core.User;

public class AdminAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return "Admin".equals(user.getRole());
    }
}