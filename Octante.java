
/**
 * Esta clase permite crear objetos tipo Octante
 * @author (Mariajose Franco y Susana Alvarez)
 * @version (Mayo 12,2019)
 */
import java.util.*;
public class Octante
{
    LinkedList<Abeja> a= new  LinkedList<Abeja>();
    double Xmax;
    double Xmin;
    double Ymax;
    double Ymin;
    double Zmax;
    double Zmin;
    Octante padre;
    /**
     * Este método construye un objeto tipo octante 
     * @param double maxX
     * @param double minX
     * @param double maxY
     * @param double minY
     * @param double maxZ
     * @param double minZ
     * @return un nuevo objeto tipo octante
     */
    public Octante(double maxX, double minX,double maxY, double minY,double maxZ, double minZ ){
        Xmax=maxX;
        Xmin=minX;
        Ymax=maxY;
        Ymin=minY;
        Zmax=maxZ;
        Zmin=minZ;
    }

    
}
