package Sintesis.ProfessorsEscriptori;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.text.View;

public class SampleController implements Initializable {
	@FXML
	private ListView alumnes;
	@FXML
	private ListView exercicis;
	@FXML
	private TextField curs;
	Connection conect = null;

	// Event Listener on ListView[#alumnes].onMouseClicked
	@FXML
	public void buscaExercicis(MouseEvent event) {
		String alumne = (alumnes.getSelectionModel().getSelectedItem().toString());
		String[] splitAlumne = alumne.split(" "); //splitAlumne[0] = idalumne
		String consulta = "SELECT e.tema "
						+ "FROM application_exercici e, application_resposta_user r "
						+ "WHERE e.id == r.exercici and"
						+ "r.usuari =="+splitAlumne[0];
		
		Statement peticio;
		ResultSet resultat;
		try {
			peticio = conect.createStatement();
			resultat = peticio.executeQuery(consulta);
			
			ObservableList <ListView> exercicisList = FXCollections.observableArrayList();
			while(resultat.next()){
				String tema =resultat.getString("e.tema");
				ListView actual = new ListView(tema);
				exercicisList.add(actual);
			}
			exercicis.setItems(exercicisList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	// Event Listener on Button.onAction
	@FXML
	public void buscaAlumnes(ActionEvent event) {
		String cursTriat = curs.getText();
		String consulta = "SELECT u.last_name, u.first_name p.id FROM auth_user u, application_perfil p,"
						+ "WHERE u.id == p.usuari_id and"
						+ "p.curs =="+curs;
		Statement peticio;
		ResultSet resultat;
		try {
			peticio = conect.createStatement();
			resultat = peticio.executeQuery(consulta);
			
			ObservableList <ListView> alumnesList = FXCollections.observableArrayList();
			while(resultat.next()){
				String id = resultat.getString("p.id");
				String cognom = resultat.getString("u.last_name");
				String nom =resultat.getString("u.first_name");
				ListView actual = new ListView(id, cognom, nom);
				alumnesList.add(actual);
			}
			alumnes.setItems(alumnesList);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conect = DriverManager.getConnection("jdbc:mysql://192.168.15.78/appunts", "kevin", "kevin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
