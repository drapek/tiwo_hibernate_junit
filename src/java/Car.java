
import java.util.SortedMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drapek
 */
public class Car {
    private int id;
    private String brand_name;
    private String model_name;
    private int production_year;
    private float prize;
    private String engine_type;
    private SortedMap additions;
    
    public Car() {
    }
    
    public Car(String brand_name, String model_name, int production_year, float prize, String engine_type, SortedMap additions) {
        this.brand_name = brand_name;
        this.model_name = model_name;
        this.production_year = production_year;
        this.prize = prize;
        this.engine_type = engine_type;
        this.additions = additions;
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
     * @return the brand_name
     */
    public String getBrand_name() {
        return brand_name;
    }

    /**
     * @param brand_name the brand_name to set
     */
    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    /**
     * @return the model_name
     */
    public String getModel_name() {
        return model_name;
    }

    /**
     * @param model_name the model_name to set
     */
    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    /**
     * @return the production_year
     */
    public int getProduction_year() {
        return production_year;
    }

    /**
     * @param production_year the production_year to set
     */
    public void setProduction_year(int production_year) {
        this.production_year = production_year;
    }

    /**
     * @return the prize
     */
    public float getPrize() {
        return prize;
    }

    /**
     * @param prize the prize to set
     */
    public void setPrize(float prize) {
        this.prize = prize;
    }

    /**
     * @return the engine_type
     */
    public String getEngine_type() {
        return engine_type;
    }

    /**
     * @param engine_type the engine_type to set
     */
    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    /**
     * @return the additions
     */
    public SortedMap getAdditions() {
        return additions;
    }

    /**
     * @param additions the additions to set
     */
    public void setAdditions(SortedMap additions) {
        this.additions = additions;
    }
    
    
}
