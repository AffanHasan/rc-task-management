/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management;

import rc_task_management.interfaces.AbstractDaoFactory;
import rc_task_management.interfaces.AbstractDao;

/**
 * It is a <b>singleton</b> and it has a a factory method named <b>getInstance</b>
 * @author root
 */
class DaoFactory extends AbstractDaoFactory {
    
    private DaoFactory(){
    }
    
    /**
     This class is to make <b>DaoFactory</b> a singleton
     */
    private static class DaoFactoryHolder{
        static DaoFactory instance = new DaoFactory();
    }
    
    public static DaoFactory getInstance(){
        return DaoFactoryHolder.instance;
    }
    
    @Override
    public AbstractDao getDao(){
        switch(persistenceEngine){
            case RDBMS:
                return DaoRdbms.getInstance();
            default :
                return null;
        }
    }
}