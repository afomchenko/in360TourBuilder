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

import ru.in360.TourProject;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class NewProjectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField projectPathField;
    private JTextField nameField;
    private JButton selectButton;
    private JTextField skinField;
    private JButton skinButton;
    private JRadioButton localViewerRadioButton;
    private JRadioButton dnlViewerRadioButton;
    private JTextField krpanoJsField;
    private JTextField krpanoSwfField;
    private JButton krpanoJsButton;
    private JButton krpanoSwfButton;
    private JTextField pluginsField;
    private JButton pluginsButton;
    private JRadioButton createNewProjectRadioButton;
    private JRadioButton openProjectRadioButton;
    private JTextField openProjectField;
    private JButton selectProjectButton;
    private JPanel createProjectPanel;

    private JFileChooser projectFolderChooser;
    private JFileChooser skinFolderChooser;
    private JFileChooser pluginsFolderChooser;
    private JFileChooser jsFileChooser;
    private JFileChooser swfFileChooser;
    private JFileChooser projectFileChooser;

    public NewProjectDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        ButtonGroup viewerRadioButtonGroup = new ButtonGroup();
        viewerRadioButtonGroup.add(localViewerRadioButton);
        viewerRadioButtonGroup.add(dnlViewerRadioButton);

        ButtonGroup createProjectRadioButtonGroup = new ButtonGroup();
        createProjectRadioButtonGroup.add(createNewProjectRadioButton);
        createProjectRadioButtonGroup.add(openProjectRadioButton);

        FileFilter jsFilter = new FileNameExtensionFilter("JavaScript", "js");
        FileFilter swfFilter = new FileNameExtensionFilter("Flash", "swf");

        projectFolderChooser = new JFileChooser();
        projectFolderChooser.setCurrentDirectory(new java.io.File("."));
        projectFolderChooser.setDialogTitle("Select project folder");
        projectFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        projectFolderChooser.setAcceptAllFileFilterUsed(false);

        skinFolderChooser = new JFileChooser();
        skinFolderChooser.setCurrentDirectory(new java.io.File("."));
        skinFolderChooser.setDialogTitle("Select skin folder");
        skinFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        skinFolderChooser.setAcceptAllFileFilterUsed(false);

        pluginsFolderChooser = new JFileChooser();
        pluginsFolderChooser.setCurrentDirectory(new java.io.File("."));
        pluginsFolderChooser.setDialogTitle("Select plugins folder");
        pluginsFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pluginsFolderChooser.setAcceptAllFileFilterUsed(false);

        jsFileChooser = new JFileChooser();
        jsFileChooser.setCurrentDirectory(new java.io.File("."));
        jsFileChooser.setDialogTitle("Select krpano.js");
        jsFileChooser.setFileFilter(jsFilter);

        swfFileChooser = new JFileChooser();
        swfFileChooser.setCurrentDirectory(new java.io.File("."));
        swfFileChooser.setDialogTitle("Select krpano.swf");
        swfFileChooser.setFileFilter(swfFilter);

        FileFilter projectFileFilter = new FileNameExtensionFilter("Project file", "proj");
        projectFileChooser = new JFileChooser();
        projectFileChooser.setCurrentDirectory(new java.io.File("."));
        projectFileChooser.setDialogTitle("Select project file");
        projectFileChooser.setFileFilter(projectFileFilter);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        localViewerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                krpanoJsButton.setEnabled(true);
                krpanoSwfButton.setEnabled(true);
                pluginsButton.setEnabled(true);
            }
        });

        dnlViewerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                krpanoJsButton.setEnabled(false);
                krpanoSwfButton.setEnabled(false);
                pluginsButton.setEnabled(false);
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

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (projectFolderChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    projectPathField.setText(projectFolderChooser.getSelectedFile().getPath());
                    buttonOK.setEnabled(true);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        skinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (skinFolderChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    skinField.setText(skinFolderChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        krpanoJsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jsFileChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    krpanoJsField.setText(jsFileChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        krpanoSwfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (swfFileChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    krpanoSwfField.setText(swfFileChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        pluginsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pluginsFolderChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    pluginsField.setText(pluginsFolderChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        selectProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (projectFileChooser.showOpenDialog(NewProjectDialog.this) == JFileChooser.APPROVE_OPTION) {
                    openProjectField.setText(projectFileChooser.getSelectedFile().getPath());
                    buttonOK.setEnabled(true);
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        openProjectRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectProjectButton.setEnabled(true);
                nameField.setEditable(false);
                selectButton.setEnabled(false);
                dnlViewerRadioButton.setEnabled(false);
                localViewerRadioButton.setEnabled(false);
                skinButton.setEnabled(false);
                if (projectFileChooser.getSelectedFile() != null)
                    buttonOK.setEnabled(true);
                else
                    buttonOK.setEnabled(false);
            }
        });


        createNewProjectRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectProjectButton.setEnabled(false);
                nameField.setEditable(true);
                selectButton.setEnabled(true);
                dnlViewerRadioButton.setEnabled(true);
                localViewerRadioButton.setEnabled(true);
                skinButton.setEnabled(true);
                if (projectFolderChooser.getSelectedFile() != null)
                    buttonOK.setEnabled(true);
                else
                    buttonOK.setEnabled(false);
            }
        });

        setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);


    }

    public static void main(String[] args) {
        NewProjectDialog dialog = new NewProjectDialog();

        System.exit(0);
    }

    private void onOK() {

        if (openProjectRadioButton.isSelected()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(projectFileChooser.getSelectedFile()))) {
                TourProject project = (TourProject) ois.readObject();
                System.out.println("project " + project.getProjectFolder().getPath() + " loaded");
                System.out.println("project " + project.getSkinFolder().getPath() + " loaded");
                System.out.println("project " + TourProject.getInstance().getProjectFolder().getPath() + " loaded");
                System.out.println("project " + TourProject.getInstance().getSkinFolder().getPath() + " loaded");
                //MainWindow.getInstance().loadProject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (nameField.getText().length() < 1) {
                JOptionPane.showMessageDialog(this, "Please specify name");
                return;
            } else if (projectFolderChooser.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(this, "Please specify project folder");
                return;
            } else if (jsFileChooser.getSelectedFile() == null && localViewerRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please specify krpano.js");
                return;
            } else if (swfFileChooser.getSelectedFile() == null && localViewerRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please specify krpano.swf");
                return;
            } else if (pluginsFolderChooser.getSelectedFile() == null && localViewerRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please specify plugins folder");
                return;
            } else if (skinFolderChooser.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(this, "Please specify skin folder");
                return;
            }

            if (localViewerRadioButton.isSelected()) {
                TourProject project = TourProject.getInstance();
                project.setProjectName(nameField.getText());
                project.setProjectFolder(new File(projectFolderChooser.getSelectedFile().getPath() + "/" + nameField.getText()));
                project.setViewers(jsFileChooser.getSelectedFile(), swfFileChooser.getSelectedFile());
                project.addSkinFolder(skinFolderChooser.getSelectedFile());
                project.addPluginsFolder(pluginsFolderChooser.getSelectedFile());
            }

        }
        dispose();
    }

    private void onCancel() {
        //dispose();
        System.exit(0);
    }
}
