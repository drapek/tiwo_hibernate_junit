/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drapek
 */
public class CarAddition implements Comparable<String>{

    private int id;
    private String addition_name;
    
    
    public CarAddition() {
    }
    public CarAddition( String addition_name) {
        this.addition_name = addition_name;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the addition_name
     */
    public String getAddition_name() {
        return addition_name;
    }

    /**
     * @param addition_name the addition_name to set
     */
    public void setAddition_name(String addition_name) {
        this.addition_name = addition_name;
    }


    @Override
    public int compareTo(String o) {
        if( o == null)
            return -1;
        
        Comparable thisAddition = this;
        Comparable oAddtion = o;
        
        if( thisAddition == null) {
            return 1;
        }
        else if (oAddtion == null) {
               return -1;
        } else {
                return thisAddition.compareTo(oAddtion);
        }
     
    }
   
    
    
}
