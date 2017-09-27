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
import org.apache.log4j.Logger;
import ru.in360.SceneTemplates;
import ru.in360.TourInfoFactory;
import ru.in360.TourProject;
import ru.in360.XMLTools;
import ru.in360.pano.Pano;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindow extends JFrame {
    private static MainWindow instance;
    private static Lock lock = new ReentrantLock();
    private static Lock updateLock = new ReentrantLock();
    ImagePanelEditor previewImagePanel;
    TourProject project;
    private JPanel mainPanel;
    private JButton addPanoramaButton;
    private JButton buildButton;
    private JButton uploadButton;
    private JTextField hlookatField;
    private JTextField vlookatField;
    private JTextField fovField;
    private JTextField latField;
    private JTextField lngField;
    private JTextField headingField;
    private JTextField nameField;
    private JButton deletePanoramaButton;
    private JButton previewButton;
    private JList panoList;
    private JPanel previewPanel;
    private JCheckBox uploadToFTPCheckBox;
    private JButton settingsButton;
    private Pano selectedPano;

    private final static Logger logger = Logger.getLogger(TourInfoFactory.class);

    private MainWindow() {
        this.setContentPane(mainPanel);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (TourProject.getInstance().getProjectName() != null) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to save project?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        TourProject.getInstance().saveProject();
                    }
                }
                System.exit(0);
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                System.out.println("gained focus");
                loadProject();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                System.out.println("activated");
                loadProject();
            }
        });

        panoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displaySelectedPano((Pano) panoList.getSelectedValue());
            }
        });

        addPanoramaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewPanoWizard();
            }
        });
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(TourProject.getInstance().buildTour());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FTPSettings();
            }
        });

        uploadToFTPCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setUploaded(!uploadToFTPCheckBox.isSelected());
            }
        });
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setName(nameField.getText());
                selectedPano.updateSceneFromPano();
            }
        });

        hlookatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setHlookat(Double.parseDouble(hlookatField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + hlookatField.getText());
                }
            }
        });

        vlookatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setVlookat(Double.parseDouble(vlookatField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + vlookatField.getText());
                }
            }
        });

        fovField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setFov(Double.parseDouble(fovField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + fovField.getText());
                }
            }
        });

        latField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setLat(Double.parseDouble(latField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + latField.getText());
                }
            }
        });

        lngField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setLng(Double.parseDouble(lngField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + lngField.getText());
                }
            }
        });

        headingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    selectedPano.setHeading(Double.parseDouble(headingField.getText()));
                    selectedPano.updateSceneFromPano();
                } catch (NumberFormatException nEx) {
                    System.err.println("illegal input field:" + headingField.getText());
                }
            }
        });

        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPreview();
            }
        });
        deletePanoramaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeletePano();
            }
        });

        project = TourProject.getInstance();
        new NewProjectDialog();
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TourSettings();
            }
        });

        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.PAGE_AXIS));
        previewImagePanel = new ImagePanelEditor();
        previewImagePanel.setMaximumSize(new Dimension(600, 300));
        previewPanel.add(previewImagePanel);


        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.pack();
        setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public static MainWindow getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new MainWindow();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }


    void loadProject() {
        System.out.println("adding project...");
        project = TourProject.getInstance();
        System.out.println("updating list..");
        if (project.getPanoramaStorage().getPanoramas().size() > 0) {
            updatePanoList();
        }
        System.out.println("updating done...");
        this.setTitle(project.getProjectName());
    }


    void updatePanoList() {
        DefaultListModel<Pano> model = new DefaultListModel<>();
        for (Pano pano : TourProject.getInstance().getPanoramaStorage().getPanoramas()) {
            model.addElement(pano);
        }
        panoList.setModel(model);
        panoList.setSelectedValue(selectedPano, true);

    }

    private void onPreview() {

        if (selectedPano != null) {
            selectedPano.preview();
        }

    }

    private void displaySelectedPano(Pano pano) {
        if (pano != null) {
            selectedPano = pano;

            hlookatField.setEnabled(true);
            vlookatField.setEnabled(true);
            fovField.setEnabled(true);
            latField.setEnabled(true);
            lngField.setEnabled(true);
            headingField.setEnabled(true);
            nameField.setEnabled(true);
            previewButton.setEnabled(true);
            deletePanoramaButton.setEnabled(true);

            hlookatField.setText(Double.toString(selectedPano.getHlookat()));
            vlookatField.setText(Double.toString(selectedPano.getVlookat()));
            fovField.setText(Double.toString(selectedPano.getFov()));
            latField.setText(Double.toString(selectedPano.getLat()));
            lngField.setText(Double.toString(selectedPano.getLng()));
            headingField.setText(Double.toString(selectedPano.getHeading()));
            nameField.setText(selectedPano.getName());
            uploadToFTPCheckBox.setSelected(!selectedPano.isUploaded());
            previewImagePanel.addPreview(selectedPano.getPreviewImage());
            previewImagePanel.setVisible(true);

        }
    }

    private void onDeletePano() {

        int dialogResult = JOptionPane.showConfirmDialog(null, "Delete pano? This cannot be undone!\nProject will be saved.", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            project.getPanoramaStorage().deletePano(selectedPano);
            selectedPano = null;
            updatePanoList();
            hlookatField.setEnabled(false);
            vlookatField.setEnabled(false);
            fovField.setEnabled(false);
            latField.setEnabled(false);
            lngField.setEnabled(false);
            headingField.setEnabled(false);
            nameField.setEnabled(false);
            previewButton.setEnabled(false);
            deletePanoramaButton.setEnabled(false);
            previewImagePanel.setVisible(false);
            TourProject.getInstance().saveProject();
        }
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(8, 2, new Insets(5, 5, 5, 5), -1, -1));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        mainPanel.add(toolBar1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        addPanoramaButton = new JButton();
        addPanoramaButton.setText("Add Panorama");
        toolBar1.add(addPanoramaButton);
        buildButton = new JButton();
        buildButton.setText("Build Tour");
        toolBar1.add(buildButton);
        uploadButton = new JButton();
        uploadButton.setText("Upload");
        toolBar1.add(uploadButton);
        settingsButton = new JButton();
        settingsButton.setText("Settings");
        toolBar1.add(settingsButton);
        previewPanel = new JPanel();
        previewPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(previewPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(600, 300), null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(1, 0, 7, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        scrollPane1.setVerticalScrollBarPolicy(20);
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panoList = new JList();
        scrollPane1.setViewportView(panoList);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Panorama name:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameField = new JTextField();
        nameField.setEnabled(false);
        panel2.add(nameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(5, 5, 5, 5), -1, -1));
        mainPanel.add(panel3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder("View Settings"));
        hlookatField = new JTextField();
        hlookatField.setEnabled(false);
        panel3.add(hlookatField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        vlookatField = new JTextField();
        vlookatField.setEnabled(false);
        panel3.add(vlookatField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        fovField = new JTextField();
        fovField.setEnabled(false);
        panel3.add(fovField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Look at horizontal:");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Look at vertical:");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Field of view:");
        panel3.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 7, new Insets(5, 5, 5, 5), -1, -1));
        mainPanel.add(panel4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder("Map settings"));
        final JLabel label5 = new JLabel();
        label5.setText("Latitude:");
        panel4.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        latField = new JTextField();
        latField.setEnabled(false);
        panel4.add(latField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Longitude:");
        panel4.add(label6, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lngField = new JTextField();
        lngField.setEnabled(false);
        panel4.add(lngField, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Heading:");
        panel4.add(label7, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        headingField = new JTextField();
        headingField.setEnabled(false);
        panel4.add(headingField, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setEnabled(true);
        mainPanel.add(panel5, new GridConstraints(5, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        uploadToFTPCheckBox = new JCheckBox();
        uploadToFTPCheckBox.setText("upload to FTP");
        panel5.add(uploadToFTPCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previewButton = new JButton();
        previewButton.setEnabled(false);
        previewButton.setText("Preview");
        panel5.add(previewButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deletePanoramaButton = new JButton();
        deletePanoramaButton.setEnabled(false);
        deletePanoramaButton.setText("Delete Panorama");
        panel5.add(deletePanoramaButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    private class ImagePanelEditor extends JPanel {

        private BufferedImage image;

        public ImagePanelEditor() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedPano != null) {
                        selectedPano.setHeading((((double) e.getX() / image.getWidth()) * 360) + 90);
                        headingField.setText(Double.toString(selectedPano.getHeading()));
                        selectedPano.updateSceneFromPano();
                        repaint();
                    }
                }
            });
        }

        public void addPreview(File imageFile) {
            try {
                image = ImageIO.read(imageFile);
                repaint();
            } catch (IOException ex) {
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (selectedPano != null) {
                g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
                g.setColor(Color.red);
                g.drawLine((int) (image.getWidth() * ((selectedPano.getHeading() - 90) / 360)), 0, (int) (image.getWidth() * ((selectedPano.getHeading() - 90) / 360)), image.getHeight());
                g.drawString("N", (int) (image.getWidth() * ((selectedPano.getHeading() - 90) / 360)) + 5, 10);
                g.drawRect((int) (image.getWidth() * ((selectedPano.getHlookat() - 35 - 90) / 360)), (int) (image.getWidth() * ((selectedPano.getHlookat() - 35 + 45) / 180)), 116, 116);
            }
        }
    }
}
