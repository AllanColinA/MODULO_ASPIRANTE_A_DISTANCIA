/*
 * Método para la cración de un archivo PDF para generar la Preficha de registro
 */
package ConexionBD;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import modelos.GeneraAuditoria;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author ElyyzZ BaRruEtA
 */
/**
 * **
 * Se cambio el formato del pdf cuando existe un error en la búsqueda de la curp
 * Se agregó el la fecha de emisión, antes solo tenia la fecha actual *
 */
public class CreaPrefichaPDF {

    public void ElaboraPreficha(String curp, HttpServletResponse response, ServletContext d) {
        String fichabd = null;
        String periodobd = null;
        String fechapdf = null;
        String prefichabd = null;
        String nombrebd = null;
        String apellidosbd = null;
        String curpbd = null;
        String carrerabd = null;
        String modalidadbd = null;
        String ref_bancaria = null;
        String importe_bd = null;
        String fechaEm = null;
//        String fechalim = null;

        String user = "ASPIRANTE_ITT";
        String pass = "ASP1R4NT3";
        CallableStatement cst = null;

//creando el pdf
        try {
            Document preficha = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(preficha, baos);
            PdfWriter writer = PdfWriter.getInstance(preficha, baos);

            preficha.open();
            String resultado_error = "";
            Conexion con = new Conexion(user, pass);

            cst = con.getConnection().prepareCall("{call FICHAS.PQ_GET_ASPIRANTE_2.GET_RECUPERAR_PREFICHA_ASP_SP(?,?,?,?)}");
            cst.setString("paCurpAspirante", curp);
            cst.registerOutParameter("paCurRetorno", OracleTypes.CURSOR);
            cst.registerOutParameter("paCodigoError", OracleTypes.NUMBER);
            cst.registerOutParameter("paMjeDescError", OracleTypes.VARCHAR);
            cst.execute();
            resultado_error = cst.getString("paCodigoError");
            if ("0".equals(resultado_error)) {
                ResultSet rset = (ResultSet) cst.getObject("paCurRetorno");
                nombrebd = rset.getString("NOMBRE");
                while (rset.next()) {
                    nombrebd = rset.getString("NOMBRE");
                    if ("".equals(nombrebd) || nombrebd == null) {
                    } else {
                        String appat = rset.getObject("APELLIDO_PAT").toString();
                        String apmat = rset.getObject("APELLIDO_MAT").toString();
                        apellidosbd = appat + " " + apmat;
                        fichabd = rset.getObject("PREFICHA").toString();
                        carrerabd = rset.getObject("NOMBRE_CARRERA").toString();
                        prefichabd = rset.getObject("PREFICHA").toString();
                        periodobd = rset.getObject("PERIODO_CONSURSA").toString();
                        modalidadbd = rset.getObject("MODALIDAD").toString();
                        ref_bancaria = rset.getObject(11).toString();
                        importe_bd = rset.getObject("IMPORTE").toString();
                        fechaEm = rset.getObject("FECHA_EMISION").toString();
                        fechapdf = rset.getObject("FECHA_ACTUAL").toString();
//                        fechalim = rset.getObject("FECHA_LIMITE_PAGO").toString();
                        curpbd = curp;

                        /**
                         * Obtener correo y tipo de registro Tipo registro: 0 :
                         * El aspirante cuenta con referencia bancaria 1 : El
                         * aspirante no cuenta con referencia bancaria (esta en
                         * linea de espera)
                         */
                    }
                }
                cst.close();
                con.CerraConexion();
//encabezado
//encabezado
//encabezado
//encabezado
                Paragraph depto = new Paragraph("Departamento de servicios escolares", FontFactory.getFont("arial", 20, Font.BOLD));
                depto.setAlignment(Element.ALIGN_CENTER);
                preficha.add(depto);

                PdfContentByte rectangulo_general = writer.getDirectContentUnder();
                rectangulo_general.rectangle(50, 48, 500, 710);
                rectangulo_general.fill();
                drawRectangleSC(rectangulo_general, 50, 48, 500, 710);

                Paragraph vacio = new Paragraph("  ", FontFactory.getFont("arial", 10, Font.BOLD));
                vacio.setAlignment(Element.ALIGN_CENTER);

                if ("".equals(nombrebd) || nombrebd == null) {

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph curpNoEncontrada = new Paragraph("                                                              Lo sentimos, no se encontraron "
                            + "                                                                                coincidencias con su clave CURP.", FontFactory.getFont("arial", 14, Font.BOLD));
                    curpNoEncontrada.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(curpNoEncontrada);

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph curp_no = new Paragraph(curp, FontFactory.getFont("arial", 19, Font.PLAIN));
                    curp_no.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(curp_no);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph lamenta = new Paragraph(""
                            + "El deparamento de servicios escolares lamenta los inconvenientes ocurridos al intentar recuperar su preficha."
                            + "", FontFactory.getFont("arial", 19, Font.BOLD));
                    lamenta.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(lamenta);

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph se_le_aconseja = new Paragraph("           RECOMENDACIONES", FontFactory.getFont("arial", 14, Font.BOLD));
                    se_le_aconseja.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(se_le_aconseja);

                    Paragraph msjCurp = new Paragraph("\n"
                            + "              - Le aconsejamos revisar su CURP, ya que sin esta, no podrá recuperar su preficha.\n"
                            + "              - Si el problema continúa, acuda con esta hoja al departamento de SERVICIOS ESCOLARES (Edif.\n"
                            + "                X) de lunes a viernes de 9:00 a 18:00 horas, de lo contrario \n"
                            + "                haga su registro.\n"
                            + "              - Revise que en el proceso de registro cada paso se haya terminado correctamente\n"
                            + "              - Revise el manual de proceso de registro que se encuentra en la página www.ittoluca.edu.mx\n"
                            + "              - Revise el apartado de preguntas frecuentes que se encuentra en la página www.ittoluca.edu.mx\n"
                            + "              - En la sección de contacto, se encuentran el teléfono de contacto y la extensión.\n"
                            + "              - Otra alternativa es enviar un correo exponiendo su situación al departamento de servicios \n"
                            + "                escolares."
                            + "\n"
                            + ""
                            + "", FontFactory.getFont("arial", 10, Font.BOLD));
                    msjCurp.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(msjCurp);

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph no_comprobante = new Paragraph(""
                            + "Este documento carece de validéz oficial, su función es servir como medio de comunicación.", FontFactory.getFont("arial", 8, Font.PLAIN, BaseColor.RED));
                    no_comprobante.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(no_comprobante);

//                    preficha.add(vacio);
                    String url_logo = "/Imagenes/itt_logo_opt.jpg";
                    String absolute_url_logo = d.getRealPath(url_logo);
                    Image itt_logo = Image.getInstance(absolute_url_logo);

                    Image Logo_itt = Image.getInstance(itt_logo);
                    Logo_itt.setAbsolutePosition(140f, 640f);
                    preficha.add(Logo_itt);
                } else {

                    preficha.add(vacio);
                    preficha.add(vacio);
//cuerpo
//cuerpo
//cuerpo
//cuerpo
//cuerpo
                    Paragraph periodo_text = new Paragraph("Convocatoria de nuevo ingreso periodo: " + periodobd, FontFactory.getFont("arial", 10, Font.BOLD));
                    periodo_text.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(periodo_text);

                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph fotografia = new Paragraph("", FontFactory.getFont("arial", 10, Font.BOLD));
                    fotografia.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(fotografia);
                    preficha.add(vacio);
                    String url_logo = "/Imagenes/itt_logo_opt.jpg";
                    String absolute_url_logo = d.getRealPath(url_logo);
                    Image itt_logo = Image.getInstance(absolute_url_logo);

                    Image Logo_itt = Image.getInstance(itt_logo);
                    Logo_itt.setAbsolutePosition(260f, 640f);
                    preficha.add(Logo_itt);

                    PdfContentByte rectangulo_periodo = writer.getDirectContentUnder();
                    rectangulo_periodo.rectangle(125, 725, 350, 20);
                    rectangulo_periodo.fill();
                    drawRectangleSC(rectangulo_periodo, 125, 725, 350, 20);

                    String url_logo_bnmx = "/Imagenes/bnmx_color_opt.jpg";
                    String absolute_url_logo_bnmx = d.getRealPath(url_logo_bnmx);
                    Image bnmx_logo = Image.getInstance(absolute_url_logo_bnmx);

                    Image Logo_banco = Image.getInstance(bnmx_logo);
                    Logo_banco.setAbsolutePosition(380f, 310f);
                    preficha.add(Logo_banco);
//rectangulo de la  fotografia
//                    PdfContentByte espacio_imagen = writer.getDirectContentUnder();
//                    espacio_imagen.rectangle(255, 635, 85, 80);
//                    espacio_imagen.fill();
//                    drawRectangleSC(espacio_imagen, 255, 635, 85, 80);

                    preficha.add(vacio);

                    PdfContentByte fechaimpr = writer.getDirectContentUnder();
                    fechaimpr.rectangle(416, 635, 100, 35);
                    fechaimpr.fill();
                    drawRectangleSC(fechaimpr, 416, 635, 100, 35);

                    Paragraph fechapdf_impr = new Paragraph("\tFecha de impresión                ", FontFactory.getFont("arial", 10, com.itextpdf.text.Font.BOLD));
                    fechapdf_impr.setAlignment(Element.ALIGN_RIGHT);
                    preficha.add(fechapdf_impr);

                    Paragraph fechapdf_fec = new Paragraph("\t" + fechapdf + "                          ", FontFactory.getFont("arial", 10, com.itextpdf.text.Font.BOLD));
                    fechapdf_fec.setAlignment(Element.ALIGN_RIGHT);
                    preficha.add(fechapdf_fec);

                    preficha.add(vacio);

                    Paragraph no_preficha = new Paragraph("Preficha N°: " + prefichabd, FontFactory.getFont("arial", 20, Font.BOLD));
                    no_preficha.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(no_preficha);

                    preficha.add(vacio);

                    PdfContentByte rectangulo_preficha_no = writer.getDirectContentUnder();
                    rectangulo_preficha_no.rectangle(85, 590, 430, 25);
                    rectangulo_preficha_no.fill();
                    drawRectangleSC(rectangulo_preficha_no, 85, 590, 430, 25);

                    PdfContentByte rectangulo_datos = writer.getDirectContentUnder();
                    rectangulo_datos.rectangle(85, 480, 430, 105);
                    rectangulo_datos.fill();
                    drawRectangleSC(rectangulo_datos, 85, 480, 430, 105);

                    Paragraph nombre = new Paragraph("                                                                              Nombre:   " + nombrebd,
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    nombre.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(nombre);

                    Paragraph apellidos = new Paragraph("                                                                                               " + apellidosbd,
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    apellidos.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(apellidos);

                    Paragraph CURP = new Paragraph("                                                                                 CURP:   " + curpbd,
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    CURP.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(CURP);

                    Paragraph carrera = new Paragraph("Carrera Solicitada:",
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    carrera.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(carrera);

                    Paragraph Nomcarrera = new Paragraph(carrerabd,
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    Nomcarrera.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(Nomcarrera);

                    Paragraph modalidad = new Paragraph("                                                                          Modalidad:   " + modalidadbd,
                            FontFactory.getFont("arial", 10, Font.BOLD));
                    modalidad.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(modalidad);

                    preficha.add(vacio);
//                    preficha.add(vacio);

                    Paragraph formatoBanamex = new Paragraph("\nFORMATO UNIVERSAL PARA DEPÓSITOS EN SUCURSALES BANAMEX", FontFactory.getFont("arial", 10, Font.BOLD));
                    formatoBanamex.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(formatoBanamex);

                    PdfContentByte rectanguloDepositoB = writer.getDirectContentUnder();
                    rectanguloDepositoB.rectangle(85, 440, 430, 20);
                    rectanguloDepositoB.fill();
                    drawRectangle(rectanguloDepositoB, 85, 440, 430, 20);

                    PdfContentByte rectanguloPago = writer.getDirectContentUnder();
                    rectanguloPago.rectangle(85, 250, 430, 190);
                    rectanguloPago.fill();
                    drawRectangleSC(rectanguloPago, 85, 250, 430, 190);

                    preficha.add(vacio);

                    PdfContentByte rectanguloConcepto = writer.getDirectContentUnder();
                    rectanguloConcepto.rectangle(150, 395, 295, 35);
                    rectanguloConcepto.fill();
                    drawRectangleSC(rectanguloConcepto, 150, 395, 295, 35);

                    Paragraph formatoConceptoPre = new Paragraph("CONCEPTO: PAGO DE DERECHO A EXAMEN DE ADMISIÓN", FontFactory.getFont("arial", 10, Font.BOLD));
                    formatoConceptoPre.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(formatoConceptoPre);
                    Paragraph fechaEmision = new Paragraph("FECHA LÍMITE DE PAGO: ", FontFactory.getFont("arial", 10, Font.BOLD));
                    fechaEmision.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(fechaEmision);

                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph importe = new Paragraph("IMPORTE A PAGAR: $" + importe_bd + ".°°", FontFactory.getFont("arial", 15, Font.BOLD));
                    importe.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(importe);

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

//                    String ref = "44353452342353464765634523434";
                    String ref = ref_bancaria;

                    Paragraph referencia = new Paragraph("                                                   REFERENCIA (B): " + ref, FontFactory.getFont("arial", 10, Font.BOLD));
                    referencia.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(referencia);

                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph atencion = new Paragraph("Atención", FontFactory.getFont("arial", 15, Font.BOLD));
                    atencion.setAlignment(Element.ALIGN_CENTER);
                    preficha.add(atencion);

                    PdfContentByte rectangulo_atencion = writer.getDirectContentUnder();
                    rectangulo_atencion.rectangle(245, 198, 100, 25);
                    rectangulo_atencion.fill();
                    drawRectangle(rectangulo_atencion, 245, 198, 100, 25);

                    PdfContentByte rectangulo_info = writer.getDirectContentUnder();
                    rectangulo_info.rectangle(85, 60, 430, 100);
                    rectangulo_info.fill();
                    drawRectangle(rectangulo_info, 85, 60, 430, 120);

                    preficha.add(vacio);
                    preficha.add(vacio);

                    Paragraph informacion = new Paragraph(
                            "                        Para continuar con el proceso de preinscripción deberá:\n"
                            + "                           - Realizar el pago para su examen de admisión con la \"REFERENCIA\" que aparece\n"
                            + "                             en este documento en cualquier sucursal BANAMEX.\n"
                            + "                           - Recibir la notificación en su correo electrónico y estar al pendiente de \n"
                            + "                             las notificaciones que serán enviadas al mismo de que el pago ya fue procesado \n"
                            + "                             para completar su proceso de preinscripción.\n", FontFactory.getFont("arial", 10, Font.BOLD));
                    informacion.setAlignment(Element.ALIGN_LEFT);
                    preficha.add(informacion);
//  pie
//  pie
//  pie
//  pie
//  pie

                    preficha.addTitle("Preficha");
                    preficha.addSubject("Instituto Tecnológico de Toluca");
                    preficha.addKeywords("Instituto Tecnológico de Toluca");
                    preficha.addAuthor("Departamento de Servicios escolares");
                    preficha.addCreator("Departamento de Servicios escolares");
                }
                preficha.close();

            } else if (!"0".equals(resultado_error)) {
                GeneraAuditoria ob = new GeneraAuditoria();
                ob.crea_archivo(resultado_error, "No se logró crear la preficha", "Error al generar la Preficha.");
                System.out.println(resultado_error);
            }
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            try (OutputStream os = response.getOutputStream()) {
                baos.writeTo(os);
                os.flush();
            }
        } catch (ClassNotFoundException | SQLException | DocumentException ex) {
            Logger.getLogger(servlets.PrefichaPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaPrefichaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void drawRectangle(PdfContentByte content, float x, float y, float width, float height) {
        content.saveState();
        PdfGState state = new PdfGState();
        content.setGState(state);
        content.setRGBColorFill(232, 232, 232);
        content.setColorStroke(BaseColor.BLUE);
        content.setLineWidth((float) .5);
        content.rectangle(x, y, width, height);
        content.fillStroke();
        content.restoreState();
    }

    public static void drawRectangleSC(PdfContentByte content, float x, float y, float width, float height) {
        content.saveState();
        PdfGState state = new PdfGState();
        content.setGState(state);
        content.setRGBColorFill(0xFF, 0xFF, 0xFA);
        content.setColorStroke(BaseColor.BLUE);
        content.setLineWidth((float) .5);
        content.rectangle(x, y, width, height);
        content.fillStroke();
        content.restoreState();
    }

    public static void drawRectangleText(PdfContentByte content, float x, float y, float width, float height) {
        content.saveState();
        PdfGState state = new PdfGState();
        content.setGState(state);
        content.setRGBColorFill(0, 230, 255);
        content.setColorStroke(BaseColor.BLUE);
        content.setLineWidth((float) .5);
        content.rectangle(x, y, width, height);
        content.fillStroke();
        content.restoreState();
    }

    public static String fecha_hoy() {
        Calendar fec = new GregorianCalendar();
        int año = fec.get(Calendar.YEAR);
        int mes = fec.get(Calendar.MONDAY);
        int dia = fec.get(Calendar.DAY_OF_MONTH);
        String fech = dia + "/" + (mes + 1) + "/" + año;
        System.out.println("Fecha  de  hoy: " + fech);
        return fech;
    }
}
