package modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sql.Conexion;

public class Registro extends Conexion{
    //Constructor vacio (la clase no tiene atributos globales)
    public Registro() {
    }
    
    //Metodo para listar personas
    public DefaultTableModel ListadoPersonas(){
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Codigo Pers.","RUT","Nombre","Apellido","Celular","Email","Sueldo Bruto","Est. Civil", "Departamento"};
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM empleados");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
      Object[][] data = new String[registros][9];
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM empleados");
         ResultSet res = pstm.executeQuery();
         int i=0;
         while(res.next()){
                data[i][0] = res.getString( "codigo" );
                data[i][1] = res.getString( "rut" );
                data[i][2] = res.getString( "nombre" );
                data[i][3] = res.getString( "apellido" );
                data[i][4] = res.getString( "celular" );
                data[i][5] = res.getString( "email" );
                data[i][6] = res.getString( "sueldo_bruto" );
                data[i][7] = res.getString( "est_civil" );
                data[i][8] = res.getString( "nom_dpto" );
            i++;
         }
         res.close();
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }

    //Agregar nueva persona
    public boolean NuevaPersona(int codigo,String rut,String nombre,String apellido,String celular,String email, int sueldo, String estado, String departamento){
            //Se arma la consulta
            String q=" INSERT INTO examen_mod6.empleados(codigo,rut,nombre,apellido,celular,email,sueldo_bruto,est_civil,nom_dpto) "
                    + "VALUES ( '" + codigo + "','" + rut + "', '" + nombre + "','" + apellido + "',"
                    + " '" + celular +" ','" + email +"','" + sueldo +"','" + estado +"','" + departamento +"' ) ";
            //se ejecuta la consulta
            try {
                if (verificarCodigo(codigo) == false){
                    PreparedStatement pstm = this.getConexion().prepareStatement(q);
                    pstm.execute();
                    pstm.close();
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null,"El codigo de persona ingresado ya existe en BD. Favor ingrese nuevo codigo");
                    return false;
                }
                
            }catch(SQLException e){
                System.err.println( e.getMessage() );
                return false;
            }
    }
    
    //Modificar persona seleccionada de listado
    public boolean modificaPersona(int codigo,String rut,String nombre,String apellido,String celular,String email, int sueldo, String estado, String departamento){
        String q= "UPDATE examen_mod6.empleados SET nombre='"+nombre+"', apellido='"+apellido+"' , celular='"+celular+"' , email='"+email+"', sueldo_bruto='"+sueldo+"', est_civil='"+estado+"', nom_dpto='"+departamento+"' "
                + " WHERE codigo='"+codigo+"' and rut='"+rut+"' ";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
         }catch(SQLException e){
            
            System.err.println( e.getMessage() );
            return false;
        }
        
    }

    //Elimina persona seleccionada de listado
    public boolean eliminarPersona(int codigo, String rut){
        boolean res=false;
        
            String q = " DELETE FROM examen_mod6.empleados WHERE codigo=" + codigo + " and rut=" + rut + " " ;
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
                return false;
            }
           
    }

 
    //Busca persona
    public DefaultTableModel BuscaPersona(int codigo){
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"Codigo Pers.","RUT","Nombre","Apellido","Celular","Email","Sueldo Bruto","Est. Civil", "Departamento"};
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM empleados where codigo ="+ codigo + ";" );
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
      Object[][] data = new String[registros][9];
      try{
         if (verificarCodigo(codigo) == true){
             PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM empleados where codigo ="+ codigo + ";" );
             ResultSet res = pstm.executeQuery();
             int i=0;
             while(res.next()){
                data[i][0] = res.getString( "codigo" );
                data[i][1] = res.getString( "rut" );
                data[i][2] = res.getString( "nombre" );
                data[i][3] = res.getString( "apellido" );
                data[i][4] = res.getString( "celular" );
                data[i][5] = res.getString( "email" );
                data[i][6] = res.getString( "sueldo_bruto" );
                data[i][7] = res.getString( "est_civil" );
                data[i][8] = res.getString( "nom_dpto" );
                i++;
             }
             res.close();
             tablemodel.setDataVector(data, columNames );
         }
         else{
            JOptionPane.showMessageDialog(null,"El codigo a buscar no existe en BD");
            }
      }catch(SQLException e){
            System.err.println( e.getMessage() );
      }
      return tablemodel;
    }
    
    public  boolean validarRut(String rut) {
        boolean validacion = false;
        try {
        rut =  rut.toUpperCase();
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
        char dv = rut.charAt(rut.length() - 1);
 
        int m = 0, s = 1;
        for (; rutAux != 0; rutAux /= 10) {
        s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
        }
        if (dv == (char) (s != 0 ? s + 47 : 75)) {
        validacion = true;
        }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
    
    public boolean verificarCodigo(int codigo){
        boolean res=false;
        int registros;
        String q = " Select codigo FROM examen_mod6.empleados WHERE codigo=" + codigo + " " ;
        
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            ResultSet rex = pstm.executeQuery();
            rex.next();
            registros = rex.getInt("codigo");
            rex.close();
            if (registros == 0){
                res = false;
            }
            else{
                res= true;
            }
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
                
        return res;
    }
}
