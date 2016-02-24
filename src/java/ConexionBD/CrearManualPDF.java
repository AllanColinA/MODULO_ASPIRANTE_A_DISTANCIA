/*
 * Método para la cración de un archivo PDF para el Manual del proceso de registro del aspirante
 */
package ConexionBD;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import utils.Constantes;

/**
 *
 * @author susi
 */
public class CrearManualPDF {

    int x = 0;
    int ancho = 0;
    int largo = 0;
    String espacios = "         ";

    public void ElaboraManual(HttpServletResponse response, ServletContext d) {

        Document manual = new Document();

        PdfWriter writer;
        try {
            Paragraph vacio = new Paragraph("                           ", FontFactory.getFont("arial", 10, Font.BOLD));
            vacio.setAlignment(Element.ALIGN_CENTER);

            writer = PdfWriter.getInstance(manual, response.getOutputStream());
            manual.open();

            String url_logo = "/Imagenes/header_ittoluca_pdf.png";
            String absolute_url_logo = d.getRealPath(url_logo);
            Image itt_logo = Image.getInstance(absolute_url_logo);
            Image Logo_itt = Image.getInstance(itt_logo);
            Logo_itt.setAbsolutePosition(30f, 770f);
            manual.add(Logo_itt);

            manual.add(vacio);
            manual.add(vacio);
            manual.add(vacio);
            Paragraph depto = new Paragraph("Proceso de Registro del Aspirante.", FontFactory.getFont("arial black", 15, Font.BOLD, BaseColor.BLUE.darker()));
            depto.setAlignment(Element.ALIGN_CENTER);
            manual.add(depto);

            manual.add(vacio);

            Paragraph p1 = new Paragraph("ETAPA 1: CREACIÓN DE PREFICHA \n", FontFactory.getFont("arial black", 11, Font.BOLD, BaseColor.BLUE.darker()));
            p1.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(p1);
            Paragraph d1 = new Paragraph(""
                    + espacios + "1.1 Ingrese a la liga:  "+Constantes.HOME+ "\n"
                    + espacios + "1.2 Ingrese a la liga que será enviada a su correo electrónico para empezar su registro.\n"
                    + espacios + "1.3 Responda los cuestionarios  y  siga  las indicaciones.  \n"
                    + espacios + "1.4 Obtenga la preficha  que será enviada  a su correo electrónico.", FontFactory.getFont("arial", 9, Font.BOLD));
            d1.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d1);
//            manual.add(vacio);

            Paragraph p2 = new Paragraph("ETAPA 2: PAGO POR DERECHOS DE EXAMEN DE ADMISIÓN.\n", FontFactory.getFont("arial black", 11, Font.BOLD, BaseColor.BLUE.darker()));
            p2.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(p2);
            Paragraph d2 = new Paragraph(" "
                    + espacios + "2.1 Realice  el  pago por  derechos a  examen de admisión,  con la   REFERENCIA que se encuentra\n"
                    + espacios + "       en la Preficha , en cualquier sucursal BANAMEX.\n"
                    + espacios + "2.2 Cuando su pago sea procesado recibirá una notificación  a su correo electrónico, además \n"
                    + espacios + "       de las indicaciones para continuar con el  proceso de registro.", FontFactory.getFont("arial black", 9, Font.BOLD));
            d2.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d2);
//            manual.add(vacio);

            Paragraph p3 = new Paragraph("ETAPA 3: OBTENCIÓN DE   FICHA.\n", FontFactory.getFont("arial black", 11, Font.BOLD, BaseColor.BLUE.darker()));
            p3.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(p3);

            Paragraph d3 = new Paragraph(""
                    + espacios + "Valide  su  correo electrónico ya que le será enviada la Ficha para continuar con su registro.\n"
                    + espacios + "Importante: Se le recomienda estar al pendiente ya que vía correo electrónico recibirá la FICHA que  usará para \n"
                    + espacios + "                     su  registro  en CENEVAL.", FontFactory.getFont("arial black", 9, Font.BOLD));
            d3.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d3);
//            manual.add(vacio);
            Paragraph p4 = new Paragraph("ETAPA 4: REGISTRO CENEVAL.\n", FontFactory.getFont("arial black", 11, Font.BOLD, BaseColor.BLUE.darker()));
            p4.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(p4);

            Paragraph d4 = new Paragraph(""
                    + espacios + "4.1 Ingrese a la liga que se  enviará  a su correo electrónico para llevar a cabo  el  registro  en CENEVAL .\n"
                    + espacios + "En dicho registro  seleccione  al Instituto Tecnológico de  Toluca en el apartado de escuela  así como la"
                    +"                                    carrera por la cual participa. Además tendrá que ingresar   la  Ficha que le fue enviada a su correo.\n"
                    + espacios + "4.2 Imprima su pase de ingreso al examen de admisión al terminar su registro CENEVAL.", FontFactory.getFont("arial black", 9, Font.BOLD));
            d4.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d4);
//            manual.add(vacio);

            Paragraph p5 = new Paragraph("ETAPA 5: ACUDA  A LAS INSTALACIONES DEL TECNOLÓGICO.", FontFactory.getFont("arial black", 11, Font.BOLD, BaseColor.BLUE.darker()));
            p5.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(p5);

            Paragraph d5 = new Paragraph(""
                    + espacios + "5.1  Canjee el  comprobante  de pago.\n"
                    + espacios + "  5.1.1 Acuda al Depto.  de SERVICIOS FINANCIEROS (Edif.” A”) de Lunes a Viernes de 9:00 a 18:00 hrs.\n"
                    + espacios + "           Cambie su comprobante  de pago proporcionado por el banco  por el recibo oficial de pago de la institución.\n"
                    + espacios + "5.2  Entrege  la  documentación  correspondiente.\n"
                    + espacios + "  5.2.1 Acuda al Depto.  de SERVICIOS ESCOLARES (Edif.” X” ) de Lunes a Viernes de 9:00 a 18:00 hrs. \n"
                    + espacios + "           Entregue  su  pase de ingreso al examen de admisión firmado, recibo  oficial de pago , una fotografía\n"
                    + espacios + "           tamaño infantil (blanco/ negro o a color) y  preficha. \n"
                    + espacios + "  5.2.2 Obtenga guías de estudio.\n"
                    + espacios + "  5.2.3 Obtenga la   Ficha de examen con la información necesaria  para presentar su examen de admisión.\n", FontFactory.getFont("arial black", 9, Font.BOLD));
            d5.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d5);
            manual.add(vacio);
            Paragraph p6 = new Paragraph("Aviso Importante.", FontFactory.getFont("arial black", 13, Font.BOLD, BaseColor.RED));
            p6.setAlignment(Element.ALIGN_CENTER);
            manual.add(p6);

            Paragraph d6 = new Paragraph(""
                    + "Para recuperar la preficha   ingrese a la siguiente liga  "+Constantes.HOME+" en el apartado de “Recuperar preficha” . Deberá contar con su clave CURP para  poder acceder.\n"
                    + "Verifique  que el folio de su  pase de ingreso al examen  de admisión (folio  CENEVAL)  y la FICHA PARA EL EXAMEN DE ADMISIÓN  en la Ficha de examen coincidan."
                    + "\nPresente  el examen de admisión y el examen de matemáticas  en la fecha, hora y el aula que se marca  en su  Ficha de examen.\n", FontFactory.getFont("arial black", 9, Font.BOLD, BaseColor.BLACK));
            d6.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d6);

            Paragraph d7 = new Paragraph("Durante el proceso de registro recibirá los siguientes correos, por favor permanezca al pendiente:\n"
                    + "1.-Correo de la liga para el  registro. (Etapa 1).\n"
                    + "2.-Correo de generación de preficha, que se le enviará al concluir el registro de sus datos. (Etapa 1)\n"
                    + "3.-Correo de liberación de pago después de haber realizado el pago bancario. (Etapa 2)\n"
                    + "4.-Correo de alta en Ceneval, después de haber recibido el correo de liberación de pago.\n"
                    + "    Es importante que reciba estos dos últimos correos. (Etapa 3 )\n"
                    + "5.-Correo de generación de ficha, que se enviará al concluir el proceso de registro, esto es después de haber entregado\n"
                    + "    sus papeles en el departamento de servicios escolares edif. X. (Etapa 5)\n", FontFactory.getFont("arial black", 9, Font.BOLD, BaseColor.BLACK));
            d7.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d7);

            String url_logo4 = "/Imagenes/footer_ittoluca_footer.png";
            String absolute_url_logo4 = d.getRealPath(url_logo4);
            Image itt_logo4 = Image.getInstance(absolute_url_logo4);
            Image Logo_itt4 = Image.getInstance(itt_logo4);
            Logo_itt4.setAbsolutePosition(30f, 10f);
            manual.add(Logo_itt4);

            manual.add(vacio);
            manual.add(vacio);
            manual.add(vacio);
            manual.add(vacio);
            manual.add(vacio);

              //We add a new page
            //manual.newPage();
            
             String url_logo_ = "/Imagenes/header_ittoluca_pdf.png";
            String absolute_url_logo_ = d.getRealPath(url_logo_);
            Image itt_logo_ = Image.getInstance(absolute_url_logo_);
            Image Logo_itt_ = Image.getInstance(itt_logo_);
            //Logo_itt_.setAbsolutePosition(30f, 770f);
            Logo_itt.setAbsolutePosition(30f, 770f);
            //manual.add(Logo_itt_);
            manual.add(Logo_itt);
            
            Paragraph d8 = new Paragraph(
                    "Importante: \n\n"
                    + "Para realizar cualquier cambio en los datos proporcionados deberá solicitarlo en ventanilla al"
                    + " momento de entregar su documentación en el departamento de Servicios Escolares Edif. X. Así mismo deberá recordar que no habrá cambios en  las opciones de carrera.\n\n"
                    + "Tome en cuenta que es responsabilidad del aspirante cumplir con todas etapas "
                    + "para finalizar su registro de lo contrario su solicitud será rechazada.", FontFactory.getFont("arial black", 11, Font.BOLD));
            d8.setAlignment(Element.ALIGN_JUSTIFIED);
            manual.add(d8);
           

            String manual_imagen = "/Imagenes/manual_imagen.png";
            //String manual_imagen = "/Imagenes/manual_imagen.PNG";
            String absolute_ur_lmanual_imagen = d.getRealPath(manual_imagen);
            Image itt_logo3 = Image.getInstance(absolute_ur_lmanual_imagen);
            Image Manual_imagen = Image.getInstance(itt_logo3);
            Manual_imagen.setAbsolutePosition(10f, 130f);
            manual.add(Manual_imagen);

            String url_logo2 = "/Imagenes/footer_ittoluca_footer.png";
            String absolute_url_logo2 = d.getRealPath(url_logo2);
            Image itt_logo2 = Image.getInstance(absolute_url_logo2);
            Image Logo_itt2 = Image.getInstance(itt_logo2);
            Logo_itt2.setAbsolutePosition(30f, 10f);
            manual.add(Logo_itt2);

            manual.addTitle("Manual  del Proceso de Registro para el Aspirante.");
            manual.addSubject("Instituto Tecnológico de Toluca");
            manual.addKeywords("Instituto Tecnológico de Toluca");
            manual.addAuthor("Departamento de Servicios escolares");
            manual.addCreator("Departamento de Servicios escolares");

            manual.close();
        } catch (DocumentException ex) {
            Logger.getLogger(servlets.PrefichaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaPrefichaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
