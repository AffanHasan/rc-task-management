/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management.interfaces;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of this class Represents a <b>Day Log Document</b>
 *
 * @author root
 */
public abstract class AbstractDayLogDocument {

    /**
     * <p>
     * {</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp; <strong>date</strong> <strong>:</strong>
     * &quot;&quot;,</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;<strong> </strong><strong>tasks_list : </strong>[</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * {</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * <strong>order_id : </strong>1,</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * <strong>text</strong><strong> : </strong>&quot;&quot;,</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * <strong>task_category_id : </strong>1,</p>
     *
     * <p>
     * <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * task_status_id </strong>: 1</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * <strong>status_log : </strong>[</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * { <strong>task_status_id : </strong>1, <strong>time_stamp :</strong>
     * &quot;&quot; }</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * ]</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * }</p>
     *
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * ]</p>
     *
     * <p>
     * }</p>
     *
     */
    protected JsonObject document;

    protected AbstractDao dao;

    public AbstractDayLogDocument(){
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskJsonDocument
     */
    public void addTask(JsonObject taskJsonDocument) {
        try {
            this.document.get("tasks_list").getAsJsonArray().add(taskJsonDocument);
            this.flush();
        } catch (NullPointerException e) {
            getLogger().log(Level.INFO, "Cannot add task", e);
        }
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param orderId
     */
    public void removeTask(int orderId) {
        try {
            for (JsonElement task : this.document.getAsJsonArray("tasks_list")) {
                if (((JsonObject) task).get("order_id").getAsInt() == orderId) {
                    this.document.getAsJsonArray("tasks_list").remove(task);
                    this.flush();
                    return;
                }
            }
        } catch (Exception e) {
            this.getLogger().log(Level.INFO, "Cannot remove the task", e.toString());
        }
    }

    /**
     * Use this method to update a task's text.
     *
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskOrderId
     * @param text
     */
    public void setTaskText(int taskOrderId, String text) {
        try {
            this.getTaskDocument(taskOrderId).addProperty("text", text);
            this.flush();
        } catch (NullPointerException e) {
            getLogger().log(Level.INFO, "Cannot set task text", e);
        }
    }

    /**
     * Returns a task item from this <b>DailyLogDocument</b>
     *
     * @param orderId
     * @return
     */
    public JsonObject getTaskDocument(int orderId) {
        try {
            for (JsonElement task : this.document.getAsJsonArray("tasks_list")) {
                if (((JsonObject) task).get("order_id").getAsInt() == orderId) {
                    return ((JsonObject) task);
                }
            }
            return null;
        } catch (Exception e) {
            this.getLogger().log(Level.INFO, e.toString());
            return null;
        }
    }

    /**
     * Returns a Jsonic list of tasks
     *
     * @return
     */
    public JsonArray getTasksList() {
        return document.getAsJsonArray("tasks_list");
    }

    /**
     * Returns a LinkedList of tasks
     *
     * @return
     */
    private LinkedList<JsonObject> getTasksListAsLinkedList() {
        LinkedList<JsonObject> list = new LinkedList<>();
        for (JsonElement item : document.getAsJsonArray("tasks_list")) {
            list.add(((JsonObject) item));
        }
        return list;
    }

    private Logger getLogger() {
        return Logger.getLogger(AbstractDayLogDocument.class.getName());
    }

    /**
     * Call this method when this <b>document</b> needs to be persisted to the
     * storage engine
     */
    public abstract void flush();

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskOrderId
     * @param statusName
     */
    public void setTaskStatus(int taskOrderId, String statusName) {
        try {
            for (JsonElement item : dao.taskStatusLookUp.get("status_list").getAsJsonArray()) {
                if (((JsonObject) item).get("status_name").getAsString().equals(statusName)) {
                    this.getTaskDocument(taskOrderId).addProperty("status_id", ((JsonObject) item).get("status_id").getAsInt());
                    flush();
                    return;
                }
            }
            throw new IllegalArgumentException();//If here then it means that there is no status item matching the parameter name statusName
        } catch (Exception e) {
            getLogger().log(Level.INFO, "Cannot set stautus", e);
        }
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskOrderId
     * @param statusId
     */
    public void setTaskStatus(int taskOrderId, int statusId) {
        try {
            for (JsonElement item : dao.taskStatusLookUp.get("status_list").getAsJsonArray()) {
                if (((JsonObject) item).get("status_id").getAsInt() == statusId) {
                    this.getTaskDocument(taskOrderId).addProperty("status_id", statusId);
                    flush();
                    return;
                }
            }
            throw new IllegalArgumentException();//If here then it means that there is no status item matching the parameter name statusId
        } catch (Exception e) {
            getLogger().log(Level.INFO, "Cannot set stautus", e);
        }
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskOrderId
     * @param categoryName
     * @throws IllegalArgumentException
     */
    public void setTaskCategory(int taskOrderId, String categoryName) throws IllegalArgumentException {
        try {
            for (JsonElement item : dao.taskCategoriesLookUp.get("categories").getAsJsonArray()) {
                if (((JsonObject) item).get("category_name").getAsString().equals(categoryName)) {
                    this.getTaskDocument(taskOrderId).addProperty("category_id", ((JsonObject) item).get("category_id").getAsInt());
                    flush();
                    return;
                }
            }
            throw new IllegalArgumentException();//If here then it means that there is no category matching the parameter name categoryName
        } catch (Exception e) {
            getLogger().log(Level.INFO, "Cannot set category", e);
        }
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param taskOrderId
     * @param categoryId
     */
    public void setTaskCategory(int taskOrderId, int categoryId) {
        try {
            for (JsonElement item : dao.taskCategoriesLookUp.get("categories").getAsJsonArray()) {
                if (((JsonObject) item).get("category_id").getAsInt() == categoryId) {
                    this.getTaskDocument(taskOrderId).addProperty("category_id", categoryId);
                    flush();
                    return;
                }
            }
            throw new IllegalArgumentException();//If here then it means that there is no category matching the parameter name categoryId
        } catch (Exception e) {
            getLogger().log(Level.INFO, "Cannot set category", e);
        }
    }

    /**
     * <p>
     * <b>NOTE:</b> calling this method will persist the changes to the storage
     * engine</p>
     *
     * @param orderId : The current ordering position of the task
     * @param toOrderedPosition : The position to which this task will be
     * repositioned
     * nothing
     */
    public void sortTask(int orderId, int toOrderedPosition){
        LinkedList<JsonObject> list = getTasksListAsLinkedList();
        JsonObject  temp = null;
        try{
            temp = list.get(orderId - 1);
            list.remove(orderId - 1);
            list.add(toOrderedPosition - 1, temp);
        }catch(ArrayIndexOutOfBoundsException e){
            list.addLast(temp);
        }catch(NullPointerException e){
           getLogger().log(Level.INFO, "", e);
           return;
        }
        this.document.add("tasks_list", null);
//      Reordering the list
        for(int c= 1; c <= list.size(); c++){
            list.get(c-1).addProperty("order_id", c);
            this.document.getAsJsonArray("tasks_list").add(list.get(c-1));
        }
        temp = null;
        list = null;
    }

    /**
     * Returns the String representation of <b>DayLogDocument</b>
     *
     * @return
     */
    public String getJson() {
        try {
            return this.document.toString();
        } catch (NullPointerException e) {
            Logger.getLogger(AbstractDayLogDocument.class.getName()).log(Level.INFO, "Document is empty", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return this.getJson();
    }
}
