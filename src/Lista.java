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
    public void editarEmpleado(int tracking, double nuevoPeso, String nuevaCiudadEntrega, String nuevaCiudadRecepcion, String nuevaCedulaReceptor) {
        Paqueteria paquete = buscarEmpleadoPorTracking(tracking);
        if (paquete != null) {
            paquete.setPeso(nuevoPeso);
            paquete.setCiudadEntrega(nuevaCiudadEntrega);
            paquete.setCiudadRecepcion(nuevaCiudadRecepcion);
            paquete.setCedulaReceptor(nuevaCedulaReceptor);
        } else {
            throw new IllegalArgumentException("No se encontró ningún paquete con el tracking especificado: " + tracking);
        }
    }


    public Paqueteria buscarEmpleadoPorTracking(int tracking) {
        for (Paqueteria empleado : serviEntrega) {
            if (empleado.getTracking() == tracking) {
                return empleado;
            }
        }
        return null; // Retorna null si no se encuentra ningún empleado con el tracking especificado
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

    int calcularTotalPaquetesEstado(String estado) {
        return totalPaquetesEstado(estado, 0);
    }

    private int totalPaquetesEstado(String estado, int indice) {
        int total = 0;
        for (Paqueteria paquete : getServiEntrega()) { // Cambio aquí
            if (paquete.getEstado().equals(estado)) {
                total++;
            }
        }
        return total;
    }

    public List<Paqueteria> ordenarBurbuja() throws Exception {
        List<Paqueteria> listaPaquetes = new ArrayList<>(serviEntrega);
        if (listaPaquetes.isEmpty()) {
            throw new Exception("No hay empleados registrados.");
        }else{
            Paqueteria temp;
            for (int i = 0; i < listaPaquetes.size() - 1; i++) {
                for (int j = 0; j < listaPaquetes.size() - i - 1; j++) {
                    if (listaPaquetes.get(j).getTracking() > listaPaquetes.get(j + 1).getTracking()) {
                        temp = listaPaquetes.get(j);
                        listaPaquetes.set(j, listaPaquetes.get(j + 1));
                        listaPaquetes.set(j + 1, temp);
                    }
                }
            }
        }
        return  listaPaquetes;
    }

    public List<Paqueteria> ordenarInsercion() throws Exception {
        List<Paqueteria> listaPaquetes = new ArrayList<>(serviEntrega);
        if (listaPaquetes.isEmpty()) {
            throw new Exception("No hay paquetes registrados.");
        } else {
            for (int i = 1; i < listaPaquetes.size(); i++) {
                Paqueteria key = listaPaquetes.get(i);
                int j = i - 1;
                while (j >= 0 && listaPaquetes.get(j).getPeso() > key.getPeso()) {
                    listaPaquetes.set(j + 1, listaPaquetes.get(j));
                    j--;
                }
                listaPaquetes.set(j + 1, key);
            }
        }
        return listaPaquetes;

    }






}



