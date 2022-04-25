
/**
 * Esta clase permite leer datos, guardalos como abejas y detectar colisiones entre abejas 
 * @author (Mariajose Franco, Susana Alvarez)
 * @version (Mayo 12, 2019)
 */
import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.lang.Runtime;
import java.lang.Long;
public class Datos
{
    public static LinkedList <Abeja>abejas=new  LinkedList<Abeja>();
    public static Octante principal;
    /**
     * Este método permite ver cuanto se demora y cuanta memoria consume el programa detectando colisiones de abejas en diferentes archivos de diferente tamaño
     */
    public void main(){
        //consumoDeMemoriaYTiempos("15abejas.txt");
        //consumoDeMemoriaYTiempos("150abejas.txt");
        //consumoDeMemoriaYTiempos("1,500abejas.txt");
        //consumoDeMemoriaYTiempos("15,000abejas.txt");
        //consumoDeMemoriaYTiempos("150,000abejas.txt");
        //consumoDeMemoriaYTiempos("1,500,000abejas.txt");
        //consumoDeMemoriaYTiempos("10abejas.txt");
        //consumoDeMemoriaYTiempos("100abejas.txt");
        //consumoDeMemoriaYTiempos("1,000abejas.txt");
        //consumoDeMemoriaYTiempos("10,000abejas.txt");
        //consumoDeMemoriaYTiempos("100,000abejas.txt");
        //consumoDeMemoriaYTiempos("1,000,000abejas.txt");
    }

    /**
     * Este método permite ver cuanto se demora el programa detectando colisiones y cuanta memoria se consume
     * @param String Archivo
     */
    private void consumoDeMemoriaYTiempos(String Archivo){
        long minTiempo=Long.MAX_VALUE;
        long maxTiempo=Long.MIN_VALUE;
        long minMemoria=Long.MAX_VALUE;
        long maxMemoria=Long.MIN_VALUE;;
        long sumaTiempos=0;
        long sumaMemoria=0;
        for(int i=1; i<=10;i++){
            System.gc();
            leerYGuardarDatos(Archivo);
            long startLectura = System.currentTimeMillis();
            detectarColisiones();
            long endLectura = System.currentTimeMillis();
            long tiempoFinal=endLectura-startLectura;
            Runtime m=Runtime.getRuntime();
            long memoriaUsada=(m.totalMemory()-m.freeMemory());
            sumaTiempos=sumaTiempos+tiempoFinal;
            sumaMemoria=memoriaUsada+sumaMemoria;
            if(tiempoFinal<minTiempo){
                minTiempo=tiempoFinal;
            }
            if(tiempoFinal>maxTiempo){
                maxTiempo=tiempoFinal;
            }
            if(memoriaUsada<minMemoria){
                minMemoria=memoriaUsada;
            }
            if(memoriaUsada>maxMemoria){
                maxMemoria=memoriaUsada;
            }
        }
        long tiempoPromedio=sumaTiempos/10;
        long memoriaPromedio=sumaMemoria/10;
        System.out.println("tiempo promedio en detectar colisiones:"+ (tiempoPromedio+" milisegundos"));
        System.out.println("tiempo máximo en detectar colisiones:"+ (maxTiempo+" milisegundos"));
        System.out.println("tiempo mínimo en detectar colisiones:"+ (minTiempo+" milisegundos"));
        System.out.println("promedio de memoria gastada en detectar colisiones:"+memoriaPromedio+" bytes");
        System.out.println("máxima memoria gastada en detectar colisiones:"+maxMemoria+" bytes"); 
        System.out.println("mínima memoria gastada en detectar colisiones:"+minMemoria+" bytes");
    }


    /**
     * Este método lee un archivo con datos y los guarda en un linkedList de objetos tipo abeja.También se encuentra el máximo y mínimo en x,y, z del espacio donde estan las abejas 
     * @param String nombreArchivo
     */
    public void leerYGuardarDatos(String nombreArchivo){
        try{

            BufferedReader br=new BufferedReader(new FileReader(nombreArchivo));
            String linea=br.readLine();
            linea=br.readLine();
            String[]c=linea.split(",");
            double Xmax=Double.parseDouble(c[0]);double Xmin=Double.parseDouble(c[0]);
            double Ymax=Double.parseDouble(c[1]);double Ymin=Double.parseDouble(c[1]);
            double Zmax=Double.parseDouble(c[2]);double Zmin=Double.parseDouble(c[2]);  

            while(linea!=null){
                String[]cadena=linea.split(",");
                double x=Double.parseDouble(cadena[0]);
                double y=Double.parseDouble(cadena[1]);
                double z=Double.parseDouble(cadena[2]);
                Abeja a=new Abeja(x,y,z);
                abejas.add(a);

                if(x>Xmax){
                    Xmax=x;
                }
                if(y>Ymax){
                    Ymax=y;
                }
                if(z>Zmax){
                    Zmax=z; 
                }
                if(x<Xmin){
                    Xmin=x;
                }
                if(y<Ymin){
                    Ymin=y;
                }
                if(z<Zmin){
                    Zmin=z;
                }     
                linea=br.readLine();
            }
            principal=new Octante(Xmax,Xmin,Ymax,Ymin,Zmax,Zmin);
            principal.a=abejas;

        }
        catch(IOException ioe){
            System.out.println("el archivo no existe");
        }

    }

    /**
     * Este método permite detectar colisiones entre las abejas
     */
    public void detectarColisiones(){// llama al metodo 
        deteccionColisiones(principal);
    }

    /**
     * Este método permite separar un octante en otros 8 octantes hasta encontrar las abejas que van a colisionar
     * @param Octante o
     */
    private void deteccionColisiones(Octante o){

        boolean entro=false;

        if(Math.sqrt((o.Xmax-o.Xmin)*(o.Xmax-o.Xmin)+((o.Ymax-o.Ymin))*(o.Ymax-o.Ymin)+(o.Zmax-o.Zmin)*(o.Zmax-o.Zmin))<=100&&entro==false||o.a.size()<=1){
            if(o.a.size()>=2){
                for(Abeja a:o.a){
                    System.out.println("The bee with coordinates "+a.x+","+a.y+","+a.z+" is in risk of colision");

                }

            }
            else if(o.a.size()==1){
                Abeja a1= o.a.get(0);
                if(o.padre!=null){
                    for(Abeja a:o.padre.a){
                        if(Math.sqrt((a1.x-a.x)*(a1.x-a.x)+(a1.y-a.y)*(a1.y-a.y)+(a1.z-a.z)*(a1.z-a.z))<=100&&Math.sqrt((a1.x-a.x)*(a1.x-a.x)+(a1.y-a.y)*(a1.y-a.y)+(a1.z-a.z)*(a1.z-a.z))!=0){
                            System.out.println("The bee with coordinates "+a1.x+","+a1.y+","+a1.z+" is in risk of colision");//si esto pasa la abeja original esta en rieso de colision
                            break;
                        }

                    }

                }

            }
            else{
            }
            entro=true; 
        }

        else{
            double Xmitad=Math.abs(o.Xmax-o.Xmin)/2+o.Xmin;
            double Ymitad=Math.abs(o.Ymax-o.Ymin)/2+o.Ymin;
            double Zmitad=Math.abs(o.Zmax-o.Zmin)/2+o.Zmin;
            Octante s1=new Octante(o.Xmax,Xmitad,o.Ymax,Ymitad, o.Zmax,Zmitad);//+,+,+
            Octante s2=new Octante(Xmitad,o.Xmin,o.Ymax,Ymitad, o.Zmax,Zmitad);//-,+,+
            Octante s3=new Octante(Xmitad,o.Xmin,Ymitad,o.Ymin, o.Zmax,Zmitad);//-,-,+
            Octante s4=new Octante(o.Xmax,Xmitad,Ymitad,o.Ymin, o.Zmax,Zmitad);//+,-,+
            Octante s5=new Octante(o.Xmax,Xmitad,o.Ymax,Ymitad, Zmitad,o.Zmin);//+,+,-
            Octante s6=new Octante(Xmitad,o.Xmin,o.Ymax,Ymitad, Zmitad,o.Zmin);//-,+,-
            Octante s7=new Octante(Xmitad,o.Xmin,Ymitad,o.Ymin, Zmitad,o.Zmin);//-,-,-
            Octante s8=new Octante(o.Xmax,Xmitad,Ymitad,o.Ymin, Zmitad,o.Zmin);//+,-,-
            s1.padre=o;s2.padre=o;s3.padre=o;s4.padre=o;s5.padre=o;s6.padre=o;s7.padre=o;s8.padre=o;

            for(Abeja a:o.a){
                double x=a.x;
                double y=a.y;
                double z=a.z;
                if(x>Xmitad&& y>Ymitad &&z>Zmitad){
                    s1.a.add(a);
                }
                if(x<Xmitad&& y>Ymitad &&z>Zmitad){
                    s2.a.add(a);
                }
                if(x<Xmitad&& y<Ymitad &&z>Zmitad){
                    s3.a.add(a);
                }
                if(x>Xmitad&& y<Ymitad &&z>Zmitad){
                    s4.a.add(a);
                }
                if(x>Xmitad&& y>Ymitad &&z<Zmitad){
                    s5.a.add(a);
                }
                if(x<Xmitad&& y>Ymitad &&z<Zmitad){
                    s6.a.add(a);
                }
                if(x<Xmitad&& y<Ymitad &&z<Zmitad){
                    s7.a.add(a);
                }
                if(x>Xmitad&& y<Ymitad &&z<Zmitad){
                    s8.a.add(a);
                }
            }
            deteccionColisiones(s1);deteccionColisiones(s2);deteccionColisiones(s3);deteccionColisiones(s4); 
            deteccionColisiones(s5);deteccionColisiones(s6);deteccionColisiones(s7);deteccionColisiones(s8);

        }
    }
}

