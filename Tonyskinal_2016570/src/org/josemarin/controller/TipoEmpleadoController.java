
package org.josemarin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.josemarin.bean.TipoEmpleado;
import org.josemarin.db.Conexion;
import org.josemarin.system.Principal;

public class TipoEmpleadoController implements Initializable {
    private Principal escenarioPrincipal;
    private Object registro;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoEmpleado> listaTipoEmpleado;
    @FXML private TextField txtCodigoTipoEmpleado;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnReportar;
    @FXML private Button btnEliminar;
    
     @Override
    public void initialize(URL lacation, ResourceBundle rusources) {
    cargarDatos();
    }      
    
    
    
    
    
     public void cargarDatos(){
        tblEmpleados.setItems(getTipoEmpleado());
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,Integer>("codigoTipoEmpleado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,String>("descripcion"));
    }
     
    public void seleccionarElemento(){
       txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
       txtDescripcion.setText(((TipoEmpleado)tblEmpleados.getSelectionModel().getSelectedItem()).getDescripcion());
   }
   
   
      public ObservableList <TipoEmpleado>getTipoEmpleado(){
          ArrayList<TipoEmpleado> lista= new ArrayList<TipoEmpleado>(); 
          try{
              PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarTipoEmpleado}");
              ResultSet resultado = procedimiento.executeQuery();
              while (resultado.next()){
                  lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                                             resultado.getString("descripcion")));
              }
          }catch(Exception e){
            e.printStackTrace();
          }
          
          return listaTipoEmpleado = FXCollections.observableArrayList(lista);
      }
  
     
          public void nuevo(){
         switch(tipoDeOperacion){
             case NINGUNO:
                 activarControles();
                 btnNuevo.setText("Guardar");
                 btnEliminar.setText("Cancelar");
                 btnEliminar.setDisable(false);
                 btnEditar.setDisable(true);
                 btnReportar.setDisable(true);
                 tipoDeOperacion = operaciones.GUARDAR;
                 break;
             case GUARDAR:
                 guardar();
                 desactivarControles();
                 limpiarControles();
                 btnNuevo.setText("Nuevo");
                 btnEliminar.setText("Eliminar");
                 btnEliminar.setDisable(true);
                 btnEditar.setDisable(true);
                 btnReportar.setDisable(true);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
         }
     }

     public void guardar(){
         TipoEmpleado registro = new TipoEmpleado();
         registro.setDescripcion(txtDescripcion.getText());
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
             procedimiento.setString(1,registro.getDescripcion());
             procedimiento.execute();
             listaTipoEmpleado.add(registro);
         }catch(Exception e ){
             e.printStackTrace();
         }
     }
   
   
   
   public void eliminar(){
       switch(tipoDeOperacion){
           case GUARDAR:
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnNuevo.setDisable(false);
               btnEliminar.setText("Eliminar");
               btnEliminar.setDisable(false);
               btnEditar.setDisable(false);
               btnReportar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblEmpleados.getSelectionModel().getSelectedItem()!=null){
                    int resultado = JOptionPane.showConfirmDialog(null,"Estas segura de Eliminar el registro?", "Eliminar Empresa",JOptionPane. YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(resultado == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                            procedimiento.setInt(1,((TipoEmpleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                            procedimiento.execute();
                            listaTipoEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
               }else{
                   JOptionPane.showMessageDialog(null,"Debe Seleccionar un Elemento");
               }
        }
   }
     
   
 public void editar(){
     switch (tipoDeOperacion){
         case NINGUNO:
             if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                 btnEditar.setText("Actualizar");
                 btnReportar.setText("Cancelar");
                 btnNuevo.setDisable(true);
                 btnEliminar.setDisable(true);
                 activarControles();
                 tipoDeOperacion = operaciones.ACTUALIZAR;
             }else{
                 JOptionPane.showMessageDialog(null, "Debe Selecionar Un Elmento ");
             }
         break;
         case ACTUALIZAR:
             actualizar();
             btnEditar.setText("editar");
             btnReportar.setText("reporte");
             btnEliminar.setDisable(false);
             btnNuevo.setDisable(false);
             tipoDeOperacion = operaciones.NINGUNO;
             cargarDatos();
             break; 
     }
     
 }
  public void reporte(){
        switch (tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnReportar.setText("Reporte");
                btnEditar.setText("Editar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;  
        }
    }
 public void actualizar(){
     try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoEmpleado(?, ?)}");
         TipoEmpleado registro =(TipoEmpleado)tblEmpleados.getSelectionModel().getSelectedItem();
         registro.setDescripcion(txtDescripcion.getText());
         procedimiento.setInt(1, registro.getCodigoTipoEmpleado());
         procedimiento.setString(2, registro.getDescripcion());
         procedimiento.execute();
     }catch(Exception e){
         e.printStackTrace();
         
     }
     
 }
 
  public void desactivarControles(){
         txtCodigoTipoEmpleado.setEditable(false);
            txtDescripcion.setEditable(false);
     }
     
     public void activarControles(){
         txtCodigoTipoEmpleado.setEditable(false);
            txtDescripcion.setEditable(true);
     }
     
     public void limpiarControles(){
         txtCodigoTipoEmpleado.setText("");
            txtDescripcion.setText("");
            
    }
 
       public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    
   }
    
    public void  setEscenarioPrincipal (Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
    public void menuPrincipal (){
        escenarioPrincipal.menuPrincipal();
        
  
     }
     public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    
}
