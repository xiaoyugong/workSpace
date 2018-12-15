/**
 * @Class: Authentication.java
 * @Description:
 * @Author: Zhaoliheng Jun 4, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.auth;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authentication
{
    private static final Logger log = LoggerFactory.getLogger(Authentication.class);
    
    private static Authentication auth = null;
    
    private Authentication()
    {
        
    }

    /**
     * 获取Instance
     * @return
     */
    public static synchronized Authentication getInstance()
    {
        if (null == auth)
        {
            auth = new Authentication();
            log.info("getInstance: create Authentication ok.");
        }
 
        return auth;
    }

    /**
     * 鉴权 name and password
     * @param uriInfo
     * @param securityContext
     * @throws Exception
     */
    public void auth(UriInfo uriInfo, SecurityContext securityContext) throws Exception
    {
        //  Authentication
    }
}
