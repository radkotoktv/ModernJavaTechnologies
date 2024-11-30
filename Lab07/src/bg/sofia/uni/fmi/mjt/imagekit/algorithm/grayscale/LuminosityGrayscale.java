package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    private static final double RED_MULTIPLY = 0.21;
    private static final double GREEN_MULTIPLY = 0.72;
    private static final double BLUE_MULTIPLY = 0.07;
    private static final int ALPHA_BITWISE = 24;
    private static final int RED_BITWISE = 16;
    private static final int GREEN_BITWISE = 8;

    @Override
    public BufferedImage process(BufferedImage image) {
        try {
            if (image == null) {
                throw new IllegalArgumentException("Image can not be null");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int rgb = image.getRGB(j, i);

                int red = new Color(rgb).getRed();
                int green = new Color(rgb).getGreen();
                int blue = new Color(rgb).getBlue();

                int gray = (int)(RED_MULTIPLY * red + GREEN_MULTIPLY * green + BLUE_MULTIPLY * blue);

                int alpha = new Color(rgb).getAlpha();
                int newRgb = (alpha << ALPHA_BITWISE) | (gray << RED_BITWISE) | (gray << GREEN_BITWISE) | gray;

                newImage.setRGB(j, i, newRgb);
            }
        }
        return newImage;
    }
}
