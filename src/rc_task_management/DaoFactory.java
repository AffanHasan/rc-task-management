/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management;

import rc_task_management.interfaces.AbstractDaoFactory;
import rc_task_management.interfaces.RCTaskManagementDao;

/**
 *
 * @author root
 */
class DaoFactory implements AbstractDaoFactory {
    
    private DaoFactory(){
    }

    @Override
    public RCTaskManagementDao getDAO(DaoType daoType) {
        RCTaskManagementDao dao = null;
        
        switch(daoType){
            case RDBMS:
                break;
            case MongoDB:
                break;
        }
        return dao;
    }
}