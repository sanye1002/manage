package com.sanye.manage.utils;

import com.sanye.manage.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 上午 11:45
 * @Description description
 */
@Component
public class WaterMarkUtils {

    @Autowired
    private UploadConfig uploadConfig;
    /*public static void main(String[] args) {

        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        //new WaterMarkUtils().setWaterMarkByText("C:\\Users\\Administrator\\Desktop\\E8BA72E00FA028AACF7057795D279185.png", "C:\\Users\\Administrator\\Desktop\\Text2.jpg", "浅浅：8号 300   9号：3500  10号：3230    麻子：8号 300   9号400  10号  100 鱼儿：9号 150    香儿：8号200  9号 100   10号：2670   安琪：232   嗯哼：300 ");
    }*/

    // 加文字水印  * @param x          -文字位于当前图片的横坐标
    //     * @param y          -文字位于当前图片的竖坐标

    public  void markForText(BufferedImage bufImg, Image img, String text, Font font, Color color, int with, int height) {
        Graphics2D g = bufImg.createGraphics();
        g.setFont(font);
        g.setColor(color);
        int fontLength = getWatermarkLength(text, g);
        System.out.println("fontLength:" + fontLength);
        //文字叠加,自动换行叠加
        //1 all line
        int fontLine = fontLength / with;
        System.out.println("fontLine:" + fontLine);
        int fontSize = height/50;
        //水印居中
        int y = height/2-(fontLine + 1) * fontSize;
        int tempX = 0;
        int tempY = y;
        int tempCharLen = 0;//单字符长度
        int tempLineLen = 0;//单行字符总长度临时计算
        StringBuffer sb = new StringBuffer();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        for (int i = 0; i < text.length(); i++) {
            char tempChar = text.charAt(i);
            tempCharLen = getCharLen(tempChar, g);

            tempLineLen += tempCharLen;

            if (tempLineLen >= with) {
                //长度已经满一行,进行文字叠加
                System.out.println(sb.toString());
                g.drawString(sb.toString(), tempX, tempY);
                sb.delete(0, sb.length());//清空内容,重新追加
                tempY += fontSize;
                tempLineLen = 0;
            }
            sb.append(tempChar);//追加字符
        }
       // g.drawString(text, with / 100, height / 2);
        //文字叠加,自动换行叠加
        g.drawString(sb.toString(), tempX, tempY);//最后叠加余下的文字
        g.dispose();
    }


    public int getCharLen(char c, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charWidth(c);
    }

    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    // 加图片水印
    public void markForImg(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.drawImage(markImg, x, y, width, height, null);
        g.dispose();
    }

    /**
     * 给图片增加文字水印
     *
     * @param imgPath    -要添加水印的图片路径
     * @param outImgPath -输出路径
     * @param text-文字
     */

    //添加水印
    public void addWaterMarkByText(String imgPath, String outImgPath, String text) {
        try {
            // 读取原图片信息
            File imgFile = null;
            Image img = null;
            if (imgPath != null) {
                imgFile = new File(imgPath);
            }
            if (imgFile != null && imgFile.exists() && imgFile.isFile() && imgFile.canRead()) {
                img = ImageIO.read(imgFile);
            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            Font font = new Font("黑体", Font.PLAIN, imgHeight/50);
            markForText(bufImg, img, text, font, Color.red, imgWidth, imgHeight);
            // 输出图片

            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载图片
    public String getWaterMarkByText(String imgPath, String text) {
        try {
            // 读取原图片信息
            File imgFile = null;
            Image img = null;
            if (imgPath != null) {
                imgFile = new File(imgPath);
            }
            if (imgFile != null && imgFile.exists() && imgFile.isFile() && imgFile.canRead()) {
                img = ImageIO.read(imgFile);
            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            Font font = new Font("黑体", Font.PLAIN, imgHeight/50);
            markForText(bufImg, img, text, font, Color.red, imgWidth, imgHeight);
            // 输出图片
            String outImgPath = uploadConfig.getPath()+File.separator+"watermark";
            File dir = new File(outImgPath);
            if (!dir.exists()) {
                // 如果不存在，自动创建
                dir.mkdirs();
            }
            String returnPath = outImgPath+File.separator+KeyUtil.genUniqueKey()+".jpg";
            FileOutputStream outImgStream = new FileOutputStream(returnPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            return returnPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}
