package view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import data.Transition;

/**
 * Define a new TableModel to display all transition on the interface 
 */
public class TransitionTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	/**
	 * The list of Transitions to display
	 */
	ArrayList<Transition> list;
	
	public TransitionTableModel(ArrayList<Transition> li) {
		list = new ArrayList<Transition>(li);
	}
	
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Character.class;
            case 2:
                return Character.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            default:
                return String.class;
        }
    }
	
	@Override
    public String getColumnName(int column) {
        String name = " / ";
        switch (column) {
            case 0:
            	return "State";
            case 1:
            	return "Read";
            case 2:
            	return "New";
            case 3:
            	return "Dir";
            case 4:
                return "Next";
        }
        return name;
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Transition t = list.get(rowIndex);
		Object obj = null;
		switch (columnIndex) {
			case 0:
				obj = t.getCurrentState();
	            break;
	        case 1:
	        	obj = t.getReadSymbole();
	            break;
	        case 2:
	        	obj = t.getNewSymbole();
	            break;
	        case 3:
	        	obj = t.getDirection();
	            break;
	        case 4:
	        	obj = t.getNextState();
	            break;
		}
		return obj;
	}

}
