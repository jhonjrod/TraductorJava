/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clases;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Milton Rodriguez
 */
public class Traductor extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form Traductor
     */
    private String[] espanol= {"carro", "mujer", "hombre", "persona", "avion", "aeropuerto", "sillon", "edificio", "musica", "violin", "guitarra", "concierto", "maquina", "informatica", "tecnologia", "guerra", "vida", "paz", "vuelo", "partido", "enfrentamiento", "radicalidad", "versos", "beso", "cuerpo", "revolucion", "gobierno", "espionaje", "dictadura", "remolino", "desastre", "volcan", "paisaje", "paraiso", "cielo", "mente", "poder", "maquinaria", "mentira", "felicidad", "tristeza", "triunfo", "derrota", "perspicacia", "estrategia", "tecnicas", "oceano", "america", "rusia", "estados unidos", "colombia", "republica", "monarquia", "reyes"};
    private String[] portugues= {"carro", "mulheres", "homem", "pessoa", "aviao", "aeroporto", "cadeirao", "predio", "musica", "violino", "guitarra", "show", "maquina", "informatica", "tecnologia", "guerra", "vida", "paz", "voo", "jogo", "confronto", "radicalidade", "versos", "beijo", "corpo", "revolucao", "governo", "espionagem", "ditadura", "redemoinho", "desastre", "vulcao", "paisagem", "paraiso", "querido", "mente", "pode", "maquinas", "mentira", "felicidade", "tristeza", "triunfo", "derrota", "entendimento", "estrategia", "tecnicas", "oceano", "america", "russia", "estados unidos", "colombia", "republica", "monarquia", "reis"};
    private String[] ingles= {"car","women","man","person","plane","airport","armchair","building","music","fiddle","guitar","concert","machine","computing","technology","war","life","peace","flight","game","confrontation","radicality","verses","kiss","body","revolution","government","espionage","dictatorship","swirl","disaster","volcano","landscape","paradise","darling","mind","can","machinery","lie","happiness","sadness","triumph","defeat","insight","strategy","techniques","ocean","america","russia","usa","colombia","republic","monarchy","kings"};
    
    
    private int manejo=5;//para manejar si el usuario tiene seleccionado uno u otro textarea
    private Font fontOrigen = new Font("sansserif",Font.PLAIN,12);//creo una instancia de la clase font con estos valores, y lo que quieroe s que se setee cada vez que el usuario quiera, el atributo size para el tamaño de la letra en un textarea determinado
    private Font fontDestino = new Font("sansserif",Font.PLAIN,12);
    private float tamanoletraOrigen=12;
    private float tamanoletraDestino=12;
    
    private int manejonoalfabeticos=5;//para manejar los caracteres no alfabeticos y a la hora de imprimir la traduccion final en el textareadestino saber donde se deben concatenar los caracteres no alfabeticos recuperados del string original (si antes o despues del texto traducido, o antes y despues cogiendo los de antes y los de despues, logicamente, como en NoAlfabeticosAntesYDespues) se inicializa en 5, cuyo valor significa que no tiene caracteres no alfabeticos ni antes ni despues, por lo que la traduccion final se imprime normalmente. Luego de cada interaccion del usuario con el textareaorigen se reinicializa en 5, para que luego tome el valor correspondiente del texto actualizado.
    private String noalfabeticosantes="";//si al validar en textareaorigen se cumple "noalfabeticosantesydespues" aqui concatenara los no alfabeticos de antes, para imprimirlos antes de la traduccion final en textareadestino
    private String noalfabeticosdespues="";//si al validar en textareaorigen se cumple "noalfabeticosantesydespues" aqui concatenara los no alfabeticos de despues, para imprimirlos despues de la traduccion final en textareadestino
    
    private ImageIcon colombia = new ImageIcon(getClass().getResource("/imagenes/bandera colombia.png"));//imagenes de las banderas
    private ImageIcon brasil = new ImageIcon(getClass().getResource("/imagenes/bandera brasil.png"));//imagenes de las banderas
    private ImageIcon estadosunidos = new ImageIcon(getClass().getResource("/imagenes/bandera estados unidos.png"));//imagenes de las banderas
    
    
    
    public Traductor() {
        initComponents();
        setTitle("Traductor");
        setResizable(false);
        
        textareaorigen.setFont(fontOrigen);//seteo mi objeto font en ambos textarea
        textareadestino.setFont(fontDestino);
        botonmas.addActionListener(this);
        botonmenos.addActionListener(this);
        combodestino.addActionListener(this);
        comboorigen.addActionListener(this);
        
        
        combocolores.addActionListener(this);//agrego el actionlistener al combocolores para que me escuche los eventos que ahi ocurran y programar en base a ellos en el actionperformed
        
        textareaorigen.addMouseListener(new MouseAdapter() {//agrego un mouselistener a los dos textarea (origen y destino), y se le pasa como argumento una interfaz de metodos que son los que se ejecutaran cuando le de click al textarea
            
            public void mouseClicked(MouseEvent e) {//codigo que hara cuando de click sobre el textareaorigen.
                manejo=0;
                
                
                
            }
        });
        
        //ahora el .addMouseListener para el textareadestino
        
        textareadestino.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {//codigo que hara cuando de click sobre el textareaorigen.
                manejo=1;
                
                
                
            }
        });
        
        //ahora el .addDocumentListener para el textareaorigen (escucha para el textareadestino, con el y sus metodos insertUpdate,removeUpdate,changedUpdate puedo programar una accion cada vez que el usuario escriba, elimine o cambie el formato de lo que contiene el textarea, respectivamente. al implementar este tipo de escuchas se deben implementar TODOS sus metodos abstractos, si no da error, por ejemplo en este caso yo no necesito usar el changedUpdate pero aun asi debo implementarlo, solo lo dejo vacio si no necesito capturar nada de cuando cambie el formato.
        
        textareaorigen.getDocument().addDocumentListener(new DocumentListener() {  //al documento del textareaorigen (sus escritos) le añado el documentlistener, me escucha a traves de sus metodos cuando el usuario escribe, remueve o cambia el formato de lo contenido en el textarea
            @Override
            public void insertUpdate(DocumentEvent e) {
                
                // Acción que se realizará cuando se agregue texto al textarea
                String texto = textareaorigen.getText();
                //limpieza del texto
                String texto_temp="";
                for(int i=0;i<texto.length();i++){
                    if(String.valueOf(texto.charAt(i)).matches("[a-zA-Z]+")){
                        texto_temp+=String.valueOf(texto.charAt(i)).toLowerCase();
                    }
                }
                texto=texto_temp;
                //textareaorigen.setText(texto);//actualiza el textarea
                
                if(texto.equals("estadosunidos")){//pasa que arriba cuando evaluo si es caracter alfabetico, omite el espacio, entonces aqui valido si ingreso esa palabra, y si si, se la reingreso con el espacio, para que en los arreglos de idiomas de abajo la reconozca y escriba la traduccion. esto tendria que hacerlo para todas las palabras que tengan espacios en blanco legitimos, en este caso estados unidos es la unica.
                    texto="estados unidos";
                }
                
                //pregunto si el string cumple con patron de no alfabeticos antes o no alfabeticos despues, para en caso de que se cumpla alguno, recuperarle los no alfabeticos y ponerselos antes o despues de la traduccion final en el textareadestino
                String texto2 = textareaorigen.getText();//aqui se guardaran al final todos los no alfabeticos, esten antes o esten despues
                String texto_temp2="";
                
                int valor=0;
                
                if(stringTieneCaracteresNoAlfabeticosAntes(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=0;
                    //noalfabeticosantes+=texto2;
                    System.out.println("no alfabeticos antes");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosDespues(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=1;
                    //noalfabeticosdespues+=texto2;
                    System.out.println("no alfabeticos despues");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosAntesYDespues(texto2)){
                    //for(int i=0;i<texto2.length();i++){
                        //if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            //texto_temp2+=String.valueOf(texto2.charAt(i));
                        //}
                    //}
                    noalfabeticosantes="";
                    noalfabeticosdespues="";
                    manejonoalfabeticos=2;
                    System.out.println("antes y despues");
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            noalfabeticosantes+=String.valueOf(texto2.charAt(i));
                        }
                        if((i+1)<texto2.length()){
                            if(String.valueOf(texto2.charAt(i+1)).matches("[a-zA-Z]+")){
                                valor = i+1;
                                i=texto2.length();
                                System.out.println("break for");
                            }
                        }
                    }
                    System.out.println("antes:"+noalfabeticosantes);
                    
                    for(int i=valor+texto.length();i<texto2.length();i++){
                        noalfabeticosdespues+=String.valueOf(texto2.charAt(i));
                    }
                    
                    //antes=noalfabeticosantes;
                    //despues=noalfabeticosdespues;
                    
                    System.out.println("despues:"+noalfabeticosdespues);
                }
                
                
                
                //ya pregunte si el string de textareaorigen cumple con alguno de los dos patrones y le recupere los caracteres no alfabeticos correspondientes, ahora abajo, en el seteo de la traduccion final en textareadestino, debo hacer el seteo en base a una condicion, para concatenar los no alfabeticos ya sea antes o despues de la traduccion
                
                
                //arreglos idiomas
                if(comboorigen.getSelectedItem().equals("Espanol")){
                    for(int i=0;i<54;i++){
                      if(espanol[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                                //System.out.println("no alfabeticos antes");
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                                //System.out.println("no alfabeticos despues");
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                                //System.out.println("antes y despues");
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Portugues")){
                    for(int i=0;i<54;i++){
                      if(portugues[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Ingles")){
                    for(int i=0;i<54;i++){
                      if(ingles[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                manejonoalfabeticos=5;//reinicializo el valor en 5 para que en la siguiente inserta/elimina de textareaorigen actualice el valor dependiendo de si se activa noalfabeticosantes, noalfabeticosdespues o antes y despues y asi mismo trabaje en esa ejecucion
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Acción que se realizará cuando se borre texto del textarea
                String texto = textareaorigen.getText();
                //limpieza del texto
                String texto_temp="";
                for(int i=0;i<texto.length();i++){
                    if(String.valueOf(texto.charAt(i)).matches("[a-zA-Z]+")){
                        texto_temp+=String.valueOf(texto.charAt(i)).toLowerCase();
                    }
                }
                texto=texto_temp;
                //textareaorigen.setText(texto);//actualiza el textarea
                
                
                if(texto.equals("estadosunidos")){//pasa que arriba cuando evaluo si es caracter alfabetico, omite el espacio, entonces aqui valido si ingreso esa palabra, y si si, se la reingreso con el espacio, para que en los arreglos de idiomas de abajo la reconozca y escriba la traduccion. esto tendria que hacerlo para todas las palabras que tengan espacios en blanco legitimos, en este caso estados unidos es la unica.
                    texto="estados unidos";
                }
                
                
                
                //pregunto si el string cumple con patron de no alfabeticos antes o no alfabeticos despues, para en caso de que se cumpla alguno, recuperarle los no alfabeticos y ponerselos antes o despues de la traduccion final en el textareadestino
                String texto2 = textareaorigen.getText();//aqui se guardaran al final todos los no alfabeticos, esten antes o esten despues
                String texto_temp2="";
                
                int valor=0;
                
                if(stringTieneCaracteresNoAlfabeticosAntes(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=0;
                    //noalfabeticosantes+=texto2;
                    System.out.println("no alfabeticos antes");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosDespues(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=1;
                    //noalfabeticosdespues+=texto2;
                    System.out.println("no alfabeticos despues");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosAntesYDespues(texto2)){
                    //for(int i=0;i<texto2.length();i++){
                        //if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            //texto_temp2+=String.valueOf(texto2.charAt(i));
                        //}
                    //}
                    noalfabeticosantes="";
                    noalfabeticosdespues="";
                    manejonoalfabeticos=2;
                    System.out.println("antes y despues");
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            noalfabeticosantes+=String.valueOf(texto2.charAt(i));
                        }
                        if((i+1)<texto2.length()){
                            if(String.valueOf(texto2.charAt(i+1)).matches("[a-zA-Z]+")){
                                valor = i+1;
                                i=texto2.length();
                                System.out.println("break for");
                            }
                        }
                    }
                    System.out.println("antes:"+noalfabeticosantes);
                    
                    for(int i=valor+texto.length();i<texto2.length();i++){
                        noalfabeticosdespues+=String.valueOf(texto2.charAt(i));
                    }
                    
                    //antes=noalfabeticosantes;
                    //despues=noalfabeticosdespues;
                    
                    System.out.println("despues:"+noalfabeticosdespues);
                }
                
                
                
                //ya pregunte si el string de textareaorigen cumple con alguno de los dos patrones y le recupere los caracteres no alfabeticos correspondientes, ahora abajo, en el seteo de la traduccion final en textareadestino, debo hacer el seteo en base a una condicion, para concatenar los no alfabeticos ya sea antes o despues de la traduccion
                
                
                //arreglos idiomas
                if(comboorigen.getSelectedItem().equals("Espanol")){
                    for(int i=0;i<54;i++){
                      if(espanol[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                                //System.out.println("no alfabeticos antes");
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                                //System.out.println("no alfabeticos despues");
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                                //System.out.println("antes y despues");
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Portugues")){
                    for(int i=0;i<54;i++){
                      if(portugues[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Ingles")){
                    for(int i=0;i<54;i++){
                      if(ingles[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                manejonoalfabeticos=5;//reinicializo el valor en 5 para que en la siguiente inserta/elimina de textareaorigen actualice el valor dependiendo de si se activa noalfabeticosantes, noalfabeticosdespues o antes y despues y asi mismo trabaje en esa ejecucion
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                // Acción que se realizará cuando se borre texto del textarea
                //String text = textareaorigen.getText();
                //System.out.println(text);
            }

            
        });
        
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelopciones = new javax.swing.JPanel();
        labelorigen = new javax.swing.JLabel();
        labeldestino = new javax.swing.JLabel();
        comboorigen = new javax.swing.JComboBox<>();
        combodestino = new javax.swing.JComboBox<>();
        labelcambiartamanoletra = new javax.swing.JLabel();
        botonmas = new javax.swing.JButton();
        botonmenos = new javax.swing.JButton();
        labelcambiarcolorletra = new javax.swing.JLabel();
        combocolores = new javax.swing.JComboBox<>();
        labelpalabrasdisponibles = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        paneltraduccion = new javax.swing.JPanel();
        labelorigen2 = new javax.swing.JLabel();
        labeldestino2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textareaorigen = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textareadestino = new javax.swing.JTextArea();
        banderaorigen = new javax.swing.JLabel();
        banderadestino = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelopciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        labelorigen.setText("Idioma origen");

        labeldestino.setText("Idioma destino");

        comboorigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Espanol", "Ingles", "Portugues" }));
        comboorigen.setActionCommand("comboBoxChangedorigen");

        combodestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Espanol", "Ingles", "Portugues" }));
        combodestino.setActionCommand("comboBoxChangeddestino");

        labelcambiartamanoletra.setText("Cambiar tamano letra");

        botonmas.setText("+");

        botonmenos.setText("-");

        labelcambiarcolorletra.setText("Cambiar color letra");

        combocolores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Azul", "Verde", "Rosado", "Naranja", "Negro", "Amarillo" }));
        combocolores.setActionCommand("comboBoxChangedcolorletra");

        labelpalabrasdisponibles.setText("Palabras disponibles:");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Carro", "Mujer", "Hombre", "Persona", "Avion", "Aeropuerto", "Sillon", "Edificio", "Musica", "Violin", "Guitarra", "Concierto", "Maquina", "Informatica", "Tecnologia", "Guerra", "Vida", "Paz", "Vuelo", "Partido", "Enfrentamiento", "Radicalidad", "Versos", "Beso", "Cuerpo", "Revolucion", "Gobierno", "Espionaje", "Dictadura", "Remolino", "Desastre", "Volcan", "Paisaje", "Paraiso", "Cielo", "Mente", "Poder", "Maquinaria", "Mentira", "Felicidad", "Tristeza", "Triunfo", "Derrota", "Perspicacia", "Estrategia", "Tecnicas", "Oceano", "America", "Rusia", "Estados Unidos", "Colombia", "Republica", "Monarquia", "Reyes" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        javax.swing.GroupLayout panelopcionesLayout = new javax.swing.GroupLayout(panelopciones);
        panelopciones.setLayout(panelopcionesLayout);
        panelopcionesLayout.setHorizontalGroup(
            panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelopcionesLayout.createSequentialGroup()
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelopcionesLayout.createSequentialGroup()
                        .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelcambiartamanoletra)
                            .addComponent(labelcambiarcolorletra))
                        .addGap(18, 18, 18)
                        .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelopcionesLayout.createSequentialGroup()
                                .addComponent(botonmas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonmenos))
                            .addComponent(combocolores, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelopcionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelopcionesLayout.createSequentialGroup()
                                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelorigen)
                                    .addComponent(labeldestino))
                                .addGap(48, 48, 48)
                                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboorigen, 0, 148, Short.MAX_VALUE)
                                    .addComponent(combodestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelopcionesLayout.createSequentialGroup()
                                .addComponent(labelpalabrasdisponibles)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        panelopcionesLayout.setVerticalGroup(
            panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelopcionesLayout.createSequentialGroup()
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelorigen)
                    .addComponent(comboorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeldestino)
                    .addComponent(combodestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelopcionesLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(labelpalabrasdisponibles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelopcionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelcambiarcolorletra)
                    .addComponent(combocolores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelopcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelcambiartamanoletra)
                    .addComponent(botonmas)
                    .addComponent(botonmenos))
                .addGap(14, 14, 14))
        );

        paneltraduccion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Traduccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        labelorigen2.setForeground(new java.awt.Color(0, 0, 0));
        labelorigen2.setText("Idioma origen");

        labeldestino2.setForeground(new java.awt.Color(0, 0, 0));
        labeldestino2.setText("Idioma destino");

        textareaorigen.setColumns(20);
        textareaorigen.setLineWrap(true);
        textareaorigen.setRows(5);
        jScrollPane1.setViewportView(textareaorigen);

        textareadestino.setColumns(20);
        textareadestino.setRows(5);
        jScrollPane2.setViewportView(textareadestino);

        javax.swing.GroupLayout paneltraduccionLayout = new javax.swing.GroupLayout(paneltraduccion);
        paneltraduccion.setLayout(paneltraduccionLayout);
        paneltraduccionLayout.setHorizontalGroup(
            paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltraduccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneltraduccionLayout.createSequentialGroup()
                        .addComponent(banderaorigen)
                        .addGap(21, 21, 21)
                        .addComponent(labelorigen2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneltraduccionLayout.createSequentialGroup()
                        .addComponent(banderadestino)
                        .addGap(25, 25, 25)
                        .addComponent(labeldestino2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        paneltraduccionLayout.setVerticalGroup(
            paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneltraduccionLayout.createSequentialGroup()
                .addGroup(paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelorigen2)
                    .addComponent(labeldestino2)
                    .addComponent(banderaorigen)
                    .addComponent(banderadestino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneltraduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelopciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneltraduccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelopciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneltraduccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Traductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Traductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Traductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Traductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Traductor().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel banderadestino;
    private javax.swing.JLabel banderaorigen;
    private javax.swing.JButton botonmas;
    private javax.swing.JButton botonmenos;
    private javax.swing.JComboBox<String> combocolores;
    private javax.swing.JComboBox<String> combodestino;
    private javax.swing.JComboBox<String> comboorigen;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelcambiarcolorletra;
    private javax.swing.JLabel labelcambiartamanoletra;
    private javax.swing.JLabel labeldestino;
    private javax.swing.JLabel labeldestino2;
    private javax.swing.JLabel labelorigen;
    private javax.swing.JLabel labelorigen2;
    private javax.swing.JLabel labelpalabrasdisponibles;
    private javax.swing.JPanel panelopciones;
    private javax.swing.JPanel paneltraduccion;
    private javax.swing.JTextArea textareadestino;
    private javax.swing.JTextArea textareaorigen;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChangedcolorletra")){
            if(manejo==0){
                if(combocolores.getSelectedItem().equals("Azul")){
                    textareaorigen.setForeground(Color.BLUE);
                }
                if(combocolores.getSelectedItem().equals("Verde")){
                    textareaorigen.setForeground(Color.GREEN);
                }
                if(combocolores.getSelectedItem().equals("Rosado")){
                    textareaorigen.setForeground(Color.PINK);
                }
                if(combocolores.getSelectedItem().equals("Naranja")){
                    textareaorigen.setForeground(Color.ORANGE);
                }
                if(combocolores.getSelectedItem().equals("Negro")){
                    textareaorigen.setForeground(Color.BLACK);
                }
                if(combocolores.getSelectedItem().equals("Amarillo")){
                    textareaorigen.setForeground(Color.YELLOW);
                }
            }
            if(manejo==1){
                if(combocolores.getSelectedItem().equals("Azul")){
                    textareadestino.setForeground(Color.BLUE);
                }
                if(combocolores.getSelectedItem().equals("Verde")){
                    textareadestino.setForeground(Color.GREEN);
                }
                if(combocolores.getSelectedItem().equals("Rosado")){
                    textareadestino.setForeground(Color.PINK);
                }
                if(combocolores.getSelectedItem().equals("Naranja")){
                    textareadestino.setForeground(Color.ORANGE);
                }
                if(combocolores.getSelectedItem().equals("Negro")){
                    textareadestino.setForeground(Color.BLACK);
                }
                if(combocolores.getSelectedItem().equals("Amarillo")){
                    textareadestino.setForeground(Color.YELLOW);
                }
            }
        }
        
        if(e.getActionCommand().equals("+")){
            if(manejo==0){
                if(tamanoletraOrigen<=47){
                    tamanoletraOrigen++;//aumenta porque esta en el rango, si se sale ya no le aumento el tamaño
                }
                fontOrigen = fontOrigen.deriveFont(tamanoletraOrigen);//creo nueva instancia de font a partir de fontOrigen, pero con el tamaño de letra cambiado. se pone la f al final para senalar que estoy estableciendo el size de esa fuente. la f al final en java dice que ese valor es de tipo float, debe ponerse siempre para especificar que ese valor es float, ya que java no lo entiende incluso cuando tiene decimales
                textareaorigen.setFont(fontOrigen);
            }
            if(manejo==1){
                if(tamanoletraDestino<=47){
                    tamanoletraDestino++;//aumenta porque esta en el rango, si se sale ya no le aumento el tamaño
                }
                fontDestino = fontDestino.deriveFont(tamanoletraDestino);//creo nueva instancia de font a partir de fontOrigen, pero con el tamaño de letra cambiado. se pone la f al final para senalar que estoy estableciendo el size de esa fuente. la f al final en java dice que ese valor es de tipo float, debe ponerse siempre para especificar que ese valor es float, ya que java no lo entiende incluso cuando tiene decimales
                textareadestino.setFont(fontDestino);
            }
        }
        
        if(e.getActionCommand().equals("-")){
            if(manejo==0){
                if(tamanoletraOrigen>3){
                    tamanoletraOrigen--;//aumenta porque esta en el rango, si se sale ya no le aumento el tamaño
                }
                fontOrigen = fontOrigen.deriveFont(tamanoletraOrigen);//creo nueva instancia de font a partir de fontOrigen, pero con el tamaño de letra cambiado. se pone la f al final para senalar que estoy estableciendo el size de esa fuente. la f al final en java dice que ese valor es de tipo float, debe ponerse siempre para especificar que ese valor es float, ya que java no lo entiende incluso cuando tiene decimales
                textareaorigen.setFont(fontOrigen);
            }
            if(manejo==1){
                if(tamanoletraDestino>3){
                    tamanoletraDestino--;//aumenta porque esta en el rango, si se sale ya no le aumento el tamaño
                }
                fontDestino = fontDestino.deriveFont(tamanoletraDestino);//creo nueva instancia de font a partir de fontOrigen, pero con el tamaño de letra cambiado. se pone la f al final para senalar que estoy estableciendo el size de esa fuente. la f al final en java dice que ese valor es de tipo float, debe ponerse siempre para especificar que ese valor es float, ya que java no lo entiende incluso cuando tiene decimales
                textareadestino.setFont(fontDestino);
            }
            
        }
        if(e.getActionCommand().equals("comboBoxChangeddestino")){
            labeldestino2.setText("A "+(String)combodestino.getSelectedItem());
            if(combodestino.getSelectedItem().equals("Espanol")){
                banderadestino.setIcon(colombia);
                banderadestino.setToolTipText("Colombia tierra querida");
            }
            if(combodestino.getSelectedItem().equals("Ingles")){
                banderadestino.setIcon(estadosunidos);
                banderadestino.setToolTipText("EEUU");
            }
            if(combodestino.getSelectedItem().equals("Portugues")){
                banderadestino.setIcon(brasil);
                banderadestino.setToolTipText("Brasil");
            }
            String texto = textareaorigen.getText();
                //limpieza del texto
                String texto_temp="";
                for(int i=0;i<texto.length();i++){
                    if(String.valueOf(texto.charAt(i)).matches("[a-zA-Z]+")){
                        texto_temp+=String.valueOf(texto.charAt(i)).toLowerCase();
                    }
                }
                texto=texto_temp;
                //textareaorigen.setText(texto);//actualiza el textarea
                
                
                
                if(texto.equals("estadosunidos")){//pasa que arriba cuando evaluo si es caracter alfabetico, omite el espacio, entonces aqui valido si ingreso esa palabra, y si si, se la reingreso con el espacio, para que en los arreglos de idiomas de abajo la reconozca y escriba la traduccion. esto tendria que hacerlo para todas las palabras que tengan espacios en blanco legitimos, en este caso estados unidos es la unica.
                    texto="estados unidos";
                }
                
                
                
                //pregunto si el string cumple con patron de no alfabeticos antes o no alfabeticos despues, para en caso de que se cumpla alguno, recuperarle los no alfabeticos y ponerselos antes o despues de la traduccion final en el textareadestino
                String texto2 = textareaorigen.getText();//aqui se guardaran al final todos los no alfabeticos, esten antes o esten despues
                String texto_temp2="";
                
                int valor=0;
                
                if(stringTieneCaracteresNoAlfabeticosAntes(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=0;
                    //noalfabeticosantes+=texto2;
                    System.out.println("no alfabeticos antes");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosDespues(texto2)){
                    
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            texto_temp2+=String.valueOf(texto2.charAt(i));
                        }
                    }
                    texto2=texto_temp2;
                    manejonoalfabeticos=1;
                    //noalfabeticosdespues+=texto2;
                    System.out.println("no alfabeticos despues");
                    
                }
                
                if(stringTieneCaracteresNoAlfabeticosAntesYDespues(texto2)){
                    //for(int i=0;i<texto2.length();i++){
                        //if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            //texto_temp2+=String.valueOf(texto2.charAt(i));
                        //}
                    //}
                    noalfabeticosantes="";
                    noalfabeticosdespues="";
                    manejonoalfabeticos=2;
                    System.out.println("antes y despues");
                    
                    for(int i=0;i<texto2.length();i++){
                        if(!String.valueOf(texto2.charAt(i)).matches("[a-zA-Z]+")){
                            noalfabeticosantes+=String.valueOf(texto2.charAt(i));
                        }
                        if((i+1)<texto2.length()){
                            if(String.valueOf(texto2.charAt(i+1)).matches("[a-zA-Z]+")){
                                valor = i+1;
                                i=texto2.length();
                                System.out.println("break for");
                            }
                        }
                    }
                    System.out.println("antes:"+noalfabeticosantes);
                    
                    for(int i=valor+texto.length();i<texto2.length();i++){
                        noalfabeticosdespues+=String.valueOf(texto2.charAt(i));
                    }
                    
                    //antes=noalfabeticosantes;
                    //despues=noalfabeticosdespues;
                    
                    System.out.println("despues:"+noalfabeticosdespues);
                }
                
                
                
                //ya pregunte si el string de textareaorigen cumple con alguno de los dos patrones y le recupere los caracteres no alfabeticos correspondientes, ahora abajo, en el seteo de la traduccion final en textareadestino, debo hacer el seteo en base a una condicion, para concatenar los no alfabeticos ya sea antes o despues de la traduccion
                
                
                //arreglos idiomas
                if(comboorigen.getSelectedItem().equals("Espanol")){
                    for(int i=0;i<54;i++){
                      if(espanol[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                                //System.out.println("no alfabeticos antes");
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                                //System.out.println("no alfabeticos despues");
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                                //System.out.println("antes y despues");
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Portugues")){
                    for(int i=0;i<54;i++){
                      if(portugues[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                if(comboorigen.getSelectedItem().equals("Ingles")){
                    for(int i=0;i<54;i++){
                      if(ingles[i].equals(texto)){
                          if(combodestino.getSelectedItem().equals("Espanol")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+espanol[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(espanol[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+espanol[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(espanol[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Portugues")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+portugues[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(portugues[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+portugues[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(portugues[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                          if(combodestino.getSelectedItem().equals("Ingles")){
                              if(manejonoalfabeticos==0){
                                textareadestino.setText(texto2+ingles[i]);
                              }
                              if(manejonoalfabeticos==1){
                                textareadestino.setText(ingles[i]+texto2);
                              }
                              if(manejonoalfabeticos==2){
                                textareadestino.setText(noalfabeticosantes+ingles[i]+noalfabeticosdespues);
                              }
                              if(manejonoalfabeticos==5){
                                textareadestino.setText(ingles[i]);
                                //System.out.println("ni antes ni despues");
                              }
                          }
                      }  
                    }
                }
                manejonoalfabeticos=5;//reinicializo el valor en 5 para que en la siguiente inserta/elimina de textareaorigen actualice el valor dependiendo de si se activa noalfabeticosantes, noalfabeticosdespues o antes y despues y asi mismo trabaje en esa ejecucion
        }
        if(e.getActionCommand().equals("comboBoxChangedorigen")){
            labelorigen2.setText("De "+(String)comboorigen.getSelectedItem());
            if(comboorigen.getSelectedItem().equals("Espanol")){
                banderaorigen.setIcon(colombia);
                banderaorigen.setToolTipText("Colombia tierra querida");
            }
            if(comboorigen.getSelectedItem().equals("Ingles")){
                banderaorigen.setIcon(estadosunidos);
                banderaorigen.setToolTipText("EEUU");
            }
            if(comboorigen.getSelectedItem().equals("Portugues")){
                banderaorigen.setIcon(brasil);
                banderaorigen.setToolTipText("Brasil");
            }
        }
        
        
        
        
    }
    
    public boolean stringTieneCaracteresNoAlfabeticosAntes(String str){//metodo que recibe un string y me dice si el string tiene caracteres no alfabeticos antes y si alfabeticos despues. "123*hola"
        String patron = "[^a-zA-Z]+[a-zA-Z]+";//caracteres no alfabeticos, luego caracteres si alfabeticos.
        
        Pattern p = Pattern.compile(patron);//creo un objeto de tipo pattern a partir del patron establecido
        Matcher m = p.matcher(str);//creo un objeto de tipo matcher a partir del objeto p, pasando como argumento el string que vamos a evaluar. El objeto de tipo matcher lo necesitamos para buscar en el string si se cumple o no el patron establecido al principio.
        
        return m.matches();//el llamado al metodo .matches del objeto m que creamos en la linea anterior, devuelve TRUE si el string que pasamos como argumento cumple con el patron, y FALSE si no es asi.
    }
    
    public boolean stringTieneCaracteresNoAlfabeticosDespues(String str){//metodo que recibe un string y me dice si el string tiene caracteres si alfabeticos antes y no alfabeticos despues. "hola123*"
        String patron = "[a-zA-Z]+[^a-zA-Z]+";//caracteres si alfabeticos, luego caracteres no alfabeticos.
        
        Pattern p = Pattern.compile(patron);//creo un objeto de tipo pattern a partir del patron establecido
        Matcher m = p.matcher(str);//creo un objeto de tipo matcher a partir del objeto p, pasando como argumento el string que vamos a evaluar. El objeto de tipo matcher lo necesitamos para buscar en el string si se cumple o no el patron establecido al principio.
        
        return m.matches();//el llamado al metodo .matches del objeto m que creamos en la linea anterior, devuelve TRUE si el string que pasamos como argumento cumple con el patron, y FALSE si no es asi.
    }
    
    public boolean stringTieneCaracteresNoAlfabeticosAntesYDespues(String str){//metodo que recibe un string y me dice si el string tiene caracteres si alfabeticos antes y no alfabeticos despues. "hola123*"
        String patron = ".*[^a-zA-Z]+[a-zA-Z]+[^a-zA-Z]+";//cualquier cantidad de caracteres antes, luego caracteres si alfabeticos, luego caracteres no alfabeticos.
        
        Pattern p = Pattern.compile(patron);//creo un objeto de tipo pattern a partir del patron establecido
        Matcher m = p.matcher(str);//creo un objeto de tipo matcher a partir del objeto p, pasando como argumento el string que vamos a evaluar. El objeto de tipo matcher lo necesitamos para buscar en el string si se cumple o no el patron establecido al principio.
        
        return m.matches();//el llamado al metodo .matches del objeto m que creamos en la linea anterior, devuelve TRUE si el string que pasamos como argumento cumple con el patron, y FALSE si no es asi.
    }
    
    public String queTienenEnComun(String str1,String str2){
        ArrayList<Character> chars1 = new ArrayList<>();
        ArrayList<Character> chars2 = new ArrayList<>();
        
        for(char c : str1.toCharArray()) {
            chars1.add(c);
        }

        for(char c : str2.toCharArray()) {
            chars2.add(c);
        }
        
        chars1.retainAll(chars2);//luego del llamado a retainAll, chars1 contiene los elementos que tengan en comun chars1 y chars2
        
        // Convertir ArrayList chars1 en un arreglo de caracteres estatico "charsArray"
        char[] charsArray = new char[chars1.size()];
        for (int i = 0; i < chars1.size(); i++) {
            charsArray[i] = chars1.get(i);
        }
        
        //Convertir arreglo de caracteres estatico "charsArray" en String
        String texto = new String(charsArray);//el constructor de la clase String hace esto
        
        return texto;
        
    }
    
    public String queNoTienenEnComun(String str1,String str2){
        ArrayList<Character> chars1 = new ArrayList<>();
        ArrayList<Character> chars2 = new ArrayList<>();
        
        for(char c : str1.toCharArray()) {
            chars1.add(c);
        }

        for(char c : str2.toCharArray()) {
            chars2.add(c);
        }
        
        chars1.removeAll(chars2);//lo que este en chars1 que NO este en chars2
        
        // Convertir ArrayList chars1 en un arreglo de caracteres estatico "charsArray"
        char[] charsArray = new char[chars1.size()];
        for (int i = 0; i < chars1.size(); i++) {
            charsArray[i] = chars1.get(i);
        }
        
        //Convertir arreglo de caracteres estatico "charsArray" en String
        String texto = new String(charsArray);//el constructor de la clase String hace esto
        
        return texto;
        
    }
    
}
