package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import view.TMView;

public class TMData {
	
	private ArrayList<Transition> list;
	private TMView view;
	private int lect; //Tete de lecture
	private Transition current;
	
	public TMData(TMView v){
		view = v;
		list = new ArrayList<Transition>();
	}
	
	public void run(){
		lect = 0;
		Character first = view.getTapeLabel().getText().charAt(lect);
		String firstChar = first.toString();
		current = getTransitionSym("q0", firstChar);
		while(!current.getCurrentState().equals("rej")){
			if(doTransition(current)){
				System.out.println("Accepted");
				break;
			}
		}
	}
	
	public boolean doTransition(Transition t){
		boolean accepted = false;
		System.out.println("do");
		if(!t.getCurrentState().equals("acc")){
			String redSymbole = t.getRedSymbole();
			String newSymbole = t.getNewSymbole();
			String direction = t.getDirection();
			String nextState = t.getNextState();
			System.out.println("next : "+nextState);
			
			Character redChar = view.getTapeLabel().getText().charAt(lect);
			String redS = redChar.toString();
			
			String tape = view.getTapeLabel().getText();
			StringBuilder sbuild = new StringBuilder(tape);
			sbuild.setCharAt(lect, newSymbole.charAt(0));
			view.getTapeLabel().setText(sbuild.toString());
			
			if(direction.equals("r")) lect++;
			else if(direction.equals("l")) lect--;
			
			current = getTransitionSym(nextState,redSymbole);
		}
		else accepted = true;
		return accepted;
	}
	
	public Transition getTransition(String state){
		Transition ret = null;
		if(state.equals("acc")) return new Transition("acc","","","","");
		Iterator<Transition> it = list.iterator();
		while(it.hasNext()){
			Transition index = it.next();
			if(index.getCurrentState().equals(state)){
				ret = index;
			}
		}
		if(ret == null) throw new NoSuchElementException("Etat "+state+"introuvable");
		return ret;
	}
	
	public Transition getTransitionSym(String state, String redSymbole){
		Transition ret = null;
		if(state.equals("acc")) return new Transition("acc","","","","");
		Iterator<Transition> it = list.iterator();
		while(it.hasNext()){
			Transition index = it.next();
			if(index.getCurrentState().equals(state) && index.getRedSymbole().equals(redSymbole)){
				ret = index;
			}
		}
		if(ret == null) throw new NoSuchElementException("Etat "+state+" symbole "+redSymbole+" introuvable");
		return ret;
	}

	public void loadTrans(){
		
		BufferedReader flux;
		try{
			flux = new BufferedReader(new FileReader("../FoncTrans.txt"));
			try{
				String line="";
				while((line = flux.readLine()) != null){
					if(line.equals("<transitions>")){ //<transition> = début des informations
						while(!(line = flux.readLine()).equals("<fin transitions>")){
							String[] lineSplited = line.split(" ");
							String currentState = lineSplited[0];
							String redSymbole = lineSplited[1];
							String newSymbole = lineSplited[2];
							String direction = lineSplited[3];
							String nextState = lineSplited[4];
							
							list.add(new Transition(currentState,redSymbole,newSymbole,direction,nextState));
						}
					}
				}
			}
			finally{ flux.close(); }
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	
	public ArrayList<Transition> getList(){
		return list;
	}
}
