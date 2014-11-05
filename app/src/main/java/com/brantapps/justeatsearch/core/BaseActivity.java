package com.brantapps.justeatsearch.core;

import android.app.Activity;
import android.os.Bundle;

import com.brantapps.justeatsearch.JustEatSearchApplication;
import com.brantapps.justeatsearch.api.Injectable;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Non-support library requiring implementation of the base control fragment.
 */
public abstract class BaseActivity extends Activity implements Injectable {
    private ObjectGraph mActivityGraph;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the activity graph by .plus-ing our modules onto the application graph.
        final JustEatSearchApplication application = (JustEatSearchApplication) getApplication();
        mActivityGraph = application.getObjectGraph().plus(getModules().toArray());

        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        mActivityGraph.inject(this);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        mActivityGraph = null;
        super.onDestroy();
    }


    /**
     * @return A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    protected abstract List<Object> getModules();


    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object) {
        mActivityGraph.inject(object);
    }
}