import bg.sofia.uni.fmi.mjt.imagekit.filesystem.FileSystemImageManager;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileSystemImageManager fsImageManager = new LocalFileSystemImageManager();

        BufferedImage image = fsImageManager.loadImage(new File("F:/Java/Lab07/kitten.png"));
        //fsImageManager.saveImage(image, new File("."));

//        ImageAlgorithm grayscaleAlgorithm = new LuminosityGrayscale();
//        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);
//
//        ImageAlgorithm sobelEdgeDetection = new SobelEdgeDetection(grayscaleAlgorithm);
//        BufferedImage edgeDetectedImage = sobelEdgeDetection.process(image);
//
//        fsImageManager.saveImage(grayscaleImage, new File("kitten-grayscale.png"));
//        fsImageManager.saveImage(edgeDetectedImage, new File("kitten-edge-detected.png"));
    }
}