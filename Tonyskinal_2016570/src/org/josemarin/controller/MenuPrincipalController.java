
package org.josemarin.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.josemarin.system.Principal;


public class MenuPrincipalController implements Initializable{
  private Principal escenarioPrincipal ;

  
  @Override 
    public void initialize(URL location, ResourceBundle rusources) {
  
    }
  
    public Principal getEscenarioPrincipal (){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
   
    public void  ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador(); 
    }

    
   public void ventanaEmpresa (){
       escenarioPrincipal.ventanaEmpresa();
  
   }

   
   public void ventanaTipoEmpleado(){
       escenarioPrincipal.ventanaTipoEmpleado();
   }
   public void ventanaPresupuestos(){
       escenarioPrincipal.ventanaPresupuestos();
   }
    public void ventanaProducto(){
        escenarioPrincipal.ventanaProducto();
    }
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    public void ventanaProductoHasPlato(){
        escenarioPrincipal.ventanaProductoHasPlato();
    }
     public void ventanaServicioHasEmpleado(){
        escenarioPrincipal.ventanaServicioHasEmpleado();
    }
   public void ventanaServicioHasPlato(){
        escenarioPrincipal.ventanaServicioHasPlato();
    }
}
