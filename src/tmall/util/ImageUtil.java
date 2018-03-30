package tmall.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * @Package: tmall.util
 * @Author: WangXu
 * @CreateDate: 2018/3/30 9:39
 * @Version: 1.0
 */

public class ImageUtil {
    /**
     * 确保图片的二进制格式是jpg
     * @param file
     * @return
     */
    public static BufferedImage change2jpg(File file) {
        try {
            Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, true);
            pixelGrabber.grabPixels();
            int width = pixelGrabber.getWidth(), height = pixelGrabber.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer dataBuffer = new DataBufferInt((int[]) pixelGrabber.getPixels(), pixelGrabber.getWidth() * pixelGrabber.getHeight());
            WritableRaster writableRaster = Raster.createPackedRaster(dataBuffer, width, height, width, RGB_MASKS, null);
            BufferedImage bufferedImage = new BufferedImage(RGB_OPAQUE, writableRaster, false, null);
            return bufferedImage;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 改变图片大小
     * @param scrFile
     * @param width
     * @param height
     * @param destFile
     */
    public static void resizeImage(File scrFile,int width,int height,File destFile){
        try {
            Image image = ImageIO.read(scrFile);
            image = resizeImage(image,width,height);
            ImageIO.write((RenderedImage) image,"jpg",destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image resizeImage(Image srcImage,int width,int height){
        try {
            BufferedImage bufferedImage = null;
            bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
            bufferedImage.getGraphics().drawImage(srcImage.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);
            return bufferedImage;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
