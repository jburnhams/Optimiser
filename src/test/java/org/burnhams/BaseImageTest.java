package org.burnhams;

import org.burnhams.imaging.SimpleImage;
import org.burnhams.utils.ImageUtils;
import org.junit.BeforeClass;

import java.io.IOException;

public abstract class BaseImageTest {

    protected static final String TEST_DIR = "src/test/resources/";
    protected static final int WIDTH = 40;
    protected static final int HEIGHT = 30;

    protected static SimpleImage testImage;

    @BeforeClass
    public static void loadTestImage() throws Exception {
        testImage = getSimpleImage();
    }

    protected static SimpleImage getSimpleImage() throws IOException {
        return new SimpleImage(ImageUtils.getPixels(TEST_DIR + "testcard.jpg", WIDTH, HEIGHT));
    }
}
