package Sintesis.ProfessorsEscriptori;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.control.ComboBox;


public class SampleController implements Initializable {
	@FXML
	private ListView alumnes;
	@FXML
	private ListView exercicis;
	@FXML
	private ComboBox<String> curs;
	Connection conect = null;

	// Event Listener on ListView[#alumnes].onMouseClicked
		@FXML
		public void buscaExercicis(MouseEvent event) {
			String alumne = (alumnes.getSelectionModel().getSelectedItem().toString());
			String[] splitAlumne = alumne.split(" "); //splitAlumne[0] = idalumne
			System.out.println(splitAlumne[0]);
			String consulta = "SELECT e.nom, e.tema "
							+ "FROM application_exercici e, application_resposta_user r "
							+ "WHERE e.id = r.exercici_id and "
							+ "r.usuari_id ="+splitAlumne[0];
			Statement peticio;
			ResultSet resultat;
			try {
				peticio = conect.createStatement();
				resultat = peticio.executeQuery(consulta);
				
				ObservableList <String> exercicisList = FXCollections.observableArrayList();
				while(resultat.next()){
					String tema =resultat.getString("e.tema");
					exercicisList.add(tema);
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
			String cursTriat= curs.getValue().toString();
			
			String consulta = "SELECT u.last_name, u.first_name, p.id FROM auth_user u, application_perfil p, application_tipus t "
							+ "WHERE u.id = p.usuari_id and"
							+ " p.curs_id ="+cursTriat+" and"
							+ " p.tipus_id = t.id and"
							+ " t.nom = 'alumne'";
			Statement peticio;
			ResultSet resultat;
			try {
				peticio = conect.createStatement();
				resultat = peticio.executeQuery(consulta);
				
				ObservableList <String> alumnesList = FXCollections.observableArrayList();
				while(resultat.next()){
					String id = resultat.getString("p.id");
					String cognom = resultat.getString("u.last_name");
					String nom =resultat.getString("u.first_name");
					alumnesList.add(id+" "+cognom+" "+nom);
				}
				alumnes.setItems(alumnesList);
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}

		public void initialize(URL location, ResourceBundle resources) {
			curs.getItems().addAll("1","2","3","4","5","6");
			curs.setValue("1");
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
