package com.ginkgooai.core.common.security;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class CustomGrantTypes {
    /**
     * The "guest_code" authorization grant type - used for anonymous access via shared links
     */
    public static final AuthorizationGrantType GUEST_CODE = 
        new AuthorizationGrantType("urn:ietf:params:oauth:grant-type:guest_code");
}
