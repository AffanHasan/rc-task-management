/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management;

import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rc_task_management.interfaces.AbstractDao;

/**
 * A singleton with static factory method <b>getInstance()</b>
 * @author root
 */
class DaoRdbms extends AbstractDao {

    private DaoRdbms() {
        this.initPersistenceEngine();
    }
    
    private Connection connection;

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        connection.close();
    }
    
    /**
     * This class is to make <b>DaoRdbms</b> a singleton
     */
    private static class DaoRdbmsHolder {

        static DaoRdbms instanceHolder = new DaoRdbms();
    }

    @Override
    protected boolean initPersistenceEngine() {
        try {
            connection = DriverManager.getConnection("jdbc:derby:rc_task_management;create=true");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DaoRdbms.class.getName()).log(Level.SEVERE, "Unable to start Java db", ex);
            return false;
        }
    }

    @Override
    public boolean makeTodayEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean persistDayLogDocument(JsonObject dayLogDocument) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Factory method will return a singleton object of <b>DaoRdbms</b>
     * @return 
     */
    public static DaoRdbms getInstance() {
        return DaoRdbmsHolder.instanceHolder;
    }
}
