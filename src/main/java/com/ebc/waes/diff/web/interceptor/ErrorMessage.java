package com.ebc.waes.diff.web.interceptor;

import java.io.Serializable;

/**
 * @version 1.0
 * @author eduardo.costa
 * @since 08/10/2017
 */
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -5081998148804500911L;

    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
