package view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import data.Transition;

public class TransitionTableModel extends AbstractTableModel{

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
	public Class getColumnClass(int column) {
        switch (column) {
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
                name = "State";
                break;
            case 1:
                name = "Read Sym";
                break;
            case 2:
                name = "New Sym";
                break;
            case 3:
                name = "Dir";
                break;
            case 4:
                name = "Next";
                break;
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
