/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management.interfaces;

/**
 * <p>
 * A Dao Factory is a <b>singleton</b>. It has factory method named <b>getInstance</b></p>
 *
 * @author Affan Hasan
 */
public abstract class AbstractDaoFactory {

    public enum DaoType {

        RDBMS,
        MongoDB
    }
    
    protected static final DaoType persistenceEngine = DaoType.RDBMS;

    public abstract AbstractDao getDao();
}