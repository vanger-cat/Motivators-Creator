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
public class MotivatorGenerationWindow {
    private JButton loadImageButton;
    private JPanel imagePlaceHolder;
    private JPanel rootPanel;

    private BufferedImage frameForMotivator;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MotivatorGenerationWindow");
        MotivatorGenerationWindow motivatorGenerationWindow = new MotivatorGenerationWindow();
        frame.setContentPane(motivatorGenerationWindow.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);

        motivatorGenerationWindow.init();
    }

    private void init() {
        try {
            loadFrameForMotivators();
        } catch (MotivatorImageException e) {
            processException(e);
        }
    }

    public MotivatorGenerationWindow() {

        loadImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                selectAndLoadImage();
            }
        });
    }

    private void processException(MotivatorImageException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this.rootPanel, e.getMessage());
    }

    private void loadFrameForMotivators() {
        try {
            frameForMotivator = ImageIO.read(new File("frame_vertical.png"));
            imagePlaceHolder.getGraphics().drawImage(frameForMotivator, 0, 0, imagePlaceHolder.getWidth(), imagePlaceHolder.getHeight(), null);
        } catch (IOException e) {
            throw new MotivatorImageException("Не удаётся загрузить рамку для мотиваторов.", e);
        }
    }

    private void selectAndLoadImage() {
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
            tryToShowImageFromFile(imageFile);
        } catch (IOException e) {
            processException(new MotivatorImageException("Не удаётся загрузить изобржание.", e));
        }
    }

    private void tryToShowImageFromFile(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        showImage(image);
    }

    private void showImage(BufferedImage image) {
        imagePlaceHolder.getGraphics().drawImage(image, 10, 10, imagePlaceHolder.getWidth() - 20, imagePlaceHolder.getHeight() - 60, null);
    }
}
