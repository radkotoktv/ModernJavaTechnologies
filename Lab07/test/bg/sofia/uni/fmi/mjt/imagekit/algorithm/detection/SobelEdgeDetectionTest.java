package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class SobelEdgeDetectionTest {

    private SobelEdgeDetection sobelEdgeDetection;
    private ImageAlgorithm grayscaleAlgorithmMock;

    @BeforeEach
    void setUp() {
        grayscaleAlgorithmMock = mock(ImageAlgorithm.class);
        sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithmMock);
    }

    @Test
    void testProcessWithNullImage() {
        assertThrows(IllegalArgumentException.class, () -> sobelEdgeDetection.process(null));
    }

    @Test
    void testProcessWithValidImage() {
        int width = 5;
        int height = 5;

        BufferedImage inputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = (x + y) * 10;
                int grayRGB = new Color(grayValue, grayValue, grayValue).getRGB();
                grayscaleImage.setRGB(x, y, grayRGB);
            }
        }

        when(grayscaleAlgorithmMock.process(inputImage)).thenReturn(grayscaleImage);

        BufferedImage resultImage = sobelEdgeDetection.process(inputImage);

        assertNotNull(resultImage, "Resulting image should not be null");
        assertEquals(width, resultImage.getWidth(), "Width of the resulting image should match input");
        assertEquals(height, resultImage.getHeight(), "Height of the resulting image should match input");

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int computedRGB = resultImage.getRGB(x, y);
                Color color = new Color(computedRGB);
                assertTrue(color.getRed() <= 255 && color.getRed() >= 0, "Red channel value should be valid");
                assertTrue(color.getGreen() <= 255 && color.getGreen() >= 0, "Green channel value should be valid");
                assertTrue(color.getBlue() <= 255 && color.getBlue() >= 0, "Blue channel value should be valid");
            }
        }

        verify(grayscaleAlgorithmMock).process(inputImage);
    }
}
