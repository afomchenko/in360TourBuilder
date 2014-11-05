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


import ru.in360.FTPUploader;
import ru.in360.TourProject;

import javax.swing.*;
import java.awt.event.*;

public class FTPSettings extends JDialog {
    static ProgressMonitor pbar;
    static Timer timer;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ipField;
    private JTextField tcpField;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField remoteDirField;

    public FTPSettings() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        FTPUploader ftp = TourProject.getInstance().getFtpUploader();

        ipField.setText(ftp.getServer());
        tcpField.setText(Integer.toString(ftp.getPort()));
        userField.setText(ftp.getUser());
        passwordField.setText(ftp.getPass());
        remoteDirField.setText(ftp.getRemoteProject());

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


        pack();
        setVisible(true);
    }

    private void onOK() {
        FTPUploader ftp = TourProject.getInstance().getFtpUploader();
        try {
            ftp.setServer(ipField.getText());
            ftp.setPort(Integer.parseInt(tcpField.getText()));
            ftp.setUser(userField.getText());
            ftp.setPass(String.valueOf(passwordField.getPassword()));
            ftp.setRemoteProject(remoteDirField.getText());

            pbar = new ProgressMonitor(this, "Upload Progress", "Initializing . . .", 0, 100);
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Update());
                }
            });
            timer.start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    TourProject.getInstance().uploadToFTP();
                    timer.stop();
                    pbar.close();
                }
            }).start();


        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }


    private class Update implements Runnable {

        public void run() {

            if (pbar.isCanceled()) {

                pbar.close();
                // dispose();
            }

            pbar.setProgress(TourProject.getInstance().getComplete());

            pbar.setNote("Uploading is " + TourProject.getInstance().getComplete() + "% complete");

        }
    }


}
