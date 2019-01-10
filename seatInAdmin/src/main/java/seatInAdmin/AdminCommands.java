package seatInAdmin;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import static seatInAdmin.Items.getServerConnection;

public class AdminCommands {

    private Proxy server = getServerConnection();
    private static AdminCommands commands = new AdminCommands();

    private AdminCommands(){

    }
    
    public static AdminCommands getInstance() {
    	if(commands == null)
    		commands = new AdminCommands();
    	
    	return commands;
    }
    /**
     * Operazione di autenticazione di un utente.
     * @param email dell'utente.
     * @param password dell'utente.
     * @return Se, i dati inseriti sono coretti, ritorna un oggetto di tipo User,
     * contenente tutte le informazionei relative all'utente autenticato.
     * Se i dati sono sbagliti, ritorna una stringa che indica l'accesso negato(Esempio: DENIED).
     */
    public Object login(String email, String password) {
        String command = "Login/"+email+"/"+password;
        return this.callServer(command);
    }
    /**
     * Operazione di "ResetPassword" di un account già presente nel sistema.
     * @param email dell'utente.
     */
    public void resetPassword(String email) {
        String command = "ResetPassword/"+email;
        server.sendCommand(command);
    }
    /**
     * Esegue l'operazione di attivazione account al primo accesso.
     * @param userId -- l'id dell'utente.
     * @param password -- la nuova password inserita dall'utente.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String activationAccount(int userId, String password) {
        String command = "ActivationAccount/"+userId+"/"+password;
        return (String) this.callServer(command);
    }
    /**
     * Visualizza i dati relativi ad un determinato profilo utente, che può essere Studente o Professore.
     * @param userId -- l'id dell'utente.
     * @return User -- contenente le informazioni relative all'utente autenticato.
     */
    public User viewUserProfileData(int userId) {
        String command = "ViewUserProfileData/"+userId;
        return (User) this.callServer(command);
    }

    /**
     * Visualizza i dati relativi ad un determinato corso.
     * @param courseId -- l'id del corso.
     * @return Course -- contenente tutte le informazioni relative ad un determinato corso.
     */
    public Course viewCourseData(int courseId){
        String command = "ViewCourseData/"+courseId;
        return (Course) this.callServer(command);
    }
    /**
     * Visualizza l'elenco dei corsi presenti nel piano di studi, nel caso di uno studente.
     * Visualizza l'elenco dei corsi tenuti, nel caso di un professore.
     * @param userId -- l'id dell'utent, che può essere uno studente, o un professore.
     * @return Collection contiene tutte le informazioni relativi a tutti i corsi.
     */
    @SuppressWarnings("unchecked")
    public Collection<Course> viewCourseList(int userId) {
        String command = "ViewCourses/"+userId;
        return (Collection<Course>) this.callServer(command);
    }
    /**
     * Visualizza il contenuto di un corso.
     * Un corso è caratterizzato da sezioni. Una sezione può avere le cartelle, file o sotto-sezioni.
     * Sotto-sezione ha la medesima struttura di una sezione.
     * @param courseId -- l'id del corso.
     * @param isLecture -- Se la visualizzazione avviene da parte di un proffessore,il contenuto verrà 
     * visualizzato senza prendere in cosiderazione i modoficatori di visibilità.
     * @return Collection contenente tutte le informazioni relative a tutte le sezioni.
     */
    @SuppressWarnings("unchecked")
    public Collection<Section> viewCourseContent(int courseId, boolean isLecture) {
        String command = "ViewCourseContent/"+courseId+"/"+isLecture;
        return (Collection<Section>) this.callServer(command);
    }
    /**
     * Effettua l'iscrizione di un professore/Admin ad un determinato corso.
     * @param userId -- l'id dell'utente.
     * @param courseId -- l'id del corso.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String registrationAtCourse(int userId, int courseId) {
        String command ="RegistrationAtCourse/"+userId+"/"+courseId;
        return (String) this.callServer(command);
    }
    /**
     * Effettua il download di un file presente nei contenuti di un corso.
     * @param fileId -- l'id della file.
     * @param fileName -- il nome del file.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String downloadFile(int fileId, String fileName) {
        String result = "DENIED";
        String command = "DownloadFile/"+fileId;
        String path = "Files/";
        byte[] fileContent = (byte[]) this.callServer(command);
        try {
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdir();

            File file = new File(path+fileName);
            Files.write(file.toPath(), fileContent);
            result = "ACCEPT";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Effettua il download di una risorsa/cartella presente nei contenuti di una corso.
     * @param resourceId -- l'id della risorsa/cartella.
     * @param resourceName -- il nome della risorsa/cartella.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String downloadZip(int resourceId, String resourceName){
        String result = "DENIED";
        String command = "DownloadZip/"+resourceId;
        String directory = "Files/";
        String path = "Files/"+resourceName+".zip";
        server.sendCommand(command);
        byte[] zipContent = (byte[]) server.getResult();

        try {
        	File dir = new File(directory);
            if(!dir.exists())
                dir.mkdir();
            
            File zipFile = new File(path);
            Files.write(zipFile.toPath(), zipContent);
            result = "ACCEPT";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Manda al server una Collection contenente gli utenti da inserire nel data base.
     * @param path il percorso al file.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String regisrationUsersFromCSVFile(String path) {
        BasicCSVReader read = new BasicCSVReader();
        String command = "RegistrationFromCSVFile/";
        server.sendCommand(command);
        Collection<User> objects = read.readFile(path);
        server.sendCommand(objects);
        return (String)server.getResult();
    }

    /**
     * Esegue la modifica dei dati personali relative af un determianto user.
     * @param user l'oggetto contenente le modifiche da effettuare.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String modifyUserData(User user) {
        String command = "ModifyUserData/";
        server.sendCommand(command);
        server.sendCommand(user);
        return(String) server.getResult();
    }

    /**
     * Esegue l'unlock di un account di un determinato utente.
     * @param userId -- l'id dell'account di un utente.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String unlockAccount(int userId) {
        String command = "UnlockAccount/"+userId;
        return (String) this.callServer(command);
    }

    /**
     * Crea un istanza di un corso
     * @param course l'oggetto contenente tutti i dati relativi al corso.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String createCourseInstance(Course course){
        String command = "CreateCourseInstance/";
        server.sendCommand(command);
        server.sendCommand(course);
        return (String)server.getResult();
    }

    /**
     * Esegue la modifica dei ati relativi ad un determianto corso.
     * @param course l'oggetto contenente le informazioni da modificare.
	 * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String modifyCourseData(Course course) {
        String command = "ModifyCourseData/";
        server.sendCommand(command);
        server.sendCommand(course);
        return (String)server.getResult();
    }

    /**
     * Invia una mail a tutti gli studenti che seguono un determinato corso identificato da courseId.
     * @param senderEmail -- l'indirizo di posta eletronica del mittente.
     * @param senderPassword -- la password dell'accoubt della posta del mittente.
     * @param courseId -- l'id del corso, ai studenti del quale, verrà mandata la mail.
     * @param emailSubject -- il soggetto della mail.
     * @param emailBody -- il contenuto/messaggio della mail.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String sendEmailToCourseStudents(String senderEmail, String senderPassword, int courseId, String emailSubject, String emailBody) {
        String command ="sendEmailToCourseStudents/"+senderEmail+"/"+senderEmail+"/"+courseId+"/"+emailSubject+"/"+emailBody;
        return (String) this.callServer(command);
    }
    /**
     * Invia una mail ad una selezione di studenti.
     * @param senderEmail -- l'indirizzo di posta eletronica del mittente.
     * @param senderPassword -- la password dell'acoount della posta del mittente.
     * @param students -- gli studenti a cui verrà mandata la mail.
     * @param emailSubject -- il soggetto della mail.
     * @param emailBody -- il contenuto della mail.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String sendEmailToASelectionOfStudents(String senderEmail, String senderPassword, Collection<Integer> students, String emailSubject, String emailBody ) {
        String command = "sendEmailToASelectionOfStudents/"+senderEmail+"/"+senderPassword+"/"+emailSubject+"/"+emailBody;
        server.sendCommand(command);
        server.sendCommand(students);
        return (String) server.getResult();
    }
    /**
     * Visualizza il numero complessivo di utenti connessi che stanno visualizzando/integrando con i contenuti del corso.
     * @return int
     */
    public int viewTotalNumberConnectedUsers(){
        String command = "viewTotalNumberConnectedUsers/";
        return (int) this.callServer(command);
    }
    /**
     * Visualizza il numero complessivo di utenti che hanno effettuato il download di una o più risorse in intervalli temporali.
     * @param start -- inizio dell'itervallor.
     * @param end -- fine dell'intervallo.
     * @return int
     */
    public int viewTotalNumberOfUsersHaveDownloadOneOrMoreResourcesInTimeIntervals(Timestamp start, Timestamp end){
        String command = "usersHaveDownloadOneOrMoreResources/"+start+"/"+end;
        return (int) this.callServer(command);
    }
    /**
     * Derivare il tempo medio di connessione degli studenti alle pagine del corso.
     * @return double
     */
    public double viewAverageConnectionTimeOfStudentsToTheCoursePage(){
        String command = "averageConnectionTime/";
        return (double) this.callServer(command);
    }
    /**
     * Mostra il numero complessivo accessi per corso in una fascia temporale.
     * @param start inizio periodo temporale.
     * @param end fine periodo temporale.
     * @return int
     */
    public int viewTotalNumberOfAccessesPerCourseInTimeBand(Timestamp start, Timestamp end) {
        String command = "viewTotalNumberOfAccessesPerCourse/"+start+"/"+end;
        return (int) this.callServer(command);
    }
    /**
     * Deriva il tempo medio di connesione degli studenti per ogni corso.
     * @return HashMap contenente come chiave il corso e come valore il tempo di connessione per quel corso.
     */
    @SuppressWarnings("unchecked")
	public HashMap<Course, Double> viewAverageConnectionTimeOfStudentsForEachCourse(){
        String command = "viewAverageConnectionTimeOfStudents/";
        return  (HashMap<Course, Double>) this.callServer(command);
    }
    /**
     * Deriva il numero complessivo di download per ogni corso.
     * @return HashMap contenente come chiave il corso e come valore il numero di download per quel corso.
     */
    @SuppressWarnings("unchecked")
	public HashMap<Course, Integer> viewTotalNumberDownloadsForEachCourse(){
        String command = "viewTotalNumberDownloadsPerCourse/";
        return  (HashMap<Course, Integer>) this.callServer(command);
    }
    /**
     * Chiusura connessione al server.
     */
    public void close() {
    	String command = "Close/";
    	this.callServer(command);
    }
    /*
     * Il metodo generale usato per mandare le richieste al server.
     * Si basa sul mandare il comando e ricevere la risposta.
     * @return Object che può essere qualsiasi tipo di oggetto.
     * In locale(da dove viene eseguito l'invocazione del metodo) si esegue una cast al tipo aspettato.
     */
    private Object callServer(String command) {
        server.sendCommand(command);
        return server.getResult();
    }
}
