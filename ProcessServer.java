package mini_projet1;
import java.io.*;

import java.net.*;





public class ProcessServer {

    public static void sendMsg(DatagramSocket sc, String msgSend, InetAddress address, int port) throws IOException{

        DatagramPacket pkSend = new DatagramPacket(msgSend.getBytes(), msgSend.length(), address, port);

        sc.send(pkSend);

    }

    public static void main(String[] args) throws IOException {

        DatagramSocket sc=new DatagramSocket(3000);

        while (true){

            try {

                DatagramPacket pkRcv=new DatagramPacket(new byte[1024], 1024);

                sc.receive(pkRcv);

                String msgSend, msgRcv=new String(pkRcv.getData(), 0, pkRcv.getLength());

                if(msgRcv.startsWith("LOGIN") && msgRcv.length()>5) {

                    membre_enchere m=SERVER1.checkmembre(pkRcv.getAddress(), pkRcv.getPort());

                    if(m==null){

                    	 membre_enchere s=SERVER1.checkmembre(msgRcv.substring(5));

                        if(s==null){

                            SERVER1.membres.add(new membre_enchere(msgRcv.substring(5), pkRcv.getAddress(), pkRcv.getPort()));

                            msgSend="Successfully signed in as "+msgRcv.substring(5);

                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                        }else {

                            msgSend="Login "+msgRcv.substring(5)+" already in use";

                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                        }

                    }else{

                        msgSend="Already logged in as "+m.GetName();

                        sendMsg(sc, msgSend, m.getAddress(), m.getPort());

                    }

                }else {membre_enchere p=SERVER1.checkmembre(pkRcv.getAddress(), pkRcv.getPort());

                if(p==null){

                    msgSend="Log in first";

                    sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                }else {

                       if(msgRcv.equals("LIST1")) {

                    msgSend=SERVER1.listmembres();

                    sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort()); } 
                       else 

                               if(msgRcv.equals("ENCHERE")) {

                    msgSend=SERVER1.listencheres();

                    sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort()); } 

                        else  if(msgRcv.startsWith("add")){
                            membre_enchere mbrSend=SERVER1.checkmembre(pkRcv.getAddress(), pkRcv.getPort());

                        
                      String cd1 = msgRcv.substring(4);
                      String t1[]=cd1.split("#");
                      encheres  e = new encheres(Integer.parseInt(t1[0]),Integer.parseInt(t1[1]),t1[2]);
                      SERVER1.addenchere(e);
                      offre_membre o =new offre_membre(mbrSend,e,Integer.parseInt(t1[0]));
                      e.addoffre(o);

                    e.addmembre(mbrSend);
                    msgSend="l'enchere est cree";

                    sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                    

                    

                }       else
                
                


                if(msgRcv.startsWith("join")){
                    membre_enchere mbrSend=SERVER1.checkmembre(pkRcv.getAddress(), pkRcv.getPort());

                    encheres e=SERVER1.checkenchere(Integer.parseInt(msgRcv.substring(4)));

                    if(e==null){

                        msgSend="enchere "+msgRcv.substring(4)+" n'est pas trouvee";

                        sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                    }else{if(SERVER1.checkenchereetat(e)==2)
                    {
                    	 msgSend = "l'enchere est terminer " ;

                         sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());}
                         else if(SERVER1.checkenchereetat(e)==0) {
                          	 msgSend = "l'enchere est non actif " ;

                             sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());}
                         
                        	
                else if(SERVER1.checkenchereetat(e)==0) {
                        	 if(e.checkmembre(mbrSend)) {
                         

                            msgSend = "Already in enchere " + msgRcv.substring(4);

                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                        }else {

                            e.addmembre(mbrSend);

                            msgSend = "Successfully joined enchere " + msgRcv.substring(4);

                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                        } }}}
                   
                
                
                
                else   if(msgRcv.startsWith("offre")){
                        	String cd = msgRcv.substring(5);
                            String t[]=cd.split("#");
                            encheres e2 = SERVER1.checkenchere(Integer.parseInt(t[0]));
                            if(SERVER1.checkenchereetat(e2)==1) {
                            if(e2.checkoffre(Integer.parseInt(t[1]),Integer.parseInt(t[0]))){
                            	msgSend="offre"+t[1]+"non validee";

                                sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                            }else{
                                membre_enchere mbrSend=SERVER1.checkmembre(pkRcv.getAddress(), pkRcv.getPort());

                            
                            	
                                offre_membre e3 =new offre_membre(mbrSend,e2,Integer.parseInt(t[1]));


                            	msgSend="offre "+t[1]+" validee";
                            	e2.addoffre(e3);
                            	e2.Setprix(Integer.parseInt(t[1]));
                                sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                                }}else { if(SERVER1.checkenchereetat(e2)==2) {msgSend="cette enchere est terminee !!";

                                sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());}else  if(SERVER1.checkenchereetat(e2)==0) {msgSend="cette enchere est non actif !!";

                                sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());}}}
                        
                

                else    if(msgRcv.startsWith("list")){
                        	String cd = msgRcv.substring(5);
                        	String t[]=cd.split("#");
                            encheres e3 = SERVER1.checkenchere(Integer.parseInt(t[0]));
                            if(e3==null) {msgSend="enchere n'est pas existetnte";
                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());}else {
                        	msgSend=e3.listoffres();
                            sendMsg(sc, msgSend, pkRcv.getAddress(), pkRcv.getPort());

                        	}}
                       
                        
                        
                    
               

             
                
                }}
                     } catch (IOException e) {

                e.printStackTrace();

            }}}}

        

    

