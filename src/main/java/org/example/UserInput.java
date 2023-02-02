package org.example;

import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserInput extends JFrame {
    private JLabel label;
    private JTextField pathField;
    private JButton convertButton;
    private JPanel panelMain;
    public String imgPath;
    public UserInput(){
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imgPath = pathField.getText();

                try {
                    BufferedImage image = ImageIO.read(new File(imgPath));

                    // Obtain a WebP ImageWriter instance
                    ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();


                    // Configure encoding parameters
                    WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
                    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);

                    FileImageOutputStream imgOutput = new FileImageOutputStream(new File("C:\\%USERPROFILE%\\Desktop\\output2.jpg"));
                    // Configure the output on the ImageWriter
                    writer.setOutput(imgOutput);


                    // Encode
                    writer.write(null, new IIOImage(image, null, null), writeParam);
                    // We need to close output stream otherwise image not be released properly until closing program
                    imgOutput.close();
                } catch (IOException a) {
                    a.printStackTrace();
                }
            }

        });
    }

    public static void main(String[] args) {
        UserInput ui = new UserInput();
        ui.setContentPane(ui.panelMain);
        ui.setTitle("Convert WEBP To JPG");
        ui.setSize(500,300);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
