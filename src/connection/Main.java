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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Directorio de la plantilla y de la salida del informe
        String reportSource = "./report/templates/DatosClientes.jrxml";
        String reportOutHTML = "./report/results/DatosClientes.html";
        
        // Mapa de parametrización de opciones del informe
        Map<String, Object> params = new HashMap<>();
        
        params.put("reportTitle", "Informe de clientes de Derby [APP]");
        params.put("author", "Davibern");
        params.put("startDate", (new Date().toString()));
        
        try {
            // Creación del informe en base a la plantilla
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            // Conexión con derby
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            // Configuración del informe y salida estándar
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportOutHTML);
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
