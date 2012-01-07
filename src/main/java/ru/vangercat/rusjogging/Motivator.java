package ru.vangercat.rusjogging;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 07.01.12
 * Time: 15:22
 */
public class Motivator {
    private JButton loadImageButton;
    private JPanel imagePlaceHolder;
    private JPanel rootPanel;

    public Motivator() {
        loadImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                selectAndShowImage();
            }
        });
    }

    private void selectAndShowImage() {
        JFileChooser fileChooser = createFileChooserDialog();
        int selectingResult = fileChooser.showOpenDialog(imagePlaceHolder);

        if (selectingResult == JFileChooser.APPROVE_OPTION) {
            showImageFromFile(fileChooser.getSelectedFile());
        }

    }

    private JFileChooser createFileChooserDialog() {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return fileChooser;
    }

    private void showImageFromFile(File imageFile) {
        try {
            tryToLoadImageFromFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.loadImageButton, e.getMessage());
        }
    }

    private void tryToLoadImageFromFile(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        showImage(image);
    }

    private void showImage(BufferedImage image) {
        imagePlaceHolder.getGraphics().drawImage(image, 0, 0, imagePlaceHolder.getWidth(), imagePlaceHolder.getHeight(), null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Motivator");
        frame.setContentPane(new Motivator().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
