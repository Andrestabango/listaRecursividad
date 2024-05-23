import java.util.ArrayList;
import java.util.List;

public class Lista {

    private List<Paqueteria> serviEntrega ;

    public Lista( ){
         serviEntrega =new ArrayList<Paqueteria>();
    }

    public List<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    public void adicionarElementos(Paqueteria p) throws Exception{
        if(serviEntrega.isEmpty())
            serviEntrega.add(p);
         else{
            for(Paqueteria p1: serviEntrega)
                if(p1.getTracking()== p.getTracking())
                    throw new Exception("Ya existe el paquete");
            serviEntrega.add(p);
        }
    }

    public String listarPaquetes(){
        String mensaje ="";
        for(Paqueteria p: serviEntrega)
            mensaje += p +"\n";
        return mensaje;
    }


    public int sumarTotalPaquetes (){
     return totalPaquetes(0);

    }

    private int totalPaquetes (int indice){
        if(serviEntrega.size()==indice)
            return 0;
        else{
            return 1+totalPaquetes(indice+1);
        }

    }

    public Double sumarTotalPeso(){
        return totalPeso(0);
    }

    private double totalPeso(int indice) {

        if(serviEntrega.size()==indice)
            return 0;
        else{
            return  serviEntrega.get(indice).getPeso()+totalPeso(indice+ 1);
        }
        }

    public double sumarTotalPesoCiudad(String ciudad) {
        return totalPesoCiudad(0, ciudad);
    }

    private double totalPesoCiudad(int indice, String ciudad) {
        if (serviEntrega.size() == indice)
            return 0;
         else {
            double pesoActual = 0;
            if (serviEntrega.get(indice).getCiudadRecepcion().equals(ciudad))
                return serviEntrega.get(indice).getPeso() + totalPesoCiudad(indice + 1, ciudad);
            else
                return totalPesoCiudad(indice+1,ciudad);

        }
    }




}



