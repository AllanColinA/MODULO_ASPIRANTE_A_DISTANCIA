/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ConexionBD.Conexion;
import ConexionBD.CreaPrefichaPDF;
import ConexionBD.Procedimientos;
import beans.BMail;
import beans.BaseDatos;
import beans.ContactoEmeAsp;
import beans.DomicilioAspirante;
import beans.EscProcedenciaAsp;
import beans.PersonalesAspirante;
import beans.SocioeconomicosAsp;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Encripta;
import modelos.GeneraAuditoria;
import modelos.Mail;
import modelos.llenarBD;
import oracle.jdbc.driver.OracleTypes;
import utils.Constantes;

/**
 *
 * @author Desarrollo de sistem
 */
public class InsertandoDatos extends HttpServlet {

    String home = Constantes.HOME;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=ISO-8859-1");
            request.setCharacterEncoding("UTF8");
            llenarBD b = new llenarBD();
            PersonalesAspirante aspirant = (PersonalesAspirante) request.getSession().getAttribute("aspirante");
            EscProcedenciaAsp AspEsc = (EscProcedenciaAsp) request.getSession().getAttribute("AspEscuela");
            DomicilioAspirante AspDom = (DomicilioAspirante) request.getSession().getAttribute("AspDomicilio");
            SocioeconomicosAsp AspSocioecono = new SocioeconomicosAsp();
            ContactoEmeAsp contacto = new ContactoEmeAsp();
            String DatosSocioeconomicos = request.getParameter("DatosSoc");
            ArrayList<String> SocioeconomicosAsp = b.Formatea(DatosSocioeconomicos);
            SeteaSocioeconomicos(SocioeconomicosAsp, AspSocioecono, contacto);
            boolean inserta = false;
            int fijo = -1;
            int cel = -1;
            int teltrab = -1;
            try {
                fijo = Integer.parseInt(contacto.getTelFijo().trim());
            } catch (NumberFormatException e) {
                inserta = true;
            }
            try {
                cel = Integer.parseInt(contacto.getTelCelular().trim());
            } catch (NumberFormatException e) {
                inserta = true;
            }
            try {
                teltrab = Integer.parseInt(contacto.getTelTrab().trim());
            } catch (NumberFormatException e) {
                inserta = true;
            }
            if (fijo == 0 || cel == 0 || teltrab == 0) {
                inserta = false;
            } else {
                inserta = true;
            }
            if (inserta) {
                InsertaDatosGeneral(response, AspSocioecono, contacto, aspirant, AspDom, AspEsc);
                request.getSession().setAttribute("AspSocioecono", AspSocioecono);
                request.getSession().setAttribute("contacto", contacto);
            } else {
                List<BaseDatos> RespuestaInsert = new ArrayList<>();
                BaseDatos bd = new BaseDatos();
                bd.setNombre("Los  teléfonos deben ser distintos de  cero.");
                bd.setClave("5");
                RespuestaInsert.add(bd);
                String json = null;
                json = new Gson().toJson(RespuestaInsert);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InsertandoDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InsertandoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            Logger.getLogger(InsertandoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void SeteaSocioeconomicos(ArrayList<String> SocioeconomicosAsp, SocioeconomicosAsp AspSocioecono, ContactoEmeAsp contacto) {
        AspSocioecono.setNomPadre(SocioeconomicosAsp.get(0));
        AspSocioecono.setNomMadre(SocioeconomicosAsp.get(1));
        contacto.setNomContacto(SocioeconomicosAsp.get(2));
        contacto.setMunicipio(Integer.parseInt(SocioeconomicosAsp.get(3).trim()));
        contacto.setEstado(Integer.parseInt(SocioeconomicosAsp.get(4).trim()));
        contacto.setColonia(SocioeconomicosAsp.get(5));
        contacto.setCalle(SocioeconomicosAsp.get(6));
        contacto.setTelFijo(SocioeconomicosAsp.get(7));
        contacto.setTelCelular(SocioeconomicosAsp.get(8));
        contacto.setCentroTrab(SocioeconomicosAsp.get(9));
        contacto.setTelTrab(SocioeconomicosAsp.get(10));
        contacto.setNumExt(Integer.parseInt(SocioeconomicosAsp.get(11).trim()));
        contacto.setNumInt(SocioeconomicosAsp.get(12).trim());
        contacto.setCodPostal(SocioeconomicosAsp.get(13).trim());

        AspSocioecono.setVivePadre(SocioeconomicosAsp.get(14));
        AspSocioecono.setViveMadre(SocioeconomicosAsp.get(15));
        if (!"N".equals(SocioeconomicosAsp.get(16))) {
            AspSocioecono.setTipoBeca(SocioeconomicosAsp.get(17));
        } else {
            AspSocioecono.setTipoBeca(SocioeconomicosAsp.get(16));
        }
        AspSocioecono.setZonaProced(SocioeconomicosAsp.get(18));
        AspSocioecono.setNivEstPadre(SocioeconomicosAsp.get(19));
        AspSocioecono.setNivEstMadre(SocioeconomicosAsp.get(20));
        AspSocioecono.setIngreTot(SocioeconomicosAsp.get(21));
        if (!"Otro".equals(SocioeconomicosAsp.get(25))) {
            AspSocioecono.setDepenEcon(SocioeconomicosAsp.get(25));
        } else {
            AspSocioecono.setDepenEcon(SocioeconomicosAsp.get(22));
        }
        AspSocioecono.setOcuPadre(SocioeconomicosAsp.get(23));
        AspSocioecono.setOcuMadre(SocioeconomicosAsp.get(24));
        AspSocioecono.setTipoCasa(SocioeconomicosAsp.get(26));
        if (!"Otro".equals(SocioeconomicosAsp.get(31))) {
            AspSocioecono.setViveCon(SocioeconomicosAsp.get(31));
        } else {
            AspSocioecono.setViveCon(SocioeconomicosAsp.get(27));//opcion  
        }
        AspSocioecono.setPerViveCasa(SocioeconomicosAsp.get(28));
        AspSocioecono.setCuartosCasa(SocioeconomicosAsp.get(29));
        AspSocioecono.setOportunidades(SocioeconomicosAsp.get(30));
        AspSocioecono.setNumPerEcono(SocioeconomicosAsp.get(32));
    }

    private void InsertarDatos(HttpServletResponse response, SocioeconomicosAsp AspSocioecono, ContactoEmeAsp contacto, PersonalesAspirante aspirante, DomicilioAspirante AspDomicilio, EscProcedenciaAsp AspEscuela) {
        try {
            Procedimientos p = new Procedimientos();
            int Resultado = -1;
            int[] ResPersonalData = new int[2];
            boolean termina = true;
            int opcion = 1;
            do {
                switch (opcion) {
                    case 1:
                        ResPersonalData = p.InsertaPersonales(aspirante.getCurp(), aspirante.getNombre(), aspirante.getAppat(),
                                aspirante.getApmat(), aspirante.getFechaNac(), aspirante.getPaisNac(), aspirante.getEdoNac(),
                                aspirante.getMpioNac(), aspirante.getLocNac(), aspirante.getSexo(), aspirante.getEdoCivil(),
                                aspirante.getTipoSangre(), aspirante.getCapacidadDif(),aspirante.getCorreo());
                        Resultado = ResPersonalData[1];
                        if (Resultado != 0) {
                            termina = false;
                        }
                        aspirante.setIdAspirante(ResPersonalData[0]);
                        opcion = 2;
                        System.out.println("AUDITORIA------ RESULTADO DE DATOS PERSONALES: " + Resultado);
                        break;
                    case 2:
                        Resultado = p.InsertaDireccion(aspirante.getIdAspirante(), Integer.parseInt(AspDomicilio.getEstadoVive().trim()), Integer.parseInt(AspDomicilio.getMunicipioVive().trim()),
                                Integer.parseInt(AspDomicilio.getLocalidadVive().trim()), AspDomicilio.getCalleVive(), AspDomicilio.getNumInt(), Integer.parseInt(AspDomicilio.getNumExt().trim()),
                                AspDomicilio.getColoniaVive(), Integer.parseInt(AspDomicilio.getCodPostal().trim()), AspDomicilio.getTelFijo(), AspDomicilio.getTelCelular());
                        opcion = 3;
                        System.out.println("AUDITORIA------ RESULTADO DE DOMICILIO: " + Resultado);

                        break;
                    case 3:
                        Resultado = p.InsertaEscProcedencia(aspirante.getIdAspirante(), AspEscuela.getEscuela(), AspEscuela.getClaveEsc(), AspEscuela.getTipoEsc(), Integer.parseInt(AspEscuela.getMesInicio().trim()),
                                Integer.parseInt(AspEscuela.getMesFin().trim()), Integer.parseInt(AspEscuela.getAnioInicio().trim()), Integer.parseInt(AspEscuela.getAnioFin().trim()), Integer.parseInt(AspEscuela.getPromedio().trim()));
                        opcion = 4;
                        System.out.println("AUDITORIA------ RESULTADO DE ESCUELA DE PROCEDENCIA: " + Resultado);

                        break;
                    case 4:
                        int i = 1;
                        while (Resultado == 0 && i <= 3) {
                            switch (i) {
                                case 1:
                                    Resultado = p.InsertaCarrera(aspirante.getIdAspirante(), aspirante.getCarrOp1(), i);
                                    i++;
                                    break;
                            }
                        }
                        System.out.println("AUDITORIA------ RESULTADO DE CARRERA: " + Resultado);

                        opcion = 5;
                        break;
                    case 5:
                        Resultado = p.InsertaSocioeconomicos(aspirante.getIdAspirante(), AspSocioecono.getNomPadre(), AspSocioecono.getVivePadre(),
                                Integer.parseInt(AspSocioecono.getOcuPadre().trim()), Integer.parseInt(AspSocioecono.getNivEstPadre().trim()), AspSocioecono.getNomMadre(),
                                AspSocioecono.getViveMadre(), Integer.parseInt(AspSocioecono.getOcuMadre().trim()), Integer.parseInt(AspSocioecono.getNivEstMadre().trim()),
                                AspSocioecono.getViveCon(), AspSocioecono.getDepenEcon(), AspSocioecono.getIngreTot(), AspSocioecono.getTipoCasa(), AspSocioecono.getCuartosCasa(),
                                Integer.parseInt(AspSocioecono.getNumPerEcono().trim()), Integer.parseInt(AspSocioecono.getPerViveCasa().trim()), AspSocioecono.getOportunidades(),
                                AspSocioecono.getZonaProced(), AspSocioecono.getTipoBeca());
                        opcion = 6;
                        System.out.println("AUDITORIA------ RESULTADO DE SOCIOECONOMICOS: " + Resultado);

                        break;
                    case 6:
                        Resultado = p.InsertaContactoEmergencia(aspirante.getIdAspirante(), contacto.getNomContacto(), contacto.getEstado(), contacto.getMunicipio(),
                                contacto.getColonia(), contacto.getCalle(), contacto.getNumInt(), contacto.getNumExt(), contacto.getTelFijo(), contacto.getTelCelular(),
                                contacto.getCentroTrab(), contacto.getTelTrab());
                        termina = false;
                        System.out.println("AUDITORIA------ RESULTADO DE CONTACTO DE EMERGENCIA: " + Resultado);
                        break;
                }
            } while (Resultado == 0 && termina);
            String RespuestaDatosAsp;
            List<BaseDatos> RespuestaInsert = new ArrayList<>();
            BaseDatos bd;

            if (Resultado != 0 || aspirante.getIdAspirante() < 0) {
                //Hubo  un error  y el aspirante se  borro 
                switch (Resultado) {
                    case 20000:
                        RespuestaDatosAsp = "Ha ocurrido un error: Al comparar las  opciones de carrera elegidas."
                                + "Por favor vuelva a intentarlo y si el error persiste favor de "
                                + "contactarnos desde la  página  principal en el  apartado de contacto.";
                        break;
                    case 20001:
                        RespuestaDatosAsp = "Ha ocurrido un error: No puede elegir dos veces la misma carrera."
                                + "Por favor vuelva a intentarlo y si el error persiste favor de "
                                + "contactarnos desde la  página  principal en el  apartado de contacto.";
                        break;
                    case 20002:
                        RespuestaDatosAsp = "Ha ocurrido un error: Al generar preficha y/o id de  aspirante."
                                + "Por favor vuelva a intentarlo y si el error persiste favor de "
                                + "contactarnos desde la  página  principal en el  apartado de contacto.";
                        break;
                    default:
                        RespuestaDatosAsp = "Ha ocurrido un error y no se  han  podido guardar sus datos. "
                                + "Por favor vuelva a intentarlo y si el error persiste favor de "
                                + "contactarnos desde la  página  principal en el  apartado de contacto.";
                        break;

                }
                int resultado_borrar;
                CreaPrefichaPDF fecha = new CreaPrefichaPDF();
                resultado_borrar = p.Borrando(aspirante.getIdAspirante());
                System.out.println(" ----------AUDITORIA----------  Error:" + Resultado + "----" + fecha.fecha_hoy() + " El aspirante con  Id: " + aspirante.getIdAspirante() + " Nombre: " + aspirante.getNombre() + " Curp: " + aspirante.getCurp() + " Correo: " + aspirante.getCorreo() + " no ha podido guardar sus datos");
                bd = new BaseDatos();
                bd.setNombre(RespuestaDatosAsp);
                bd.setClave("-1");
                RespuestaInsert.add(bd);
            } else {
                
                //Verificar cuenta de prefichas disponibles
                
                
                Encripta e = new Encripta();
                if (Resultado == 0) {
                    String curp = e.encryptURL(aspirante.getCurp().trim());
//                    String url = "http://localhost:33956//MODULO_ASPIRANTE/PrefichaGenerar?curp=" + curp;
                    String url = Constantes.URL_ENCRIPT + curp;
                    BMail beanMail = new BMail();
                    beanMail.setCuerpo("<b><font size=4 face=\"arialblack\" > Estimado apirante por favor haga click en el siguiente enlace para visualizar su preficha."
                            + "<a href=" + url
                            + ">Genera Preficha</a></font>.");
                    
                    Mail m = new Mail();
                    boolean ret = m.sendMail(beanMail, "aspirantes@ittoluca.edu.mx", aspirante.getCorreo(), "11280672", true, "Aspirante  Tecnológico de Toluca: Generar Preficha");
                    if (ret) {
                        //se  envio correo
                        RespuestaDatosAsp = "Su registro ha finalizado con éxito. \n En un  momento será enviada su solicitud junto con el voucher de pago al correo \n"
                                + aspirante.getCorreo() + " . Si no logra visualizar el correo en su \"Bandeja de entrada\" debe verificar en la bandeja de \"Correo no deseado\". Las\n"
                                + "                                instrucciones para darle seguimiento a  su registro están anexas en el correo que le fue enviado,  por ello es  \n"
                                + "                                importante que lo lea atentamente";
                        bd = new BaseDatos();
                        bd.setNombre(RespuestaDatosAsp);
                        bd.setClave("0");
                        RespuestaInsert.add(bd);
                    } else {
                        //no se  envio correo vuleve  a intentar
                        RespuestaDatosAsp = "No se  pudo enviar el correo. Por favor vuelva a intentarlo";
                        bd = new BaseDatos();
                        bd.setNombre(RespuestaDatosAsp);
                        bd.setClave("2");
                        RespuestaInsert.add(bd);
                    }
                }
            }
            if (Resultado == 1) {
                RespuestaDatosAsp = "El aspirante  ya se encuentra registrado";
                bd = new BaseDatos();
                bd.setNombre(RespuestaDatosAsp);
                bd.setClave("-2");
                RespuestaInsert.add(bd);

            }
            String json = null;
            json = new Gson().toJson(RespuestaInsert);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(DatosPersonalesAsp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InsertaDatosGeneral(HttpServletResponse response, SocioeconomicosAsp AspSocioecono, ContactoEmeAsp contacto, PersonalesAspirante aspirante, DomicilioAspirante AspDomicilio, EscProcedenciaAsp AspEscuela) throws ClassNotFoundException, SQLException, Exception {
        String error = "Ocurrio Error";
        int Resultado = 20;

        try {
            Conexion con = new Conexion();

            Encripta e = new Encripta();
            List<BaseDatos> RespuestaInsert = new ArrayList<>();
            BaseDatos bd;
            System.out.println("AUDITORIA--- Datos  que se ingresarán: ---Datos personales:"
                    + aspirante.getCurp() + " "
                    + aspirante.getNombre() + " "
                    + aspirante.getAppat() + " "
                    + aspirante.getApmat() + " "
                    + aspirante.getFechaNac() + " "
                    + aspirante.getPaisNac() + " "
                    + aspirante.getEdoNac() + " "
                    + aspirante.getMpioNac() + " "
                    + aspirante.getLocNac() + " "
                    + aspirante.getSexo() + " "
                    + aspirante.getEdoCivil() + " "
                    + aspirante.getTipoSangre() + " "
                    + aspirante.getCapacidadDif() + " "
                    + aspirante.getCorreo() + " "
                    //domicilio
                    + "Domicilio: "
                    + AspDomicilio.getEstadoVive() + " "
                    + AspDomicilio.getMunicipioVive() + " "
                    + AspDomicilio.getLocalidadVive() + " "
                    + AspDomicilio.getCalleVive() + " "
                    + AspDomicilio.getNumInt() + " "
                    + AspDomicilio.getNumExt() + " "
                    + AspDomicilio.getColoniaVive() + " "
                    + AspDomicilio.getCodPostal() + " "
                    + AspDomicilio.getTelFijo() + " "
                    + AspDomicilio.getTelCelular() + " "
                    //carrera
                    + "Carrera:  1.-"
                    + aspirante.getCarrOp1() + " "
                    //Escuela de procedecia
                    + "Escuela de procedencia: "
                    + AspEscuela.getEscuela() + " "
                    + AspEscuela.getClaveEsc() + " "
                    + AspEscuela.getTipoEsc() + " "
                    + AspEscuela.getMesInicio() + " "
                    + AspEscuela.getMesFin() + " "
                    + AspEscuela.getAnioInicio() + " "
                    + AspEscuela.getAnioFin() + " "
                    + AspEscuela.getPromedio() + " "
                    //Socioeconomicos
                    + "Socioeconomicos  "
                    + AspSocioecono.getNomPadre() + " "
                    + AspSocioecono.getVivePadre() + " "
                    + AspSocioecono.getOcuPadre() + " "
                    + AspSocioecono.getNivEstPadre() + " "
                    + AspSocioecono.getNomMadre() + " "
                    + AspSocioecono.getViveMadre() + " "
                    + AspSocioecono.getOcuMadre() + " "
                    + AspSocioecono.getNivEstMadre() + " "
                    + AspSocioecono.getViveCon() + " "
                    + AspSocioecono.getDepenEcon() + " "
                    + AspSocioecono.getIngreTot() + " "
                    + AspSocioecono.getTipoCasa() + " "
                    + AspSocioecono.getCuartosCasa() + " "
                    + AspSocioecono.getNumPerEcono() + " "
                    + AspSocioecono.getPerViveCasa() + " "
                    + AspSocioecono.getOportunidades() + " "
                    + AspSocioecono.getZonaProced() + " "
                    + AspSocioecono.getTipoBeca() + " "
                    //contacto
                    + "Contacto: "
                    + contacto.getNomContacto() + " "
                    + contacto.getEstado() + " "
                    + contacto.getMunicipio() + " "
                    + contacto.getColonia() + " "
                    + contacto.getCalle() + " "
                    + contacto.getNumExt() + " "
                    + contacto.getNumInt() + " "
                    + contacto.getCodPostal() + " "
                    + contacto.getTelFijo() + " "
                    + contacto.getTelCelular() + " "
                    + contacto.getTelTrab() + " "
                    + contacto.getCentroTrab() + " "
                    //                    
                    //                    
                    + " Codigo de Error " + Resultado + " "
                    + "Descripcion Error " + error);

            try {
                CallableStatement cs = null;
                cs = con.getConnection().prepareCall(" {call FICHAS.PQ_PROCESO_DISTANCIA_ASP.SET_GENERAR_DISTANCIA_ASP_SP(?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?)}");
                //personales
                cs.setString("paCurp", aspirante.getCurp());
                cs.setString("paNombre", aspirante.getNombre());
                cs.setString("paApellidoPat", aspirante.getAppat());
                cs.setString("paApellidoMat", aspirante.getApmat());
                cs.setString("paFechaDeNacimiento", aspirante.getFechaNac());
                cs.setString("paPaisNacimiento", aspirante.getPaisNac());
                if (!"null".equals(aspirante.getEdoNac())) {
                    cs.setInt("paEstadoNacimiento", Integer.parseInt(aspirante.getEdoNac().trim()));
                    cs.setInt("paMunicipioNac", Integer.parseInt(aspirante.getMpioNac().trim()));
                    cs.setInt("paLocalidadNac", Integer.parseInt(aspirante.getLocNac().trim()));
                } else {
                    cs.setString("paEstadoNacimiento", "");
                    cs.setString("paMunicipioNac", "");
                    cs.setString("paLocalidadNac", "");
                }
                cs.setString("paSexo", String.valueOf(aspirante.getSexo()));
                cs.setString("paEdoCivil", aspirante.getEdoCivil());
                cs.setString("paTipoSangre", aspirante.getTipoSangre());
                cs.setString("paCapDiferente", aspirante.getCapacidadDif());
                cs.setString("paCorreo", aspirante.getCorreo());

                //domicilio
                cs.setInt("paEstadoViveActual", Integer.parseInt(AspDomicilio.getEstadoVive().trim()));
                cs.setInt("paMunicipioVive", Integer.parseInt(AspDomicilio.getMunicipioVive().trim()));
                cs.setInt("paLocalidadVive", Integer.parseInt(AspDomicilio.getLocalidadVive().trim()));
                cs.setString("paCalleAsp", AspDomicilio.getCalleVive());
                if (!"null".equals(AspDomicilio.getNumInt().trim())) {
                    if (!"".equals(AspDomicilio.getNumInt().trim())) {
                        cs.setInt("paNoInterior", Integer.parseInt(AspDomicilio.getNumInt().trim()));
                    } else {
                        cs.setString("paNoInterior", "");
                    }
                } else {
                    cs.setString("paNoInterior", "");
                }
                cs.setInt("paNoExterior", Integer.parseInt(AspDomicilio.getNumExt().trim()));
                cs.setString("paColoniaPob", AspDomicilio.getColoniaVive());
                cs.setInt("paCodPost", Integer.parseInt(AspDomicilio.getCodPostal().trim()));
                cs.setString("paTelFijo", AspDomicilio.getTelFijo());
                cs.setString("paTelCel", AspDomicilio.getTelCelular());

                // Carrera
                cs.setInt("paCarreraOp1", aspirante.getCarrOp1());
                //Escuela de procedencia
                cs.setString("paEscuela", AspEscuela.getEscuela());
                cs.setString("paClaveEscuela", AspEscuela.getClaveEsc());
                cs.setString("paTipoEscuela", AspEscuela.getTipoEsc());
                cs.setInt("paMesIniEsc", Integer.parseInt(AspEscuela.getMesInicio().trim()));
                cs.setInt("paMesFinEsc", Integer.parseInt(AspEscuela.getMesFin().trim()));
                cs.setInt("paAñoIniEsc", Integer.parseInt(AspEscuela.getAnioInicio().trim()));
                cs.setInt("paAñoFinEsc", Integer.parseInt(AspEscuela.getAnioFin().trim()));
                cs.setInt("paPromedio", Integer.parseInt(AspEscuela.getPromedio().trim()));
                //socioeconomicos
                cs.setString("paNombrePadre", AspSocioecono.getNomPadre());
                cs.setString("paVivePadre", AspSocioecono.getVivePadre());
                cs.setInt("paOcupacionPadre", Integer.parseInt(AspSocioecono.getOcuPadre().trim()));
                cs.setInt("paEstudiosPadre", Integer.parseInt(AspSocioecono.getNivEstPadre().trim()));
                cs.setString("paNombreMadre", AspSocioecono.getNomMadre());
                cs.setString("paViveMadre", AspSocioecono.getViveMadre());
                cs.setInt("paOcupacionMadre", Integer.parseInt(AspSocioecono.getOcuMadre().trim()));
                cs.setInt("paEstudiosMadre", Integer.parseInt(AspSocioecono.getNivEstMadre().trim()));
                cs.setString("paViveCon", AspSocioecono.getViveCon());
                cs.setString("paDependEcon", AspSocioecono.getDepenEcon());
                cs.setString("paIngresosTot", AspSocioecono.getIngreTot());
                cs.setString("paTipoDeCasa", AspSocioecono.getTipoCasa());
                cs.setString("paCuartosCasa", AspSocioecono.getCuartosCasa());
                cs.setInt("paNumPerDepEcon", Integer.parseInt(AspSocioecono.getNumPerEcono().trim()));
                cs.setInt("paPersonasVCasa", Integer.parseInt(AspSocioecono.getPerViveCasa().trim()));
                cs.setString("paPrgOportunid", AspSocioecono.getOportunidades());
                cs.setString("paZonaProced", AspSocioecono.getZonaProced());
                cs.setString("paTipoBeca", AspSocioecono.getTipoBeca());
                //contacto
                cs.setString("paNombreContacto", contacto.getNomContacto());
                cs.setInt("paEstado", contacto.getEstado());
                cs.setInt("paMunicipioFk", contacto.getMunicipio());
                cs.setString("paColonia", contacto.getColonia());
                cs.setString("paCalle", contacto.getCalle());
                if (!"null".equals(contacto.getNumInt().trim())) {
                    cs.setInt("paNo_Interior", Integer.parseInt(contacto.getNumInt().trim()));
                } else {
                    cs.setString("paNo_Interior", "");
                }
                cs.setInt("paNo_Exterior", contacto.getNumExt());
                cs.setInt("paCodigoPostal", Integer.parseInt(contacto.getCodPostal().trim()));
                cs.setString("paTel_Fijo", contacto.getTelFijo());
                cs.setString("paTel_Cel", contacto.getTelCelular());
                cs.setString("paCentroTrabajo", contacto.getCentroTrab());
                cs.setString("paTel_Centro_Trab", contacto.getTelTrab());

                cs.registerOutParameter("paCodigoError", OracleTypes.NUMBER);
                cs.registerOutParameter("paMjeDescError", OracleTypes.VARCHAR);
                cs.execute();
                Resultado = cs.getInt("paCodigoError");
                error = cs.getString("paMjeDescError");
                cs.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InsertandoDatos.class.getName()).log(Level.SEVERE, null, ex);

            } finally {
                con.CerraConexion();
            }
            String RespuestaDatosAsp = "";
            
            System.out.println("El codigo de error al hacer la insercion general del aspirante con CURP: "
                                + aspirante.getCurp() + " dio: " + Resultado + " MjeDesc: " +error);
            if (Resultado != 0) {
                System.out.println("AUDITORIA--- Error: " + Resultado + "-- Descripción:  " + error);
                System.out.println("AUDITORIA--- Datos ingresados: ---Datos personales:"
                        + aspirante.getCurp() + " "
                        + aspirante.getNombre() + " "
                        + aspirante.getAppat() + " "
                        + aspirante.getApmat() + " "
                        + aspirante.getFechaNac() + " "
                        + aspirante.getPaisNac() + " "
                        + aspirante.getEdoNac() + " "
                        + aspirante.getMpioNac() + " "
                        + aspirante.getLocNac() + " "
                        + aspirante.getSexo() + " "
                        + aspirante.getEdoCivil() + " "
                        + aspirante.getTipoSangre() + " "
                        + aspirante.getCapacidadDif() + " "
                        + aspirante.getCorreo() + " "
                        //domicilio
                        + "Domicilio: "
                        + AspDomicilio.getEstadoVive() + " "
                        + AspDomicilio.getMunicipioVive() + " "
                        + AspDomicilio.getLocalidadVive() + " "
                        + AspDomicilio.getCalleVive() + " "
                        + AspDomicilio.getNumInt() + " "
                        + AspDomicilio.getNumExt() + " "
                        + AspDomicilio.getColoniaVive() + " "
                        + AspDomicilio.getCodPostal() + " "
                        + AspDomicilio.getTelFijo() + " "
                        + AspDomicilio.getTelCelular() + " "
                        //carrera
                        + "Carrera:  1.-"
                        + aspirante.getCarrOp1() + " "
                        //Escuela de procedecia
                        + "Escuela de procedencia: "
                        + AspEscuela.getEscuela() + " "
                        + AspEscuela.getClaveEsc() + " "
                        + AspEscuela.getTipoEsc() + " "
                        + AspEscuela.getMesInicio() + " "
                        + AspEscuela.getMesFin() + " "
                        + AspEscuela.getAnioInicio() + " "
                        + AspEscuela.getAnioFin() + " "
                        + AspEscuela.getPromedio() + " "
                        //Socioeconomicos
                        + "Socioeconomicos  "
                        + AspSocioecono.getNomPadre() + " "
                        + AspSocioecono.getVivePadre() + " "
                        + AspSocioecono.getOcuPadre() + " "
                        + AspSocioecono.getNivEstPadre() + " "
                        + AspSocioecono.getNomMadre() + " "
                        + AspSocioecono.getViveMadre() + " "
                        + AspSocioecono.getOcuMadre() + " "
                        + AspSocioecono.getNivEstMadre() + " "
                        + AspSocioecono.getViveCon() + " "
                        + AspSocioecono.getDepenEcon() + " "
                        + AspSocioecono.getIngreTot() + " "
                        + AspSocioecono.getTipoCasa() + " "
                        + AspSocioecono.getCuartosCasa() + " "
                        + AspSocioecono.getNumPerEcono() + " "
                        + AspSocioecono.getPerViveCasa() + " "
                        + AspSocioecono.getOportunidades() + " "
                        + AspSocioecono.getZonaProced() + " "
                        + AspSocioecono.getTipoBeca() + " "
                        //contacto
                        + "Contacto: "
                        + contacto.getNomContacto() + " "
                        + contacto.getEstado() + " "
                        + contacto.getMunicipio() + " "
                        + contacto.getColonia() + " "
                        + contacto.getCalle() + " "
                        + contacto.getNumInt() + " "
                        + contacto.getNumExt() + " "
                        + contacto.getTelFijo() + " "
                        + contacto.getTelCelular() + " "
                        + contacto.getCentroTrab() + " "
                        + contacto.getTelTrab() + " "
                        + contacto.getCodPostal() + " "
                        + " Codigo de Error " + Resultado + " "
                        + "Descripcion Error " + error);
            }
            switch (Resultado) {
                case 20000:
                    RespuestaDatosAsp = "Ha ocurrido un error: Al comparar las  opciones de carrera elegidas."
                            + "Por favor vuelva a intentarlo y si el error persiste favor de "
                            + "contactarnos desde la  página  principal en el  apartado de contacto.";
                     GeneraAuditoria audi = new GeneraAuditoria();
                    audi.crea_archivo("20000", "Ha ocurrido un error: Al comparar las  opciones de carrera elegidas." , RespuestaDatosAsp);

                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-1");
                    RespuestaInsert.add(bd);
                    break;
                case 20001:
                    RespuestaDatosAsp = "Ha ocurrido un error: No puede elegir dos veces la misma carrera."
                            + "Por favor vuelva a intentarlo y si el error persiste favor de "
                            + "contactarnos desde la  página  principal en el  apartado de contacto.";
                    GeneraAuditoria aud = new GeneraAuditoria();
                    aud.crea_archivo("20001", "Ha ocurrido un error: No puede elegir dos veces la misma carrera." , RespuestaDatosAsp);

                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-1");
                    RespuestaInsert.add(bd);
                    break;
                case 20002:
                    RespuestaDatosAsp = "Ha ocurrido un error: Al generar preficha y/o id de  aspirante."
                            + "Por favor vuelva a intentarlo y si el error persiste favor de "
                            + "contactarnos desde la  página  principal en el  apartado de contacto.";
                    
                    GeneraAuditoria au = new GeneraAuditoria();
                    au.crea_archivo("20002", "Ha ocurrido un error: Al generar preficha y/o id de  aspirante." , RespuestaDatosAsp);

                    
                    
                    
                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-1");
                    RespuestaInsert.add(bd);
                    break;
                case 1:

                    RespuestaDatosAsp = "El aspirante ya se encuentra registrado en esta convocatoria.";
                    GeneraAuditoria ob = new GeneraAuditoria();
                    ob.crea_archivo("-2", "La clave CURP " + aspirante.getCurp() + "y/o el correo electrónico  " + aspirante.getCorreo() + " ya se registró en esta convocatoria.", RespuestaDatosAsp);

                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-2");
                    RespuestaInsert.add(bd);
                    break;
                case 2:
                    RespuestaDatosAsp = "No se pudo realizar el registro por la siguiente razón: \n" + error;
                    GeneraAuditoria ob2 = new GeneraAuditoria();
                    ob2.crea_archivo("-3", "CURP " + aspirante.getCurp() + "|correo electrónico  " + aspirante.getCorreo() + " Ocurrio un error al obtener la cantidad de prefichas.", error);

                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-3");
                    RespuestaInsert.add(bd);
                    break;
                default:
                    RespuestaDatosAsp = "Ha ocurrido un error y no se  han  podido guardar sus datos. "
                            + "Por favor vuelva a intentarlo y si el error persiste favor de "
                            + "contactarnos desde la  página  principal en el  apartado de contacto. \n\n"
                            + error;
                    bd = new BaseDatos();
                    bd.setNombre(RespuestaDatosAsp);
                    bd.setClave("-1");
                    RespuestaInsert.add(bd);
                    
                    GeneraAuditoria o = new GeneraAuditoria();
                    o.crea_archivo("-1", "No se puedieron almacenar los datos al realizar el registro. " , RespuestaDatosAsp);

                    
                    break;

            }
            
            System.out.println("Lo que retorna el servlet para los mensajes es Con algo como: " + RespuestaInsert.get(0).getClave());
            String json = null;
            json = new Gson().toJson(RespuestaInsert);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(InsertandoDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
