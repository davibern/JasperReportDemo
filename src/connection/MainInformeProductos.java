package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author davibern
 * @version 1.0
 */
public class MainInformeProductos {
    
    public static void main(String[] args) {
        
        // Variables para obtener la plantilla y exportarla en html
        String reportSource = "./report/templates/DatosProductos.jrxml";
        String reportOutHTML = "./report/results/DatosProductos.html";
        
        // Mapa de parametrización de opciones del informe
        Map<String, Object> params = new HashMap<>();
        params.put("reportTitle", "Informe de productos de Derby [APP]");
        params.put("author", "Davibern");
        params.put("startDate", new Date().toString());
        
        try {
            
            // Creación del informe en base a la plantilla
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            
            // Conectamos el driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Conectamos a la base de datos
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            
            // Configuramos el informe de salida
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            
            // Exportamos el fichero
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportOutHTML);
            
            // Se visualiza
            JasperViewer.viewReport(jasperPrint);
            
            // Se cierra la conexión
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainInformeProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainInformeProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(MainInformeProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
