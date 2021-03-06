
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

/**
 *
 * @author Rocio
 */
public class ClaseEnviarCorreo {

    private MimeMultipart multipart = new MimeMultipart("related");
    private String origen = "aspirantes@ittoluca.edu.mx";
    private String contrasenia = "11280672";
    private String correo;
    private String asuntoIni = "Aspirante  Tecnológico de Toluca: Liga Para Registro.";
    private String asuntoPre = "Aspirante  Tecnológico de Toluca: Generar Preficha";
    private String cuerpo;
    private String error = "correcto";

    /**
     * 
     * @param context : para integrar las imagenes alojadas en el directorio del aplicativo
     * @param sendTo: Destinatario (correo aspirante)
     * @param cuerpo: Cuerpo del mensaje
     * @param asunto: Asunto del correo  
     *                1 -> El primer correo que envia la liga de registro.
     *                2 -> Liga para generar preficha
     * @throws Exception: Alguna razon por la que no sepudo enviar el correo
     */
    public void sendFromMail(ServletContext context, String sendTo, String cuerpo, int asunto) throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");

        String host = "mail.ittoluca.edu.mx"; //itt

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", origen);
        props.put("mail.smtp.password", contrasenia);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        String cabecera = "<html>\n"
                + "    <head>\n"
                + "      \n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <header style=\"position: relative;left:80px;\">\n"
                + "        <img src=\"cid:cidcabecera\" />\n"
                + "        <pre style=\"font-family:'calibri'; font-size: 16px;\">\n"
                + "            Instituto Tecnológico de Toluca\n"
                + "        </pre>\n"
                + "    </header>\n"
                + "    <body >";

        String pie = " </body>\n"
                + "    <footer  style=\"position: relative;\" >\n"
                + "         <img src=\"cid:cidpie\" />\n"
                + "    </footer>\n"
                + "</html>";
        String content = String.format("%s%s%s%s%s", cabecera, "<br/>", cuerpo, "<br/>", pie);
        addContent(content);

        String url_cab = "/Imagenes/header_ittoluca.png";
        String cab_img = context.getRealPath(url_cab);
        String url_pie = "/Imagenes/footer_ittoluca.png";
        String pie_img = context.getRealPath(url_pie);

        addCID("cidcabecera", cab_img);
        addCID("cidpie", pie_img);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(origen));
            InternetAddress toAddress = new InternetAddress(sendTo);


            message.addRecipient(Message.RecipientType.TO, toAddress);

            if( asunto == 1){
            message.setSubject(asuntoIni);
            }else{
                message.setSubject(asuntoPre);
            }
            
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, origen, contrasenia);
            try {

                transport.sendMessage(message, message.getAllRecipients());

                transport.close();
            } catch (MessagingException ex) {
                error = ex.getMessage();
                System.out.println(ex.getCause());
            }

        } catch (MessagingException me) {
            me.printStackTrace();
            error = me.getMessage();
            System.out.println(me.getMessage());
        }

    }

    /**
     * 
     * @param context: Contexto del servlet para integrar las imagenes
     * @param sendTo: Destinatario del correo (aspirante)
     * @param cuerpo: Cuerp del mensaje
     * @param asunto: Asunto del correo  
     *                1 -> El primer correo que envia la liga de registro.
     *                2 -> Liga para generar preficha
     * @return: Codigo de error/informacion del envio de correo
     * @throws Exception: Cualquier causa por la que no se enviara el correo.
     */
    public int sendMail(ServletContext context, String sendTo, String cuerpo, int asunto) throws Exception {
        int retorno = 0;
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");

        String host = "mail.ittoluca.edu.mx"; //itt

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", origen);
        props.put("mail.smtp.password", contrasenia);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        String cabecera = "<html>\n"
                + "    <head>\n"
                + "      \n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <header style=\"position: relative;left:80px;\">\n"
                + "        <img src=\"cid:cidcabecera\" />\n"
                + "        <pre style=\"font-family:'calibri'; font-size: 16px;\">\n"
                + "            Instituto Tecnológico de Toluca\n"
                + "        </pre>\n"
                + "    </header>\n"
                + "    <body >";

        String pie = " </body>\n"
                + "    <footer  style=\"position: relative;\" >\n"
                + "         <img src=\"cid:cidpie\" />\n"
                + "    </footer>\n"
                + "</html>";
        String content = String.format("%s%s%s%s%s", cabecera, "<br/>", cuerpo, "<br/>", pie);
        addContent(content);

        String url_cab = "/Imagenes/header_ittoluca.png";
        String cab_img = context.getRealPath(url_cab);
        String url_pie = "/Imagenes/footer_ittoluca.png";
        String pie_img = context.getRealPath(url_pie);

        addCID("cidcabecera", cab_img);
        addCID("cidpie", pie_img);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(origen));
            InternetAddress toAddress = new InternetAddress(sendTo);


            message.addRecipient(Message.RecipientType.TO, toAddress);

            if( asunto == 1){
            message.setSubject(asuntoIni);
            }else{
                message.setSubject(asuntoPre);
            }
            
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, origen, contrasenia);
            try {

                transport.sendMessage(message, message.getAllRecipients());

                transport.close();
				retorno = 0;
            } catch (MessagingException ex) {
                error = ex.getMessage();
                System.out.println(ex.getCause());
				retorno = -1;
            }

        } catch (MessagingException me) {
            me.printStackTrace();
            error = me.getMessage();
            System.out.println(me.getMessage());
			retorno = -2;
        }
        return retorno;
    }
    
    
    public boolean sendMailFin(ServletContext context, String sendTo, String cuerpo, int asunto) throws Exception {
        boolean retorno = false;
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");

        String host = "mail.ittoluca.edu.mx"; //itt

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", origen);
        props.put("mail.smtp.password", contrasenia);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        String cabecera = "<html>\n"
                + "    <head>\n"
                + "      \n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <header style=\"position: relative;left:80px;\">\n"
                + "        <img src=\"cid:cidcabecera\" />\n"
                + "        <pre style=\"font-family:'calibri'; font-size: 16px;\">\n"
                + "            Instituto Tecnológico de Toluca\n"
                + "        </pre>\n"
                + "    </header>\n"
                + "    <body >";

        String pie = " </body>\n"
                + "    <footer  style=\"position: relative;\" >\n"
                + "         <img src=\"cid:cidpie\" />\n"
                + "    </footer>\n"
                + "</html>";
        String content = String.format("%s%s%s%s%s", cabecera, "<br/>", cuerpo, "<br/>", pie);
        addContent(content);

        String url_cab = "/Imagenes/header_ittoluca.png";
        String cab_img = context.getRealPath(url_cab);
        String url_pie = "/Imagenes/footer_ittoluca.png";
        String pie_img = context.getRealPath(url_pie);

        addCID("cidcabecera", cab_img);
        addCID("cidpie", pie_img);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(origen));
            InternetAddress toAddress = new InternetAddress(sendTo);


            message.addRecipient(Message.RecipientType.TO, toAddress);

            if( asunto == 1){
            message.setSubject(asuntoIni);
            }else{
                message.setSubject(asuntoPre);
            }
            
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, origen, contrasenia);
            try {

                transport.sendMessage(message, message.getAllRecipients());

                transport.close();
				retorno = true;
            } catch (MessagingException ex) {
                error = ex.getMessage();
                System.out.println(ex.getCause());
				retorno = false;
            }

        } catch (MessagingException me) {
            me.printStackTrace();
            error = me.getMessage();
            System.out.println(me.getMessage());
			retorno = false;
        }
        return retorno;
    }
    
    
    public void addContent(String htmlText) throws Exception {
        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        this.multipart.addBodyPart(messageBodyPart);
    }

    public void addCID(String cidname, String pathname) throws Exception {
        DataSource fds = new FileDataSource(pathname);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<" + cidname + ">");
        this.multipart.addBodyPart(messageBodyPart);
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the cuerpo
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * @param cuerpo the cuerpo to set
     */
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
}
