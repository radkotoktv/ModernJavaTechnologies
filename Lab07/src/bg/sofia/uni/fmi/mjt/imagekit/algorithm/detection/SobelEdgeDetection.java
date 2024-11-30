package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    private final ImageAlgorithm grayscaleAlgorithm;
    private static final int MAX_RGB = 255;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);
        int[][] sobelHorizontal = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
        int[][] sobelVertical = { { -1, -2, -1 }, {  0,  0,  0 }, {  1,  2,  1 } };
        BufferedImage resultImage = new BufferedImage(grayscaleImage.getWidth(),
                                    grayscaleImage.getHeight(),
                                    BufferedImage.TYPE_INT_RGB);
        for (int i = 1; i < grayscaleImage.getHeight() - 1; i++) {
            for (int j = 1; j < grayscaleImage.getWidth() - 1; j++) {
                int gradientX = 0;
                int gradientY = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = new Color(grayscaleImage.getRGB(j + kx, i + ky)).getRed();
                        gradientX += pixel * sobelVertical[ky + 1][kx + 1];
                        gradientY += pixel * sobelHorizontal[ky + 1][kx + 1];
                    }
                }
                int magnitude = (int) Math.min(MAX_RGB, Math.sqrt(gradientX * gradientX + gradientY * gradientY));
                int grayscaleColor = new Color(magnitude, magnitude, magnitude).getRGB();
                resultImage.setRGB(j, i, grayscaleColor);
            }
        }
        return resultImage;
    }
}
