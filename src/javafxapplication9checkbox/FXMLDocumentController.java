/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication9checkbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    @FXML ArrayList<String> mainBusinessCategoryArrayList;
    @FXML ArrayList<String> listView1CheckedItems = new ArrayList<String>();
    @FXML ArrayList<String> listView2CheckedItems = new ArrayList<String>();
    @FXML ArrayList<String> listView3CheckedItems = new ArrayList<String>();
    @FXML String mainBusinessCategorySimpleArray[];
    
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

    //CLOSE BUTTON ACTION HANDLER
    @FXML private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    //BUSINESS TABLEVIEW ROW SELECTION HANDLER
    @FXML private void tableView1SelectAction(Event event) throws IOException, SQLException {
        
        
        //SETUP FOR PREPARING SWITCH OF SCENE
        String css = JavaFXApplication9CheckBox.class.getResource("listStyle.css").toExternalForm();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLSecondDocument.fxml"));
        Parent home_page_parent = loader.load();
        Scene home_page_scene = new Scene(home_page_parent);
        FXMLSecondDocumentController controller = loader.getController();
        
        
        
        //PASS REVIEW ARRAYLIST TO NEXT SCENE
//        controller.initObservableReviewList(tableView1.getSelectionModel().getSelectedItem().getReviews());
        controller.initObservableReviewList(review_final());
        Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        home_page_scene.getStylesheets().add(css);
        app_stage.setScene(home_page_scene);
        app_stage.show();
     }
    
    //SEARCH BUTTON ACTION HANDLER
    @FXML private void searchButtonAction(ActionEvent event) throws IOException {
        String css = JavaFXApplication9CheckBox.class.getResource("listStyle.css").toExternalForm();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLSecondDocument.fxml"));
        Parent home_page_parent = loader.load();
        
        
        Scene home_page_scene = new Scene(home_page_parent);
        
        FXMLSecondDocumentController controller = loader.getController();
        
        Review tempReview1 = new Review("date","3","search button temporary review","Vijayvargiya", "11");
        Review[] tempReviewArray1 = new Review[]{tempReview1};
//        controller.initObservableReviewList(tempReviewArray1);
//      controller.initObservableReviewList(tableView1.getSelectionModel().getSelectedItem().getReviewList());
        
        Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        home_page_scene.getStylesheets().add(css);
        app_stage.setScene(home_page_scene);
        app_stage.show();
     }
    
//    MOAKE CONNECTION TO database
    @FXML private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:assignment3d", "scott", "tiger");
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
   
    @FXML private ArrayList<Review> review_final() throws SQLException {
        ArrayList<Review> listOfReviewAttributes = new ArrayList<Review>();
        String a  = "fpL1qcZ6qbWzC79WU0E-Ug";
        Connection conn = null;
        ResultSet result = null;
        java.sql.Statement stmt = null;
        if (a != null){
            String mainQuery = "SELECT R.R_DATE, R.STARS, R.R_TEXT, R.USER_ID, R.USEFUL_VOTE FROM REVIEWS R WHERE R.BID LIKE ";
            mainQuery = mainQuery + "'" + a + "'";
            conn = getDBConnection();
            stmt = conn.prepareStatement(mainQuery);
            result = stmt.executeQuery(mainQuery);		
            int nCol = result.getMetaData().getColumnCount();
            
            System.out.println("Column Size->" + nCol);
            
            System.out.println("Justine");
            
            while (result.next()){
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    if (iCol == 3) {
                        StringBuffer strOut = new StringBuffer();
			String aux;
                        try {
			BufferedReader br = new BufferedReader(result.getClob("R_TEXT").getCharacterStream());
			while ((aux = br.readLine()) != null) {
				strOut.append(aux);
				strOut.append(System.getProperty("line.separator"));
			}
			} catch (Exception e) {
			e.printStackTrace();
                        }
                        String clobStr = strOut.toString();
			
			row[iCol - 1] = clobStr;
                    }
                    else{
                    row[iCol - 1] = result.getObject(iCol).toString();
                    }
                    
                }
                listOfReviewAttributes.add(new Review(row[0],row[1],row[2],row[3],row[4]));
            }
        }
        return listOfReviewAttributes;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainBusinessCategoryArrayList = new ArrayList<String>();
        mainBusinessCategorySimpleArray = new String[]{
            "Active Life"
            ,"Arts & Entertainment"
            ,"Automotive"
            ,"Car Rental"
            ,"Cafes"
            ,"Beauty & Spas"
            ,"Convenience Stores"
            ,"Dentists"
            ,"Doctors"
            ,"Drugstores"
            ,"Department Stores"
            ,"Education"
            ,"Event Planning & Services"
            ,"Flowers & Gifts"
            ,"Food"
            ,"Health & Medical"
            ,"Home Services"
            ,"Home & Garden"
            ,"Hospitals"
            ,"Hotels & Travel"
            ,"Hardware Stores"
            ,"Grocery"
            ,"Medical Centers"
            ,"Nurseries & Gardening"
            ,"Nightlife"
            ,"Restaurants"
            ,"Shopping"
            ,"Transportation"};
        for(String str: mainBusinessCategorySimpleArray){
                mainBusinessCategoryArrayList.add(str);  //something like this?
           }
        //SETTING FOR 1ST LISTVIEW
        list1 = FXCollections.observableArrayList(mainBusinessCategoryArrayList);
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
                                listView1CheckedItems.add(item);
                                System.out.println(listView1CheckedItems.toString());
                               
                        } else {
                                listView1CheckedItems.remove(item);
                               list2.remove(item);
                              list3.remove(item);
                           System.out.println(listView1CheckedItems.toString());
                        }
                    });
                    
                    return observable;
                }
            }));
        
        //SETTING FOR 2ND LISTVIEW
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
                            listView2CheckedItems.add(item);
                        } else {
                            list3.remove(item);
                            listView2CheckedItems.remove(item);
                        }
                    });
                    return observable;
                }
            }));
        
        
        //SETTING FOR 3RD LISTVIEW
        list3 = FXCollections.observableArrayList();
        listView3.setItems(list3);
        listView3.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(String item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            listView3CheckedItems.add(item);
                        } else {
                            listView3CheckedItems.remove(item);
                        }
                    });
                    return observable;
                }
            }));
        
        
        //SETUP FOR BUSINESS TABLEVIEW
        tableList1 =  FXCollections.observableArrayList();
        table1ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1ColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        table1ColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        table1ColumnStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
        
        tableView1.setItems(tableList1);
        tableView1.setRowFactory( tv -> {
            TableRow<Business> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    try {   
                        tableView1SelectAction(event);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });

        Review tempReview = new Review("date","4","some text","Anmol", "6");
        Review[] tempReviewArray = new Review[]{tempReview};
        tableList1.add(new Business(1 ,"Business1", "Santa Clara", "CA", "4.5", tempReviewArray));
        
        
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
