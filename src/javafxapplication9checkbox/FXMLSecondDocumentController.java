/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication9checkbox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anmol
 */
public class FXMLSecondDocumentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Button goBackButton;
    
    @FXML private TableView<Review> tableViewReview;
    @FXML private TableColumn<Review, String> tableColumnDate;
    @FXML private TableColumn<Review, String> tableClumnStars;
    @FXML private TableColumn<Review, String> tableColumnText;
    @FXML private TableColumn<Review, String> tableColumnUserId;
    @FXML private TableColumn<Review, String> tableColumnUsefulVotes;
    public ObservableList<Review> reviewList = FXCollections.observableArrayList();
    
    @FXML
    private void goBackButtonAction(ActionEvent event) throws IOException {
        System.out.println("You Want to go Back!");
        System.out.println("You clicked Search!");
        String css = GUI.class.getResource("listStyle.css").toExternalForm();
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        home_page_scene.getStylesheets().add(css);
        app_stage.setScene(home_page_scene);
        app_stage.show();
        
    }
    
    
    public void initObservableReviewList(ArrayList<Review> rList){
        for(Review r : rList){
            reviewList.add(r);
//            System.out.println(r.toString());
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Review, String>("date"));
        tableClumnStars.setCellValueFactory(new PropertyValueFactory<Review, String>("stars"));
        tableColumnText.setCellValueFactory(new PropertyValueFactory<Review, String>("text"));
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<Review, String>("userid"));
        tableColumnUsefulVotes.setCellValueFactory(new PropertyValueFactory<Review, String>("usefulVotes"));
        tableViewReview.setItems(reviewList);
        
//        initObservableReviewList(tempReviewArray);
        
    }    
    
}
