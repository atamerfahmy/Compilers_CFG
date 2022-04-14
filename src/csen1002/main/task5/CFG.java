package csen1002.main.task5;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write your info here
 * 
 * @name Ahmed Tamer
 * @id 43-2117
 * @labNumber 11
 */
public class CFG {
	/**
	 * CFG constructor
	 * 
	 * @param description is the string describing a CFG
	 */
	
	HashMap<String, ArrayList<String>> cfg;
	ArrayList<String> cfgVars;
	
	public CFG(String description) {
		String[] parts = description.split(";");
		
		this.cfg = new HashMap<String, ArrayList<String>>();
		this.cfgVars = new ArrayList<String>();
		
		for (String x : parts) {
			String[] sub = x.split(",");
			ArrayList<String> grammer = new ArrayList<String>();
			
			for(int i = 1; i < sub.length; i++) {
				grammer.add(sub[i]);
			}
			this.cfg.put(sub[0], grammer);
			this.cfgVars.add(sub[0]);
		}

	}

	/**
	 * Returns a string of elimnated left recursion.
	 * 
	 * @param input is the string to simulate by the CFG.
	 * @return string of elimnated left recursion.
	 */
	public String lre() {
		// TODO Write Your Code Here
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		String returnVal = "";
		
		for (int i = 0; i<this.cfgVars.size(); i++) {
			String name = this.cfgVars.get(i);
		    String key = name.toString();
		    ArrayList<String> values;
		    
			HashMap<String, ArrayList<String>> updatedResults = new HashMap<String, ArrayList<String>>();

		    for(String res: result.keySet()) {
		    	ArrayList<String> resGrammer = result.get(res);

		    	updatedResults.put(res, resGrammer);
		    }
		    
		    for(int j = 0; j<this.cfgVars.size() && j<i; j++) {
		    	String res = this.cfgVars.get(j);
		    	
		    	ArrayList<String> resGrammer = result.get(res);
		    	
		    	if(updatedResults.get(name) != null) {
			    	values = updatedResults.get(name);
			    }else {
			    	values = this.cfg.get(name);
			    }
		    	
			    if(!res.toString().contains("'")) {
			    	
			    	ArrayList<String> newValues = new ArrayList<String>();

			    	for(String var: values) {
			    		if((var.charAt(0) + "").compareTo(res) == 0) {
			    			for(String resGrammerItem: resGrammer) {
				    			String substitute = resGrammerItem + var.substring(1);
				    			newValues.add(substitute);
			    			}
			    		}else {
			    			newValues.add(var);
			    		}
			    	}
			    	
			    	updatedResults.put(name, newValues);
			    }

		    }
		    
//		    for (String n: updatedResults.keySet()) {
//			    String k = name.toString();
//			    String value = updatedResults.get(n).toString();
//			    System.out.println(k + " " + value);
//			}
		    
		    result = updatedResults;
		    
		    //Check Direct LR
		    if(updatedResults.get(name) != null) {
		    	values = updatedResults.get(name);
		    }else {
		    	values = this.cfg.get(name);
		    }
	    	ArrayList<String> updatedValues = new ArrayList<String>();
	    	ArrayList<String> dashedValues = new ArrayList<String>();

	    	ArrayList<String> alphas = new ArrayList<String>();
	    	ArrayList<String> betas = new ArrayList<String>();

	    	boolean flag = false;
	    	
		    for(String var: values) {
		    	if((var.charAt(0) + "").compareTo(name) == 0) {
		    		flag = true;
	    			alphas.add(var.substring(1));
	    		}else {
	    			betas.add(var);
	    		}
		    }
		    
		    if(flag) {
		    	for(String b: betas) {
			    	updatedValues.add(b + name + "'");
			    }

			    for(String a: alphas) {
			    	dashedValues.add(a + name + "'");
			    }
		    	dashedValues.add("e");
		    	
		    	result.put(name, updatedValues);
			    result.put(name + "'", dashedValues);
		    }else {
		    	result.put(name, values);

		    }
		    
		    ArrayList<String> grr = result.get(name);
		    
		    returnVal += name;
		    
		    for(String grrV: grr) {
		    	returnVal += "," + grrV;
		    }
		    
		    returnVal += ";";
		    
		    if(updatedResults.get(name + "'") != null) {
			    ArrayList<String> grrDash = updatedResults.get(name + "'");
			    
			    returnVal += name + "'";
			    
			    for(String grrV: grrDash) {
			    	returnVal += "," + grrV;
			    }
			    
			    returnVal += ";";
			    
		    }

		}
		
		
		return returnVal.substring(0, returnVal.length()-1);
	}
	
//	public static void main(String[] args) {
//		CFG x = new CFG("S,SaT,T;T,TzG,G;G,i");
//		x.lre();
//	}
}
