package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Registro;
//Interfaz grafica - Vista
import vista.Inicio;
import vista.IngresarPersona;
import vista.ListaModificaPersona;

public class Controlador implements ActionListener,MouseListener {

    public Controlador() {
    }
    
    //vista
    private Inicio vistaPrincipal ;
    private IngresarPersona ingProd = new IngresarPersona();
    private ListaModificaPersona listProd = new ListaModificaPersona();

    //modelo
    private Registro modelo = new Registro();

    //acciones que se ejecuta por los controles de cada VISTA
    public enum Accion{
        mnIIngresoP, // Menú Itém que ingresa personas
        mnILista, //Menú Itém que lista personas ingresadas
        btnBuscar,//-> Ejecuta consulta de búsqueda
        btnElimina,//-> Ejecuta consulta de eliminación
        btnGuardar,//->se ejecuta cuando se hace clic en boton guardar en vista Ingresar Persona
        btnmodificar, //-> Ejecuta cuando se hace clic en boton modificar en vista Listar Personas
        btningvol, //boton volver a pantalla principal en Ingreso de persona
        btnlistvol, //boton volver a pantalla principal en Lista personas
        btnLimpiar //Boton para limpiar formulario de personas (requerimiento consulta 1)
    }
    
    /** Constructor de clase */
    public Controlador( JFrame padre ){
        this.vistaPrincipal = (Inicio) padre;
    }
    
    /** Inicia todos las acciones y listener de la vista */
    public void iniciar(){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaPrincipal);
            SwingUtilities.updateComponentTreeUI( this.ingProd );
            SwingUtilities.updateComponentTreeUI( this.listProd );
            //SwingUtilities.updateComponentTreeUI( this.modProd );
            this.vistaPrincipal.setLocationRelativeTo(null);
            this.vistaPrincipal.setTitle("Mantenedor de Personal");
            this.vistaPrincipal.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}

        //Listener
        this.vistaPrincipal.mnIIngresoP.setActionCommand( "mnIIngresoP" );
        this.vistaPrincipal.mnIIngresoP.addActionListener(this);
        //boton
        this.vistaPrincipal.mnILista.setActionCommand( "mnILista" );
        this.vistaPrincipal.mnILista.addActionListener(this);
        //boton
        this.vistaPrincipal.btnBuscar.setActionCommand("btnBuscar");
        this.vistaPrincipal.btnBuscar.addActionListener(this);
        //boton
        this.listProd.btnElimina.setActionCommand("btnElimina");
        this.listProd.btnElimina.addActionListener(this);

        //Ingresa persona
        this.ingProd.btnGuardar.setActionCommand("btnGuardar");
        this.ingProd.btnGuardar.addActionListener(this);
        
        this.ingProd.btningvol.setActionCommand("btningvol");
        this.ingProd.btningvol.addActionListener(this);
        
        //Lista producto
        this.listProd.btnlistvol.setActionCommand("btnlistvol");
        this.listProd.btnlistvol.addActionListener(this);
        this.listProd.tbProducto.addMouseListener(this);
        this.listProd.btnmodificar.setActionCommand("btnmodificar");
        this.listProd.btnmodificar.addActionListener(this);
        
        //Limpia formulario de ingreso de persona
        this.ingProd.btnLimpiar.setActionCommand("btnLimpiar");
        this.ingProd.btnLimpiar.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch ( Accion.valueOf( e.getActionCommand() ) )
        {
            case mnIIngresoP:
                this.vistaPrincipal.lblBusca.setVisible(false);
                this.vistaPrincipal.jpnBusca.setVisible(false);
                this.vistaPrincipal.txtCodBusca.setVisible(false);
                this.vistaPrincipal.btnBuscar.setVisible(false);
                this.vistaPrincipal.lblcodigo.setVisible(false);
                this.vistaPrincipal.cboDepa.setVisible(false);
                this.vistaPrincipal.lblDepartamento.setVisible(false);
                this.ingProd.setLocationRelativeTo(null);
                this.ingProd.setTitle("Ingresar Personas");
                this.ingProd.setVisible(true);
                this.vistaPrincipal.setVisible(false);
                break;
            case mnILista:
                this.vistaPrincipal.lblBusca.setVisible(false);
                this.vistaPrincipal.jpnBusca.setVisible(false);
                this.vistaPrincipal.txtCodBusca.setVisible(false);
                this.vistaPrincipal.btnBuscar.setVisible(false);
                this.vistaPrincipal.lblcodigo.setVisible(false);
                this.vistaPrincipal.cboDepa.setVisible(false);
                this.vistaPrincipal.lblDepartamento.setVisible(false);
                this.listProd.setLocationRelativeTo(null);
                this.listProd.setTitle("Lista de Personas en el Sistema");
                this.listProd.setVisible(true);
                this.vistaPrincipal.setVisible(false);
                this.listProd.tbProducto.setModel(this.modelo.ListadoPersonas());
                break;

            case btnGuardar:
                //Se obtienen datos de formulario y se almacenan en variables
                String codigo = this.ingProd.txtCodigo.getText();
                String rut = this.ingProd.txtRut.getText();
                String nombre = this.ingProd.txtNombre.getText();
                String apellido = this.ingProd.txtApellido.getText();
                String celular = this.ingProd.txtCelular.getText();
                String email = this.ingProd.txtEmail.getText();
                String sueldo = this.ingProd.txtSueldo.getText();
                String estado = this.ingProd.cboEstado.getSelectedItem().toString();
                String departamento = this.ingProd.cboDepa.getSelectedItem().toString();
                
                //Se valida el tipo de datos acorde a las reglas de negocio
                //Se valida codigo
                if (codigo.trim().equals("")||Integer.parseInt(codigo) <= 0||!codigo.matches("^[0-9]*$")||Integer.parseInt(codigo) > 100||codigo.trim().length() > 3){
                    JOptionPane.showMessageDialog(null,"Debe ingresar codigo definido con digitos, comprendido entre 1 y 100");
                    this.ingProd.txtCodigo.setText("");
                 }
                
                //Se valida RUT
                else if (rut.trim().equals("")||!rut.matches("^0*(\\d{1,3}(\\.?\\d{3})*)\\-?([\\dkK])$")||rut.trim().length() > 12||rut.trim().length() < 7){
                    JOptionPane.showMessageDialog(null,"Debe ingresar rut valido comprendido con digitos y K/k");
                    this.ingProd.txtRut.setText("");
                }
                else  if (this.modelo.validarRut(rut) == false){
                        JOptionPane.showMessageDialog(null,"Debe ingresar rut valido acorde a MOD11");
                        this.ingProd.txtRut.setText("");
                }
                
                // Se valida nombre
                else if (nombre.trim().equals("")||!nombre.matches("^[A-Za-z ´¨']*$")||nombre.trim().length() > 20){
                    JOptionPane.showMessageDialog(null,"Debe ingresar nombre de persona distinto de blanco y definido con caracteres. El largo permitido es de 20 caracteres"
                            + " Se permite el uso de acentos, dieresis y apostrofe");
                    this.ingProd.txtNombre.setText(""); 
                }
                
                //Se valida apellido
                 else if (apellido.trim().equals("")||!apellido.matches("^[A-Za-z ´¨']*$")||apellido.trim().length() > 20){
                    JOptionPane.showMessageDialog(null,"Debe ingresar apellido de persona distinto de blanco y definido con caracteres.. El largo permitido es de 20 caracteres"
                            + " Se permite el uso de acentos, dieresis y apostrofe");
                    this.ingProd.txtApellido.setText(""); 
                }
                 
                //Se valida N° Celular
                else if (celular.trim().equals("")||!celular.matches("^[0-9]*$")||celular.trim().length() > 9||celular.length() < 9){
                    JOptionPane.showMessageDialog(null,"Debe ingresar N° Celular definido con 9 digitos");
                    this.ingProd.txtCelular.setText(""); 
                }
                
                //Se valida Sueldo
                else if (sueldo.trim().equals("")||!sueldo.matches("^[0-9]*$")||Integer.parseInt(sueldo) < 120000||sueldo.trim().length() > 7){
                    JOptionPane.showMessageDialog(null,"Debe ingresar sueldo definido en digitos, sin puntos y mayor a $120.000. Se permite sueldo de hasta 7 digitos");
                    this.ingProd.txtSueldo.setText(""); 
                }
                
                //Se valida email
                else if (email.trim().equals("")||!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")||email.trim().length() > 30){
                    JOptionPane.showMessageDialog(null,"Debe ingresar email sin espacios en blanco, con caracteres alfanumericos y con un largo menor a 30 caracteres");
                    this.ingProd.txtEmail.setText("");
                }
                
                //Si todas las validaciones se cumplen, se registra persona
                else {
                    rut = rut.toUpperCase();
                    rut = rut.replace(".", "");
                    rut = rut.replace("-", "");
                    if (this.modelo.NuevaPersona(Integer.parseInt(codigo),rut,nombre,apellido,celular,email,Integer.parseInt(sueldo),estado,departamento) == true){
                    //Se indica mensaje a usuario de persona registrada en forma correcta
                        JOptionPane.showMessageDialog(null,"Persona agregada correctamente al sistema");
                    //Limpiamos cajas de texto
                        this.ingProd.txtCodigo.setText("");
                        this.ingProd.txtRut.setText("");
                        this.ingProd.txtNombre.setText(""); 
                        this.ingProd.txtApellido.setText("");
                        this.ingProd.txtCelular.setText("");
                        this.ingProd.txtEmail.setText("");
                        this.ingProd.txtSueldo.setText("");}
                    
                    else {
                        JOptionPane.showMessageDialog(null,"No se pudo agregar persona (Error en BD: Ver log en consola");
                    }}
               break;
               
               
            case btnmodificar:
                //Se obtienen datos de formulario y se almacenan en variables
                String codigom = this.listProd.txtcod.getText();
                String nombrem = this.listProd.txtnombre.getText();
                String rutm = this.listProd.txtRUT.getText();
                String apellidom = this.listProd.txtapellido.getText();
                String celularm = this.listProd.txtcelular.getText();
                String emailm = this.listProd.txtemail.getText();
                String sueldom = this.listProd.txtsueldo.getText();
                String estadom = this.listProd.cboestado.getSelectedItem().toString();
                String departamentom = this.listProd.cboDepa.getSelectedItem().toString();
                
                //Se valida el tipo de datos acorde a las reglas de negocio
               
                // Se valida nombre
                if (nombrem.trim().equals("")||!nombrem.matches("^[A-Za-z ´¨']*$")||nombrem.trim().length() > 20){
                    JOptionPane.showMessageDialog(null,"Debe ingresar nombre de persona distinto de blanco y definido con caracteres. El largo permitido es de 20 caracteres"
                            + " Se permite el uso de acentos, dieresis y apostrofe");
                    this.listProd.txtnombre.setText(""); 
                }
                
                //Se valida apellido
                 else if (apellidom.trim().equals("")||!apellidom.matches("^[A-Za-z ´¨']*$")||apellidom.trim().length() > 20){
                    JOptionPane.showMessageDialog(null,"Debe ingresar apellido de persona distinto de blanco y definido con caracteres.. El largo permitido es de 20 caracteres"
                            + " Se permite el uso de acentos, dieresis y apostrofe");
                    this.listProd.txtapellido.setText(""); 
                }
                 
                //Se valida N° Celular
                else if (celularm.trim().equals("")||!celularm.matches("^[0-9]*$")||celularm.trim().length() > 9||celularm.length() < 9){
                    JOptionPane.showMessageDialog(null,"Debe ingresar N° Celular definido con 9 digitos");
                    this.listProd.txtcelular.setText(""); 
                }
                
                //Se valida Sueldo
                else if (sueldom.trim().equals("")||!sueldom.matches("^[0-9]*$")||Integer.parseInt(sueldom) < 120000||sueldom.trim().length() > 7){
                    JOptionPane.showMessageDialog(null,"Debe ingresar sueldo definido en digitos, sin puntos y mayor a $120.000. Se permite sueldo de hasta 7 digitos");
                    this.listProd.txtsueldo.setText(""); 
                }
                
                //Se valida email
                else if (emailm.trim().equals("")||!emailm.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")||emailm.trim().length() > 30){
                    JOptionPane.showMessageDialog(null,"Debe ingresar email sin espacios en blanco, con caracteres alfanumericos y con un largo menor a 30 caracteres");
                    this.listProd.txtemail.setText("");
                }
                
                //Si todas las validaciones se cumplen, se modifica persona
                else {
                    //Se modifica mensaje a usuario indicandole que el sueldo ingresado ha sido aumentado un 20%
                   if (this.modelo.modificaPersona(Integer.parseInt(codigom),rutm,nombrem,apellidom,celularm,emailm,Integer.parseInt(sueldom),estadom,departamentom) == true){
                        JOptionPane.showMessageDialog(null,"Se han modificado los datos de la persona en forma correcta. Recuerde que el sueldo ha sido aumentado un 20%");
                            //actualiza JTable
                        this.listProd.tbProducto.setModel(this.modelo.ListadoPersonas()); 
                            
                            //Limpiamos textField
                            this.listProd.txtcod.setText("");
                            this.listProd.txtRUT.setText("");
                            this.listProd.txtnombre.setText(""); 
                            this.listProd.txtapellido.setText("");
                            this.listProd.txtcelular.setText("");
                            this.listProd.txtemail.setText("");
                            this.listProd.txtsueldo.setText("");
                            this.listProd.cboestado.setSelectedItem("");
                            this.listProd.cboDepa.setSelectedItem("");
                   }
                   else{
                       JOptionPane.showMessageDialog(null,"No fue posible modificar la persona seleccionada. Revise log en consola");
                   }
                    
                }
                break;
                
                
            case btningvol:
                this.vistaPrincipal.lblBusca.setVisible(false);
                this.vistaPrincipal.jpnBusca.setVisible(false);
                this.vistaPrincipal.txtCodBusca.setVisible(false);
                this.vistaPrincipal.btnBuscar.setVisible(false);
                this.vistaPrincipal.lblcodigo.setVisible(false);
                this.vistaPrincipal.cboDepa.setVisible(false);
                this.vistaPrincipal.lblDepartamento.setVisible(false);
                this.vistaPrincipal.setLocationRelativeTo(null);
                this.vistaPrincipal.setVisible(true);
                this.ingProd.setVisible(false);
                break;
            case btnlistvol:
                this.vistaPrincipal.lblBusca.setVisible(false);
                this.vistaPrincipal.jpnBusca.setVisible(false);
                this.vistaPrincipal.txtCodBusca.setVisible(false);
                this.vistaPrincipal.btnBuscar.setVisible(false);
                this.vistaPrincipal.lblcodigo.setVisible(false);
                this.vistaPrincipal.cboDepa.setVisible(false);
                this.vistaPrincipal.lblDepartamento.setVisible(false);
                this.vistaPrincipal.setLocationRelativeTo(null);
                this.vistaPrincipal.setVisible(true);
                this.listProd.setVisible(false);
                break;
            case btnBuscar:
                String cod = this.vistaPrincipal.txtCodBusca.getText();
                String depa = this.vistaPrincipal.cboDepa.getSelectedItem().toString();
                if (cod.trim().equals("")||Integer.parseInt(cod) <= 0||!cod.matches("^[0-9]*$")||Integer.parseInt(cod) > 100||cod.trim().length() > 3){
                    JOptionPane.showMessageDialog(null,"Ingrese un codigo de persona valido a buscar");
                }
                else {
                   this.vistaPrincipal.txtCodBusca.setText("");
                   this.listProd.setLocationRelativeTo(null);
                   this.listProd.setTitle("Lista de Persona codigo: "+ cod);
                   this.listProd.setVisible(true);
                   this.listProd.tbProducto.setModel(this.modelo.BuscaPersona(Integer.parseInt(cod), depa));
                   break; 
                }
            case btnElimina:
                //Se condiciona el sueldo de la persona como un parametro para el metodo. Se puede modificar el sueldo y presionar
                //btn eliminar, sin pasar por el metodo modificador
                String codigoe = this.listProd.txtcod.getText();
                String rute = this.listProd.txtRUT.getText();
                String sueld = this.listProd.txtsueldo.getText();
                
                if (Integer.parseInt(sueld) > 120000||Integer.parseInt(sueld) < 120000){
                    JOptionPane.showMessageDialog(null,"No fue posible eliminar la persona de BD"
                            + " (Recuerde que solo se eliminara usuario si su sueldo es de $120.000). Revise log en consola");
                    //Limpiamos textField
                     this.listProd.txtcod.setText("");
                     this.listProd.txtRUT.setText("");
                     this.listProd.txtnombre.setText(""); 
                     this.listProd.txtapellido.setText("");
                     this.listProd.txtcelular.setText("");
                     this.listProd.txtemail.setText("");
                     this.listProd.txtsueldo.setText("");
                     this.listProd.cboestado.setSelectedItem("");
                     this.listProd.cboDepa.setSelectedItem("");
                     //Actualizamos la vista del listado
                     this.listProd.tbProducto.setModel(this.modelo.ListadoPersonas());
                }
                else {
                if (this.modelo.eliminarPersona(Integer.parseInt(codigoe),rute, Integer.parseInt(sueld)) == true){
                    JOptionPane.showMessageDialog(null,"Se ha eliminado a la persona seleccionada");
                    //Limpiamos textField
                     this.listProd.txtcod.setText("");
                     this.listProd.txtRUT.setText("");
                     this.listProd.txtnombre.setText(""); 
                     this.listProd.txtapellido.setText("");
                     this.listProd.txtcelular.setText("");
                     this.listProd.txtemail.setText("");
                     this.listProd.txtsueldo.setText("");
                     this.listProd.cboestado.setSelectedItem("");
                     this.listProd.cboDepa.setSelectedItem("");
                     //Actualizamos la vista del listado
                     this.listProd.tbProducto.setModel(this.modelo.ListadoPersonas());
                     
                }
                else {
                    JOptionPane.showMessageDialog(null,"No fue posible eliminar la persona de BD. Revise log en consola");
                }
                }
                
                //Se agrega comportamiento de botón Limpiar - Req: consulta 1
            case btnLimpiar:
                this.ingProd.txtCodigo.setText("");
                this.ingProd.txtRut.setText("");
                this.ingProd.txtNombre.setText(""); 
                this.ingProd.txtApellido.setText("");
                this.ingProd.txtCelular.setText("");
                this.ingProd.txtEmail.setText("");
                this.ingProd.txtSueldo.setText("");
                this.ingProd.cboEstado.setSelectedItem("");
                this.ingProd.cboDepa.setSelectedItem("");
                //Se coloca cursor en el primer campo del formulario de ingreso de personas
                this.ingProd.txtCodigo.requestFocus();
                
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if( e.getButton()== 1)//boton izquierdo
        {
            //Muestro datos de producto a modificar
             int fila = this.listProd.tbProducto.rowAtPoint(e.getPoint());
             if (fila > -1){
                this.listProd.txtcod.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 0) ));
                this.listProd.txtRUT.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 1) ));
                this.listProd.txtnombre.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 2) ));
                this.listProd.txtapellido.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 3) ));
                this.listProd.txtcelular.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 4) ));
                this.listProd.txtemail.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 5) ));
                this.listProd.txtsueldo.setText(String.valueOf(this.listProd.tbProducto.getValueAt(fila, 6) ));
                this.listProd.cboestado.setSelectedItem(this.listProd.tbProducto.getValueAt(fila, 7));
                this.listProd.cboDepa.setSelectedItem(this.listProd.tbProducto.getValueAt(fila, 8));
                }
                
             }
             
        }
    

    @Override
   public void mousePressed(MouseEvent e) {}

   @Override
   public void mouseReleased(MouseEvent e) {}

   @Override
   public void mouseEntered(MouseEvent e) {}

   @Override
   public void mouseExited(MouseEvent e) {}
    
    }

