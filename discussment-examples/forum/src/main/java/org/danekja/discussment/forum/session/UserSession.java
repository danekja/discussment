package org.danekja.discussment.forum.session;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public final class UserSession extends AuthenticatedWebSession{

    private String user = null;

    public UserSession(Request request)
    {
        super(request);
    }

    @Override
    public final boolean authenticate(final String username, final String password)
    {
        if (user == null)
        {
            user = username;
        }

        return user != null;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Roles getRoles()
    {
        return null;
    }
}