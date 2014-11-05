package com.brantapps.justeatsearch;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

/**
 * Extension of the {@link org.robolectric.RobolectricTestRunner} for Gradle-based projects.
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {

    /**
     * Constructs a gradle-compatible {@link org.robolectric.RobolectricTestRunner}.
     *
     * @param testClass The class under test.
     * @throws org.junit.runners.model.InitializationError When the test cannot be started.
     */
    public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }


    /**
     * Retrieve the application manifest.
     *
     * @param config The configuration to use for a test.
     * @return The AndroidManifest bean object.
     */
    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String myAppPath = RobolectricGradleTestRunner.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        String manifestPath = myAppPath + "../../src/main/AndroidManifest.xml";
        String resPath = myAppPath + "../../src/main/res";
        String assetPath = myAppPath + "../../src/main/assets";
        return createAppManifest(Fs.fileFromPath(manifestPath), Fs.fileFromPath(resPath), Fs.fileFromPath(assetPath));
    }
}
