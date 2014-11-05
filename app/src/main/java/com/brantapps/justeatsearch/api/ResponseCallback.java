package com.brantapps.justeatsearch.api;

/**
 * Abstraction for response callbacks emanating from the web-service client request
 * implementations.
 *
 * Created by david.branton on 29/05/2014.
 */
public interface ResponseCallback {


    /**
     * Upon a successful request, this callback is invoked.
     */
    void onSuccess(final String successMessage);


    /**
     * Upon a failed request, this callback is invoked.
     *
     * @param failureMessage The message explaining the failure
     */
    void onFailure(final String failureMessage);
}
