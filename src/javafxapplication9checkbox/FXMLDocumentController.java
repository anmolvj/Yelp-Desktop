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
import java.sql.ResultSetMetaData;
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
    @FXML ObservableList<String> comboBoxWeek, comboBoxSelectAttribute ;
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
    @FXML private void searchButtonAction(ActionEvent event) throws IOException, Exception {
//        String css = JavaFXApplication9CheckBox.class.getResource("listStyle.css").toExternalForm();
//        
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("FXMLSecondDocument.fxml"));
//        Parent home_page_parent = loader.load();
//        
//        
//        Scene home_page_scene = new Scene(home_page_parent);
//        
//        FXMLSecondDocumentController controller = loader.getController();
//        
//        Review tempReview1 = new Review("date","3","search button temporary review","Vijayvargiya", "11");
//        Review[] tempReviewArray1 = new Review[]{tempReview1};
////        controller.initObservableReviewList(tempReviewArray1);
////      controller.initObservableReviewList(tableView1.getSelectionModel().getSelectedItem().getReviewList());
//        
//        Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//        home_page_scene.getStylesheets().add(css);
//        app_stage.setScene(home_page_scene);
//        app_stage.show();
//        app_stage.show();
          ArrayList<String[]> tempBusinessTableData = new ArrayList<>();
//          tempBusinessTableData = final_result(
//                  listView1CheckedItems,
//                  listView2CheckedItems,
//                  listView3CheckedItems,
//                  comboBox1.getSelectionModel().getSelectedItem().toString(),
//                  comboBox3.getSelectionModel().getSelectedItem().toString(),
//                  comboBox2.getSelectionModel().getSelectedItem().toString(),
//                  comboBox4.getSelectionModel().getSelectedItem().toString()
//          );
            tempBusinessTableData = final_result(
                  listView1CheckedItems,
                  listView2CheckedItems,
                  listView3CheckedItems,
                  comboBox1.getSelectionModel().getSelectedItem().toString(),
                  comboBox3.getSelectionModel().getSelectedItem().toString(),
                  comboBox2.getSelectionModel().getSelectedItem().toString(),
                  comboBox4.getSelectionModel().getSelectedItem().toString()
             );
          System.out.println(tempBusinessTableData.toString());
          for(String[] str : tempBusinessTableData){
             tableList1.add(new Business(str[0] ,str[1], str[2], str[3], str[4])); 
          }
          
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
    
    @FXML public ArrayList<String> find_second_col (ArrayList<String> a, String b) throws Exception
	{
		ArrayList<String> sub_catarray = new ArrayList<String>();
		String between_values = b;
		between_values = " " + between_values + " ";
		Connection conn = null;
		ResultSet fireResult = null;
		java.sql.Statement stmt = null;
		if (a != null) 
		{
			String mainQuery = "SELECT DISTINCT S.C_NAME FROM B_MAIN_CATEGORY M,B_SUB_CATEGORY S WHERE M.BID = S.BID AND M.C_NAME = ";
			String query = mainQuery;
			System.out.println("SELECTED MAIN CATEGORIES ARE: " + a);
			for (int i = 0; i < a.size(); i++) 
			{
				if (i == a.size() - 1) 
				{
					query = query + "'" + a.get(i) + "'";
				} else {
					query = query + "'" + a.get(i) + "'" + between_values + mainQuery;
				}
			}	
			conn = getDBConnection();
			stmt = conn.prepareStatement(query);
			fireResult = stmt.executeQuery(query);
			ResultSetMetaData meta = fireResult.getMetaData();
			while (fireResult.next()) 
			{
				for (int i = 1; i <= meta.getColumnCount(); i++) 
				{
					sub_catarray.add(fireResult.getString(i));
				}
			}
		}else{
                    return sub_catarray;
                }
		return sub_catarray;			
	}
    
    @FXML public ArrayList<String> find_third_col (ArrayList<String> a, ArrayList<String> b, String c) throws Exception
	{
		ArrayList<String> att_catarray = new ArrayList<String>();
		ArrayList<String> main_catarray = new ArrayList<String>();
		main_catarray = b;
		String between_values = c;
		between_values = " " + between_values + " ";
		
		Connection conn = null;
		ResultSet fireResult = null;
		java.sql.Statement stmt = null;
		if (a != null) 
		{
			String mainQuery = "SELECT DISTINCT A.A_NAME FROM B_MAIN_CATEGORY M, B_SUB_CATEGORY S, B_ATTRIBUTES A WHERE M.BID = S.BID AND S.BID = A.BID AND M.C_NAME LIKE ";
			//System.out.println(a.toString());
			String query = mainQuery;
			String subquery = " AND S.C_NAME LIKE ";
			for (int i = 0; i < main_catarray.size(); i++) 
			{
				for(int j = 0; j < a.size(); j++)
				{	
					if (i == main_catarray.size() - 1 && j == a.size() -1) 
					{
						query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + a.get(j) + "'" ;
						//System.out.println("i is " + i + " & j is " + j);
						//System.out.println(query);
					} else {
						query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + a.get(j) + "'" + between_values + mainQuery;
						//System.out.println("i is " + i + " & j is " + j);
						//System.out.println(query);
					}
				}
			}			
			
			System.out.println(query);
			conn = getDBConnection();
			stmt = conn.prepareStatement(query);
			fireResult = stmt.executeQuery(query);
			
			ResultSetMetaData meta = fireResult.getMetaData();
			while (fireResult.next()) 
			{
				for (int i = 1; i <= meta.getColumnCount(); i++) 
				{
					att_catarray.add(fireResult.getString(i));
				}
			}			
		}
		return att_catarray;
	}
    
    
    @FXML public ArrayList<String[]> final_result(ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, String d, String e, String f, String g) throws Exception
	{
		ArrayList<String> main_catarray = new ArrayList<String>();
		ArrayList<String> sub_catarray = new ArrayList<String>();
		ArrayList<String> att_catarray = new ArrayList<String>();
		ArrayList<String[]> bus_list = new ArrayList<String[]>();
		main_catarray = a;
		sub_catarray = b;
		att_catarray = c;
		String between_values;
		String b_v = g;
		if(b_v.equals("AND"))
		{
			between_values = " INTERSECT ";
		}
		else
		{
			between_values = " UNION ";
		}
		
		
		String day = d;
		String to = e;
		String from = f;
                float from_sel, to_sel;
		//String loc_state = null;
		//String loc_city = null;
		if(from != null)
                {
                   from_sel = Float.parseFloat(from); 
                }
		else
                {
                    from_sel = -1;
                }
                if(to != null)
                {
                   to_sel = Float.parseFloat(to); 
                }
		else
                {
                     to_sel = 25;
                }
		
                
		String[] row;
                row = null;
                
		Connection conn = null;
		ResultSet fireResult = null;
		java.sql.Statement stmt = null;
		String query = null;
		if(att_catarray!=null)
		{
			String mainQuery = null;
			String select_query = " SELECT DISTINCT B.BID, B.B_NAME, B.CITY, B.STATE, B.STARS ";
			String from_query = " FROM B_MAIN_CATEGORY M, BUSINESS B, B_SUB_CATEGORY S, B_ATTRIBUTES A ";
			String where_query = " WHERE M.BID = B.BID AND M.BID = S.BID AND A.BID = B.BID ";
//			if (loc_city != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.CITY LIKE '" + loc_city + "'";
//			}
//			
//			if (loc_state != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.STATE LIKE '" + loc_state + "'";
//			}
			
			if (day != null)
			{
				from_query = from_query + ", B_HOURS H ";
				where_query = where_query + " AND B.BID = H.BID AND H.D_O_W LIKE '"+ day + "' AND H.FROM_H >= " + from_sel + " AND H.TO_H <=" + to_sel;
			}
			else
			{
				if(from_sel >= 0)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.FROM_H >= " + from_sel;
					if(to_sel <= 24)
					{
						where_query = where_query + " AND H.TO_H <= " + to_sel;
					}
				}
				else if(to_sel <= 24)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.TO_H <=" + to_sel;
				}
			}			
			
			query = select_query + from_query + where_query + " AND M.C_NAME LIKE ";
			mainQuery = query;
			String subquery2 = " AND A.A_NAME LIKE ";
				String subquery = " AND S.C_NAME LIKE ";

				for (int i = 0; i < main_catarray.size(); i++) 
				{
					for(int j = 0; j < sub_catarray.size(); j++)
					{	
						for(int k = 0; k < att_catarray.size(); k++)
						{
							if (i == main_catarray.size() - 1 && j == sub_catarray.size() -1) 
							{
								query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + sub_catarray.get(j)+ "'" + subquery2 + "'" + att_catarray.get(k) + "'";
							} else 
							{
								query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + sub_catarray.get(j) + "'" + subquery2 + "'" + att_catarray.get(k) + "'" + between_values + mainQuery;
							}
						}
					}
				}
				//last line of attr_catarray
		}
		else if( sub_catarray != null)
		{
			String mainQuery = null;
			String select_query = " SELECT DISTINCT B.BID, B.B_NAME, B.CITY, B.STATE, B.STARS ";
			String from_query = " FROM B_MAIN_CATEGORY M, BUSINESS B, B_SUB_CATEGORY S ";
			String where_query = "WHERE M.BID = B.BID AND M.BID = S.BID ";
//			if (loc_city != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.CITY LIKE '" + loc_city + "'";
//			}
//			
//			if (loc_state != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.STATE LIKE '" + loc_state + "'";
//			}
			
			if (day != null)
			{
				from_query = from_query + ", B_HOURS H ";
				where_query = where_query + " AND B.BID = H.BID AND H.D_O_W = '"+ day + "' AND H.FROM_H >= " + from_sel + " AND H.TO_H <=" + to_sel;
			}
			else
			{
				if(from_sel >= 0)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.FROM_H >= " + from_sel;
					if(to_sel <= 24)
					{
						where_query = where_query + " AND H.TO_H <= " + to_sel;
					}
				}
				else if(to_sel <= 24)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.TO_H <=" + to_sel;
				}
			}			
			
			query = select_query + from_query + where_query + " AND M.C_NAME = ";
			mainQuery = query;
				String subquery = " AND S.C_NAME = ";
				for (int i = 0; i < main_catarray.size(); i++) 
				{
					for(int j = 0; j < sub_catarray.size(); j++)
					{	
						if (i == main_catarray.size() - 1 && j == sub_catarray.size() -1) 
						{
							query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + sub_catarray.get(j) + "'" ;
						} else 
						{
							query = query + "'" + main_catarray.get(i) + "'" + subquery + "'" + sub_catarray.get(j) + "'" + between_values + mainQuery;
						}
					}
				}
				
			//last line of sub_catarray
		}
		else if(main_catarray!=null)
		{
			//String mainQuery = null;
			String select_query = " SELECT DISTINCT B.BID, B.B_NAME, B.CITY, B.STATE, B.STARS ";
			String from_query = " FROM B_MAIN_CATEGORY M, BUSINESS B ";
			String where_query = "WHERE M.BID = B.BID ";
//			if (loc_city != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.CITY = '" + loc_city + "'";
//			}
//			
//			if (loc_state != null)
//			{
//				from_query = from_query + "";
//				where_query = where_query + " AND B.STATE = '" + loc_state + "'";
//			}
			
			if (day != null)
			{
				from_query = from_query + ", B_HOURS H ";
				where_query = where_query + " AND B.BID = H.BID AND H.D_O_W = '"+ day + "' AND H.FROM_H >= " + from_sel + " AND H.TO_H <=" + to_sel;
			}
			else
			{
				if(from_sel >= 0)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.FROM_H >= " + from_sel;
					if(to_sel <= 24)
					{
						where_query = where_query + " AND H.TO_H <= " + to_sel;
					}
				}
				else if(to_sel <= 24)
				{
					from_query = from_query + ", B_HOURS H ";
					where_query = where_query + " AND B.BID = H.BID AND H.TO_H <=" + to_sel;
				}
			}
			
			
			query = select_query + from_query + where_query + " AND M.C_NAME = ";
			String mainQuery = query;
			for (int i = 0; i < main_catarray.size(); i++) 
			{
				if (i == main_catarray.size() - 1) 
				{
					query = query + "'" + main_catarray.get(i) + "'";
				} else {
					query = query + "'" + main_catarray.get(i) + "'" + between_values + mainQuery;
				}
			}	
		}
		
		System.out.println(query);
		conn = getDBConnection();
		stmt = conn.prepareStatement(query);
		fireResult = stmt.executeQuery(query);
			
		ResultSetMetaData meta = fireResult.getMetaData();
		while (fireResult.next()) 
		{
			for (int iCol = 1; iCol <= 5; iCol++) {
				
				
					row[iCol - 1] = (fireResult.getObject(iCol)).toString();
				

				System.out.println("Display->" + fireResult.getString(iCol));
				//row[iCol - 1] = result.getObject(iCol).toString();

			}
                        bus_list.add(row);

		}
		return bus_list;
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
                            listView1CheckedItems.add(item);    
                            try { 
                                ArrayList<String> tempRes = find_second_col(listView1CheckedItems, comboBox4.getSelectionModel().getSelectedItem().toString());
                                if(tempRes==null){
                                    list2.clear();
                                    System.out.println("1");
                                } else{
                                 list2.clear();
                                 list2.addAll(tempRes);
                                 System.out.println("2" + comboBox4.getSelectionModel().getSelectedItem().toString());
                                }
                                
                            } catch (Exception ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println(listView1CheckedItems.toString());
                               
                        } else {
                                listView1CheckedItems.remove(item);
                                try {        
                                
                                if(listView1CheckedItems.isEmpty()){
                                    list2.clear();
                                    System.out.println("3");
                                } else{
                                 list2.clear();
                                 list2.addAll(find_second_col(listView1CheckedItems, comboBox4.getSelectionModel().getSelectedItem().toString()));
                                 System.out.println("4" + comboBox4.getSelectionModel().getSelectedItem().toString());
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            list3.remove(item);
//                           System.out.println(listView1CheckedItems.toString());
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
//                            list3.add(item);
//                            listView2CheckedItems.add(item);
                            listView2CheckedItems.add(item);    
                            try { 
                                ArrayList<String> tempRes = find_third_col(listView2CheckedItems,listView1CheckedItems, comboBox4.getSelectionModel().getSelectedItem().toString());
                                if(tempRes==null){
                                    list3.clear();
                                    System.out.println("1");
                                } else{
                                 list3.clear();
                                 list3.addAll(tempRes);
                                 System.out.println("2" + comboBox4.getSelectionModel().getSelectedItem().toString());
                                }
                                
                            } catch (Exception ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println(listView1CheckedItems.toString());
                        } else {
                            listView2CheckedItems.remove(item);
                                try {        
                                
                                if(listView2CheckedItems.isEmpty()){
                                    list3.clear();
                                    System.out.println("3");
                                } else{
                                 list3.clear();
                                 list3.addAll(find_third_col(listView2CheckedItems,listView1CheckedItems, comboBox4.getSelectionModel().getSelectedItem().toString()));
                                 System.out.println("4" + comboBox4.getSelectionModel().getSelectedItem().toString());
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            } 
//                            list3.remove(item);
//                            list3.remove(item);
//                            listView2CheckedItems.remove(item);
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

//        Review tempReview = new Review("date","4","some text","Anmol", "6");
//        Review[] tempReviewArray = new Review[]{tempReview};
//        tableList1.add(new Business("1" ,"Business1", "Santa Clara", "CA", "4.5"));
        
        
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
         
         
        comboBoxSelectAttribute = FXCollections.observableArrayList("AND","OR"); 
        comboBox4.setItems(comboBoxSelectAttribute);
        comboBox4.setPromptText("Select Attributes");
        comboBox4.getSelectionModel().selectFirst();
        
        //END OF INITIALIZE BLOCK
    }    
    
}
