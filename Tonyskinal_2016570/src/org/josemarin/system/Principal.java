
package org.josemarin.system;



import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josemarin.controller.DatosPersonalesController;
import org.josemarin.controller.EmpleadoController;
import org.josemarin.controller.EmpresaController;
import org.josemarin.controller.MenuPrincipalController;
import org.josemarin.controller.PlatoController;
import org.josemarin.controller.PresupuestoController;
import org.josemarin.controller.ServicioController;
import org.josemarin.controller.TipoEmpleadoController;
import org.josemarin.controller.TipoPlatoController;
import org.josemarin.controller.ProductoController;
import org.josemarin.controller.ProductoHasPlatoController;
import org.josemarin.controller.ServicioHasPlatoController;
import org.josemarin.controller.ServicioHasEmpleadoController;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/josemarin/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal)throws Exception {
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony`s Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/josemarin/image/ImagenEsenarioPrincipal.jpg"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/josemarin/view/MenuPrincipalview.fxml"));
        //Scene escena = new Scene(root);
        
      
        menuPrincipal();
        escenarioPrincipal.show();
        
    
    }
           public void  menuPrincipal(){
              try{
               MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalview.fxml",600,400); 
               menuPrincipal.setEscenarioPrincipal(this);
              }catch(Exception e){
                  e.printStackTrace();
              } 
              
           }
           
           
               public void ventanaProgramador(){
        try {
            DatosPersonalesController datosPersonales = (DatosPersonalesController)cambiarEscena("DatosView.fxml",600,400);
            datosPersonales.
                    setEscenarioPrincipal(this);
        }catch (Exception a){
            a.printStackTrace();
        }
    }
           
                     
           public void  ventanaEmpresa(){
                try{
               EmpresaController Empresa = (EmpresaController)cambiarEscena("EmpresaView.fxml",679,583); 
               Empresa.setEscenarioPrincipal(this);
              }catch(Exception d){
                  d.printStackTrace();
              }      
           }
           
           
             public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",600 ,414);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch (Exception e ){
            e.printStackTrace();
        }
        
    }
              public void ventanaPresupuestos(){
            try {
                PresupuestoController presupuesto = (PresupuestoController) cambiarEscena("PresupuestoView.fxml",600,391);
                presupuesto.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
              public void ventanaEmpleado(){
        try{
        EmpleadoController empleadoController = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",867,532);    
        empleadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
        e.printStackTrace(); 
        }
    }
              public void ventanaServicio(){
            try{
                ServicioController servicioController = (ServicioController)cambiarEscena("ServicioView.fxml",793,540);
                servicioController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
 public void ventanaTipoPlato(){
            try{
                TipoPlatoController tipoPlatoController = (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",600,413);
                tipoPlatoController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   public void ventanaPlato(){
            try{
                PlatoController platoController = (PlatoController)cambiarEscena("PlatoView.fxml",670,491);
                platoController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   public void ventanaProducto(){
            try{
                ProductoController productoController = (ProductoController)cambiarEscena("ProductoView.fxml",616,507);
                productoController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    public void ventanaProductoHasPlato(){
            try{
                ProductoHasPlatoController productoHasPlatoController = (ProductoHasPlatoController)cambiarEscena("ProductoPlatoView.fxml",616,507);
                productoHasPlatoController.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
     public void ventanaServicioHasEmpleado(){
            try{
                ServicioHasEmpleadoController servicioHasEmpleadoController = (ServicioHasEmpleadoController)cambiarEscena("ServicioEmpleadoView.fxml",864,527);
                servicioHasEmpleadoController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   public void ventanaServicioHasPlato(){
            try{
                ServicioHasPlatoController servicioHasPlatoController = (ServicioHasPlatoController)cambiarEscena("ServicioPlatoView.fxml",600,400);
                servicioHasPlatoController.setEscenarioPrincipal(this);  
            }catch(Exception e){
                e.printStackTrace();
            }
        }
     
    
    public static void main(String[] args) {
        launch(args);
    }
   public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
      Initializable resultado=null; 
       FXMLLoader cargadorFXML= new FXMLLoader();
       InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
       cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
       cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
       escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto); 
       escenarioPrincipal.setScene(escena);
       escenarioPrincipal.sizeToScene();
       resultado =(Initializable)cargadorFXML.getController();
        
       
       return resultado;
       
   }

}

