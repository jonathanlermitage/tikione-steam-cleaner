package fr.tikione.steam.cleaner.util;

import javax.swing.table.DefaultTableModel;

/**
 * A table model for redistributable packages found on the hard drive.
 */
@SuppressWarnings("serial")
public class RedistTableModel extends DefaultTableModel {

    /** Table columns type. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private final Class<Object>[] types = new Class[]{Boolean.class, String.class, Double.class, String.class};

    /** Indicates if table columns are editable. */
    private final boolean[] canEdit = new boolean[]{true, false, false, false};

    @Override
    public Class<Object> getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    /**
     * Table model for redistributable packages found.
     *
     * @param translation messages translation handler.
     */
    public RedistTableModel(Translation translation) {
        super(new Object[][]{}, new String[]{
            translation.getString("W_MAIN", "redistTable.col.title.select"),
            translation.getString("W_MAIN", "redistTable.col.title.path"),
            translation.getString("W_MAIN", "redistTable.col.title.size"),
            translation.getString("W_MAIN", "redistTable.col.title.title")});
    }
}
