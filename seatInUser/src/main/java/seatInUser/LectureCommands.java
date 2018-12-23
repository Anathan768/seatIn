package seatInUser;

import seatInServer.JDBC.Beans.Resource;
import seatInServer.JDBC.Beans.Section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Collection;

public class LectureCommands extends StudentCommands{

    private Proxy server = super.server;

    public LectureCommands() {

    }
    /**
     * Aggiunge una sezione/sotto-sezione ad un corso.
     * @param section -- l'oggetto contenente tutte le informazioni relative alla sezione/sotto-sezione.
     * @return Section -- lo stesso oggetto con l'id assegnato dal database.
     */
    public int addSectionToCourse(Section section) {
        String command = "addSectionToCourse/";
        server.sendCommand(command);
        server.sendCommand(section);
        return (int) server.getResult();
    }

    /**
     * Aggiunge una risorsa/cartella ad una sezione.
     * @param resource -- l'oggetto contenente tutte le informazioni relative alla risorsa/cartella.
     * @return int -- l'id, assegnato dal database, alla risorsa creata.
     */
    public int addResourceToSection(Resource resource) {
        String command = "addResourceToSection/";
        server.sendCommand(command);
        server.sendCommand(resource);
        return (int) server.getResult();
    }
    /**
     * Aggiunge un file ad una resorsa/cartella. Esegue l'upload.
     * @param resourceId -- l'id della cartella in cui si vuole aggiungere un file.
     * @param path -- il percorso dove si trova il file(pc del client).
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public int addFileToResource(int resourceId, String path) {
        File file;
        String fileName = null;
        byte[] content = null;
        try {
            file = new File(path);
            if(!file.exists())
                throw new FileNotFoundException();

            String[] name = path.split("/");
            int i = name.length-1;
            fileName = name[i];
            content = Files.readAllBytes(file.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        String command = "addFileToResource/"+resourceId+"/"+fileName;
        server.sendCommand(command);
        server.sendCommand(content);
        return (int) server.getResult();
    }
    /**
     * Esegue la modifica di una sezione/sotto-sezione di un corso.
     * @param section -- l'oggeto contenente le informazioni relative ad una sezione da modificare.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String modifyCourseSection(Section section) {
        String command = "modifyCourseSection/";
        server.sendCommand(command);
        server.sendCommand(section);
        return (String) server.getResult();
    }
    /**
     * Esegue la modifica di una risorsa/cartella di una sezione.
     * @param resource -- l'oggetto contenente tutte le informazione relative alla cartrlla.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String modifySectionResource(Resource resource) {
        String command = "modifySectionResource/";
        server.sendCommand(command);
        server.sendCommand(resource);
        return (String)server.getResult();
    }

    /**
     * Esegue la cancellazione di una sezione/sotto-sezione. Il contenuto verrà cancellato pure.
     * @param sectionId -- l'id della sezione da cancellare.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String deleteCourseSection(int sectionId){
        String command = "deleteCourseSection/"+sectionId;
        return (String) super.callServer(command);
    }

    /**
     * Esegue la cancellazione di una risorsa/cartella di una sezione.
     * @param resourceId -- l'id della risorsa/cartella.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String deleteSectionResource(int resourceId){
        String command = "deleteSectionResource/"+resourceId;
        return (String) super.callServer(command);
    }

    /**
     * Esegue la cancellazione di un file.
     * @param fileId -- l'id del file da cancellare.
     * @return String che indica l'operazione andata a buon fine o fallita.
     * Esempio: ACCEPT - andata a buon fine, DENIED - fallita.
     */
    public String deleteResourceFile(int fileId){
        String command = "deleteResourceFile/"+fileId;
        return (String) super.callServer(command);
    }
    /**
     * Visualizza il contenuto di un corso, dal punto di vista di uno studente.
     * Si presuppone che il seguente metodo verrà utilizato da un professore per vedere il contenuto di un corso
     * in seguito alla modifica di visibilità.S
     * @param courseId -- l'id del corso.
     * @return Collection contenente tutte le sezioni di un determinato corso.
     */
    public Collection<Section> viewCourseContentByStudent(int courseId){
        return super.viewCourseContent(courseId, false);
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
        return (String) super.callServer(command);
        //TODO da testare.
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
        //TODO da testare.
    }
    /**
     * Visualizza il numero complessivo di utenti connessi che stanno visualizzando/integrando con i contenuti del corso.
     * @return int
     */
    public int viewTotalNumberConnectedUsers(){
        String command = "viewTotalNumberConnectedUsers/";
        return (int) super.callServer(command);
    }
    /**
     * Visualizza il numero complessivo di utenti che hanno effettuato il download di una o più risorse in intervalli temporali.
     * @param start -- inizio dell'itervallor.
     * @param end -- fine dell'intervallo.
     * @return int
     */
    public int viewTotalNumberOfUsersHaveDownloadOneOrMoreResoursesInTimeIntervals(Timestamp start, Timestamp end){
        String command = "usersHaveDownloadOneOrMoreResources/"+start+"/"+end;
        return (int) super.callServer(command);
    }
    /**
     * Derivare il tempo medio di connessione degli studenti alle pagine del corso.
     * @return double
     */
    public double viewAverageConnectionTimeOfStudentsToTheCoursePage(){
        String command = "averageConnectionTime/";
        return (double) super.callServer(command);
    }
}
