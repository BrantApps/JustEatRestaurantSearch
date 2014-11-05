package com.brantapps.justeatsearch.api;

/**
 * Contract for classes contributing to the object graph
 * via 'dependency injection'.
 *
 * Created by david.branton on 22/08/2014.
 */
public interface Injectable {

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     *
     * @param object the class to inject.
     */
    public void inject(final Object object);
}
