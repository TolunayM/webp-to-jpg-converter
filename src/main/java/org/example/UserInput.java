package org.example;

import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
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
    private JButton chooseFileButton;
    private String imgPath;
    private String userProfile = System.getenv("USERPROFILE");

    public String getImgName(String imgPath){
        this.imgPath = imgPath;

        String absName = imgPath.toLowerCase();
        String[] nameArr = absName.split("\\\\|.webp",50); // 50 :O who knows...
        return nameArr[nameArr.length - 2] + ".jpg";
    }
    public UserInput(){
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    BufferedImage image = ImageIO.read(new File(imgPath));

                    // Obtain a WebP ImageWriter instance
                    ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();



                    // Configure encoding parameters
                    WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
                    writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);
                    FileImageOutputStream imgOutput = new FileImageOutputStream(new File(userProfile + "\\Desktop\\"+ getImgName(imgPath)));


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
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == chooseFileButton) {

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(userProfile + "\\Desktop")); //sets current directory

                    int response = fileChooser.showOpenDialog(null); //select file to open

                    //TODO FIX FOR MULTIPLE FILE SELECTION
                    fileChooser.setMultiSelectionEnabled(true);
                    if(response == JFileChooser.APPROVE_OPTION) {
                        imgPath = fileChooser.getSelectedFile().getAbsolutePath();
                        pathField.setText(imgPath);
                        System.out.println(imgPath);
                    }
                }

            }
        });
    }

    public static void main(String[] args) {
        UserInput ui = new UserInput();
        ui.setContentPane(ui.panelMain);
        ui.setTitle("Convert WEBP To JPG");
        ui.setSize(450,250);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
