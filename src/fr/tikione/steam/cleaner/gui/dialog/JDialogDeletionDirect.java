package fr.tikione.steam.cleaner.gui.dialog;

import fr.tikione.steam.cleaner.util.FileUtils;
import fr.tikione.steam.cleaner.util.GraphicsUtils;
import fr.tikione.steam.cleaner.util.Log;
import fr.tikione.steam.cleaner.util.Translation;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;

/**
 * Deletion window.
 */
@SuppressWarnings("serial")
public class JDialogDeletionDirect extends JDialog {

    private List<File> filesToDel = null;

    private List<File> foldersToDel = null;

    /**
     * Create new form JDialogDeletionDirect.
     *
     * @param parent the parent component.
     * @param modal indicates if the frame is modal.
     * @param translation the program language translation handler.
     * @throws IOException
     */
    @SuppressWarnings({"LeakingThisInConstructor", "CallToThreadStartDuringObjectConstruction"})
    public JDialogDeletionDirect(java.awt.Frame parent, boolean modal, final Translation translation)
            throws IOException {
        super(parent, modal);
        initComponents();
        initTranslateComponents(translation);
        GraphicsUtils.setFrameCentered(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String labelOK = translation.getString(Translation.SEC_DELETE, "info.success");
                String labelERROR = translation.getString(Translation.SEC_DELETE, "info.error");
                String labelDelFile = translation.getString(Translation.SEC_DELETE, "info.deleteFile");
                String labelDelFolder = translation.getString(Translation.SEC_DELETE, "info.deleteFolder");
                String labelSaved = translation.getString(Translation.SEC_DELETE, "info.spaceSaved");
                long totalSaved = 0;
                for (File file : filesToDel) {
                    if (file.exists()) {
                        jTextAreaDeletionLog.setText(jTextAreaDeletionLog.getText() + "\r\n- "
                                + labelDelFile.replace("{0}", file.getAbsolutePath()));
                        boolean res;
                        long fileSize = org.apache.commons.io.FileUtils.sizeOf(file);
                        try {
                            res = file.delete();
                        } catch (Exception ex) {
                            res = false;
                            Log.error(ex);
                        }
                        if (res) {
                            totalSaved += fileSize;
                        }
                        jTextAreaDeletionLog.setText(jTextAreaDeletionLog.getText() + " " + (res ? labelOK : labelERROR));
                        Log.info("Delete file : " + file.getAbsolutePath() + " ... " + (res ? "OK" : "ERROR"));
                    } else {
                        Log.info("Delete file : " + file.getAbsolutePath() + " ... SKIPPED (already deleted)");
                    }
                }
                for (File folder : foldersToDel) {
                    if (folder.exists()) {
                        jTextAreaDeletionLog.setText(jTextAreaDeletionLog.getText() + "\r\n- "
                                + labelDelFolder.replace("{0}", folder.getAbsolutePath()));
                        boolean res;
                        long folderSize = org.apache.commons.io.FileUtils.sizeOfDirectory(folder);
                        try {
                            res = FileUtils.deleteFolder(folder);
                        } catch (Exception ex) {
                            res = false;
                            Log.error(ex);
                        }
                        if (res) {
                            totalSaved += folderSize;
                        }
                        jTextAreaDeletionLog.setText(jTextAreaDeletionLog.getText() + " " + (res ? labelOK : labelERROR));
                        Log.info("Delete folder : " + folder.getAbsolutePath() + " ... " + (res ? "OK" : "ERROR"));
                    } else {
                        Log.info("Delete folder : " + folder.getAbsolutePath() + " ... SKIPPED (already deleted)");
                    }
                }
                double dSize = totalSaved;
                dSize /= (1024.0 * 1024.0);
                dSize = Math.round(dSize * 100.0) / 100.0;
                jTextAreaDeletionLog.setText(jTextAreaDeletionLog.getText() + "\r\n\r\n"
                        + labelSaved.replace("{0}", Double.toString(dSize)));
                Log.info(labelSaved.replace("{0}", Double.toString(dSize)));
            }
        }).start();
    }

    private void initTranslateComponents(Translation translation) {
        setTitle(translation.getString(Translation.SEC_DELETE, "title"));
        jButtonClose.setText(translation.getString(Translation.SEC_DELETE, "button.close"));
        jTextAreaDeletionLog.setText(translation.getString(Translation.SEC_DELETE, "info.title"));
        jButtonPaypalDonation.setToolTipText("<html><body>" + translation.getString(Translation.SEC_WABOUT, "icon.donate")
                + "<br><font color=\"blue\">http://sourceforge.net/donate/index.php?group_id=357545</font></body></html>");
    }

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void setFilesToDelete(List<File> filesToDel, List<File> foldersToDel) {
        this.filesToDel = filesToDel;
        this.foldersToDel = foldersToDel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextAreaDeletionLog = new JTextArea();
        jButtonClose = new JButton();
        jButtonPaypalDonation = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new Color(255, 255, 255));

        jTextAreaDeletionLog.setColumns(20);
        jTextAreaDeletionLog.setFont(new Font("Monospaced", 0, 11)); // NOI18N
        jTextAreaDeletionLog.setRows(5);
        jTextAreaDeletionLog.setText("(logfile) :");
        jTextAreaDeletionLog.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextAreaDeletionLog);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonPaypalDonation.setBackground(new Color(255, 255, 255));
        jButtonPaypalDonation.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/steam/cleaner/gui/icons/paypal_donate_btn.png"))); // NOI18N
        jButtonPaypalDonation.setToolTipText("");
        jButtonPaypalDonation.setBorder(null);
        jButtonPaypalDonation.setBorderPainted(false);
        jButtonPaypalDonation.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonPaypalDonation.setFocusable(false);
        jButtonPaypalDonation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonPaypalDonationActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonPaypalDonation)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jButtonClose, Alignment.TRAILING)
                    .addComponent(jButtonPaypalDonation, Alignment.TRAILING))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonPaypalDonationActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonPaypalDonationActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("http://sourceforge.net/donate/index.php?group_id=357545"));
            } catch (URISyntaxException | IOException ex) {
                Log.error(ex);
            }
        }
    }//GEN-LAST:event_jButtonPaypalDonationActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonClose;
    private JButton jButtonPaypalDonation;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextAreaDeletionLog;
    // End of variables declaration//GEN-END:variables
}
