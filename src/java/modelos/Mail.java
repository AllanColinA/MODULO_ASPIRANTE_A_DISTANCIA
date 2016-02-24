package modelos;

import beans.BMail;

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

public class Mail {

    MimeMultipart multipart = new MimeMultipart("related"); //Para agrgar imagenes de header/footer 

    public boolean sendMail(BMail beanMail, String from, String to, String pass, boolean link, String asunto) {
        boolean retorno = false;
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");
        String host = "mail.ittoluca.edu.mx"; //itt
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);

            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(asunto);
            message.setText("Cuerpo");

            if (link == true) {
                message.setText(beanMail.getCuerpo(), "ISO-8859-1", "html");
            } else {

                String nom = beanMail.getNombre();
                String corr = beanMail.getCorreo();
                String mens = beanMail.getTexto();

                String mensaje = "Enviando un correo\n\n"
                        + "Nombre: " + nom + "\n"
                        + "Correo: " + corr + "\n\n"
                        + "Mensaje:\n" + mens + "\n";

                message.setText(mensaje);

            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);

            try {
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                retorno = true;
            } catch (MessagingException ex) {
                System.out.println("AUDITORIA ERROR AL ENVIAR CORREO:  " + ex);
                retorno = false;
            }
        } catch (MessagingException me) {
            me.printStackTrace();
            retorno = false;
        }
        return retorno;
    }

    public int sendMail(BMail beanMail, String to, boolean link, String asunto) {
        int retorno = 0;
        String from = "aspirantes@ittoluca.edu.mx";
        String pass = "11280672";
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");
        String host = "mail.ittoluca.edu.mx"; //itt
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);

            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(asunto);
            message.setText("Cuerpo");

            if (link == true) {
                message.setText(beanMail.getCuerpo(), "ISO-8859-1", "html");
            } else {

                String nom = beanMail.getNombre();
                String corr = beanMail.getCorreo();
                String mens = beanMail.getTexto();

                String mensaje = "Enviando un correo\n\n"
                        + "Nombre: " + nom + "\n"
                        + "Correo: " + corr + "\n\n"
                        + "Mensaje:\n" + mens + "\n";
                System.out.println("Enviando mensaje: " + mensaje);

                message.setText(mensaje);

            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);

            try {
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                retorno = 0;
            } catch (MessagingException ex) {
                System.out.println("AUDITORIA ERROR AL ENVIAR CORREO *******" + ex);
                retorno = -1;
            }
        } catch (MessagingException me) {
            System.out.println("Messaging Exception" + me.getMessage());
            me.printStackTrace();
            retorno = -2;
        }
        return retorno;
    }

    public int sendMailImg(BMail beanMail, ServletContext context, String to, boolean link, String asunto) throws Exception {
        int retorno = 0;

        String from = "aspirantes@ittoluca.edu.mx";
        String pass = "11280672";
        Properties props = System.getProperties();
        props.setProperty("mail.mime.charset", "ISO-8859-1");
        String host = "mail.ittoluca.edu.mx"; //itt
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.localhost", "sia.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "mail.ittoluca.edu.mx");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth", "true");

        String header = "<html> "
                + "<head></head>"
                + "<header style =\"position: relative;\">"
                + "<img src=\"cid:cidheader\" /> "
                + "</header>"
                + "<body>";
        String footer = "</body>"
                + "<footer style =\"position: relative;\">"
                + "<img src=\"cid:cidfooter\"/>"
                + " </footer>";
        String content = String.format("%s%s%s%s%s", header, "<br>", beanMail.getCuerpo(), "<br>", footer);
        addContent(content);

        String url_head = "/Imagenes/footer_ittoluca.png";
        String real_url_head = context.getRealPath(url_head);
        String url_foot = "/Imagenes/header_ittoluca.png";
        String real_url_foot = context.getRealPath(url_foot);

        addCID("cidheader", real_url_head);
        addCID("cidfooter", real_url_foot);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);

            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(asunto);
           // message.setText("Cuerpo");

            if (link == true) {

                //message.setText(beanMail.getCuerpo(), "ISO-8859-1", "html");
                message.setContent(multipart);
            } else {

                String nom = beanMail.getNombre();
                String corr = beanMail.getCorreo();
                String mens = beanMail.getTexto();

                String mensaje = "Enviando un correo\n\n"
                        + "Nombre: " + nom + "\n"
                        + "Correo: " + corr + "\n\n"
                        + "Mensaje:\n" + mens + "\n";
                System.out.println("Enviando mensaje: " + mensaje);

                message.setContent(multipart);

            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);

            try {
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                retorno = 0;
            } catch (MessagingException ex) {
                System.out.println("AUDITORIA ERROR AL ENVIAR CORREO *******" + ex);
                retorno = -1;
            }
        } catch (MessagingException me) {
            System.out.println("Messaging Exception" + me.getMessage());
            me.printStackTrace();
            retorno = -2;
        }
        return retorno;
    }

    void addContent(String htmlText) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlText, "text/hmtl");
        this.multipart.addBodyPart(messageBodyPart);
    }

    void addCID(String cidname, String path) throws Exception {
        DataSource fds = new FileDataSource(path);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<" + cidname + ">");
        this.multipart.addBodyPart(messageBodyPart);
    }

    public static void main(String[] args) {
        BMail beanMail = new BMail();
        beanMail.setCuerpo("Este es un correo de verificación. Por favor haga click en el siguiente enlace\n"
                + "para que pueda continuar con su registro."
                + "<a href=" + ">Enlace</a>");
        Mail m = new Mail();
        int ret = m.sendMail(beanMail, "creyesman@hotmail.com", true, "Correo de Registro Aspirante");
        System.out.println("ret" + ret);
    }
}
