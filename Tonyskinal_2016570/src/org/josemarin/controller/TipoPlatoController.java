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
import org.josemarin.bean.TipoPlato;
import org.josemarin.db.Conexion;
import org.josemarin.system.Principal;

public class TipoPlatoController implements Initializable{
    private enum operaciones {NUEVO,GUARDAR,ELIMIAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TipoPlato>listaTipoPlato;
    
    @FXML private TextField txtCodigoTipoPlato;
    @FXML TextField txtDescripcionTipoPlato;
    @FXML TableView tblTipoPlatos;
    @FXML TableColumn colCodigoTipoPlato;
    @FXML TableColumn colDescripcionTipoPlato;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
        public void cargarDatos(){
            tblTipoPlatos.setItems(getTipoPlato());
            colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato,Integer>("codigoTipoPlato"));
            colDescripcionTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato,String>("descripcionTipo"));
    }
        public ObservableList<TipoPlato>getTipoPlato(){
            ArrayList<TipoPlato> lista = new ArrayList<TipoPlato> ();
                try{
                    PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlatos}");
                    ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new TipoPlato ( resultado.getInt("codigoTipoPlato"),
                                              resultado.getString("descripcionTipo")));
                }
                }catch(Exception e){
                    e.printStackTrace();
                }
                  return  listaTipoPlato = FXCollections.observableArrayList(lista);
        }
        
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                        activarControles();
                        limpiarControles();
                        btnNuevo.setText("Guardar");
                        btnEliminar.setDisable(false);
                        btnEliminar.setText("Cancelar");
                        btnEditar.setDisable(true);
                        btnReporte.setDisable(true);
                        tipoDeOperaciones = operaciones.GUARDAR;
                break;
            case GUARDAR:
                    guardar();
                    desactivarControles();
                    limpiarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setDisable(false);
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    tipoDeOperaciones = operaciones.NINGUNO;
                    cargarDatos();
               break;
        }   
    
        }
    public void guardar(){
        TipoPlato registro = new TipoPlato();
        registro.setDescripcionTipo(txtDescripcionTipoPlato.getText());
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
           procedimiento.setString(1,registro.getDescripcionTipo());
           procedimiento.execute();
           listaTipoPlato.add(registro);
           cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void seleccionarElemento(){
         if(tblTipoPlatos.getSelectionModel().getSelectedItem() == null){
         }else{
        txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
        txtDescripcionTipoPlato.setText(((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getDescripcionTipo());
         }
    }
    public TipoPlato buscarTipoPlato(int codigoTipoPlato){
        TipoPlato resultado = null;
            try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoPlato(?)}");
            ResultSet registro = procedimiento.executeQuery();
                while(registro.next()){
                    resultado = new TipoPlato(
                            registro.getInt("codigoTipoPlato"),
                                    registro.getString("descripcionTipo"));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return resultado;
    }
    public void eliminar() {
        switch (tipoDeOperaciones){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
              if(tblTipoPlatos.getSelectionModel().getSelectedItem() !=null){
                  int respuesta = JOptionPane.showConfirmDialog(null,"¿Esta seguro de eliminar el registro?","Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION) 
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoPlato(?)}");
                            procedimiento.setInt(1, ((TipoPlato)tblTipoPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                            procedimiento.execute();
                            listaTipoPlato.remove(tblTipoPlatos.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
              }else{
                  JOptionPane.showMessageDialog(null,"Debe seleccionar un dato");
              }    
        }
    }
    public void editar(){
        switch(tipoDeOperaciones){
             case NINGUNO:
                    if(tblTipoPlatos.getSelectionModel().getSelectedItem() !=null){
                btnEditar.setText("actualizar");
                btnReporte.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                tipoDeOperaciones = operaciones.ACTUALIZAR;
            }else{
                    JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
             }
            break;
             case ACTUALIZAR:
                    actualizar();
                    limpiarControles();
                    desactivarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperaciones = operaciones.NINGUNO;
                    cargarDatos();
                    break;
        }
    }
    public void actualizar(){
        try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoPlato(?,?)}");
             TipoPlato registro =(TipoPlato) tblTipoPlatos.getSelectionModel().getSelectedItem();
             registro.setDescripcionTipo(txtDescripcionTipoPlato.getText());
             procedimiento.setInt(1,registro.getCodigoTipoPlato());
             procedimiento.setString(2,registro.getDescripcionTipo());
             procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void generarReporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    

        
    public void desactivarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipoPlato.setEditable(false);
    }
    public void activarControles(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcionTipoPlato.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoTipoPlato.setText("");
        txtDescripcionTipoPlato.setText("");
    }
        
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    public void ventanaPlato(){
        escenarioPrincipal.ventanaPlato();
    }
    
}
