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
    
    public static void main(String[] args) throws IOException {    
        Map <Integer, Double> mapa = new HashMap<>();
        
        DOMParser parser= new DOMParser();
                
        InputSource source = new InputSource("http://servicos.cptec.inpe.br/XML/cidade/7dias/634/previsao.xml");
         
        /////////////////////////////////////////////////////////////////////
             
        DOMImplementation di = new DOMImplementationImpl();
        int contaDiaPrevisao=0;
        int campoPrev=0;
        Document documentoFinal=null;
        Element elemento1=null;
        Element media=null;
        
                
            try{
                parser.parse(source);
                Document document=parser.getDocument();
                Element raizPrevisaoSemana = document.getDocumentElement();
                documentoFinal = di.createDocument(null, raizPrevisaoSemana.getLocalName(), null);
                Element raizFinal=documentoFinal.getDocumentElement();
                raizFinal.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
    "xsi:schemaLocation", "https://github.com/iclodoaldo/trabalhoXml2/blob/master/src/XSD_ex_2.xsd");
                NodeList filhosPrevisaoSemana = raizPrevisaoSemana.getChildNodes();
                
        for(int cont = 0;cont < filhosPrevisaoSemana.getLength();cont++){//cidade, nome, uf, atualizacao...previsao
               if (!filhosPrevisaoSemana.item(cont).getLocalName().equals("previsao")){
                    elemento1 = documentoFinal.createElement(filhosPrevisaoSemana.item(cont).getLocalName());
                    elemento1.setTextContent(filhosPrevisaoSemana.item(cont).getTextContent());
                    raizFinal.appendChild(elemento1);
                }
                
               if(filhosPrevisaoSemana.item(cont).getLocalName().equals("previsao")){//cidade, nome, uf, atualizacao...previsao
                
                   NodeList filhosDaPrevisao = filhosPrevisaoSemana.item(cont).getChildNodes();
                   Element elemento2=null;
                   
                   Double maxima=null;
                   Double minima=null;
                   
                  
                for(int cont2 = 0;cont2 < filhosDaPrevisao.getLength();cont2++){//dia,tempo,maxima,minima,iuv 
                         
                    
                    if(filhosDaPrevisao.item(cont2).getLocalName().equals("dia")){
                        
                        elemento1 = documentoFinal.createElement(filhosDaPrevisao.item(cont2).getLocalName());
                        //Text textoNomePrevisao = documentoFinal.createTextNode(filhosDaPrevisao.item(cont2).getTextContent());
                        elemento1.setAttribute("data", filhosDaPrevisao.item(cont2).getTextContent());
                        //elemento1.appendChild(textoNomePrevisao);
                        raizFinal.appendChild(elemento1);
                    }else{
                    
                        if(filhosDaPrevisao.item(cont2).getLocalName().equals("maxima")){
                            maxima=Double.parseDouble(filhosDaPrevisao.item(cont2).getTextContent());
                        }
                    
                        if(filhosDaPrevisao.item(cont2).getLocalName().equals("minima")){
                            minima=Double.parseDouble(filhosDaPrevisao.item(cont2).getTextContent());
                            media = documentoFinal.createElement("media");
                            media.setTextContent(Double.toString((maxima+minima)/2));
                            elemento1.appendChild(media);
                            raizFinal.appendChild(elemento1);
                           mapa.put(cont2, ((maxima+minima)/2));
                        }
                       
                    
                    } 
                                                         
                    DOMParser parser2= new DOMParser();
                    InputSource source2 = new InputSource("http://servicos.cptec.inpe.br/XML/cidade/634/dia/"+contaDiaPrevisao+"/ondas.xml");
                    
              //  try {
                    parser2.parse(source2);
                    
                    Document document2=parser2.getDocument();
                    
                    Element PrevisaoPorDia = document2.getDocumentElement();
                    NodeList filhosPrevisaoPorDia = PrevisaoPorDia.getChildNodes();
                    for(int outroCont=0; outroCont < filhosPrevisaoPorDia.getLength(); outroCont++){
                        
                        if(filhosDaPrevisao.item(cont2).getLocalName().equals("dia")){
                            
                            if(filhosPrevisaoPorDia.item(outroCont).getLocalName().equals("manha")){
                            NodeList listaManha = filhosPrevisaoPorDia.item(outroCont).getChildNodes();
                            Element manha = documentoFinal.createElement("manha");
                            for(int outroCont2=0; outroCont2 < listaManha.getLength(); outroCont2++){
                                
                               if(listaManha.item(outroCont2).getLocalName().equals("altura")){
                                    elemento2 = documentoFinal.createElement(listaManha.item(outroCont2).getLocalName());
                                    elemento2.setTextContent(listaManha.item(outroCont2).getTextContent());
                                    manha.appendChild(elemento2);
                                    elemento1.appendChild(manha);
                                    raizFinal.appendChild(elemento1);
                               }
                               
                               
                               
                            }
                            
                        }
                            ////////tarde
                            if(filhosPrevisaoPorDia.item(outroCont).getLocalName().equals("tarde")){
                            NodeList listaManha = filhosPrevisaoPorDia.item(outroCont).getChildNodes();
                            Element tarde = documentoFinal.createElement("tarde");
                            for(int outroCont2=0; outroCont2 < listaManha.getLength(); outroCont2++){
                                
                               if(listaManha.item(outroCont2).getLocalName().equals("altura")){
                                    elemento2 = documentoFinal.createElement(listaManha.item(outroCont2).getLocalName());
                                    elemento2.setTextContent(listaManha.item(outroCont2).getTextContent());
                                    tarde.appendChild(elemento2);
                                    elemento1.appendChild(tarde);
                                    raizFinal.appendChild(elemento1);
                                    
                                    
                               }
                            }
                            }
                            /////////////////////////////noite
                            
                            if(filhosPrevisaoPorDia.item(outroCont).getLocalName().equals("noite")){
                            NodeList listaManha = filhosPrevisaoPorDia.item(outroCont).getChildNodes();
                            Element noite = documentoFinal.createElement("noite");
                            for(int outroCont2=0; outroCont2 < listaManha.getLength(); outroCont2++){
                               if (listaManha.item(outroCont2).getLocalName().equals("agitacao")){
                                    elemento2 = documentoFinal.createElement(listaManha.item(outroCont2).getLocalName());
                                    elemento2.setTextContent(listaManha.item(outroCont2).getTextContent());
                                    noite.appendChild(elemento2);
                                    elemento1.appendChild(noite);
                                    raizFinal.appendChild(elemento1);
                                    
                                } 
                               if(listaManha.item(outroCont2).getLocalName().equals("altura")){
                                    elemento2 = documentoFinal.createElement(listaManha.item(outroCont2).getLocalName());
                                    elemento2.setTextContent(listaManha.item(outroCont2).getTextContent());
                                    noite.appendChild(elemento2);
                                    elemento1.appendChild(noite);
                                    raizFinal.appendChild(elemento1);
                               }
                            }
                        }
                        
                            
                            ///////////////////////////////
                    }
                    
                    }
               // }catch (SAXException ex) {
               //         }
                
                    
                }
          contaDiaPrevisao++;}
        }
        
            
            
            }catch (SAXException ex) {
                        }
            
        salvar(documentoFinal);
            imprime(1,documentoFinal);
          
        
        
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
