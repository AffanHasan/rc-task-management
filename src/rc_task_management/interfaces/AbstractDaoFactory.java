/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management.interfaces;

/**
 *
 * @author Affan Hasan
 */
public interface AbstractDaoFactory {
    
    public enum DaoType{
        RDBMS,
        MongoDB
    }
    
    /**
     * @param daoType
     * @return <b>RCTaskManagementDao</b>
     */
    public RCTaskManagementDao getDAO(DaoType daoType) throws IllegalArgumentException;
}