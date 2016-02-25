/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ConexionBD.Procedimientos;
import ConexionBD.VerificaVigencia;
import beans.BMail;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.ClaseEnviarCorreo;
import modelos.Encripta;
import modelos.GeneraAuditoria;
import modelos.Mail;
import utils.Constantes;

/**
 * Envía el primer correo de Verificación para continuar con el registro desde
 * un link
 *
 * @author ElyyzZ BaRruEtA
 */
public class EnviaEmailInicio extends HttpServlet {

    Encripta e = new Encripta();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            response.setContentType("text/html;charset=ISO-8859-1");
            request.setCharacterEncoding("UTF8");
            String correo = request.getParameter("correo");

            PrintWriter out = response.getWriter();
            Procedimientos p = new Procedimientos();
            VerificaVigencia v = new VerificaVigencia();
            //String Url = "http://192.168.40.112:8080/MODULO_ASPIRANTE/Datos_Aspirante.jsp";
            String Url = Constantes.URL;
            String home = Constantes.HOME;
            //String home = "http://192.168.40.112:8080/MODULO_ASPIRANTE/";
            
            String CorreoEnc = e.encryptURL(correo);
            String UrlEnc = e.encryptURL(Url);
            String liga = Url + "?correo=" + CorreoEnc;
            //fechaList = v.getPeriodoRenovacion();

            //Obtenemos la cadena compuesta de la verificacion del correo 
            //  y la transformamos a vector
            String[] cadExiste = p.GetValidaCorreo(correo, UrlEnc).split("&");
            int existe = Integer.parseInt(cadExiste[0]);
            String msg = cadExiste[1].trim();
            //System.out.println("*******************************************************-----------------------");
            //System.out.println("Resultado de Validacion de correo: " + correo + "  Existe ? :" + existe);
            existe=0;
            switch (existe) {

                case 0:
                    BMail beanMail = new BMail();
                    beanMail.setCuerpo("<b><font size=4 face=\"arialblack\" >"
                            + " Durante el proceso de registro recibirá el  siguiente  correo, por favor permanezca al pendiente: \n"
                            + "<br><br>"
                            + " 1.-Correo  de generación de preficha, que se le enviará  al concluir el registro de sus datos. \n"
                            + "<br>"
                            + "<br><br>"
                            + "<b><ins>Importante:</ins><b> Para realizar cualquier cambio en los datos proporcionados deberá solicitarlo en ventanilla del"
                            + " Departamento de Coordinación de Educación a Distancia. \n"
                            + "Tome en cuenta que es responsabilidad del aspirante cumplir con todas las etapas del proceso\n"
                            + "para finalizar su registro de lo contrario su solicitud será rechazada."
                            + "<br><br>"
                            + "<br><br>"
                            + "Para continuar con su registro por favor haga click en el siguiente enlace. "
                            + "<a href=" + liga + " >  Registro Aspirante </a></font>."
                    );

                    //System.out.println("Enviando correo a: " + correo);
                    //int ret = m.sendMail(beanMail, correo, true, "Aspirante  Tecnológico de Toluca: Liga Para Registro.");
                    //int ret = m.sendMailImg(beanMail, getServletContext(), correo, true, "Aspirante  Tecnológico de Toluca: Liga Para Registro.");
                    //System.out.println("Enviando correo con imagens :S");
                    ClaseEnviarCorreo cec = new ClaseEnviarCorreo();
                    int ret = cec.sendMail(getServletContext(), correo, beanMail.getCuerpo(), 1);
                    switch (ret) {

                        case 0:
                            out.print("Se ha enviado un enlace a su correo para continuar con su registro. Si no  logra  visualizar el correo en su bandeja  de  entrada no  olvide consultar  la  bandeja de correo no deseado.");
                            break;
                        case -1:
                            out.print("Su dirección de correo  electrónico es inválida. Vuelva a Intentarlo. Revise que este  bien escrita antes de dar clic en enviar");
                            break;
                        case -2:
                            msg = "No se  pudo enviar el correo. Por favor vuelva a intentarlo. Revise  que este escrito correctamente";
                            out.print(msg);
                            GeneraAuditoria ob = new GeneraAuditoria();
                            ob.crea_archivo("1961", "com.sun.mail.util.MailConnectException: Couldn't connect to host, port: mail.ittoluca.edu.mx, 25; timeout -1;", msg);
                            break;
                        default:
                            msg = "No se  pudo enviar el correo. Por favor vuelva a intentarlo. Revise  que este escrito correctamente";
                            out.print(msg);
                            GeneraAuditoria o = new GeneraAuditoria();
                            o.crea_archivo("654", "com.sun.mail.util.MailConnectException: Couldn't connect to host, port: mail.ittoluca.edu.mx, 25; timeout -1;", msg);
                            break;
                    }
                    //System.out.println("Se trato de enviar el correo y el resultado fue: " + ret);
                    break;

                case 1:
                    // retorna  que ya existe correo
                    //String msg = "El correo  ya  fue registrado en esta convocatoria";
                    out.print(msg);
                    GeneraAuditoria ob = new GeneraAuditoria();
                    ob.crea_archivo("1", "AUDITORIA, El aspirante con correo: " + correo + " ya se registro en esta convocatoria", msg);
                    System.out.println(" AUDITORIA, El aspirante con correo: " + correo + " ya se registro en esta convocatoria");
                    break;

                case 2:
                    out.print(msg);
                    GeneraAuditoria err2 = new GeneraAuditoria();
                    err2.crea_archivo("2", "AUDITORIA, Surgio un error al generar la liga para el aspirante con correo: " + correo, msg);
                    break;
                case 3:
                    BMail beanNoFichas = new BMail();

                    beanNoFichas.setCuerpo("<b><font size=4 face=\"arialblack\" >"
                            + " Durante el proceso de registro recibirá el  siguiente  correo, por favor permanezca al pendiente: \n"
                            + "<br><br>"
                            + " 1.-Correo  de generación de preficha, que se le enviará  al concluir el registro de sus datos. \n"
                            + "<br>"
                            + "<br><br>"
                            + "<b><ins>Importante:</ins><b> Para realizar cualquier cambio en los datos proporcionados deberá solicitarlo en ventanilla del"
                            + " Departamento de Coordinación de Educación a Distancia. \n"
                            + "Tome en cuenta que es responsabilidad del aspirante cumplir con todas las etapas del proceso\n"
                            + "para finalizar su registro de lo contrario su solicitud será rechazada."
                            + "<br><br>"
                            + "<br><br>"
                            + "Para continuar con su registro por favor haga click en el siguiente enlace. "
                            + "<a href=" + liga + " >  Registro Aspirante </a></font>."
                    );

                    //Mail m2 = new Mail();
                    ClaseEnviarCorreo cec2 = new ClaseEnviarCorreo();
                    //int ret2 = m2.sendMail(beanNoFichas, correo, true, "Aspirante  Tecnológico de Toluca: Liga Para Registro.");
                    int ret2 = cec2.sendMail(getServletContext(),correo, beanNoFichas.getCuerpo(),1);
                    switch (ret2) {

                        case 0:
                            out.print("Se ha enviado un enlace a su correo para continuar con su registro. Si no  logra  visualizar el correo en su bandeja  de  entrada no  olvide consultar  la  bandeja de correo no deseado.");
                            break;
                        case -1:
                            out.print("Su dirección de correo no electrónico es inválida. Vuelva a Intentarlo. Revise que este  bien escrita antes de dar clic en enviar");
                            break;
                        case -2:
                            msg = "No se  pudo enviar el correo. Por favor vuelva a intentarlo. Revise  que este escrito correctamente";
                            out.print(msg);
                            GeneraAuditoria ob2 = new GeneraAuditoria();
                            ob2.crea_archivo("1961", "com.sun.mail.util.MailConnectException: Couldn't connect to host, port: mail.ittoluca.edu.mx, 25; timeout -1;", msg);
                            break;
                        default:
                            msg = "No se  pudo enviar el correo. Por favor vuelva a intentarlo. Revise  que este escrito correctamente";
                            out.print(msg);
                            GeneraAuditoria o = new GeneraAuditoria();
                            o.crea_archivo("654", "com.sun.mail.util.MailConnectException: Couldn't connect to host, port: mail.ittoluca.edu.mx, 25; timeout -1;", msg);
                            break;
                    }
                    break;
                case 6:
                    out.print(msg);
                    GeneraAuditoria err6 = new GeneraAuditoria();
                    err6.crea_archivo("6", "Surgio un error al revisar las fichas", msg);
                    break;
                default:
                    // error  inesperado al crear  la liga
                    //msg = "Ha ocurrido un error. Por favor  vuelve intentarlo.";
                    out.print(msg);
                    System.out.println("AUDITORIA   Error al crear la liga");
                    GeneraAuditoria o = new GeneraAuditoria();
                    o.crea_archivo("---", "AUDITORIA   Error al crear la liga", msg);
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EnviaEmailInicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EnviaEmailInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>}
}
