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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import rc_task_management.interfaces.AbstractDao;

/**
 * A singleton with static factory method <b>getInstance()</b>
 * @author root
 */
class DaoRdbms extends AbstractDao {

    private DaoRdbms() {
        try {
            connection = DriverManager.getConnection("jdbc:derby:rc_task_management;create=true");
            this.initPersistenceEngine();
        } catch (SQLException ex) {
            Logger.getLogger(DaoRdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Statement statement = connection.createStatement();
//          Create table day_entry
            statement.execute("CREATE TABLE day_entry( date date )");
//          Create table task
            statement.execute("CREATE TABLE task( id int, day_entry_date date, text varchar(500), task_category_id int, task_status_id int, order_id int )");
//          Create table task_status_log
            statement.execute("CREATE TABLE task_status_log( task_id int, task_status_id int, time_stamp timestamp )");
//          Create table task_categories
            statement.execute("CREATE TABLE task_categories( category_id int, category_name varchar(255) )");
//          Create table task_status
            statement.execute("CREATE TABLE task_status( status_id int, status_name varchar(255) )");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DaoRdbms.class.getName()).log(Level.SEVERE, "", ex);
            return false;
        }
    }

    @Override
    public boolean makeTodayEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean persistDayLogDocument(JsonObject dayLogDocument) {
        try{
            java.sql.Date d;
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Factory method will return a singleton object of <b>DaoRdbms</b>
     * @return 
     */
    public static DaoRdbms getInstance() {
        return DaoRdbmsHolder.instanceHolder;
    }
}