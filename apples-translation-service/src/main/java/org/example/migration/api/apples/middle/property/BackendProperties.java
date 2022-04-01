package org.example.migration.api.apples.middle.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Accessors(chain = true)
@Data
@Component
@ConfigurationProperties(prefix = "apples.backend")
public class BackendProperties {
    /**
     * base url (scheme, host, port) of the backend to proxy to. (default: 'http://localhost:8082')
     */
    private String url = "http://localhost:8082";

    /**
     * used to authenticate with the backend (default: 'admin')
     */
    private String username = "admin";

    /**
     * used to authenticate with the backend (default: 'password')
     */
    private String password = "password";

}
