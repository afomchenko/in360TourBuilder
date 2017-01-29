/*
 * This file is part of in360TourBuilder.
 *
 *     in360TourBuilder is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     in360TourBuilder is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Этот файл — часть in360TourBuilder.
 *
 *    in360TourBuilder - свободная программа: вы можете перераспространять ее и/или
 *    изменять ее на условиях Стандартной общественной лицензии GNU в том виде,
 *    в каком она была опубликована Фондом свободного программного обеспечения;
 *    либо версии 3 лицензии, либо (по вашему выбору) любой более поздней
 *    версии.
 *
 *    in360TourBuilder распространяется в надежде, что она будет полезной,
 *    но БЕЗО ВСЯКИХ ГАРАНТИЙ; даже без неявной гарантии ТОВАРНОГО ВИДА
 *    или ПРИГОДНОСТИ ДЛЯ ОПРЕДЕЛЕННЫХ ЦЕЛЕЙ. Подробнее см. в Стандартной
 *    общественной лицензии GNU.
 *
 *    Вы должны были получить копию Стандартной общественной лицензии GNU
 *    вместе с этой программой. Если это не так, см.
 *    <http://www.gnu.org/licenses/>.
 *
 * 06.11.14 1:47 Anton Fomchenko 360@in360.ru
 */



package ru.in360.GUI;

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
        inputFileChooser.setCurrentDirectory(new java.io.File("."));
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
                            !inputFileChooser.getSelectedFile().getPath().endsWith("tiff"))
                        oversamplingCheckBox.setEnabled(true);


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
        pano.buildScene();
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

            if(project.getBingMapsKey()==null || project.getBingMapsKey().length()<10){
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
                            .replace("%BINGMAPSKEY%", TourProject.getInstance().getBingMapsKey() )
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
