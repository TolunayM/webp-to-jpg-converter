package org.example;

import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//
//        File imgFile = new File("C:\\Users\\Tolunay\\Desktop\\Cava\\JavaImageConverter\\src\\main\\resources\\file.webp");
//        try {
//            BufferedImage image = ImageIO.read(imgFile);
//            ImageIO.write(image,"jpg",new File("C:\\Users\\Tolunay\\Desktop\\output.jpg"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        UserInput input = new UserInput();
        String path;
        Scanner scan = new Scanner(System.in);
        System.out.println("path girin:");
        path = scan.nextLine();
        try {
            BufferedImage image = ImageIO.read(new File(input.imgPath));

            // Obtain a WebP ImageWriter instance
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

            // Configure encoding parameters
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);

            // Configure the output on the ImageWriter
            writer.setOutput(new FileImageOutputStream(new File("C:\\Users\\Tolunay\\Desktop\\output2.jpg")));

            // Encode
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}