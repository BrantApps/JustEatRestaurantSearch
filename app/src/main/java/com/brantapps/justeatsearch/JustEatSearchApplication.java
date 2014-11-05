package com.brantapps.justeatsearch;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * The application subclass.
 */
public class JustEatSearchApplication extends Application {
    public ObjectGraph objectGraph;

    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules());
        objectGraph.inject(this);
    }


    /**
     * Retrieve all of the contributing modules.
     *
     * @return An array of modules.
     */
    protected Object[] getModules() {
        return new Object[] {new JustEatSearchModule(this)};
    }


    /**
     * @return the current application object graph.
     */
    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
