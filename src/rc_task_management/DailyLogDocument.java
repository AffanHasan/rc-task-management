/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management;

import rc_task_management.interfaces.AbstractDayLogDocument;

/**
 *
 * @author root
 */
public class DailyLogDocument extends AbstractDayLogDocument {

    @Override
    public void flush() {
        DaoFactory.getInstance().getDao().persistDayLogDocument(document);
    }
}
