package mini_projet1;

import java.net.*;

import java.util.*;



public class SERVER1 {


    public static List<membre_enchere>membres =new ArrayList<membre_enchere>();
    public static List<encheres>enchere =new ArrayList<encheres>();
    public static List<offre_membre>offres1 =new ArrayList<offre_membre>();



	
    

    public static void addmembre (membre_enchere m){

    	membres.add(m);

    }

  
    public static void addenchere (encheres e){

    	enchere.add(e);

    }
    
    public static boolean cheklistencheres(){

       	if(enchere.isEmpty()) {
        		return true;
        	}
       	return false;
    }
    
public static String listencheres() {
     String m="";
     for(encheres e:enchere)  {            
		m += e.GetId() + "# "+ e.Getdescription() +"#"+e.Getprix()+"#"+e.Getheure()+"\n";
     }return m;}

    public static membre_enchere checkmembre(String name){

        for(membre_enchere m:membres){

            if(m.GetName().equals(name)  ){

                return m;

            }

        }

        return null;

    }

    public static membre_enchere checkmembre(InetAddress address, int port){

        for(membre_enchere m:membres){

            if(m.getAddress().getHostAddress().equals(address.getHostAddress()) && m.getPort()==port ){

                return m;

            }

        }

        return null;

    }
    

    public static encheres checkenchere(int id ){
             for(encheres e:enchere){
            if(e.GetId()==id){

                return e;

            }

        }

        return null;

    }
   

 
    public static String listmembres() {
        String m="";
        for(membre_enchere e:membres)  {            
   		m += e.GetId()+"#"+e.GetName()+ "\n";
        }return m;}

      
    public static int checkenchereetat (encheres e ){
       if(e.GetEtat()==0){

           return 0;

       }else if(e.GetEtat()==1){
return(1);
   }

   return (2);

}


}
