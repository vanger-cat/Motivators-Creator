package ru.vangercat.rusjogging;

import com.google.inject.Guice;
import com.google.inject.Inject;
import ru.vangercat.rusjogging.services.MotivatorsFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private JTextField motivatorTextField;

    private MotivatorsFactory motivatorsFactory;
    private File inputFile;
    private File directoryWhereWasLastFileSelected = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MotivatorGenerationWindow");
        MotivatorGenerationWindow motivatorGenerationWindow = Guice.createInjector(new ProductionModule()).getInstance(MotivatorGenerationWindow.class);
        frame.setContentPane(motivatorGenerationWindow.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 600);
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
            inputFile = fileChooser.getSelectedFile();
            directoryWhereWasLastFileSelected = inputFile.getParentFile();
            showMotivatorWithImageFromFile(inputFile);
        }
    }

    private JFileChooser createFileChooserDialog() {
        JFileChooser fileChooser;
        if (directoryWhereWasLastFileSelected == null) {
            fileChooser = new JFileChooser();
        } else {
            fileChooser = new JFileChooser(directoryWhereWasLastFileSelected);
        }
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return fileChooser;
    }

    private void showMotivatorWithImageFromFile(File imageFile) {
        BufferedImage motivator = null;
        try {
            motivator = tryToLoadImageFromFileAndMakeMotivator(imageFile);
        } catch (IOException e) {
            processException(new MotivatorImageException("Не удаётся загрузить изобржание.", e));
        }

        if (motivator == null) {
            return;
        }

        String inputFileName = inputFile.getName();
        String[] fileParts = inputFileName.split("\\.");
        File outputFileName = new File(inputFile.getParentFile().getAbsolutePath() + "/" + fileParts[0] + "-motivator" + ".png");
        try {
            ImageIO.write(motivator, "png", outputFileName);
        } catch (IOException e) {
            processException(new MotivatorImageException("Не удаётся сохранить изобржание в файл " + outputFileName.getName(), e));
        }
    }

    private BufferedImage tryToLoadImageFromFileAndMakeMotivator(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        String text = motivatorTextField.getText().isEmpty() ? "Укажите текст для мотиватора" : motivatorTextField.getText();
        Motivator motivator = motivatorsFactory.getMotivator(image, text);
        BufferedImage resultingImage = motivator.getResultingImage();
        showImage(resultingImage);
        return resultingImage;

    }

    private void showImage(BufferedImage image) {
        int koef = 4;

        int width = image.getWidth() / koef;
        int height = image.getHeight() / koef;

        Graphics graphics = imagePlaceHolder.getGraphics();
        graphics.setColor(imagePlaceHolder.getBackground());
        graphics.fillRect(0, 0, imagePlaceHolder.getWidth(), imagePlaceHolder.getHeight());

        int x = (imagePlaceHolder.getWidth() - width) / 2;
        int y = (imagePlaceHolder.getHeight() - height) / 2;
        imagePlaceHolder.getGraphics().drawImage(image, x, y, width, height, null);
    }
}
