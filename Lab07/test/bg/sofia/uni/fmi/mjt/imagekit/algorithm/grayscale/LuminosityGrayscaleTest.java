package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class LuminosityGrayscaleTest {

    @Test
    void testProcessWithValidImage() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, new Color(255, 0, 0).getRGB());
        image.setRGB(1, 0, new Color(0, 255, 0).getRGB());
        image.setRGB(0, 1, new Color(0, 0, 255).getRGB());
        image.setRGB(1, 1, new Color(255, 255, 0).getRGB());

        LuminosityGrayscale luminosityGrayscale = new LuminosityGrayscale();

        BufferedImage processedImage = luminosityGrayscale.process(image);

        int expectedRedGray = (int)(0.21 * 255 + 0.72 * 0 + 0.07 * 0);
        int expectedGreenGray = (int)(0.21 * 0 + 0.72 * 255 + 0.07 * 0);
        int expectedBlueGray = (int)(0.21 * 0 + 0.72 * 0 + 0.07 * 255);
        int expectedYellowGray = (int)(0.21 * 255 + 0.72 * 255 + 0.07 * 0);

        assertEquals(expectedRedGray, new Color(processedImage.getRGB(0, 0)).getRed());
        assertEquals(expectedGreenGray, new Color(processedImage.getRGB(1, 0)).getGreen());
        assertEquals(expectedBlueGray, new Color(processedImage.getRGB(0, 1)).getBlue());
        assertEquals(expectedYellowGray, new Color(processedImage.getRGB(1, 1)).getRed());
        assertEquals(expectedYellowGray, new Color(processedImage.getRGB(1, 1)).getGreen());
        assertEquals(expectedYellowGray, new Color(processedImage.getRGB(1, 1)).getBlue());
    }

    @Test
    void testProcessWithNullImage() {
        LuminosityGrayscale luminosityGrayscale = new LuminosityGrayscale();

        assertThrows(IllegalArgumentException.class, () -> luminosityGrayscale.process(null));
    }
}
