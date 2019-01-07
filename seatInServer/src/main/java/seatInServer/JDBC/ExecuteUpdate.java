package seatInServer.JDBC;

import static seatInServer.Utilities.Utilities.getCurrentClassName;
import static seatInServer.JDBC.QueryResources.closeResources;
import static seatInServer.Utilities.Utilities.getCurrentDateAndTime;
import static seatInServer.Utilities.ResultType.negative;
import static seatInServer.Utilities.ResultType.positive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInServer.JDBC.ConnectionPool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ExecuteUpdate {

	private static ExecuteUpdate insert;
	private static ExecuteSelect select = ExecuteSelect.getInstance();
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	
	private ExecuteUpdate() {
		
	}
	/**
	 * @return il riferimento all'oggetto della classe ExecuteSelect
	 */
	public static ExecuteUpdate getInstance() {
		if(insert == null) {
			insert = new ExecuteUpdate();
		}		
		return insert;
	}
	/**
	 * Esegue l'inserimento di un utente nella base di dati.
	 * @param user -- l'oggetto contenente le informazioni relative ad un determinato untene.
	 * @param userType -- viene rappresentato da un intero: 1 - Admin, 2 - Lecture, 3 - AStudent.
	 * @param department -- l'id del dipartimento a cui si vuole associare un determinato utente. 
	 * @param degreeCourse -- l'utente di tipo "Studente" viene associato ad un corso di laurea di riferimento
	 * @param careerStatus -- rappresent lo stato della cariera di uno studente.
	 */
	public void insertUser(User user, int userType, Integer department, Integer degreeCourse, Character careerStatus) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "WITH insert AS(INSERT INTO users(surname, first_name,email,password,group_id)"
				+ "VALUES(?, ?, ?, ?, ?) RETURNING id) "
				+ " INSERT INTO profiles(activation_code, created, modified, is_active, department_id, degree_course_id, career_status, user_id)"
				+ "VALUES(?, ?, ?, false, ?, ?, ?, (SELECT id FROM insert));";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getSurname());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setInt(5, userType);
			
			stmt.setString(6, user.getActivationCode());
			stmt.setTimestamp(7, getCurrentDateAndTime());
			stmt.setTimestamp(8, getCurrentDateAndTime());
			
			if(department == null)
				stmt.setNull(9, java.sql.Types.INTEGER);
			else
				stmt.setInt(9, department);
			
			if(degreeCourse == null)
				stmt.setNull(10, java.sql.Types.INTEGER);
			else
				stmt.setInt(10, degreeCourse);
			
			if(careerStatus == null)
				stmt.setNull(11, java.sql.Types.CHAR);
			else
				stmt.setString(11, String.valueOf(careerStatus));
			
			stmt.executeUpdate();
			
		}catch(SQLException e ) {
			logger.debug("Errore esecuzione insertUser: "+e);
		}finally {
			closeResources(stmt, conn);
		}
	}
	/**
	 * Esegue l'inserimento di un corso nella base di dati.
	 * @param course l'oggetto contenente tutte le informazioni relative ad un determinato corso.
	 * @param degreeCourseId l'id del orso di laurea di riferimento.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String insertCourse(Course course, Integer degreeCourseId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "INSERT INTO courses(name,description,activation_date,is_active,degree_course_id)VALUES(?,?,?,?,?);";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDescription());

			if(course.getActivationDate() == null)
				stmt.setTimestamp(3, getCurrentDateAndTime());
			else 
				stmt.setTimestamp(3, course.getActivationDate());
			
			stmt.setBoolean(4, course.isActive());
			
			if(degreeCourseId == null)
				stmt.setNull(5, java.sql.Types.INTEGER);
			else
				stmt.setInt(5, degreeCourseId);
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore esecuzione insertCourse: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Il metodo viene utilizzato per bloccare l'utente che ha effettuato 10 inserimenti consecutivi
	 * falliti del login. 
	 * @param email l'indirizzo della posta eletrinica
	 * @param wrongLogins quantità dei tentativi di login falliti.
	 */
	public void blockAccount(String email, int wrongLogins) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE profiles SET is_active=false, wrong_logins=? "
				 +" WHERE user_id = (SELECT u.id FROM users u WHERE email=?);";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, wrongLogins);
			stmt.setString(2, email);
			
			stmt.executeUpdate();
		
		}catch(SQLException e) {
			logger.debug("Errore esecuzione blockAccount: "+e);
		}finally {
			closeResources(stmt, conn);
		}
	}
	/**
	 * Esegue lo sblocco del account bloccato. 
	 * Può essere utilizzato solo dagli utenti di tipo Admin.
	 * @param userId -- l'id dell'utente.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String unlockAccount(int userId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE profiles SET is_active = true, wrong_logins = 0 WHERE user_id = ?;";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			
			int result1 = stmt.executeUpdate();
			logger.debug("Result: "+result1);
			result = positive;
		}catch(SQLException e) {
			logger.debug("unlockAccount è fallito: "+e);
		}finally{
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Aggiorna nel data base la password e codice attivazione account di un determinato utente. 
	 * Il seguente metodo viene utilizzato nel caso di "Reset Password", di conseguenza l'account 
	 * corrispondente alla mail passata come argomento viene bloccato. 
	 * @param email l'indirizzo della posta eletronica.
	 * @param password la nuova password.
	 * @param activationCode il nuovo codice attivazione.
	 */
	public void updatePasswordAndActivationCode(String email, String password, String activationCode) {
		Connection conn = null;
		PreparedStatement stmtPswd = null;
		PreparedStatement stmtCode = null;
		String apdatePassword = "UPDATE users SET password = ? WHERE email = ?;";
		String updateActivationCode = "UPDATE profiles SET activation_code= ?, is_active = false " 
					+" WHERE user_id = (SELECT u.id FROM users u WHERE u.email = ?);"; 
		try {
			conn = ConnectionPool.getConnection();
			stmtPswd = conn.prepareStatement(apdatePassword);
			stmtPswd.setString(1, password);
			stmtPswd.setString(2, email);
			
			stmtCode = conn.prepareStatement(updateActivationCode);
			stmtCode.setString(1, activationCode);
			stmtCode.setString(2, email);
			
			stmtPswd.executeUpdate();
			stmtCode.executeUpdate();
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione aggiornamento Password e ActivationCode: "+e);
		}finally {
			closeResources(stmtPswd, conn);
		}		
	}
	/**
	 * Modifica le informazioni relativi ad un determinato corso.
	 * @param course -- l'oggetto contenente le informazioni relative ad un corso da modificare.
	 * @param degreeCourse -- corso di laurea di riferimento. 
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifyCourseInfo(Course course, int degreeCourse) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE courses SET name=?,description=?,activation_date=?,is_active=?,degree_course_id =? "
					 + "WHERE id = ?;";
		String result = negative;
		try {
			logger.debug("Get connection");
			conn = ConnectionPool.getConnection();
			logger.debug("had connestion");
			stmt = conn.prepareStatement(query);
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDescription());
			stmt.setTimestamp(3, course.getActivationDate());
			stmt.setBoolean(4, course.isActive());
			stmt.setInt(5, degreeCourse);
			stmt.setInt(6, course.getId());
			
			stmt.executeUpdate();
			//TODO da testare il funzionamento  
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore esecuzione updateCourseInfo: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la modifica delle informazioni relative ad una sezione/sotto-sezione.
	 * @param section l'oggetto contenente tutte le informazione da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifyCourceSection(Section section) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE modules "
					 + "SET parent_id=?,title=?,description=?,is_active=?,active_from=?,active_to=? " 
					 + "WHERE id=?;";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			if(section.getParentId() == null)
				stmt.setNull(1, java.sql.Types.INTEGER);
			else
				stmt.setInt(1, section.getParentId());
			stmt.setString(2, section.getTitle());
			stmt.setString(3, section.getDescription());
			stmt.setBoolean(4, section.isActive());
			stmt.setTimestamp(5, section.getActiveFrom());
			stmt.setTimestamp(6, section.getActiveTo());
			stmt.setInt(7, section.getId());
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("errore esecuzione modifyCourceSection: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la modifica di una risorsa(cartella) di una sezione.
	 * @param resource l'oggetto contenente tutte le informazioni da aggiornare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String modifySectionResource(Resource resource) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE resources SET title=?, description=?"
					 + "WHERE id = ?";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, resource.getTitle());
			stmt.setString(2, resource.getDescription());
			stmt.setInt(3, resource.getId());
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("errore esecuzione modifySectionResource: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la cancellazione di una sezione/sotto-sezione di una corso.
	 * @param sectionId -- l'id della sezione da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteCourseSection(int sectionId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "DELETE FROM modules WHERE id = ?;";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, sectionId);
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore esecuzione changeUserType: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la cancellazione di una risorsa/cartella di una sezione. Il contenuto verrà pure cancellato.
	 * @param resourceId -- l'id della risorsa/cartella da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteSectionResource(int resourceId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "DELETE FROM resources WHERE id = ?;";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, resourceId);
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore esecuzione changeUserType: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la cancellazione di un file.
	 * @param fileId -- l'id del file da cancellare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String deleteResourceFile(int fileId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "DELETE FROM files WHERE id = ?;";
		String result = negative;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, fileId);
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore esecuzione changeUserType: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
	/**
	 * Esegue la modifica di un determinato profilo utente.
	 * @param user -- contenente tutti i dati da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	@SuppressWarnings("resource")
	public String modifyUserInfo(User user) {
	Connection conn = null;
	PreparedStatement stmt = null;
	String userQuery = "UPDATE users SET surname=?, first_name=?, email=?"
				+ " WHERE id=?;";
	String profileQuery = null;
	String result = negative;

	try {
		conn = ConnectionPool.getConnection();
		stmt = conn.prepareStatement(userQuery);
		stmt.setString(1, user.getSurname());
		stmt.setString(2, user.getName());
		stmt.setString(3, user.getEmail());
		stmt.setInt(4, user.getId());
		stmt.executeUpdate();
		
		if(user instanceof Student) {
			profileQuery = "UPDATE profiles SET degree_course_id=?, career_status=? WHERE user_id=?;";
			stmt = conn.prepareStatement(profileQuery);
			Student st = (Student) user;
			int degreeCourseId = select.selectDegreeCourseIdByName(st.getDegreeCourse());
			stmt.setInt(1, degreeCourseId);
			stmt.setString(2, String.valueOf(st.getCareerStatus()));
			stmt.setInt(3, user.getId());
			stmt.executeUpdate();
		}
		if(user instanceof Lecture || user instanceof Admin) {
			profileQuery = "UPDATE profiles SET department_id = ? WHERE user_id=?;";
			stmt = conn.prepareStatement(profileQuery);
			Lecture prof = (Lecture) user;
			int departmentId = select.selectDepartementIdByName(prof.getDepartment());
			stmt.setInt(1, departmentId);
			stmt.setInt(2, user.getId());
			stmt.executeUpdate();
		}
		result = positive;
	}catch(SQLException e) {
		logger.debug("errore esecuzione updateUsereInfo: "+e);
	}finally {
		closeResources(stmt, conn);
	}
	return result;
	}
	/**
	 * Modifica il tipo di utente
	 * @param userId dell'utente.
	 * @param userType il tipo a cui si vuole passare: 1--Admin, 2--Lecture, 3--Student
	 */
	public void modifyUserType(int userId, int userType) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "UPDATE users SET group_id = ? WHERE id = ?;";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userType);
			stmt.setInt(2, userId);
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione changeUserType: "+e);
		}finally {
			closeResources(stmt, conn);
		}
	}
	/**
	 * Esegue la registrazione di un utente ad un corso. 
	 * @param userId -- l'id di un utente.
	 * @param courseId -- l'id di un corso.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
	 */
	public String registrationUserAtCourse(int userId, int courseId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int userType = select.selectUserTypeById(userId);
		String query = "";
		String result = negative;
		
		if(userType == 2 || userType == 1) {
			query = "INSERT INTO lectures_courses(user_id, course_id) "+
					"VALUES(?, ?);";
		}else {
			if(userType == 3) {
				query = "INSERT INTO study_plans(user_id, course_id) "
					   +"VALUES(?, ?);";
			}
		}
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			stmt.setInt(2, courseId);
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("errore esecuzione registrationUserAtCourse: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}

	/**
	 * Aggiunge una sezione/sotto-sezione ad un determinato corso.
	 * @param section l'oggetto contenente tutte le informazione relative alla sezione(contiene anche l'id del corso a cui associare la sezione).
	 * @return int che rappresenta l'id assegnato dal database.
	 */
	public int addSectionToCourse(Section section) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int sectionId = 0;
		String query = "WITH insert AS(INSERT INTO modules(parent_id, title, description, is_active, active_from, active_to, course_id) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING id) SELECT id FROM insert;";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			
			if(section.getParentId() == null)
				stmt.setNull(1, java.sql.Types.INTEGER);
			else
				stmt.setInt(1, section.getParentId());
			
			stmt.setString(2, section.getTitle());
			stmt.setString(3, section.getDescription());
			stmt.setBoolean(4, section.isActive());
			stmt.setTimestamp(5, section.getActiveFrom());
			stmt.setTimestamp(6, section.getActiveTo());
			stmt.setInt(7, section.getCourseId());
			
			rs = stmt.executeQuery();
			while(rs.next()){
				sectionId = rs.getInt("id");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione addSectionToCourse: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return sectionId;
	}
	/**
	 * Aggiunge una cartella ad una sezione.
	 * @param resource -- contiene tutte le informazioni relative ad una risora/cartella.
	 * @return -- l'id, assegnato dal database, alla risorsa/cartella creata.
	 */
	public int addResourceToCourseSection(Resource resource) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int resourceId = 0;
		String query = "WITH insert AS(INSERT INTO resources(title, description, module_id) " + 
					   "VALUES(?, ?, ?) RETURNING id) SELECT id FROM insert;";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, resource.getTitle());
			stmt.setString(2, resource.getDescription());
			stmt.setInt(3, resource.getModuleId());
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				resourceId = rs.getInt("id");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore addResourceToCourseSection: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return resourceId;
	}
	/**
	 * Esegue la memorizzazione del file aggiunto ad una risorsa(cartella).
	 * @param resourceId -- l'id della cartella in cui viene aggiunto il file.
	 * @param path - il percorso al file.
	 * @return int -- l'id del file.
	 */
	public int addFileToCourseResource(int resourceId, String path) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int fileId = 0;
		String query = "WITH insert AS(INSERT INTO files(file, resource_id) VALUES(?, ?) RETURNING id) "
				+ "SELECT id FROM insert;";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, path);
			stmt.setInt(2, resourceId);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				fileId = rs.getInt("id");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore addResourceToCourseSection: "+e);
		}finally {
			closeResources(stmt, conn);
		}
		return fileId;
	}
	/**
	 * Esegue l'attivazione dell'account di un determinato utente, in seguito al primo accesso.
	 * @param userId -- l'id dell'utente.
	 * @param password la nuova password.
	 * @return una String con la conferma o negazione dell'operazione effettuata.
	 */
	public String activationUserAccount(int userId, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "WITH update AS("
				+ " UPDATE users SET password = ? WHERE id = ? RETURNING id) "
				+ " UPDATE profiles SET is_active = true WHERE user_id = (SELECT id FROM update);";
		String result = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, password);
			stmt.setInt(2, userId);
			
			stmt.executeUpdate();
			result = positive;
		}catch(SQLException e) {
			logger.debug("Errore activationUserAccount: "+e);
			result = negative;
		}finally {
			closeResources(stmt, conn);
		}
		return result;
	}
}


