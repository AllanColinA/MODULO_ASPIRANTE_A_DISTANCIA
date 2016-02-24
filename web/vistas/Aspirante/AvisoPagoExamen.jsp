<%-- 
    Document   : AvisoPagoExamen
    Created on : 29/09/2015, 10:44:11 AM
    Author     : Chris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Estilos/css/bootstrap.min.css.css" type="text/css"> 
        <link rel="stylesheet" href="Estilos/PAES_css.css" media="all" type="text/css">
    </head>
    <body>
        <div id="divAvisoPago" class="modal fade" role="dialog" >
            <div class="modal-dialog">
                
                <div class="modal-content">
                    <div class="modal-header">
                        <button id="btnXPagoExa" type="button" class="close">&times;</button>
                        <h4>Aviso</h4>
                    </div>
                    <div class="modal-body">
                        <label id="centrar_inf">
                                <!--Es imperativo que el aspirante reflexione si realmente efectuar&aacute; el pago,
                                ya que s&oacute;lo contar&aacute; con dos d&iacute;as h&aacute;biles posteriores a
                                su registro para efectuar el pago. -->
                                Es indispensable que el aspirante reflexione si realmente su inscripción se realizará
                                en el programa de Educación a Distancia.
                            </label>
                       
                    </div>
                    <div class="modal-footer">
                        <button id="btnAvisoPagoCancel" class="btn btn-default" >Cancelar</button>
                        <button id="btnAvisoPagoAcep" class="btn btn-info btn-primary" style="background-color: #337ab7">Aceptar</button>
                    </div>
                </div>
                
            </div>
        </div>
    </body>
</html>
<script src="${pageContext.request.contextPath}/JQueryClases/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/JQueryClases/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/JQueryClases/PAES_js.js" type="text/javascript"></script>