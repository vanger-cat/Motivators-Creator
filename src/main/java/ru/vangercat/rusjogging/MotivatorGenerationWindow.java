package ru.vangercat.rusjogging;

import com.google.inject.Guice;
import com.google.inject.Inject;
import ru.vangercat.rusjogging.services.MotivatorsFactory;

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

    private MotivatorsFactory motivatorsFactory;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MotivatorGenerationWindow");
        MotivatorGenerationWindow motivatorGenerationWindow = Guice.createInjector(new ProductionModule()).getInstance(MotivatorGenerationWindow.class);
        frame.setContentPane(motivatorGenerationWindow.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(1280, 780);
        frame.setVisible(true);
    }

    @Inject
    public MotivatorGenerationWindow(MotivatorsFactory motivatorsFactory) {
        this.motivatorsFactory = motivatorsFactory;
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

    private void selectAndLoadImage() {
        JFileChooser fileChooser = createFileChooserDialog();
        int selectingResult = fileChooser.showOpenDialog(imagePlaceHolder);

        if (selectingResult == JFileChooser.APPROVE_OPTION) {
            showMotivatorWithImageFromFile(fileChooser.getSelectedFile());
        }
    }

    private JFileChooser createFileChooserDialog() {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return fileChooser;
    }

    private void showMotivatorWithImageFromFile(File imageFile) {
        try {
            tryToLoadImageFromFileAndMakeMotivator(imageFile);
        } catch (IOException e) {
            processException(new MotivatorImageException("Не удаётся загрузить изобржание.", e));
        }
    }

    private void tryToLoadImageFromFileAndMakeMotivator(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        Motivator motivator = motivatorsFactory.getMotivator(image);
        showImage(motivator.getResultingImage());
    }

    private void showImage(BufferedImage image) {
        int koef = 4;
        imagePlaceHolder.getGraphics().drawImage(image, 0, 0, image.getWidth() / koef, image.getHeight() / koef, null);
    }
}
