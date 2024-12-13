package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        try {
            if (imageFile == null) {
                throw new IllegalArgumentException("File is null");
            }

            if (!imageFile.exists() || !imageFile.isFile()) {
                throw new IOException("File does not exist or is not a regular file!");
            }

            if (!imageFile.getName().endsWith(".jpg")     &&
                    !imageFile.getName().endsWith(".png") &&
                    !imageFile.getName().endsWith(".bmp")) {
                throw new IOException("File is not in one of the supported formats");
            }

//            if (imageFile.getParent() == null) {
//                throw new IOException("Parent directory can not be null");
//            }

        } catch (IllegalArgumentException | IOException e) {
            throw e;
        }

        return ImageIO.read(imageFile);
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        try {
            if (imagesDirectory == null) {
                throw new IllegalArgumentException("Directory is null");
            }

            if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
                throw new IOException("Directory does not exist or is not a directory");
            }

        } catch (IllegalArgumentException | IOException e) {
            throw e;
        }

        List<BufferedImage> images = new ArrayList<>();
        for (File file : imagesDirectory.listFiles()) {
            images.add(loadImage(file));
        }

        return images;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        try {
            if (image == null) {
                throw new IllegalArgumentException("Image is null");
            }

            if (imageFile == null) {
                throw new IllegalArgumentException("imageFile is null");
            }

            if (imageFile.exists()) {
                throw new IOException("imageFile already exists");
            }

//            if (imageFile.getParent() == null) {
//                throw new IOException("Parent directory is null");
//            }
        } catch (IllegalArgumentException | IOException e) {
            throw e;
        }

        String format = imageFile.getName().substring(imageFile.getName().lastIndexOf('.') + 1);
        ImageIO.write(image, format, imageFile);
        if (!imageFile.exists()) {
            throw new IOException("Failed to write the image to the file. Unsupported format");
        }
    }
}
