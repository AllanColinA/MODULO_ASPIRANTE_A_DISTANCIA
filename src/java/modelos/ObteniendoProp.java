/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ElyyzZ BaRruEtA
 */
public class ObteniendoProp {

    private String usuario;
    private String pass;
    private String BaseDatos;
    private String ligaInicio;
    private String  ligaPreficha;
    private String encripta;

    public ObteniendoProp() {
        Seteando();
    }

    private void Seteando() {
        
        try {
            Properties pro = new Properties();
            InputStream entrada;
            entrada = new FileInputStream("C:\\Users\\Macedo\\Documents\\NetBeansProjects\\PAES_ASPIRANTE\\src\\java\\ConexionBD\\AspiranteConstantes.properties");
            pro.load(entrada);
            setUsuario(pro.getProperty("usuario"));
            setPass(pro.getProperty("password"));
            setBaseDatos(pro.getProperty("BaseDatos"));
            setLigaInicio(pro.getProperty("liga"));
            setLigaPreficha(pro.getProperty("ligaPreficha"));
            setEncripta(pro.getProperty("ClaveEncripta"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ObteniendoProp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObteniendoProp.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public static void main(String[] args) {
        ObteniendoProp d=new ObteniendoProp();
        System.out.println(d.getBaseDatos());
        System.out.println(d.getPass());
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @return the BaseDatos
     */
    public String getBaseDatos() {
        return BaseDatos;
    }


    /**
     * @return the encripta
     */
    public String getEncripta() {
        return encripta;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @param BaseDatos the BaseDatos to set
     */
    public void setBaseDatos(String BaseDatos) {
        this.BaseDatos = BaseDatos;
    }



    /**
     * @param encripta the encripta to set
     */
    public void setEncripta(String encripta) {
        this.encripta = encripta;
    }

    /**
     * @return the ligaInicio
     */
    public String getLigaInicio() {
        return ligaInicio;
    }

    /**
     * @param ligaInicio the ligaInicio to set
     */
    public void setLigaInicio(String ligaInicio) {
        this.ligaInicio = ligaInicio;
    }

    /**
     * @return the ligaPreficha
     */
    public String getLigaPreficha() {
        return ligaPreficha;
    }

    /**
     * @param ligaPreficha the ligaPreficha to set
     */
    public void setLigaPreficha(String ligaPreficha) {
        this.ligaPreficha = ligaPreficha;
    }

}
