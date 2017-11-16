/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication9checkbox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Anmol
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label label;
    @FXML private ListView listView1, listView2, listView3;
    @FXML ObservableList<String> list1, list2, list3;
    
    @FXML private TableView<Business> tableView1;
    @FXML private TableColumn<Business, String> table1ColumnName;
    @FXML private TableColumn<Business, String> table1ColumnCity;
    @FXML private TableColumn<Business, String> table1ColumnState;
    @FXML private TableColumn<Business, String> table1ColumnStars;
    @FXML ObservableList<Business> tableList1;
   
    @FXML ComboBox comboBox1, comboBox2, comboBox3, comboBox4;
    @FXML ObservableList<String> comboBoxWeek ;
    @FXML ObservableList<Integer> comboBoxTimeFrom, comboBoxTimeTo;    
    @FXML ArrayList<Integer> timeArrayList;
    @FXML int timeListSimpleArray[];
    
    
    @FXML private Button closeButton, searchButton;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    @FXML
    private void searchButtonAction(ActionEvent event) {
        System.out.println("You clicked Search!");
        
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
        
        
        tableList1 =  FXCollections.observableArrayList();
        table1ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1ColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        table1ColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        table1ColumnStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        
        tableView1.setItems(tableList1);
        tableList1.add(new Business(1 ,"Business1", "Santa Clara", "CA", "4.5"));
        
        
        comboBoxWeek =  FXCollections.observableArrayList("Monday", "Teusday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        comboBox1.setItems(comboBoxWeek);
        comboBox1.setPromptText("Day Of Week");
        
        
        timeListSimpleArray = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        timeArrayList = new ArrayList<Integer>();
        for(int i =  0; i < timeListSimpleArray.length; i++){
                timeArrayList.add(timeListSimpleArray[i]);  //something like this?
           }
        
        comboBoxTimeFrom =  FXCollections.observableArrayList(timeArrayList);
        comboBox2.setItems(comboBoxTimeFrom);
        comboBox2.setPromptText("From");
       
        
        comboBoxTimeTo =  FXCollections.observableArrayList();
        comboBox3.setItems(comboBoxTimeTo);
        comboBox3.setPromptText("To");
        
         comboBox2.setOnAction( e -> {
            System.out.println(comboBox2.getValue().getClass().getName());
            ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
            for(Integer i : timeArrayList){
                if( i > (Integer)comboBox2.getValue()){
                    tempArrayList.add(i);
                }
            }
         comboBoxTimeTo.setAll(tempArrayList);
         
            
           
            
           
         });
        
        
        
        //END OF INITIALIZE BLOCK
    }    
    
}
