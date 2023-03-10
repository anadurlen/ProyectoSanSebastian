package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import dataAccess.HibernateDataAccess;
import dataAccess.IHibernateDataAccess;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import modelo.dominio.Event;
import modelo.dominio.Question;

/**
 * It implements the business logic as a web service.
 */

public class BLFacadeImplementation  implements BLFacade {
	private static BLFacadeImplementation facade;
	private IHibernateDataAccess dbManager;
	
	
	
	public BLFacadeImplementation() {
		this.dbManager = new HibernateDataAccess();
		
	}

	
		/*if (c.getDataBaseOpenMode().equals("initialize")) {
			
		    dbManager=new DataAccessInterface(new ObjectDbDAOManager());
			dbManager.initializeDB();
			dbManager.close();
			}
		*/
	
	
	/*
	 * public BLFacadeImplementation(DataAccessInterface da) {
	 * 
	 * System.out.
	 * println("Creating BLFacadeImplementation instance with DataAccess parameter"
	 * ); ConfigXML c=ConfigXML.getInstance();
	 * 
	 * if (c.getDataBaseOpenMode().equals("initialize")) { da.emptyDatabase();
	 * da.open(); da.initializeDB(); da.close();
	 * 
	 * } dbManager=da; }
	 */
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   
	
	
	public void setData(IHibernateDataAccess dataAccess) {
		this.dbManager=dataAccess;
	}
	
	
	public IHibernateDataAccess getData() {
		return dbManager;
	}
	
	
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    	
   
	public List<Event> getEvents(Date date) {
		List<Event> eventos = dbManager.getEvents(date);
		return eventos;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	 public List<Date> getEventsMonth(Date date) {
		
		List<Date>  dates=dbManager.getEventsMonth(date);
		
		return dates;
	}
	 
	 
	 public List<Event> allEvents(){
		 
		 List<Event> eventos =this.dbManager.allEvents();
		 return eventos;
	 }
	 
	
	
	
	


	

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    
	 public void initializeBD(){
    	
		this.dbManager.initializeDB();
		
	}


	 public static BLFacadeImplementation getInstance()  {		
			System.out.println("Creating BLFacadeImplementation instance");
			if (facade==null) {
				facade=new BLFacadeImplementation();
				
				
			}
			
			return facade;
	 
	


}
}

