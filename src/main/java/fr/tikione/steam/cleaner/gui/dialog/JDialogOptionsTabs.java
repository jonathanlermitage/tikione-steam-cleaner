package fr.tikione.steam.cleaner.gui.dialog;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.steam.cleaner.util.CountryLanguage;
import fr.tikione.steam.cleaner.util.GraphicsUtils;
import fr.tikione.steam.cleaner.util.Log;
import fr.tikione.steam.cleaner.util.Translation;
import fr.tikione.steam.cleaner.util.conf.Config;
import fr.tikione.steam.cleaner.util.conf.Patterns;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.CharConversionException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
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

	/** The program configuration handler (global). */
	private Config config;

	/** The program configuration handler (redist patterns). */
	private Patterns patternsCfg;

	/** The links between program languages descriptions (ex: French) and codes (ex: fr_FR). */
	private Map<String, String> langDescToLangCode = new LinkedHashMap<>(4);

	/** The program language translation handler. */
	private final Translation translation;

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
					throws IOException, CharConversionException, InfinitiveLoopException {
		super(parent, modal);
		config = Config.getInstance();
		patternsCfg = Patterns.getInstance();
		this.translation = translation;
		initComponents();
		initTranslateComponents(translation);
		jLabelDescP0.setVisible(true);
		jLabelDescP1.setVisible(true);
		jLabelDownloadDefinitionsProgress.setText("");
		GraphicsUtils.setFrameCentered(this);		
		jTextAreaRedistDefinitions.setText(config.getRemoteDefinitionFiles().replaceAll(Patterns.REMOTE_DEFINITION_FILES_SEPARATOR, "\n"));
		
		new Thread(() -> {
			List<CountryLanguage> availLang;
			try {
				jCheckBoxCheckForUpdatesAtStartup.setSelected(config.getCheckForUpdatesAtStartup());
				jCheckBoxListEnableExpRedists.setSelected(patternsCfg.getEnableExperimentalPatterns());
				// List and auto-select language.
				availLang = Translation.getAvailLangList();
				availLang.stream().forEach((lang) -> {
					try {
						langDescToLangCode.put(lang.getDesc(), lang.getCode());
						ImageIcon langImg = new ImageIcon(Translation.CONF_BASEPATH_FLAGS + lang.getCode() + ".png");
						langImg.setDescription(lang.getDesc());
						jComboBoxLang.addItem(langImg);
					} catch (Exception ex) {
						Log.error(ex);
					}
				});
				String selected = config.getSelectedLanguage(availLang);
				LANG_PRESELEC:
				for (int nLng = 0; nLng < availLang.size(); nLng++) {
					if (availLang.get(nLng).getCode().equals(selected)) {
						jComboBoxLang.setSelectedIndex(nLng);
						break;
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
		}).start();

		//jTabbedPaneOpts.setEnabledAt(1, false);
	}

	/**
	 * Translate the components description.
	 *
	 * @param translation the program language translation handler.
	 */
	private void initTranslateComponents(Translation translation) {
		this.setTitle(translation.getString(Translation.SEC_OPTIONS, "title"));
		jLabelSearchlMaxDepth.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.searchMaxDepth"));
		jLabelDefinitionFiles.setText(translation.getString(Translation.SEC_OPTIONS, "optionLine.definitionFiles"));
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
    jLabelSearchlMaxDepth = new JLabel();
    jComboBoxSearchlMaxDepth = new JComboBox();
    jLabelDescP0 = new JLabel();
    jCheckBoxCheckForUpdatesAtStartup = new JCheckBox();
    jLabelDefinitionFiles = new JLabel();
    jScrollPane1 = new JScrollPane();
    jTextAreaRedistDefinitions = new JTextArea();
    jButtonDownloadDefinitions = new JButton();
    jLabelDownloadDefinitionsProgress = new JLabel();
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
    jTabbedPaneOpts.setFont(new Font("Dialog", 1, 11)); // NOI18N

    jPanelP0.setBackground(new Color(255, 255, 255));

    jLabelSelectLang.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jLabelSelectLang.setText("Program language :");
    jLabelSelectLang.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) {
        jLabelSelectLangMouseEntered(evt);
      }
      public void mouseExited(MouseEvent evt) {
        jLabelSelectLangMouseExited(evt);
      }
    });

    jComboBoxLang.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jComboBoxLang.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) {
        jComboBoxLangMouseEntered(evt);
      }
      public void mouseExited(MouseEvent evt) {
        jComboBoxLangMouseExited(evt);
      }
    });

    jLabelSearchlMaxDepth.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jLabelSearchlMaxDepth.setText("Search maximum depth :");
    jLabelSearchlMaxDepth.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) {
        jLabelSearchlMaxDepthMouseEntered(evt);
      }
      public void mouseExited(MouseEvent evt) {
        jLabelSearchlMaxDepthMouseExited(evt);
      }
    });

    jComboBoxSearchlMaxDepth.setFont(new Font("Dialog", 0, 13)); // NOI18N
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

    jLabelDescP0.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jLabelDescP0.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelDescP0.setVerticalAlignment(SwingConstants.TOP);
    jLabelDescP0.setBorder(BorderFactory.createTitledBorder(null, "Information :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", 1, 11), new Color(0, 0, 153))); // NOI18N

    jCheckBoxCheckForUpdatesAtStartup.setBackground(new Color(255, 255, 255));
    jCheckBoxCheckForUpdatesAtStartup.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jCheckBoxCheckForUpdatesAtStartup.setText("Check for updates at startup");
    jCheckBoxCheckForUpdatesAtStartup.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) {
        jCheckBoxCheckForUpdatesAtStartupMouseEntered(evt);
      }
      public void mouseExited(MouseEvent evt) {
        jCheckBoxCheckForUpdatesAtStartupMouseExited(evt);
      }
    });

    jLabelDefinitionFiles.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jLabelDefinitionFiles.setText("Redist definition files (one URL per line) :");

    jTextAreaRedistDefinitions.setColumns(20);
    jTextAreaRedistDefinitions.setFont(new Font("Monospaced", 0, 12)); // NOI18N
    jTextAreaRedistDefinitions.setForeground(new Color(0, 102, 204));
    jTextAreaRedistDefinitions.setRows(2);
    jTextAreaRedistDefinitions.setTabSize(2);
    jTextAreaRedistDefinitions.setText("https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/dist2/conf/backup/tikione-steam-cleaner_patterns.ini\n");
    jScrollPane1.setViewportView(jTextAreaRedistDefinitions);

    jButtonDownloadDefinitions.setText("Download definition files");
    jButtonDownloadDefinitions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButtonDownloadDefinitionsActionPerformed(evt);
      }
    });

    jLabelDownloadDefinitionsProgress.setText("download status ....");

    GroupLayout jPanelP0Layout = new GroupLayout(jPanelP0);
    jPanelP0.setLayout(jPanelP0Layout);
    jPanelP0Layout.setHorizontalGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelP0Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(jPanelP0Layout.createSequentialGroup()
            .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addGroup(jPanelP0Layout.createSequentialGroup()
                .addComponent(jLabelSearchlMaxDepth)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSearchlMaxDepth, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
              .addComponent(jCheckBoxCheckForUpdatesAtStartup)
              .addComponent(jLabelDefinitionFiles))
            .addGap(0, 399, Short.MAX_VALUE))
          .addGroup(jPanelP0Layout.createSequentialGroup()
            .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(jLabelDescP0, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addGroup(jPanelP0Layout.createSequentialGroup()
                .addComponent(jLabelSelectLang)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxLang, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 117, Short.MAX_VALUE))
              .addComponent(jScrollPane1)
              .addGroup(GroupLayout.Alignment.TRAILING, jPanelP0Layout.createSequentialGroup()
                .addComponent(jLabelDownloadDefinitionsProgress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDownloadDefinitions)))
            .addContainerGap())))
    );
    jPanelP0Layout.setVerticalGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelP0Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelSelectLang)
          .addComponent(jComboBoxLang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(jComboBoxSearchlMaxDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabelSearchlMaxDepth))
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jCheckBoxCheckForUpdatesAtStartup)
        .addGap(18, 18, 18)
        .addComponent(jLabelDefinitionFiles)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelP0Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonDownloadDefinitions)
          .addComponent(jLabelDownloadDefinitionsProgress))
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelDescP0, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jTabbedPaneOpts.addTab("  General  ", jPanelP0);

    jPanelP1.setBackground(new Color(255, 255, 255));

    jPanelInfoP1.setBackground(new Color(247, 234, 234));
    jPanelInfoP1.setBorder(new LineBorder(new Color(153, 0, 51), 1, true));

    jLabelInfoP1.setFont(new Font("Dialog", 1, 11)); // NOI18N
    jLabelInfoP1.setForeground(new Color(153, 0, 0));
    jLabelInfoP1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelInfoP1.setText("Warning : experimental functionalities. Use them with caution !");

    GroupLayout jPanelInfoP1Layout = new GroupLayout(jPanelInfoP1);
    jPanelInfoP1.setLayout(jPanelInfoP1Layout);
    jPanelInfoP1Layout.setHorizontalGroup(jPanelInfoP1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(GroupLayout.Alignment.TRAILING, jPanelInfoP1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabelInfoP1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanelInfoP1Layout.setVerticalGroup(jPanelInfoP1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(GroupLayout.Alignment.TRAILING, jPanelInfoP1Layout.createSequentialGroup()
        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabelInfoP1)
        .addContainerGap())
    );

    jLabelDescP1.setFont(new Font("Dialog", 0, 13)); // NOI18N
    jLabelDescP1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelDescP1.setVerticalAlignment(SwingConstants.TOP);
    jLabelDescP1.setBorder(BorderFactory.createTitledBorder(null, "Information :", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", 1, 11), new Color(0, 51, 153))); // NOI18N

    jCheckBoxListEnableExpRedists.setBackground(new Color(255, 255, 255));
    jCheckBoxListEnableExpRedists.setFont(new Font("Dialog", 0, 13)); // NOI18N
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
    jPanelP1Layout.setHorizontalGroup(jPanelP1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelP1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelP1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addComponent(jLabelDescP1, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
          .addComponent(jCheckBoxListEnableExpRedists)
          .addComponent(jPanelInfoP1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    jPanelP1Layout.setVerticalGroup(jPanelP1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelP1Layout.createSequentialGroup()
        .addGap(7, 7, 7)
        .addComponent(jPanelInfoP1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(jCheckBoxListEnableExpRedists)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 249, Short.MAX_VALUE)
        .addComponent(jLabelDescP1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    jTabbedPaneOpts.addTab("  Experimental  ", jPanelP1);

    jButtonOKP0.setFont(new Font("Dialog", 1, 11)); // NOI18N
    jButtonOKP0.setText("OK");
    jButtonOKP0.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButtonOKP0ActionPerformed(evt);
      }
    });

    jButtonCancelP0.setFont(new Font("Dialog", 1, 11)); // NOI18N
    jButtonCancelP0.setText("Cancel");
    jButtonCancelP0.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        jButtonCancelP0ActionPerformed(evt);
      }
    });

    GroupLayout jPanelMainLayout = new GroupLayout(jPanelMain);
    jPanelMain.setLayout(jPanelMainLayout);
    jPanelMainLayout.setHorizontalGroup(jPanelMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelMainLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(jPanelMainLayout.createSequentialGroup()
            .addComponent(jTabbedPaneOpts, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(jPanelMainLayout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jButtonCancelP0)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButtonOKP0)))
        .addContainerGap())
    );
    jPanelMainLayout.setVerticalGroup(jPanelMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(jPanelMainLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jTabbedPaneOpts)
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanelMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonOKP0)
          .addComponent(jButtonCancelP0))
        .addContainerGap())
    );

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(jPanelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
		patternsCfg.setEnableExperimentalPatterns(jCheckBoxListEnableExpRedists.isSelected());

		String definitions = jTextAreaRedistDefinitions.getText().replaceAll("\\n", Patterns.REMOTE_DEFINITION_FILES_SEPARATOR);
		config = Config.getInstance();
		config.setRemoteDefinitionFiles(definitions);

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
	}

	private void uiEvtLangExited() {
		jLabelDescP0.setText("");
	}

    private void jComboBoxSearchlMaxDepthMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jComboBoxSearchlMaxDepthMouseEntered
			uiEvtSearchlMaxDepthEntered();
    }//GEN-LAST:event_jComboBoxSearchlMaxDepthMouseEntered

    private void jComboBoxSearchlMaxDepthMouseExited(MouseEvent evt) {//GEN-FIRST:event_jComboBoxSearchlMaxDepthMouseExited
			uiEvtSearchlMaxDepthExited();
    }//GEN-LAST:event_jComboBoxSearchlMaxDepthMouseExited

    private void jLabelSearchlMaxDepthMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jLabelSearchlMaxDepthMouseEntered
			uiEvtSearchlMaxDepthEntered();
    }//GEN-LAST:event_jLabelSearchlMaxDepthMouseEntered

    private void jLabelSearchlMaxDepthMouseExited(MouseEvent evt) {//GEN-FIRST:event_jLabelSearchlMaxDepthMouseExited
			uiEvtSearchlMaxDepthExited();
    }//GEN-LAST:event_jLabelSearchlMaxDepthMouseExited

	private void uiEvtSearchlMaxDepthEntered() {
		jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.searchMaxDepth") + "</body></html>");
	}

	private void uiEvtSearchlMaxDepthExited() {
		jLabelDescP0.setText("");
	}

    private void jButtonOKP0ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOKP0ActionPerformed
			jButtonOKActionPerformedGeneral(evt);
    }//GEN-LAST:event_jButtonOKP0ActionPerformed

	private void uiEvtSaveLogToFileEntered() {
		jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.saveLogToFile") + "</body></html>");
	}

	private void uiEvtSaveLogToFileExited() {
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
	}

	private void uiEvtCheckForUpdatesAtStartupExited() {
		jLabelDescP0.setText("");
	}

	private void uiEvtListFromVDFOnlyEntered() {
		jLabelDescP1.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.listOnlyFromVDF") + "</body></html>");
	}

	private void uiEvtListFromVDFOnlyExited() {
		jLabelDescP1.setText("");
	}

    private void jCheckBoxListEnableExpRedistsMouseEntered(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxListEnableExpRedistsMouseEntered
			uiEvtListEnableExpRedistsEntered();
    }//GEN-LAST:event_jCheckBoxListEnableExpRedistsMouseEntered

    private void jCheckBoxListEnableExpRedistsMouseExited(MouseEvent evt) {//GEN-FIRST:event_jCheckBoxListEnableExpRedistsMouseExited
			uiEvtListEnableExpRedistsExited();
    }//GEN-LAST:event_jCheckBoxListEnableExpRedistsMouseExited

  private void jButtonDownloadDefinitionsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonDownloadDefinitionsActionPerformed
    jLabelDownloadDefinitionsProgress.setText("downloading redist definition files... 1/" + 4);
		
  }//GEN-LAST:event_jButtonDownloadDefinitionsActionPerformed

	private void uiEvtListEnableExpRedistsEntered() {
		jLabelDescP1.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.includeExpRedistPatterns") + "</body></html>");
	}

	private void uiEvtListEnableExpRedistsExited() {
		jLabelDescP1.setText("");
	}

	private void uiEvtEnableDebugEntered() {
		jLabelDescP0.setText("<html><body>" + translation.getString(Translation.SEC_OPTIONS, "notice.enableDebug") + "</body></html>");
	}

	private void uiEvtEnableDebugExited() {
		jLabelDescP0.setText("aaa");
	}
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JButton jButtonCancelP0;
  private JButton jButtonDownloadDefinitions;
  private JButton jButtonOKP0;
  private JCheckBox jCheckBoxCheckForUpdatesAtStartup;
  private JCheckBox jCheckBoxListEnableExpRedists;
  private JComboBox jComboBoxLang;
  private JComboBox jComboBoxSearchlMaxDepth;
  private JLabel jLabelDefinitionFiles;
  private JLabel jLabelDescP0;
  private JLabel jLabelDescP1;
  private JLabel jLabelDownloadDefinitionsProgress;
  private JLabel jLabelInfoP1;
  private JLabel jLabelSearchlMaxDepth;
  private JLabel jLabelSelectLang;
  private JPanel jPanelInfoP1;
  private JPanel jPanelMain;
  private JPanel jPanelP0;
  private JPanel jPanelP1;
  private JScrollPane jScrollPane1;
  private JTabbedPane jTabbedPaneOpts;
  private JTextArea jTextAreaRedistDefinitions;
  // End of variables declaration//GEN-END:variables

	private class ComboBoxRenderer extends JLabel implements ListCellRenderer {

		@SuppressWarnings("OverridableMethodCallInConstructor")
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
