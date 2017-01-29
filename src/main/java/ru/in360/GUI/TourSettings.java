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

public class TourSettings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField jsField;
    private JButton jsButton;
    private JTextField swfField;
    private JButton swfButton;
    private JTextField skinField;
    private JButton skinButton;
    private JTextField bingField;
    private JTextField projectField;

    private JFileChooser projectFolderChooser;
    private JFileChooser skinFolderChooser;
    private JFileChooser pluginsFolderChooser;
    private JFileChooser jsFileChooser;
    private JFileChooser swfFileChooser;

    TourProject project;

    public TourSettings() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        project = TourProject.getInstance();

        projectField.setText(project.getProjectFolder().getPath());
        bingField.setText(project.getBingMapsKey());
        jsField.setText(project.getViewerJsFile().getPath());
        swfField.setText(project.getViewerSwfFile().getPath());
        skinField.setText(project.getSkinFolder().getPath());

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

        projectField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                project.setProjectFolder(new File(projectField.getText()));
            }
        });

        jsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jsFileChooser.showOpenDialog(TourSettings.this) == JFileChooser.APPROVE_OPTION) {
                    jsField.setText(jsFileChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        swfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (swfFileChooser.showOpenDialog(TourSettings.this) == JFileChooser.APPROVE_OPTION) {
                    swfField.setText(swfFileChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        skinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pluginsFolderChooser.showOpenDialog(TourSettings.this) == JFileChooser.APPROVE_OPTION) {
                    skinField.setText(pluginsFolderChooser.getSelectedFile().getPath());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });

        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        bingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                project.setBingMapsKey(bingField.getText());
            }
        });
    }


    private void onOK() {
        project.setProjectFolder(new File(projectField.getText()));
        project.setBingMapsKey(bingField.getText());
        if(skinFolderChooser.getSelectedFile()!=null)
            project.addSkinFolder(skinFolderChooser.getSelectedFile());
        if(jsFileChooser.getSelectedFile()!=null)
            project.setViewers(jsFileChooser.getSelectedFile(),null);
        if(swfFileChooser.getSelectedFile()!=null)
            project.setViewers(null,swfFileChooser.getSelectedFile());
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
