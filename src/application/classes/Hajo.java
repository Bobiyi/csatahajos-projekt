package application.classes;

import java.util.ArrayList;

//nev ; orszag ; osztaly ; tomeg(tonnában megadva) ; fegyverzet
public class Hajo {

	private String nev;
	private String orszag;
	private String osztaly;
	private int tomeg;
	private ArrayList<String> fegyverzet;
	
	
	public Hajo(String nev, String orszag, String osztaly, int tomeg, ArrayList<String> fegyverzet) {
		this.nev = nev;
		this.orszag = orszag;
		this.osztaly = osztaly;
		this.tomeg = tomeg;
		this.fegyverzet = fegyverzet;
	}


	public String getNev() {
		return nev;
	}
	

	public String getOrszag() {
		return orszag;
	}
	

	public String getOsztaly() {
		return osztaly;
	}
	

	public int getTomeg() {
		return tomeg;
	}
	

	public String getFegyverzet() {
		String eredmeny="";
		
		for (String fegyver : fegyverzet) {
			
			if (fegyver.contains("- ")) {
				if(!(fegyver.equals("")))
				eredmeny+=fegyver+"\n";
				
			} else {
				eredmeny+="- "+fegyver+"\n";
			}
		}
		
		return eredmeny;
	}
	
	public String getFegyverzet_Leiras() {
		String eredmeny="";
		
		for (String fegyver : fegyverzet) {
			
			if (fegyver.contains("- ")) {
				if(!(fegyver.equals("")))
				eredmeny+=fegyver+"\n";
				
			} else {
				eredmeny+=fegyver+", ";
			}
		}
		
		return eredmeny.substring(0,eredmeny.length()-2);
	}
	
	public String getJSONFegyverzet() {
		String eredmeny="[";
		for (String fegyver : fegyverzet) {
			eredmeny+=fegyver+",";
			
		}
		return eredmeny+"]";
	}
	
	@Override
	public String toString() {
		return nev;
	}
	
	public String getInfo() {
		return String.format("Név: %s \nOrszág: %s \nOsztály: %s \nTömeg: %d Tonna \n Fegyverzet:\n%s", nev,orszag,osztaly,tomeg,getFegyverzet());
	}
	
	
	
}