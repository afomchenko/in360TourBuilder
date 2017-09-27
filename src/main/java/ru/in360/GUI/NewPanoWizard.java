/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 27.09.17 14:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.in360.SceneTemplates;
import ru.in360.TourProject;
import ru.in360.pano.BitmapEdit;
import ru.in360.pano.Pano;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewPanoWizard extends JDialog {
    static ProgressMonitor pbar;
    static ScheduledExecutorService scheduler;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTabbedPane tabbedPane;
    private JTextField inputPanoFileField;
    private JButton setEquiBitton;
    private JButton previewButton;
    private JTextField latField;
    private JTextField lonField;
    private JTextField headingField;
    private JPanel showPanoPanel;
    private JTextArea technicalRequirements;
    private JTextField hlookatField;
    private JTextField vlookatField;
    private JTextField fovField;
    private JButton nextButton;
    private JPanel inputPanel;
    private JPanel settingsPanel;
    private JTextField nameField;
    private JCheckBox oversamplingCheckBox;
    private JFileChooser inputFileChooser;
    private Pano pano;
    //static Timer timer;
    private TourProject project;

    public NewPanoWizard() {
        setContentPane(contentPane);
        setModal(true);

        project = TourProject.getInstance();

        String[] EXTENSION = new String[]{"tif", "tiff", "jpg", "jpeg", "bmp"};
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image File", EXTENSION);

        inputFileChooser = new JFileChooser();
        inputFileChooser.setCurrentDirectory(new File("."));
        inputFileChooser.setDialogTitle("Select Image");
        inputFileChooser.setFileFilter(imageFilter);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setEquiBitton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputFileChooser.showOpenDialog(NewPanoWizard.this) == JFileChooser.APPROVE_OPTION) {
                    inputPanoFileField.setText(inputFileChooser.getSelectedFile().getPath());
                    nextButton.setEnabled(true);

                    if (!inputFileChooser.getSelectedFile().getPath().endsWith("tif") &&
                            !inputFileChooser.getSelectedFile().getPath().endsWith("tiff")) {
                        oversamplingCheckBox.setEnabled(true);
                    }


                } else {
                    System.out.println("No Selection ");
                }
            }
        });


        latField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setLat(Double.parseDouble(latField.getText()));
            }
        });

        lonField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setLng(Double.parseDouble(lonField.getText()));
            }
        });
        headingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setHeading(Double.parseDouble(headingField.getText()));
            }
        });
        hlookatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setHlookat(Double.parseDouble(hlookatField.getText()));
            }
        });
        vlookatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setVlookat(Double.parseDouble(vlookatField.getText()));
            }
        });
        fovField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setFov(Double.parseDouble(fovField.getText()));
            }
        });
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveFields();
                    buildPreviewTour();
                    Desktop.getDesktop().open(new File(project.getTempFolder().getPath() + "/krpano.html"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pano.setName(nameField.getText());
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                ExecutorService es = Executors.newSingleThreadExecutor();

                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        scheduler =
                                Executors.newSingleThreadScheduledExecutor();

                        Component parent = (Component) e.getSource();
                        pbar = new ProgressMonitor(parent, "Converting Progress", "Initializing . . .", 0, 100);

                        scheduler.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {

                                if (pbar.isCanceled()) {

                                    pbar.close();
                                    scheduler.shutdown();
                                    // dispose();
                                }
                                pbar.setProgress(BitmapEdit.getComplete());
                                pbar.setNote("Convertation is " + BitmapEdit.getComplete() + "% complete");

                            }
                        }, 0, 500, TimeUnit.MILLISECONDS);
                    }
                });

                onNext();
            }
        });
        setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        saveFields();
        pano.buildSceneInfo();
        project.getPanoramaStorage().addPano(pano);
        MainWindow.getInstance().updatePanoList();
        dispose();
    }

    private void onNext() {

        ExecutorService ex = Executors.newSingleThreadExecutor();

        ex.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    pano = new Pano("new Panorama", inputFileChooser.getSelectedFile().getPath(), oversamplingCheckBox.isSelected());

                } catch (IllegalArgumentException e) {
                    scheduler.shutdown();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    scheduler.shutdown();
                    pbar.close();
                    return;
                }
                tabbedPane.setSelectedComponent(settingsPanel);
                nextButton.setEnabled(false);
                nextButton.setVisible(false);
                buttonOK.setVisible(true);
                buttonOK.setEnabled(true);

                if (pano.getLat() > 0) {
                    latField.setText(Double.toString(pano.getLat()));
                    lonField.setText(Double.toString(pano.getLng()));
                    headingField.setText(Double.toString(pano.getHeading()));
                }
                hlookatField.setText(Double.toString(pano.getHlookat()));
                vlookatField.setText(Double.toString(pano.getVlookat()));
                fovField.setText(Double.toString(pano.getFov()));
                nameField.setText(pano.getName());

                System.out.println(pano.getPreviewImage().getPath());
                showPanoPanel.setLayout(new BoxLayout(showPanoPanel, BoxLayout.PAGE_AXIS));
                showPanoPanel.add(new ImagePanelCreator(pano.getPreviewImage()));

            }
        });


    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }


    public void buildPreviewTour() {
        try (BufferedWriter writerHTML = new BufferedWriter(new FileWriter(project.getTempFolder().getPath() + "/krpano.html"));
             BufferedWriter writerXML = new BufferedWriter(new FileWriter(project.getTempFolder().getPath() + "/krpano.xml"))) {

            if (project.getBingMapsKey() == null || project.getBingMapsKey().length() < 10) {
                new BingMapsKeyDialog();
            }

            writerHTML.write(SceneTemplates.htmlPreview);
            writerXML.write(SceneTemplates.xmlPreview.replace("%HEADING%", Double.toString(360 - pano.getHeading()))
                    .replace("%LATITUDE%", Double.toString(pano.getLat()))
                    .replace("%LONGITUDE%", Double.toString(pano.getLng()))
                    .replace("%HLOOKAT%", Double.toString(pano.getHlookat()))
                    .replace("%VLOOKAT%", Double.toString(pano.getVlookat()))
                    .replace("%HEADINGOFFSET%", Double.toString(pano.getHeading()))
                    .replace("%FOV%", Double.toString(pano.getFov()))
                    .replace("%BINGMAPSKEY%", TourProject.getInstance().getBingMapsKey())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveFields() {
        pano.setName(nameField.getText());
        pano.setHeading(Double.parseDouble(headingField.getText()));
        pano.setLat(Double.parseDouble(latField.getText()));
        pano.setLng(Double.parseDouble(lonField.getText()));
        pano.setHlookat(Double.parseDouble(hlookatField.getText()));
        pano.setVlookat(Double.parseDouble(vlookatField.getText()));
        pano.setFov(Double.parseDouble(fovField.getText()));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setEnabled(false);
        buttonOK.setText("Finish");
        buttonOK.setVisible(false);
        panel2.add(buttonOK, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextButton = new JButton();
        nextButton.setEnabled(false);
        nextButton.setText("Next");
        panel2.add(nextButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        tabbedPane.setEnabled(true);
        panel3.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayoutManager(4, 2, new Insets(5, 5, 5, 5), -1, -1));
        tabbedPane.addTab("Input panorama", inputPanel);
        tabbedPane.setEnabledAt(0, false);
        setEquiBitton = new JButton();
        setEquiBitton.setText("Open panoramic image");
        inputPanel.add(setEquiBitton, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setEnabled(false);
        scrollPane1.setHorizontalScrollBarPolicy(31);
        scrollPane1.setVerticalScrollBarPolicy(21);
        inputPanel.add(scrollPane1, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        technicalRequirements = new JTextArea();
        technicalRequirements.setBackground(new Color(-986896));
        technicalRequirements.setDisabledTextColor(new Color(-16777216));
        technicalRequirements.setEditable(false);
        technicalRequirements.setEnabled(false);
        technicalRequirements.setForeground(new Color(-986896));
        technicalRequirements.setLineWrap(true);
        technicalRequirements.setText("\n\nTechnical Requirements:\n\nJPEG or 8-bit TIFF format with no layers .\n\nOnly standard ASCII characters in the filename (examples of characters not allowed: ü, á, ě, etc.)   \n\nYour panoramas must be 360º - the left side of your panorama must match with the right side\nof your  panorama so the viewer can enjoy a fully immersive experience of looking all around\nwithout any visible seams.   \n\nTo upload a fully spherical panorama, make sure the aspect ratio of your panorama in pixel width and height is 2:1.\nExample sizes of panoramas that will be treated as spherical: 6000x3000, 10000x5000, 8422x4211.\n\nThe optimal panorama size is 12288x6144.");
        technicalRequirements.setWrapStyleWord(false);
        scrollPane1.setViewportView(technicalRequirements);
        inputPanoFileField = new JTextField();
        inputPanoFileField.setEditable(false);
        inputPanel.add(inputPanoFileField, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        inputPanel.add(panel4, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        oversamplingCheckBox = new JCheckBox();
        oversamplingCheckBox.setEnabled(false);
        oversamplingCheckBox.setText("use oversampling (take a lot of RAM)");
        panel4.add(oversamplingCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayoutManager(14, 2, new Insets(5, 5, 5, 5), -1, -1));
        settingsPanel.setEnabled(true);
        tabbedPane.addTab("Panorama settings", settingsPanel);
        tabbedPane.setEnabledAt(1, false);
        previewButton = new JButton();
        previewButton.setText("Preview");
        settingsPanel.add(previewButton, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        settingsPanel.add(panel5, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        latField = new JTextField();
        panel5.add(latField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lonField = new JTextField();
        panel5.add(lonField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Latitude:");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Longitude:");
        panel5.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showPanoPanel = new JPanel();
        showPanoPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        settingsPanel.add(showPanoPanel, new GridConstraints(3, 0, 11, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(600, 300), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        settingsPanel.add(spacer3, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Look at (horizontal):");
        settingsPanel.add(label3, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hlookatField = new JTextField();
        settingsPanel.add(hlookatField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Look at (vertical):");
        settingsPanel.add(label4, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        vlookatField = new JTextField();
        settingsPanel.add(vlookatField, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Field of view:");
        settingsPanel.add(label5, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fovField = new JTextField();
        settingsPanel.add(fovField, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        settingsPanel.add(spacer4, new GridConstraints(4, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        settingsPanel.add(panel6, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameField = new JTextField();
        panel6.add(nameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Panorama name:");
        panel6.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Select the north point at the panorama:");
        settingsPanel.add(label7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Map heading:");
        settingsPanel.add(label8, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        headingField = new JTextField();
        settingsPanel.add(headingField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private class ImagePanelCreator extends JPanel {

        private BufferedImage image;

        public ImagePanelCreator(File imageFile) {
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException ex) {
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pano.setHeading((((double) e.getX() / image.getWidth()) * 360) + 90);
                    headingField.setText(Double.toString(pano.getHeading()));
                    System.out.println("clickt! " + e.getX() + " " + image.getWidth() + " " + pano.getHeading());
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
            g.setColor(Color.red);
            g.drawLine((int) (image.getWidth() * ((pano.getHeading() - 90) / 360)), 0, (int) (image.getWidth() * ((pano.getHeading() - 90) / 360)), image.getHeight());
            g.drawString("N", (int) (image.getWidth() * ((pano.getHeading() - 90) / 360)) + 5, 10);
            g.drawRect((int) (image.getWidth() * ((pano.getHlookat() - 35 - 90) / 360)), (int) (image.getWidth() * ((pano.getHlookat() - 35 + 45) / 180)), 116, 116);
        }
    }


}
