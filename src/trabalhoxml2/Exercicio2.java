/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoxml2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.swing.JOptionPane;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author clodoaldo
 */
public class Exercicio2 {
    private static String GuardarRaiz;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {    
        Map<String, Float> mapa = new HashMap<>();
        DOMParser parser= new DOMParser();
        InputSource source = new InputSource("http://servicos.cptec.inpe.br/XML/cidade/7dias/634/previsao.xml");
        try {
            System.out.println("Iniciando Parser...");
            parser.parse(source);
            System.out.println("Parser Concluido...");
            Document document=parser.getDocument();
                       
          
            Element raiz = document.getDocumentElement();
            //JOptionPane.showMessageDialog(null, raiz.getLocalName());
            NodeList lista = raiz.getChildNodes();
            String texto="";
            String previsao="Previsao do Tempo:\n";
            int contaDiaPrevisao=0;
            //obtendo uma lista com os filhos da raiz
            for (int i = 0; i < lista.getLength(); i++) {
                Element filho = (Element)lista.item(i);
                String tipo=null;
                Integer qtde=null;
                Float valor=null;
                
                
                //lendo informacoes de nome da cidade, uf e data de atualizacao
                if(filho.getLocalName().equals("nome")){
                    texto+="Cidade: "+filho.getTextContent()+"\n";
                }else if(filho.getLocalName().equals("uf")){
                    texto+="UF: "+filho.getTextContent()+"\n";
                }else if(filho.getLocalName().equals("atualizacao")){
                    texto+="Atualizacao: "+filho.getTextContent()+"\n\n";
                }
                //if(filho.getLocalName().equals("previsao")){JOptionPane.showMessageDialog(null, contaDiaPrevisao);}
                
                
                Double maxima=0.0;
                Double minima=0.0;
                NodeList listaFilho = filho.getChildNodes();
                
                for (int b =0; b<listaFilho.getLength();b++){
                   if(filho.getLocalName().equals("previsao")){
                       
                       if (listaFilho.item(b).getLocalName().equals("maxima")){
                           maxima=Double.parseDouble(listaFilho.item(b).getTextContent());
                       }else
                       if (listaFilho.item(b).getLocalName().equals("minima")){
                           minima=Double.parseDouble(listaFilho.item(b).getTextContent());
                       }
                       
                       Double media = (maxima+minima)/2;
                      //previsao+=listaFilho.item(b).getLocalName()+": "+listaFilho.item(b).getTextContent()+" - ";
                      if(listaFilho.item(b).getLocalName().equals("iuv")){
                         // previsao+="Media: "+(media)+"\n";
                      
                      
                      //ler o xml de previsao dia a dia                    
                      DOMParser parser2= new DOMParser();
                     
                      
                      //JOptionPane.showMessageDialog(null, "Dia - "+diaDaPrevisao);
                      
                      InputSource source2 = new InputSource("http://servicos.cptec.inpe.br/XML/cidade/634/dia/"+contaDiaPrevisao+"/ondas.xml");
                            String texto2="";
                      try {
                            System.out.println("Iniciando Parser...");
                            parser2.parse(source2);
                            System.out.println("Parser Concluido...");
                            Document document2=parser2.getDocument();
                       
          
                            Element raiz2 = document2.getDocumentElement();
                            //JOptionPane.showMessageDialog(null, raiz2.getLocalName());
                            previsao+=(contaDiaPrevisao+1)+"º Dia.\n";
                            NodeList lista2 = raiz2.getChildNodes();
                            String dia=null;
                            for(int c =0; c<lista2.getLength();c++){
                                Element filho2 = (Element)lista2.item(c);
                                if(filho2.getLocalName().equals("manha")){
                                    NodeList listaPeriodo = filho2.getChildNodes();
                                    for(int d=0; d<listaPeriodo.getLength();d++){
                                    Element periodos = (Element)listaPeriodo.item(d);
                                    
                                    if(periodos.getLocalName().equals("dia")){
                                        
                                    dia=periodos.getTextContent();
                                    JOptionPane.showMessageDialog(null,dia);}
                                    
                                    if(periodos.getLocalName().equals("altura")){
                                    previsao+="Dia: "+dia+"\nManha: Tep. Media:"+media+" - Alt. Ondas:"+periodos.getTextContent()+"\n";
                                    }
                                    
                                    }
                                }
                                if(filho2.getLocalName().equals("tarde")){
                                    NodeList listaPeriodo = filho2.getChildNodes();
                                    for(int d=0; d<listaPeriodo.getLength();d++){
                                    Element periodos = (Element)listaPeriodo.item(d);
                                    
                                    if(periodos.getLocalName().equals("dia")){dia=periodos.getTextContent();}
                                    
                                    if(periodos.getLocalName().equals("altura")){
                                    previsao+="Dia: "+dia+"\nTarde: Tep. Media:"+media+" - Alt. Ondas:"+periodos.getTextContent()+"\n";
                                    }
                                    
                                    }
                                }
                                if(filho2.getLocalName().equals("noite")){
                                    NodeList listaPeriodo = filho2.getChildNodes();
                                    for(int d=0; d<listaPeriodo.getLength();d++){
                                    Element periodos = (Element)listaPeriodo.item(d);
                                    
                                    if(periodos.getLocalName().equals("dia")){dia=periodos.getTextContent();}
                                    
                                    if(periodos.getLocalName().equals("agitacao")){
                                    previsao+="Dia: "+dia+"\nNOITE: Tep. Media:"
                                            +media+" - Alt. Ondas:"+periodos.getTextContent()
                                            +" - Agitacao:"+periodos.getTextContent()+"\n";
                                    }
                                    
                                    }
                                }
                                
                            //previsao+=" - "+filho2.getLocalName();
                            }
                            //JOptionPane.showMessageDialog(null, texto2);
                            
                            
                            
                      }catch(Exception e){}
         
                      
                      
                            //previsao+=texto2;
                            //texto2="";
                            previsao+="\n";contaDiaPrevisao++;
                      
                      }
                      
                     
                      // JOptionPane.showMessageDialog(null, previsao);
                   }
                   
                }                
                if (mapa.containsKey(tipo)){
                //mapa.put(tipo, valor*qtde+mapa.get(tipo));
                
                }else{
                //mapa.put(tipo, valor*qtde);
                }
                
             //contaDiaPrevisao=0;    
            }if (!texto.equals("")){
            JOptionPane.showMessageDialog(null, texto+previsao);}
                
              System.out.println(mapa);
                
            GuardarRaiz=raiz.getLocalName();
            System.out.println("Raiz filhos: "+raiz.getChildNodes().getLength());
            NodeList filhosDeRaiz = raiz.getChildNodes();
            for (int i = 0; i < filhosDeRaiz.getLength(); i++) {
             Node filho = filhosDeRaiz.item(i);
                System.out.println(filho.getLocalName());
                
                             
            }
            
        } catch (SAXException | IIOException e) {
            e.printStackTrace();
        }
        
        
        /////////////////////////////////////////////////////////////////////
        
        DOMImplementation di = new DOMImplementationImpl();
        Document document = di.createDocument(null, GuardarRaiz, null);
        Element alunos = document.createElement("alunos");
              
        Element raiz = document.getDocumentElement();
        raiz.appendChild(alunos);
        
        for (int j = 0; j < 3; j++) {
            
        Element aluno = document.createElement("aluno");
        alunos.appendChild(aluno);
        
        Text nome = document.createTextNode("Fulano");
        aluno.appendChild(nome);
       
        Random rdn = new Random();
        for (int i = 0; i < 3; i++) {
            Element nota = document.createElement("nota");
            nota.setTextContent(Integer.toString(rdn.nextInt(100)+1));
            aluno.appendChild(nota);
        }
        
        }
        
        imprime(0,raiz);
        salvar(document);
        
        
    }

    private static void imprime(int nivel, Node no) {
       
       switch(no.getNodeType()){
           case  Node.ELEMENT_NODE:
               identa(nivel);
               System.out.println(no.getNodeName());
               break;
           case Node.TEXT_NODE:
              
               String texto=no.getTextContent().trim();
               if (!texto.isEmpty()){
                   identa(nivel+1);
                   System.out.printf("'%s'\n",texto);}
               break;
               
           default:
               break;
       }
        
        for (int i = 0; i < no.getChildNodes().getLength(); i++) {
            imprime(nivel+1, no.getChildNodes().item(i));
            
        }

    }
    
   public static void identa(int nivel){
        for (int i = 0; i < nivel; i++) {
            System.out.print("  ");
        }
    }

    private static void salvar(Document document) throws IOException {
        DOMImplementationRegistry registry;
        try {
            registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS domimpl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = domimpl.createLSSerializer();
            LSOutput output = domimpl.createLSOutput();
            output.setCharacterStream(new FileWriter(new File ("src/SaidaExercicio2.xml")));
            output.setEncoding("utf-8");
            writer.setNewLine("\n");
            writer.write(document, output);
            
            
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | ClassCastException e) {
        e.printStackTrace();
        }        
    }
    
    
}
