package com.nisum.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Annotated bean class to demonstrate different scopes using annotations.
 * This version uses annotations instead of XML configuration.
 */
@Component
public class AnnotatedScopeBean {

    private final String id;
    private final LocalDateTime creationTime;
    private String type;

    public AnnotatedScopeBean() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.creationTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnnotatedScopeBean{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", created=" + creationTime +
                '}';
    }

    // Different scoped beans defined using annotations

    @Component("singletonAnnotatedBean")
    @Scope("singleton")
    public static class SingletonBean extends AnnotatedScopeBean {
        public SingletonBean() {
            super();
            setType("singleton");
        }
    }

    @Component("prototypeAnnotatedBean")
    @Scope("prototype")
    public static class PrototypeBean extends AnnotatedScopeBean {
        public PrototypeBean() {
            super();
            setType("prototype");
        }
    }

    @Component("requestAnnotatedBean")
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static class RequestBean extends AnnotatedScopeBean {
        public RequestBean() {
            super();
            setType("request");
        }
    }

    @Component("sessionAnnotatedBean")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static class SessionBean extends AnnotatedScopeBean {
        public SessionBean() {
            super();
            setType("session");
        }
    }

    @Component("globalSessionAnnotatedBean")
    @Scope(value = "globalSession", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public static class GlobalSessionBean extends AnnotatedScopeBean {
        public GlobalSessionBean() {
            super();
            setType("globalSession");
        }
    }
}
