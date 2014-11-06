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
import ru.in360.XMLTools;
import ru.in360.pano.Pano;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;
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

    private MainWindow() {
        this.setContentPane(mainPanel);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (TourProject.getInstance().getProjectName() != null) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save project?", "Warning", JOptionPane.YES_NO_OPTION);
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
                selectedPano.setHlookat(Double.parseDouble(hlookatField.getText()));
                selectedPano.updateSceneFromPano();
            }
        });

        vlookatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setVlookat(Double.parseDouble(vlookatField.getText()));
                selectedPano.updateSceneFromPano();
            }
        });

        fovField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setFov(Double.parseDouble(fovField.getText()));
                selectedPano.updateSceneFromPano();
            }
        });

        latField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setLat(Double.parseDouble(latField.getText()));
                selectedPano.updateSceneFromPano();
            }
        });

        lngField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setLng(Double.parseDouble(lngField.getText()));
                selectedPano.updateSceneFromPano();
            }
        });

        headingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPano.setHeading(Double.parseDouble(headingField.getText()));
                selectedPano.updateSceneFromPano();
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
        if (project.getPanoramaStorage().getPanoramas().size() > 0)
            updatePanoList();
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

        if (selectedPano != null)
            try (BufferedWriter writerHTML = new BufferedWriter(new FileWriter(project.getTempFolder().getPath() + "/krpano.html"))) {
                selectedPano.updateSceneFromPano();
                writerHTML.write(SceneTemplates.htmlPreview);
                XMLTools.writeDOM(selectedPano.buildScenePreviewXML(), new File(TourProject.getInstance().getTempFolder().getPath() + "/krpano.xml"));
                Desktop.getDesktop().open(new File(project.getTempFolder().getPath() + "/krpano.html"));
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
