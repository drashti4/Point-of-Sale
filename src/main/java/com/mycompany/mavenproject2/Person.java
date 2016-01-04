
package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {  
    private final StringProperty AddOnColumn;
    private final StringProperty ValueColumn;
    private final StringProperty ByPercentageColumn;
    private final StringProperty TaxColumn;
   // private final ObjectProperty<LocalDate> birthday;    
    public Person() {
        this(null, null,null,null);
    }    
    public Person(String AddOnColumn, String ValueColumn,String ByPercentageColumn,String TaxColumn) {
        this.AddOnColumn = new SimpleStringProperty(AddOnColumn);
        this.ValueColumn = new SimpleStringProperty(ValueColumn);       
        this.ByPercentageColumn = new SimpleStringProperty(ByPercentageColumn);       
        this.TaxColumn = new SimpleStringProperty(TaxColumn);       
    }
    public String getAddOnColumn() {
        return AddOnColumn.get();
    }
    public void setAddOnColumn(String AddOnColumn) {
        this.AddOnColumn.set(AddOnColumn);
    }
    public StringProperty AddOnColumnProperty() {
        return AddOnColumn;
    }
    public String getValueColumn() {
        return ValueColumn.get();
    }
    public void setValueColumn(String ValueColumn) {
        this.ValueColumn.set(ValueColumn);
    }
    public StringProperty ValueColumnProperty() {
        return ValueColumn;
    }
    public String getByPercentageColumn() {
        return ByPercentageColumn.get();
    }
    public void setByPercentageColumn(String ByPercentageColumn) {
        this.ByPercentageColumn.set(ByPercentageColumn);
    }
    public StringProperty ByPercentageColumnProperty() {
        return ByPercentageColumn;
    }
    public String getTaxColumn() {
        return TaxColumn.get();
    }
    public void setTaxColumn(String TaxColumn) {
        this.TaxColumn.set(TaxColumn);
    }
    public StringProperty TaxColumnProperty() {
        return TaxColumn;
    }   
}
