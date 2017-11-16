/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication9checkbox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;

/**
 *
 * @author Anmol
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label label;
    @FXML private ListView listView1, listView2, listView3;
    @FXML ObservableList<String> list1, list2, list3;
   
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list1 =     FXCollections.observableArrayList("ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX");
        listView1.setItems(list1);
//        listView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//        @Override
//        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            System.out.println("ListView selection changed from oldValue = " 
//                    + oldValue + " to newValue = " + newValue);
//            list2.add(newValue);
//            }
//         });
        listView1.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(String item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            list2.add(item);
                        } else {
                            list2.remove(item);
                            list3.remove(item);
                        }
                    });
                    return observable;
                }
            }));
        
        
        list2 = FXCollections.observableArrayList();
        listView2.setItems(list2);
//        listView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//        @Override
//        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            System.out.println("ListView selection changed from oldValue = " 
//                    + oldValue + " to newValue = " + newValue);
//            list3.add(newValue);
//            }
//         });
        listView2.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(String item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            list3.add(item);
                        } else {
                            list3.remove(item);
                        }
                    });
                    return observable;
                }
            }));
        
        
        list3 = FXCollections.observableArrayList();
        listView3.setItems(list3);
        listView3.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(String item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
//                    observable.addListener((obs, wasSelected, isNowSelected) -> {
//                        if (isNowSelected) {
//                            list3.add(item);
//                        } else {
//                            list3.remove(item);
//                        }
//                    });
                    return observable;
                }
            }));
    }    
    
}
