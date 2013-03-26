package fr.tikione.steam.cleaner.gui.dialog;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.steam.cleaner.util.CountryLanguage;
import fr.tikione.steam.cleaner.util.GraphicsUtils;
import fr.tikione.steam.cleaner.util.Log;
import fr.tikione.steam.cleaner.util.Translation;
import fr.tikione.steam.cleaner.util.conf.Config;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Options window.
 */
@SuppressWarnings({"serial", "rawtypes"})
public class JDialogOptionsTabs extends JDialog {

    /** The program configuration handler. */
    private Config config;

    /** The links between program languages descriptions (ex: French) and codes (ex: fr_FR). */
    private Map<String, String> langDescToLangCode = new LinkedHashMap<>(4);

    /** The program language translation handler. */
    private Translation translation;

    /**
     * Create new form JDialogOptions.
     *
     * @param parent the parent component.
     * @param modal indicates if the frame is modal.
     * @param translation the program language translation handler.
     * @throws IOException
     */
    @SuppressWarnings({"CallToThreadStartDuringObjectConstruction", "LeakingThisInConstructor", "unchecked"})
    public JDialogOptionsTabs(java.awt.Frame parent, boolean modal, final Translation translation)
            throws IOException {
        super(parent, modal);
        config = Config.getInstance();
        this.translation = translation;
        initComponents();
        initTranslateComponents(translation);
        jLabelDescP0.setVisible(false);
        jLabelDescP1.setVisible(false);
        GraphicsUtils.setFrameCentered(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CountryLanguage> availLang;
                try {
                    jCheckBoxCheckForUpdatesAtStartup.setSelected(config.getCheckForUpdatesAtStartup());
                    jCheckBoxListEnableExpRedists.setSelected(config.getEnableExperimentalPatterns());
                    // List and auto-select language.
                    availLang = Translation.getAvailLangList();
                    for (CountryLanguage lang : availLang) {
                        try {
                            langDescToLangCode.put(lang.getDesc(), lang.getCode());
                            ImageIcon langImg = new ImageIcon(Translation.CONF_BASEPATH_FLAGS + lang.getCode() + ".png");
                            langImg.setDescription(lang.getDesc());
                            jComboBoxLang.addItem(langImg);
                        } catch (Exception ex) {
                            Log.error(ex);
                        }
                    }
                    String selected = config.getSelectedLanguage(availLang);
                    LANG_PRESELEC:
                    for (int nLng = 0; nLng < availLang.size(); nLng++) {
                        if (availLang.get(nLng).getCode().equals(selected)) {
                            jComboBoxLang.setSelectedIndex(nLng);
                            break LANG_PRESELEC;
                        }
                    }
                    ComboBoxRenderer renderer = new ComboBoxRenderer();
                    jComboBoxLang.setRenderer(renderer);
                    // Auto-select search max depth.
                    int maxDepth = config.getMaDepth();
                    jComboBoxSearchlMaxDepth.setSelectedItem(Integer.toString(maxDepth));
                } catch (InfinitiveLoopException | IOException ex) {
                    Log.error(ex);
                }
            }
        }).start();
    }

    /**
     * Translate the components description.
     *
     * @param translation the program language translation handler.
     */
    private void initTranslateComponents(Translation translation) {
        this.setTitle(translation.getString(Translation.SEC_OPTIONS, "title"));
        jLabeSearchlMaxDepth.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.searchMaxDepth"));
        jCheckBoxCheckForUpdatesAtStartup.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.checkForUpdatesAtStartup"));
        jCheckBoxListEnableExpRedists.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.includeExpRedistPatterns"));
        jButtonCancelP0.setText(translation.getString(Translation.SEC_OPTIONS, "button.close"));
        jButtonOKP0.setText(translation.getString(Translation.SEC_OPTIONS, "button.validate"));
        jLabelSelectLang.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.language"));
        jTabbedPaneOpts.setTitleAt(0, "   " + translation.getString(Translation.SEC_OPTIONS, "tab.options") + "   ");
        jTabbedPaneOpts.setTitleAt(1, "   " + translation.getString(Translation.SEC_OPTIONS, "tab.experimental") + "   ");
        jLabelInfoP1.setText(translation.getString(Translation.SEC_OPTIONS, "tab.expWarning"));
    }

    /** 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new JPanel();
        jTabbedPaneOpts = new JTabbedPane();
        jPanelP0 = new JPanel();
        jLabelSelectLang = new JLabel();
        jComboBoxLang = new JComboBox();
        jLabeSearchlMaxDepth = new JLabel();
        jComboBoxSearchlMaxDepth = new JComboBox();
        jLabelDescP0 = new JLabel();
        jCheckBoxCheckForUpdatesAtStartup = new JCheckBox();
        jPanelP1 = new JPanel();
        jPanelInfoP1 = new JPanel();
        jLabelInfoP1 = new JLabel();
        jLabelDescP1 = new JLabel();
        jCheckBoxListEnableExpRedists = new JCheckBox();
        jButtonOKP0 = new JButton();
        jButtonCancelP0 = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanelMain.setBackground(new Color(255, 255, 255));

        jTabbedPaneOpts.setBackground(new Color(255, 255, 255));
        jTabbedPaneOpts.setFont(new Font("Tahoma", 1, 11)); // NOI18N

        jPanelP0.setBackground(new Color(255, 255, 255));

        jLabelSelectLang.setText("Program language :");
        jLabelSelectLang.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jLabelSelectLangMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jLabelSelectLangMouseExited(evt);
            }
        });

        jComboBoxLang.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jComboBoxLangMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jComboBoxLangMouseExited(evt);
            }
        });

        jLabeSearchlMaxDepth.setText("Search maximum depth :");
        jLabeSearchlMaxDepth.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jLabeSearchlMaxDepthMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jLabeSearchlMaxDepthMouseExited(evt);
            }
        });

        jComboBoxSearchlMaxDepth.setModel(new DefaultComboBoxModel(new String[] { "3", "4", "5", "6", "7", "8" }));
        jComboBoxSearchlMaxDepth.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jComboBoxSearchlMaxDepth.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jComboBoxSearchlMaxDepthMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jComboBoxSearchlMaxDepthMouseExited(evt);
            }
        });

        jLabelDescP0.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelDescP0.setVerticalAlignment(SwingConstants.TOP);
        jLabelDescP0.setBorder(BorderFactory.createTitledBorder(null, "Information :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 11), new Color(0, 0, 153))); // NOI18N

        jCheckBoxCheckForUpdatesAtStartup.setBackground(new Color(255, 255, 255));
        jCheckBoxCheckForUpdatesAtStartup.setText("Check for updates at startup");
        jCheckBoxCheckForUpdatesAtStartup.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jCheckBoxCheckForUpdatesAtStartupMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jCheckBoxCheckForUpdatesAtStartupMouseExited(evt);
            }
        });

        GroupLayout jPanelP0Layout = new GroupLayout(jPanelP0);
        jPanelP0.setLayout(jPanelP0Layout);
        jPanelP0Layout.setHorizontalGroup(
            jPanelP0Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelP0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelP0Layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(jPanelP0Layout.createSequentialGroup()
                        .addGroup(jPanelP0Layout.createParallelGroup(Alignment.LEADING)
                            .addComponent(jLabelDescP0, GroupLayout.PREFERRED_SIZE, 626, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelP0Layout.createSequentialGroup()
                                .addComponent(jLabelSelectLang)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jComboBoxLang, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelP0Layout.createSequentialGroup()
                        .addGroup(jPanelP0Layout.createParallelGroup(Alignment.LEADING)
                            .addGroup(jPanelP0Layout.createSequentialGroup()
                                .addComponent(jLabeSearchlMaxDepth)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jComboBoxSearchlMaxDepth, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBoxCheckForUpdatesAtStartup))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelP0Layout.setVerticalGroup(
            jPanelP0Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelP0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelP0Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabelSelectLang)
                    .addComponent(jComboBoxLang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(jPanelP0Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jComboBoxSearchlMaxDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabeSearchlMaxDepth))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxCheckForUpdatesAtStartup)
                .addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(jLabelDescP0, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneOpts.addTab("  General  ", jPanelP0);

        jPanelP1.setBackground(new Color(255, 255, 255));

        jPanelInfoP1.setBackground(new Color(247, 234, 234));
        jPanelInfoP1.setBorder(new LineBorder(new Color(153, 0, 51), 1, true));

        jLabelInfoP1.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jLabelInfoP1.setForeground(new Color(153, 0, 0));
        jLabelInfoP1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelInfoP1.setText("Warning : experimental functionalities. Use them with caution !");

        GroupLayout jPanelInfoP1Layout = new GroupLayout(jPanelInfoP1);
        jPanelInfoP1.setLayout(jPanelInfoP1Layout);
        jPanelInfoP1Layout.setHorizontalGroup(
            jPanelInfoP1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, jPanelInfoP1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelInfoP1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelInfoP1Layout.setVerticalGroup(
            jPanelInfoP1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, jPanelInfoP1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelInfoP1)
                .addContainerGap())
        );

        jLabelDescP1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelDescP1.setVerticalAlignment(SwingConstants.TOP);
        jLabelDescP1.setBorder(BorderFactory.createTitledBorder(null, "Information :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 11), new Color(0, 51, 153))); // NOI18N

        jCheckBoxListEnableExpRedists.setBackground(new Color(255, 255, 255));
        jCheckBoxListEnableExpRedists.setText("Include experimental redist package patterns in search");
        jCheckBoxListEnableExpRedists.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jCheckBoxListEnableExpRedistsMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                jCheckBoxListEnableExpRedistsMouseExited(evt);
            }
        });

        GroupLayout jPanelP1Layout = new GroupLayout(jPanelP1);
        jPanelP1.setLayout(jPanelP1Layout);
        jPanelP1Layout.setHorizontalGroup(
            jPanelP1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelP1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelP1Layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jLabelDescP1, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                    .addComponent(jCheckBoxListEnableExpRedists)
                    .addComponent(jPanelInfoP1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelP1Layout.setVerticalGroup(
            jPanelP1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelP1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanelInfoP1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxListEnableExpRedists)
                .addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addComponent(jLabelDescP1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneOpts.addTab("  Experimental  ", jPanelP1);

        jButtonOKP0.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jButtonOKP0.setText("OK");
        jButtonOKP0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOKP0ActionPerformed(evt);
            }
        });

        jButtonCancelP0.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jButtonCancelP0.setText("Cancel");
        jButtonCancelP0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCancelP0ActionPerformed(evt);
            }
        });

        GroupLayout jPanelMainLayout = new GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jTabbedPaneOpts, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCancelP0)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOKP0)))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneOpts, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jButtonOKP0)
                    .addComponent(jButtonCancelP0))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(jPanelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(jPanelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOKActionPerformedGeneral(ActionEvent evt) {
        String langDescSelected = ((ImageIcon) jComboBoxLang.getSelectedItem()).getDescription();
        String langCodeSelected = langDescToLangCode.get(langDescSelected);
        config.setSelecteLanguage(langCodeSelected);
        config.setMaxDepth(Integer.parseInt(jComboBoxSearchlMaxDepth.getSelectedItem().toString()));
        config.setCheckForUpdatesAtStartup(jCheckBoxCheckForUpdatesAtStartup.isSelected());
        config.setEnableExperimentalPatterns(jCheckBoxListEnableExpRedists.isSelected());
        this.dispose();
    }

    private void jButtonCancelP0ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonCancelP0ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelP0ActionPerformed

    private void jComboBoxLangMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jComboBoxLangMouseEntered
        uiEvtLangEntered();
    }//GEN-LAST:event_jComboBoxLangMouseEntered

    private void jComboBoxLangMouseExited(MouseEvent evt) {//GEN-FIRST:event_jComboBoxLangMouseExited
        uiEvtLangExited();
    }//GEN-LAST:event_jComboBoxLangMouseExited

    private void jLabelSelectLangMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabelSelectLangMouseEntered
        uiEvtLangEntered();
    }//GEN-LAST:event_jLabelSelectLangMouseEntered

    private void jLabelSelectLangMouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabelSelectLangMouseExited
        uiEvtLangExited();
    }//GEN-LAST:event_jLabelSelectLangMouseExited

    private void uiEvtLangEntered() {
        jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.language") + "</body></html>");
        jLabelDescP0.setVisible(true);
    }

    private void uiEvtLangExited() {
        jLabelDescP0.setVisible(false);
        jLabelDescP0.setText("");
    }

    private void jComboBoxSearchlMaxDepthMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jComboBoxSearchlMaxDepthMouseEntered
        uiEvtSearchlMaxDepthEntered();
    }//GEN-LAST:event_jComboBoxSearchlMaxDepthMouseEntered

    private void jComboBoxSearchlMaxDepthMouseExited(MouseEvent evt) {//GEN-FIRST:event_jComboBoxSearchlMaxDepthMouseExited
        uiEvtSearchlMaxDepthExited();
    }//GEN-LAST:event_jComboBoxSearchlMaxDepthMouseExited

    private void jLabeSearchlMaxDepthMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabeSearchlMaxDepthMouseEntered
        uiEvtSearchlMaxDepthEntered();
    }//GEN-LAST:event_jLabeSearchlMaxDepthMouseEntered

    private void jLabeSearchlMaxDepthMouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabeSearchlMaxDepthMouseExited
        uiEvtSearchlMaxDepthExited();
    }//GEN-LAST:event_jLabeSearchlMaxDepthMouseExited

    private void uiEvtSearchlMaxDepthEntered() {
        jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.searchMaxDepth") + "</body></html>");
        jLabelDescP0.setVisible(true);
    }

    private void uiEvtSearchlMaxDepthExited() {
        jLabelDescP0.setVisible(false);
        jLabelDescP0.setText("");
    }

    private void jButtonOKP0ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOKP0ActionPerformed
        jButtonOKActionPerformedGeneral(evt);
    }//GEN-LAST:event_jButtonOKP0ActionPerformed

    private void uiEvtSaveLogToFileEntered() {
        jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.saveLogToFile") + "</body></html>");
        jLabelDescP0.setVisible(true);
    }

    private void uiEvtSaveLogToFileExited() {
        jLabelDescP0.setVisible(false);
        jLabelDescP0.setText("");
    }

    private void jCheckBoxCheckForUpdatesAtStartupMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxCheckForUpdatesAtStartupMouseEntered
        uiEvtCheckForUpdatesAtStartupEntered();
    }//GEN-LAST:event_jCheckBoxCheckForUpdatesAtStartupMouseEntered

    private void jCheckBoxCheckForUpdatesAtStartupMouseExited(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxCheckForUpdatesAtStartupMouseExited
        uiEvtCheckForUpdatesAtStartupExited();
    }//GEN-LAST:event_jCheckBoxCheckForUpdatesAtStartupMouseExited

    private void uiEvtCheckForUpdatesAtStartupEntered() {
        jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.checkForUpdatesAtStartup") + "</body></html>");
        jLabelDescP0.setVisible(true);
    }

    private void uiEvtCheckForUpdatesAtStartupExited() {
        jLabelDescP0.setVisible(false);
        jLabelDescP0.setText("");
    }

    private void uiEvtListFromVDFOnlyEntered() {
        jLabelDescP1.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.listOnlyFromVDF") + "</body></html>");
        jLabelDescP1.setVisible(true);
    }

    private void uiEvtListFromVDFOnlyExited() {
        jLabelDescP1.setVisible(false);
        jLabelDescP1.setText("");
    }

    private void jCheckBoxListEnableExpRedistsMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxListEnableExpRedistsMouseEntered
        uiEvtListEnableExpRedistsEntered();
    }//GEN-LAST:event_jCheckBoxListEnableExpRedistsMouseEntered

    private void jCheckBoxListEnableExpRedistsMouseExited(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxListEnableExpRedistsMouseExited
        uiEvtListEnableExpRedistsExited();
    }//GEN-LAST:event_jCheckBoxListEnableExpRedistsMouseExited

    private void uiEvtListEnableExpRedistsEntered() {
        jLabelDescP1.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.includeExpRedistPatterns") + "</body></html>");
        jLabelDescP1.setVisible(true);
    }

    private void uiEvtListEnableExpRedistsExited() {
        jLabelDescP1.setVisible(false);
        jLabelDescP1.setText("");
    }

    private void uiEvtEnableDebugEntered() {
        jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.enableDebug") + "</body></html>");
        jLabelDescP0.setVisible(true);
    }

    private void uiEvtEnableDebugExited() {
        jLabelDescP0.setVisible(false);
        jLabelDescP0.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonCancelP0;
    private JButton jButtonOKP0;
    private JCheckBox jCheckBoxCheckForUpdatesAtStartup;
    private JCheckBox jCheckBoxListEnableExpRedists;
    private JComboBox jComboBoxLang;
    private JComboBox jComboBoxSearchlMaxDepth;
    private JLabel jLabeSearchlMaxDepth;
    private JLabel jLabelDescP0;
    private JLabel jLabelDescP1;
    private JLabel jLabelInfoP1;
    private JLabel jLabelSelectLang;
    private JPanel jPanelInfoP1;
    private JPanel jPanelMain;
    private JPanel jPanelP0;
    private JPanel jPanelP1;
    private JTabbedPane jTabbedPaneOpts;
    // End of variables declaration//GEN-END:variables

    private class ComboBoxRenderer extends JLabel implements ListCellRenderer {

        ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            ImageIcon icon = (ImageIcon) value;
            setText(icon.getDescription());
            setIcon(icon);
            return this;
        }
    }
}
