package com.cloudsea.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.w3c.dom.Element;

//import com.sun.image.codec.jpeg.ImageFormatException;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片缩放
 */
public class ImageResizeUtil {

    /**
     * 
     * 〈以宽为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:图的缩放宽(像素)
     * @param newFilePath:缩放图输出路径
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void resizeByWithToFile(InputStream oldIn, int newWidth, String newFilePath)
            throws InterruptedException, FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newHeight = oldHeight * newWidth / oldWidth;

        OutputStream out = createOutputStream(newFilePath);
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        out.close();
    }

    /**
     * 
     * 〈以宽为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:图的缩放宽
     * @return
     * @throws IOException
     */
    public static InputStream resizeByWith(InputStream oldIn, int newWidth) throws IOException {
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newHeight = oldHeight * newWidth / oldWidth;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    /**
     * 
     * 〈以高为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newHeight:图的缩放高
     * @param newFilePath:缩放图输出路径
     * 
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void resizeByHeightToFile(InputStream oldIn, int newHeight, String newFilePath)
            throws InterruptedException, FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newWidth = oldWidth * newHeight / oldHeight;

        OutputStream out = createOutputStream(newFilePath);
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        out.close();
    }

    /**
     * 
     * 〈以高为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newHeight:图的缩放高
     * @return
     * @throws IOException
     */
    public static InputStream resizeByHeight(InputStream oldIn, int newHeight) throws InterruptedException,
            FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newWidth = oldWidth * newHeight / oldHeight;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    /**
     * 
     * 〈以宽高大小适应性补边，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:缩放图的宽
     * @param newHeight:缩放图的高
     * @param quality:图片质量，取值0到1之间
     * @param newFilePath:缩放图输出路径
     * @return
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static InputStream resize(InputStream oldIn, int newWidth, int newHeight, float quality, OutputStream newOut)
            throws InterruptedException, FileNotFoundException, IOException {

        Image oldImage = ImageIO.read(oldIn);

        // 确定缩略图大小的宽度和高度
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        double newRatio = (double) newWidth / (double) newHeight;
        double oldRatio = (double) oldWidth / (double) oldHeight;

        if (newRatio < oldRatio) {
            newHeight = (int) (newWidth / oldRatio);
        } else if (newRatio > oldRatio) {
            newWidth = (int) (newHeight * oldRatio);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    private static void resizeToStream(Image oldImage, int newWidth, int newHeight, float quality, OutputStream out)
            throws 
//            ImageFormatException, 
            IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        newImage.getGraphics().drawImage(oldImage.getScaledInstance(newWidth, newHeight,  Image.SCALE_SMOOTH), 0,0,null);

//        // 保存图像
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(newImage);
//        quality = Math.max(0, Math.min(quality, 1));
//        param.setQuality(quality, false);
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(newImage);
        
		// 保存图像,新的方法
		saveAsJPEG(100, newImage, quality, out);
    }

    private static OutputStream createOutputStream(String newFilePath) throws FileNotFoundException {
        File newFile = new File(newFilePath);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        return new BufferedOutputStream(new FileOutputStream(newFile));
    }
    
	/**
	 * 以JPEG编码保存图片
	 * 
	 * @param dpi
	 *            分辨率
	 * @param image_to_save
	 *            要处理的图像图片
	 * @param JPEGcompression
	 *            压缩比
	 * @param fos
	 *            文件输出流
	 * @throws IOException
	 */
	public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression, OutputStream fos)
			throws IOException {

		ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
		imageWriter.setOutput(ios);
		// and metadata
		IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);

		if (dpi != null && !dpi.equals("")) {
			// old metadata
			// jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
			// jpegEncodeParam.setXDensity(dpi);
			// jpegEncodeParam.setYDensity(dpi);

			// new metadata
			Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
			Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
			jfif.setAttribute("Xdensity", Integer.toString(dpi));
			jfif.setAttribute("Ydensity", Integer.toString(dpi));
		}

		if (JPEGcompression >= 0 && JPEGcompression <= 1f) {
			// old compression
			// jpegEncodeParam.setQuality(JPEGcompression,false);

			// new Compression
			JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
			jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
			jpegParams.setCompressionQuality(JPEGcompression);
		}

		// old write and clean
		// jpegEncoder.encode(image_to_save, jpegEncodeParam);

		// new Write and clean up
		imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
		ios.close();
		imageWriter.dispose();
	}

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        resizeByWithToFile(new FileInputStream("g:/a.jpg"), 800, "g:/b.jpg");
        resizeByHeightToFile(new FileInputStream("g:/a.jpg"), 500, "g:/c.jpg");
    }
}