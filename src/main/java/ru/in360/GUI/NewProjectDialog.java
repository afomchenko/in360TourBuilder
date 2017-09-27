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
import ru.in360.TourProject;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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
        projectFolderChooser.setCurrentDirectory(new File("."));
        projectFolderChooser.setDialogTitle("Select project folder");
        projectFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        projectFolderChooser.setAcceptAllFileFilterUsed(false);

        skinFolderChooser = new JFileChooser();
        skinFolderChooser.setCurrentDirectory(new File("."));
        skinFolderChooser.setDialogTitle("Select skin folder");
        skinFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        skinFolderChooser.setAcceptAllFileFilterUsed(false);

        pluginsFolderChooser = new JFileChooser();
        pluginsFolderChooser.setCurrentDirectory(new File("."));
        pluginsFolderChooser.setDialogTitle("Select plugins folder");
        pluginsFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pluginsFolderChooser.setAcceptAllFileFilterUsed(false);

        jsFileChooser = new JFileChooser();
        jsFileChooser.setCurrentDirectory(new File("."));
        jsFileChooser.setDialogTitle("Select krpano.js");
        jsFileChooser.setFileFilter(jsFilter);

        swfFileChooser = new JFileChooser();
        swfFileChooser.setCurrentDirectory(new File("."));
        swfFileChooser.setDialogTitle("Select krpano.swf");
        swfFileChooser.setFileFilter(swfFilter);

        FileFilter projectFileFilter = new FileNameExtensionFilter("Project file", "proj");
        projectFileChooser = new JFileChooser();
        projectFileChooser.setCurrentDirectory(new File("."));
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
                if (projectFileChooser.getSelectedFile() != null) {
                    buttonOK.setEnabled(true);
                } else {
                    buttonOK.setEnabled(false);
                }
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
                if (projectFolderChooser.getSelectedFile() != null) {
                    buttonOK.setEnabled(true);
                } else {
                    buttonOK.setEnabled(false);
                }
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
        contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setMaximumSize(new Dimension(500, 500));
        contentPane.setMinimumSize(new Dimension(500, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setEnabled(false);
        contentPane.add(panel1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setEnabled(false);
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createProjectPanel = new JPanel();
        createProjectPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        createProjectPanel.setEnabled(true);
        createProjectPanel.setVisible(true);
        contentPane.add(createProjectPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        createProjectPanel.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Create folder in:");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        projectPathField = new JTextField();
        projectPathField.setEditable(false);
        panel3.add(projectPathField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        selectButton = new JButton();
        selectButton.setEnabled(false);
        selectButton.setText("Select");
        panel3.add(selectButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        createProjectPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setEnabled(true);
        panel4.add(nameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Project name:");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        createProjectPanel.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Skin folder:");
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        skinField = new JTextField();
        skinField.setEditable(false);
        panel5.add(skinField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        skinButton = new JButton();
        skinButton.setEnabled(false);
        skinButton.setText("Select");
        panel5.add(skinButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 1, new Insets(5, 5, 5, 5), -1, -1));
        panel6.setEnabled(false);
        createProjectPanel.add(panel6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder("Krpano viewer"));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setEnabled(true);
        panel6.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Krpano.js");
        panel7.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        krpanoJsField = new JTextField();
        krpanoJsField.setEditable(false);
        panel7.add(krpanoJsField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        krpanoJsButton = new JButton();
        krpanoJsButton.setEnabled(false);
        krpanoJsButton.setText("Select");
        panel7.add(krpanoJsButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Krpano.swf");
        panel8.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        krpanoSwfField = new JTextField();
        krpanoSwfField.setEditable(false);
        panel8.add(krpanoSwfField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        krpanoSwfButton = new JButton();
        krpanoSwfButton.setEnabled(false);
        krpanoSwfButton.setText("Select");
        panel8.add(krpanoSwfButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        localViewerRadioButton = new JRadioButton();
        localViewerRadioButton.setEnabled(false);
        localViewerRadioButton.setText("local");
        panel9.add(localViewerRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dnlViewerRadioButton = new JRadioButton();
        dnlViewerRadioButton.setEnabled(false);
        dnlViewerRadioButton.setSelected(true);
        dnlViewerRadioButton.setText("download from krpano.com");
        panel9.add(dnlViewerRadioButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel10, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Plugins folder:");
        panel10.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pluginsField = new JTextField();
        pluginsField.setEditable(false);
        pluginsField.setEnabled(true);
        panel10.add(pluginsField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        pluginsButton = new JButton();
        pluginsButton.setEnabled(false);
        pluginsButton.setText("Select");
        panel10.add(pluginsButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        openProjectRadioButton = new JRadioButton();
        openProjectRadioButton.setSelected(true);
        openProjectRadioButton.setText("open project:");
        panel11.add(openProjectRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openProjectField = new JTextField();
        openProjectField.setEditable(false);
        panel11.add(openProjectField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        selectProjectButton = new JButton();
        selectProjectButton.setText("Select");
        panel11.add(selectProjectButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel12, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createNewProjectRadioButton = new JRadioButton();
        createNewProjectRadioButton.setText("create new project:");
        panel12.add(createNewProjectRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel12.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
