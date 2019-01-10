package seatInUser;

import static seatInUser.Items.getServerConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import seatInServer.JDBC.Beans.Course;
import seatInServer.JDBC.Beans.Section;
import seatInServer.JDBC.Beans.User;

public class StudentCommands {

    protected Proxy server = getServerConnection();
    private static StudentCommands studCommands = new StudentCommands();

    protected StudentCommands() {

    }
    
    public static StudentCommands getInstance() {
    	if(studCommands == null)
    		studCommands = new StudentCommands();
    	return studCommands;
    }
    /**
     * Operazione di autenticazione di un utente.
     * @param email dell'utente.
     * @param password dell'utente.
     * @return  Object -- Se, i dati inseriti sono coretti, ritorna un oggetto di sotto tipo di User,
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
     * @return User contenente le informazioni relative all'utente autenticato.
     */
    public User viewUserProfileData(int userId) {
        String command = "ViewUserProfileData/"+userId;
        return (User) this.callServer(command);
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
     * Effettua l'invio di una mail, da parte dello studente, ad un professore/altro studente.
     * @param senderEmail -- la mail del mittente.
     * @param senderPassword -- la password del mittente.
     * @param recipientEmail -- la mail del destinatario.
     * @param emailSubject -- l'oggetto della mail.
     * @param emailBody -- il contenuto della mail.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String sendEmail(String senderEmail, String senderPassword, String recipientEmail, String emailSubject, String emailBody) {
        String command = "SendEmail/"+senderEmail+"/"+senderPassword+"/"+recipientEmail+"/"+emailSubject+"/"+emailBody;
        return (String) this.callServer(command);
    }
    /*
     * Il metodo generale usato per mandare le richieste al server.
     * Si basa sul mandare il comando e ricevere la risposta.
     * @return Object che può essere qualsiasi tipo di oggetto.
     * In locale(da dove viene eseguito l'invocazione del metodo) si esegue una cast al tipo aspettato.
     */
    protected Object callServer(String command) {
        server.sendCommand(command);
        return server.getResult();
    }
}
