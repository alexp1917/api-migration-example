package org.example.migration.api.apples.backend.property;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Accessors(chain = true)
@Data
@Component
@ConfigurationProperties(prefix = "apples.auth-config")
public class AuthProperties {
    /**
     * Number of seconds a token can last, defaults to 1 hr (3600).
     */
    private int tokenTimeout = 3600;

    /**
     * Items regarding the credentials for getting a token (oversimplified for proof of concept)
     */
    @NestedConfigurationProperty
    private DefaultUser defaultUser = new DefaultUser();

    @Accessors(chain = true)
    @Data
    public static class DefaultUser {
        /**
         * identifies who is authenticating (default: 'username')
         */
        private String id = "username";

        /**
         * identifies that they are authenticated (default: 'password')
         */
        private String secret = "password";
    }
}
