package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalFileSystemImageManagerTest {

    private LocalFileSystemImageManager manager;

    @BeforeEach
    void setUp() {
        manager = new LocalFileSystemImageManager();
    }

    @Test
    void testLoadImageWithNullFile() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null));
    }

    @Test
    void testLoadImageWithNonExistentFile() {
        File nonExistentFile = mock(File.class);
        Mockito.lenient().when(nonExistentFile.exists()).thenReturn(false);

        assertThrows(IOException.class, () -> manager.loadImage(nonExistentFile));
    }

    @Test
    void testLoadImageWithUnsupportedFormat() {
        File unsupportedFile = mock(File.class);
        Mockito.lenient().when(unsupportedFile.exists()).thenReturn(true);
        Mockito.lenient().when(unsupportedFile.isFile()).thenReturn(true);
        Mockito.lenient().when(unsupportedFile.getName()).thenReturn("unsupported.txt");

        assertThrows(IOException.class, () -> manager.loadImage(unsupportedFile));
    }

    @Test
    void testLoadImageWithNullParent() {
        File fileWithNullParent = mock(File.class);
        Mockito.lenient().when(fileWithNullParent.exists()).thenReturn(true);
        Mockito.lenient().when(fileWithNullParent.isFile()).thenReturn(true);
        Mockito.lenient().when(fileWithNullParent.getName()).thenReturn("image.jpg");
        Mockito.lenient().when(fileWithNullParent.getParent()).thenReturn(null);

        assertThrows(IOException.class, () -> manager.loadImage(fileWithNullParent));
    }

    @Test
    void testLoadImagesFromNullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImagesFromDirectory(null));
    }

    @Test
    void testLoadImagesFromInvalidDirectory() {
        File invalidDirectory = mock(File.class);
        Mockito.lenient().when(invalidDirectory.exists()).thenReturn(false);

        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(invalidDirectory));
    }

    @Test
    void testSaveImageWithNullImage() {
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(null, mock(File.class)));
    }

    @Test
    void testSaveImageWithNullFile() {
        assertThrows(IllegalArgumentException.class, () -> manager.saveImage(mock(BufferedImage.class), null));
    }

    @Test
    void testSaveImageWithExistingFile() {
        File existingFile = mock(File.class);
        Mockito.lenient().when(existingFile.exists()).thenReturn(true);

        assertThrows(IOException.class, () -> manager.saveImage(mock(BufferedImage.class), existingFile));
    }

    @Test
    void testSaveImageWithNullParent() {
        File fileWithNullParent = mock(File.class);
        Mockito.lenient().when(fileWithNullParent.exists()).thenReturn(false);
        Mockito.lenient().when(fileWithNullParent.getParent()).thenReturn(null);

        assertThrows(IOException.class, () -> manager.saveImage(mock(BufferedImage.class), fileWithNullParent));
    }

    @Test
    void testSaveImageWithUnsupportedFormat() {
        File unsupportedFormatFile = mock(File.class);
        BufferedImage mockImage = mock(BufferedImage.class);

        Mockito.lenient().when(unsupportedFormatFile.exists()).thenReturn(false);
        Mockito.lenient().when(unsupportedFormatFile.getParent()).thenReturn("/parent");
        Mockito.lenient().when(unsupportedFormatFile.getName()).thenReturn("image.unsupported");

        assertThrows(IOException.class, () -> manager.saveImage(mockImage, unsupportedFormatFile));
    }

    @Test
    void testSaveImageParentDirDoesNotExist() {
        File parentDirDoesNotExist = mock(File.class);
        BufferedImage mockImage = mock(BufferedImage.class);

        Mockito.lenient().when(parentDirDoesNotExist.exists()).thenReturn(true);
        Mockito.lenient().when(parentDirDoesNotExist.getParent()).thenReturn(null);
        Mockito.lenient().when(parentDirDoesNotExist.getName()).thenReturn("image.jpg");

        assertThrows(IOException.class, () -> manager.saveImage(mockImage, parentDirDoesNotExist));
    }
}
