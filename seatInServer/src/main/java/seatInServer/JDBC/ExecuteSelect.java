package seatInServer.JDBC;

import static seatInServer.JDBC.QueryResources.closeResources;
import static seatInServer.Utilities.Utilities.getCurrentClassName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seatInServer.JDBC.Beans.Admin;
import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.ResourceFile;
import seatInServer.JDBC.Beans.Lecture;
import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.Student;
import seatInServer.JDBC.Beans.User;
import seatInServer.JDBC.ConnectionPool.ConnectionPool;

/*
 * Seguente classe rappresenta il pattern "Singleton".
 * Il metodo "getInstance" esegue un controllo dell'esistenza dell'oggetto per evitare gli errori
 * nel caso qualcuno cancellasse l'unico oggetto della classe stessa(Per ulteriori informazioni studiare il pattern "Singelton")
 */
public class ExecuteSelect {
	
	private final static Logger logger = LogManager.getLogger(getCurrentClassName());
	private static ExecuteSelect select;
	
	private ExecuteSelect() {
		
	}
	/**
	 * @return il riferimento all'oggetto della classe ExecuteSelect
	 */
	public static ExecuteSelect getInstance() {
		if(select == null) {
			select = new ExecuteSelect();
		}		
		return select;
	}
	/**
	 * Seleiona il tipo, di un determinato utente. 
	 * @param userId -- l'id dell'uente.
	 * @return 1--Admin, 2--Lecture, 3--Student.
	 */
	protected int selectUserTypeById(int userId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT group_id FROM users WHERE id = ?";
		int userType = 0;
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				userType = rs.getInt("group_id");
			}
		}catch(SQLException e) {
			logger.debug("errore esecuzione selectUserType: "+e);
		}finally {
			
		}
		return userType;
	}
	/**
	 * Seleziona dal data base un utente secondo la mail passato come argomento.
	 * Il metodo viene usato nel momento del login di un utente. 
	 * Il seguente metodo ritorna i dati generali di un determinato utente, senza i dati specifici.
	 * @param email dello user.
	 * @return User con tutti i dati relativi all'utente. 
	 * @throws IllegalArgumentException se l'argomento passato e null.
	 */
	public User selectUserByEmail(String email) {		
		if(email == null)
			throw new IllegalArgumentException();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		
		String	query = "SELECT u.id, u.surname, u.first_name, u.email, u.password, g.name, p.is_active, p.wrong_logins, p.activation_code "
				+" FROM profiles p INNER JOIN users u ON p.user_id=u.id INNER JOIN groups g ON u.group_id=g.id "
				+" WHERE u.email=?;";
				
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			while(rs.next()) {	
				user = new User(rs.getInt("id"));
				user.setSurname(rs.getString("surname"));
				user.setName(rs.getString("first_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getString("name"));
				user.setActive(rs.getBoolean("is_active"));
				user.setWrongLogins(rs.getInt("wrong_logins"));
				user.setActivationCode(rs.getString("activation_code"));	
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectUserByMeail: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return user;
	}
	/**
	 * Estrae le informazioni relativi ad un determinato utente(indipendentemente dal suo tipo: Admin, Lecture o Student).
	 * @param userId l'id dell'utente.
	 * @return User contenente le informazioni relative all'utente.
	 */
	public User selectUserById(int userId) {		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int userType = this.selectUserTypeById(userId);
		String query = " ";
		User result = null;

		if(userType == 3) {
			query = "SELECT u.id, u.surname, u.first_name, u.email, u.password, g.name as type, p.is_active, p.activation_code, p.created, p.career_status, d.name "
					+" FROM profiles p INNER JOIN users u ON p.user_id=u.id INNER JOIN degree_courses d ON p.degree_course_id=d.id INNER JOIN groups g ON u.group_id=g.id "
					+" WHERE u.id = ?" ;
		}else {
			if(userType == 2 || userType == 1) {
				query = "SELECT u.id, u.surname, u.first_name, u.email, u.password, g.name as type, p.is_active, p.activation_code, d.name "
						+ " FROM profiles p INNER JOIN users u ON p.user_id=u.id INNER JOIN departments d ON p.department_id = d.id INNER JOIN groups g ON u.group_id=g.id"
						+ " WHERE u.id = ? ;";
			}else {
				logger.debug("errore identificazione del tipo di utente: selectUserById!!");
			}
		}
		try {			
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(userType == 3) {
					Student student = new Student(rs.getInt("id"));					
					String degreeCourse = rs.getString("name");
					if(degreeCourse != null)
						student.setDegreeCourse(degreeCourse);
					student.setRegistrationYear(rs.getTimestamp("created"));
					Character status = rs.getString("career_status").charAt(0);
					if(status != null)
						student.setCareerStatus(status);
					result = (User)student;
				}
				if(userType == 2) {
					Lecture lecture = new Lecture(rs.getInt("id"));
					String department = rs.getString("name");
					if(department != null)
						lecture.setDepartment(rs.getString("name"));
					result = (User)lecture;
				}
				if(userType == 1) {
					Admin admin = new Admin(rs.getInt("id"));
					String department = rs.getString("name");
					if(department != null)
						admin.setDepartment(rs.getString("name"));
					result = (User)admin;
				}

				result.setSurname(rs.getString("surname"));
				result.setName(rs.getString("first_name"));
				result.setEmail(rs.getString("email"));
				result.setPassword(rs.getString("password"));
				result.setUserType(rs.getString("type"));
				result.setActive(rs.getBoolean("is_active"));
				result.setActivationCode(rs.getString("activation_code"));
			}	
		}catch(SQLException e) {
			logger.debug("Errore esecuzione seletUserById: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
     * Visualizza i dati relativi ad un determinato corso.
     * @param courseId -- l'id del corso.
     * @return Course -- contenente tutte le informazioni relative ad un determinato corso.
     */
	public Course selectCourseById(int courseId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT c.id, c.name, c.description, c.activation_date, c.is_active, d.name AS dep "
					 + "FROM courses c, degree_courses d WHERE c.id = ?";
		Course course = null;
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setDescription(rs.getString("description"));
				course.setActivationDate(rs.getTimestamp("activation_date"));
				course.setActive(rs.getBoolean("is_active"));
				course.setDegreeCourse(rs.getString("dep"));
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectCourseById: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return course;
	}
	/**
	 * Ricava l'id del corso di laurea di riferimento di un determinato corso.
	 * Il metodo viene usato nel momento di registrazione di un corso o registrazione di uno studente(ogni studente ha il corso di laurea di riferimento).
	 * @param degreeCourseName -- il nome del corso di laurea che si vuole associare ad un corso o ad uno studente.
	 * @return Integer -- l'id del corso di laurea di riferimento(può essere null se il corsi non esiste).
	 */
	public Integer selectDegreeCourseIdByName(String degreeCourseName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT id FROM degree_courses WHERE name = ?";
		Integer id_degreeCourse = null;
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, degreeCourseName);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				id_degreeCourse = rs.getInt("id");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectDegreeCourseByName: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return id_degreeCourse;
	}
	/**
	 * Ricava l'id di un determinato dipartimento al quale si vuole associare un determinato utente.
	 * Il metodo viene usato nel momento di registrazione di un utente di tipo Lecture o Admin.
	 * @param departmentName nome del dipartimento.
	 * @return Integer -- l'id del dipartimento(può essere null se il dipartimento non esiste).
	 */
	public Integer selectDepartementIdByName(String departmentName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT id FROM departments WHERE name = ?";
		Integer departmentId = null;
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, departmentName);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				departmentId = rs.getInt("id");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectDepartementByName: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return departmentId;
	}
	/**
	 * Seleziona tutte le mail di tutti i professori che tengono un determinato corso.
	 * @param courseId -- l'id del corso tenuto da uno o più professori.
	 * @return Collection contenente le mail dei professori in question.
	 */
	public Collection<String> selectLecturesEmailByCourseId(int courseId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT u.email "
					 + "FROM users u INNER JOIN lectures_courses l ON u.id=l.user_id INNER JOIN courses c ON l.course_id = c.id "
					 + "WHERE c.id = ?;";
		Collection<String> lecturesEmail = new LinkedList<String>();
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				lecturesEmail.add(rs.getString("email"));
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectLectureEmailByCourseId: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return lecturesEmail;
	}
	/**
	 * Seleziona tutti gli studenti che seguono un determinato corso
	 * @param courseId del corso.
	 * @return Collection contenente le mail degli studenti che seguono il corso.
	 */
	public Collection<String> selectAllStudentsWhoFollowDeterminedCourse(int courseId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT users.email " +  
					   "FROM users, courses " +  
					   "WHERE users.group_id=3 AND courses.id=? AND courses.is_active;";
		Collection<String> result = new LinkedList<String>();
		try {
			conn = ConnectionPool.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			rs = stmt.executeQuery();
		
			while(rs.next()) {
				result.add(rs.getString("email"));
			}
			
		}catch(SQLException e) {
			logger.debug("Errore: esecuzione allStudentsWhoFollowDeterminedCourse: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona tutti i corsi attivi. Il metodo viene usato per il calcolo della statistica.
	 * @return Collection contenente le informazioni relative ad ogni corso.
	 */
	public Collection<Course> selectAllCourses() {
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM courses;";
		
		Collection<Course> result = new LinkedList<Course>();
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		
			while(rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setDescription(rs.getString("description"));
				course.setActivationDate(rs.getTimestamp("activation_date"));
				course.setActive(rs.getBoolean("is_active"));
				course.setDegreeCourse(rs.getString("name"));
				result.add(course);
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllCourses: "+e.getMessage());
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona tutti i corsi seguiti/sostenuti da uno Student/Lecture/Admin che sono attualemente attivi.
	 * @param userId -- l'id dell'utente.
	 * @return Collection contenente tutte le informazioni relative ai corsi.
	 */
	public Collection<Course> selectAllCoursesLiableByUser(int userId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int userType = this.selectUserTypeById(userId);
		String query = " ";
		
		if(userType == 3) {
			query = "SELECT c.id, c.name AS courseName, c.description, c.activation_date, c.is_active, d.name " + 
					"FROM study_plans s, courses c, degree_courses d " + 
					"WHERE s.user_id = ? AND s.course_id = c.id AND c.degree_course_id = d.id ORDER BY c.id;";
		}else {
			if(userType == 2 || userType == 1) {
				query = "SELECT c.id, c.name AS courseName, c.description, c.activation_date, c.is_active, d.name " + 
						"FROM lectures_courses l, courses c, degree_courses d " + 
						"WHERE l.user_id = ? AND l.course_id = c.id AND c.degree_course_id = d.id ORDER BY c.id;";
			}else {
				logger.debug("Utente non identificabile!!!...");
			}
		}
		Collection<Course> result = new ArrayList<Course>();
		try {
			conn = ConnectionPool.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
		
			while(rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("courseName"));
				course.setDescription(rs.getString("description"));
				course.setActivationDate(rs.getTimestamp("activation_date"));
				course.setActive(rs.getBoolean("is_active"));
				course.setDegreeCourse(rs.getString("name"));
				result.add(course);
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllCoursesLiableByUser: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona tutte le sezioni di un determinato corso.
	 * @param courseId -- l'id del corso.
	 * @return Collection contenente tutte le informazioni relative alle sezione e al loro contenuto.
	 */
	public Collection<Section> selectAllCourseSections(int courseId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM modules WHERE course_id = ? ORDER BY id; ";
		Collection<Section> result = new ArrayList<Section>();
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Section section = new Section();
				section.setId(rs.getInt("id"));
				section.setParentId(rs.getInt("parent_id"));
				section.setTitle(rs.getString("title"));
				section.setDescription(rs.getString("description"));
				section.setActive(rs.getBoolean("is_active"));
				section.setActiveFrom(rs.getTimestamp("active_from"));
				section.setActiveTo(rs.getTimestamp("active_to"));
				section.setCourseId(rs.getInt("course_id"));
				Collection<Resource> resources = this.selectAllSectionResources(section.getId());
				section.setResources(resources);
				result.add(section);
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllCourseSections: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona tutte le cartelle di una determianta sezione.
	 * @param sectionId -- l'id della sezione.
	 * @return Collection contenente tutte le informazioni relative alle cartelle e al loro conenuto di una determinata sezione.
	 */
	public Collection<Resource> selectAllSectionResources(int sectionId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM resources WHERE module_id = ?";
		Collection<Resource> result = new LinkedList<Resource>();
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, sectionId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Resource resource = new Resource();
				resource.setId(rs.getInt("id"));
				resource.setTitle(rs.getString("title"));
				resource.setDescription(rs.getString("description"));
				resource.setModuleId(rs.getInt("module_id"));
				Collection<ResourceFile> files = this.selectAllResourceFiles(resource.getId(), false);
				resource.setFiles(files);
				result.add(resource);
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllSectionResource: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona tutte i file contenuti in una determinata cartella.
	 * Nel caso si tratti di uno zip, vengono selezionati i percorsi completi ai file.
	 * Nel caso si tratti della visualizzazione dei file, vengono selezionati solo i nomi senza percorsi specificati.
	 * @param resourceId -- l'id della cartella.
	 * @param isZip -- indica se si tratta di una cartella o di un singolo file. 
	 * @return Collection contenente tutte le informazioni relative ai file contenuti in una cartella.
	 */
	public Collection<ResourceFile> selectAllResourceFiles(int resourceId, boolean isZip){
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection<ResourceFile> result = null;
		String query = "SELECT * FROM files WHERE resource_id = ?";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, resourceId);
			rs = stmt.executeQuery();
					
			result = new LinkedList<ResourceFile>();
			while(rs.next()) {
				ResourceFile file = new ResourceFile();
				file.setId(rs.getInt("id"));
				if(isZip) {
					file.setName(rs.getString("file"));
				}else {
					String[] path = rs.getString("file").split("/");
					int i = path.length-1;
					file.setName(path[i]);
				}
				
				file.setResourceId(rs.getInt("resource_id"));
				result.add(file);
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllResourceFile: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Seleziona il persorso completo ad un determinato file.
	 * @param fileId -- l'id del file.
	 * @return String -- il percorso completo al file.
	 */
	public String selectPathToFile(int fileId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT file FROM files WHERE id = ?";
		String result = null;
		
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, fileId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getString("file");
			}
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAllSectionOfCourse: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Mostra il numero complessivo di untenti che stanmo visualizzando/integrando con i contenuti
	 * del corso.
	 * @return int il numero di utenti attivi.
	 */
	public int selectTotalNumberConnectedUsers() {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT count(profiles.user_id) "+
					   " FROM users INNER JOIN profiles ON users.id=profiles.user_id "+
					   " WHERE is_logged_in;";
		int result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt("count");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectTotalNumberConnectedUsersAreViewingCourseContent: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		
		return result;
	}
	/**
	 * Mostra il numero complessivo di utenti che hanno effettuato il download di una o più
	 * risorse in intervalli temporali.
	 * @param start -- inizio dell'intervallo.
	 * @param end -- fine dell'intervallo.
	 * @return int -- numero di utenti.
	 */
	public int selectTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(Timestamp start, Timestamp end) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT count(file_downloads.user_id) " + 
				       " FROM file_downloads " +
					   " WHERE date BETWEEN ? AND ?;";
		int result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setTimestamp(1, start);
			stmt.setTimestamp(2, end);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt("count");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Derivare il tempo medio di connessione degli studenti alle pagine del corso.
	 * @return double -- tempo medio.
	 */
	public double selectAverageConnectionTimeOfStudentsToTheCoursePage() {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT AVG(file_downloads.user_id) "+
					   " FROM course_stats, file_downloads "+
				       " WHERE file_downloads.date BETWEEN '2018-11-12' AND '2018-10-10';";

		double result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				result = rs.getDouble("avg");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione getAverageConnectionTimeOfStudentsToTheCoursePage: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Mostra il numero complessivo accessi per corso in una fascia temporale.
	 * @param start inizio periodo temporale.
	 * @param end fine periodo temporale.
	 * @return int numero accessi.
	 */
	public int selectTotalNumberOfAccessesPerCourseInTimeBand(Timestamp start, Timestamp end) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT count(course_stats.user_id) " +  
					   " FROM course_stats " + 
					   " WHERE logged_in BETWEEN ? AND ?;";
		int result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setTimestamp(1, start);
			stmt.setTimestamp(2, end);
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				result = rs.getInt("count");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectTotalNumberOfAccessesPerCourseInTimeBand: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Mostra il tempo medio di connesione degli studenti per corso.
	 * @param courseId -- l'id del corso.
	 * @return double -- il tempo medio di connessione.
	 */
	public double selectAverageConnectionTimeOfStudentsPerCourse(int courseId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT AVG(time_conn) " +
					  " FROM (" +
					  "	SELECT id, SUM(logged_out - logged_in) AS time_conn" +
					  "	FROM course_stats" +
					  "	WHERE course_id=? AND logged_out IS NOT NULL" +
					  "	GROUP BY id) as innerq;";
		double result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				result = rs.getDouble("avg");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectAverageConnectionTimeOfStudentsPerCourse: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	/**
	 * Mosrtra il numero complessivo di download per corso.
	 * @param courseId -- l'id del corsi
	 * @return int -- numero download.
	 */
	public int selectTotalNumberDownloadsForEachCourse(int courseId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) " + 
					   "FROM file_downloads " + 
					   "WHERE id IN ( " +
					   		"SELECT files.id " +
					   		"FROM resources " +
					   		"INNER JOIN modules ON resources.module_id=modules.id " +
					   		"INNER JOIN files ON resources.id=files.resource_id " +
					   		"WHERE modules.course_id=?);";
		int result = -1;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, courseId);
			
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				result = rs.getInt("count");
			}
			
		}catch(SQLException e) {
			logger.debug("Errore esecuzione selectTotalNumberDownloadsForEachCourse: "+e);
		}finally {
			closeResources(rs, stmt, conn);
		}
		return result;
	}
	
}			

