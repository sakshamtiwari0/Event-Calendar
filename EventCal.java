import java.util.*;
public class EventCal extends Exception
{
String events[][];//2-D Array for days and events.
Scanner instance = new Scanner(System.in);//For input in functions.
	EventCal()
	{
		super();
	}
	EventCal(String exc)
	{
		super(exc);//Custom Exception Handling.
	}
	EventCal(int days)
	{
		int i, j;
		events = new String[days][10];//Initialize the object array to 10 events per day.
		for(i=0;i<days;i++)
		{
			for(j=0;j<10;j++)
			{
				events[i][j] = "Empty";
			}
		}
	}
	int DelEvent(EventCal obj, int dd)
	{
		int redir = 0, temp = 0, i = 0;
		redir = obj.ViewEvent(dd);//Checking redirect condition.
		if(redir == 0)
		{
			return 0;//Return for no events for given date.
		}
		else if(redir != 0)
		{
		System.out.print("\nEnter the event number to be deleted: ");
			temp = instance.nextInt();
			if(temp>redir)
				{
				return 10;
				}
			for(i=temp;i<10;i++)
		{
			events[dd][i - 1] = events[dd][i];
		}
		events[dd][--i] = "Empty";//Initialize the cell back to empty after event deletion.
		}
		return 1;
	}
	int ViewEvent(int dd)
	{
		String eve[];
		int i, count = 0, index = 0;
		for(i=0;i<10;i++)
		{
			if(events[dd][i].equals("Empty") == false)
				count++;//Increase count if events are found.
			if(events[dd][i].equals("Empty") == true)
			{
				index = i;
				break;
			}
		}
		if(count == 0)
		{
			System.out.println("\nNo events have been registered for the provided date.");
		}
		else
		{
		System.out.println("\nFound "+count+" event(s) for the entered date, listed below:\n");
		for(i=0;i<index;i++)
		{
			eve = (events[dd][i]).split(";");
			System.out.println("\n"+(i+1)+". Event: "+eve[0]);
			System.out.println("   Description: "+eve[1]);
			System.out.println("   Timing: "+eve[2]);
		}
		}
		return count;
	}
	int AddEvent(int dd)
	{
		int c = 0, i, index = 0;
		String event, temp;
		try {
			for(i=0;i<10;i++)
			{
				if(events[dd][i].equals("Empty") == false)
					c++;//Reaching the index of first empty cell to add event.
				if(events[dd][i].equals("Empty") == true)
				{
					index = i;
					break;
				}
			}
		if(c == 10)
		{
			throw new EventCal("All Booking slots full.");//Maximum number of events reached.
		}
		}
		catch(Exception exc1)
		{
			System.out.println("\nException while Adding event: "+exc1);
		}
		if(c != 10)
		{
			System.out.print("\nEnter event title: ");
			event = instance.nextLine();
			System.out.print("\nEnter event description: ");
			temp = instance.nextLine();
			event += ";"+temp;
			System.out.print("\nEnter event timestamps([FROM HH:MM]-[UPTO HH:MM]): ");
			temp = instance.nextLine();
			event += ";"+temp;
			System.out.println("\nEvent details collected: "+event);
			events[dd][index] = event;
			return 1;
		}
		else
			return 0;
	}
	String GetDate()//To check and validate date and proper day-month combination.
	{
		int dd = 0, mm = 0, exc = 0;
		String date = "";
		try{
			System.out.println("Enter date as requested.\n");
			System.out.print("Month: ");
			mm = instance.nextInt();
			mm = mm-1;
			if(mm>11 || mm<0)
			{
				exc = 1;//Validating month.
				throw new EventCal("Invalid month value.");
			}
			System.out.print("Day: ");
			dd = instance.nextInt();
			dd = dd-1;
			if((mm == 0 || mm == 2 || mm == 4 || mm == 6 || mm == 7 || mm == 9 || mm == 11) && (dd>30 || dd<0))
			{
				 exc = 1;//Validating day-month combination.
				throw new EventCal("Invalid month-day combination.");
			}
			if((mm == 1) && (dd>27 || dd<0))
			{
				 exc = 1;
				throw new EventCal("Invalid month-day combination.");
			}
			if((mm == 3 || mm == 5 || mm == 8 || mm == 10) && (dd>29 || dd<0))
			{
				 exc = 1;
				throw new EventCal("Invalid month-day combination.");
			}
		}catch(Exception exc2)
		{			
			System.out.println("\nException while date input: "+exc2);
		}
		date = Integer.toString(dd)+";"+Integer.toString(mm)+";"+Integer.toString(exc);//Adding exception condition to date string.
		return date;
	}
	public static void main(String []args)throws EventCal
	{
		Scanner in = new Scanner(System.in);
		EventCal months[] = new EventCal[12];
		EventCal obj = new EventCal();
		int c, i, dd = 0, mm = 0, mode = 0, redir, temp, exc = 0;
		String admin, dt[];
		char adminflag = 'N', userflag = 'N';
		for(i=0;i<12;i++)
		{
			if(i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11)
			{
				EventCal ob = new EventCal(31);
				months[i] = ob;
			}
			else if(i == 1)
			{
				EventCal ob = new EventCal(28);
				months[i] = ob;
			}
			else
			{
				EventCal ob = new EventCal(30);
				months[i] = ob;
			}
		}
		System.out.println("*** Welcome to Event Calendar for 2019 ***");
		do{
			System.out.print("\n\n1. Admin login\n2. User login\n3. Exit\nEnter your choice to continue: ");
			try{
				mode = in.nextInt();
				if(mode == 1)//Operation choice for user mode.
				{
					System.out.print("\nEnter Administrator password: ");
					try{
					admin = in.next();
					if(admin.equals("Admin") == true)
					{
						System.out.println("Administrator session active.");
						do {
						System.out.print("\nWelcome Administrator.\n\n1. Add event\n2. Delete event\n3. View all events\nEnter your choice to continue: ");
						c = in.nextInt();
						switch(c)
						{
						case 1:
						System.out.println("You have selected 'Add event'.\n");
						dt = (obj.GetDate()).split(";");//Get date string from method.
						dd = Integer.parseInt(dt[0]);
						mm = Integer.parseInt(dt[1]);
						exc = Integer.parseInt(dt[2]);
						System.out.println("\nDate entered(DD-MM-YYYY)s: "+(dd+1)+"-"+(mm+1)+"-2019.");
						if(exc == 1)
						{
							exc = 0;
							break;
						}
						else if(exc == 0)
						{
						redir = months[mm].AddEvent(dd);//Getting redirect condition.
						if(redir == 1)
						{
							System.out.println("\nEvent added successfully.");
							break;
						}
						else
						if(redir == 0)
						{//Redirect if 10 events are full.
							System.out.print("\nEnter 1 to delete an event from the same date, 0 to cancel: ");
							try{
								temp = in.nextInt();
								if(temp == 1)
								{
									months[mm].DelEvent(months[mm], dd);
									break;
								}
								else if(temp == 0)
									break;
								else
									throw new EventCal("Invalid choice entered.");
							}
							catch(Exception exc3)
							{
								System.out.println("\nException while entering deletion choice: "+exc3);
							}
						}
						}
						case 2:
							System.out.println("You have selected 'Delete event'.\n");
							dt = (obj.GetDate()).split(";");
							dd = Integer.parseInt(dt[0]);
							mm = Integer.parseInt(dt[1]);
							exc = Integer.parseInt(dt[2]);
							System.out.println("\nDate entered(DD-MM-YYYY)s: "+(dd+1)+"-"+(mm+1)+"-2019.");
							if(exc == 1)
							{
								exc = 0;
								break;
							}
							redir = months[mm].DelEvent(months[mm], dd);
							if(redir == 0)//Date invalid. Other operations cancelled.
							{
								System.out.println("\nEvent Deletion cancelled.");
								break;
							}
							else
								if(redir == 10) {
									try {
										throw new EventCal("Invalid index entered.");
									}
									catch(Exception exc6) {
										System.out.println("Exception while choice input: "+exc6);
									}
									break;
								}
								else if(redir == 1)
							{
								System.out.println("\nEvent deleted successfully. After deletion, viewing all events:");
								months[mm].ViewEvent(dd);
								break;
							}
						case 3:
							System.out.println("You have selected 'View all events'.\n");
							dt = (obj.GetDate()).split(";");
							dd = Integer.parseInt(dt[0]);
							mm = Integer.parseInt(dt[1]);
							exc = Integer.parseInt(dt[2]);
							System.out.println("\nDate entered(DD-MM-YYYY)s: "+(dd+1)+"-"+(mm+1)+"-2019.");
							if(exc == 1)
							{
								exc = 0;
								break;
							}
							else if(exc == 0)
							{
								redir = months[mm].ViewEvent(dd);//List out events for selected date.
								break;
							}
							default:
							try{
								throw new EventCal("Invalid choice entered.");
							}
							catch(Exception exc6){
								System.out.println("\nException while choice input: "+exc6);
							}
						}
							System.out.print("\nContinue as Administrator(Y/N)?: ");
							adminflag = in.next().charAt(0);
							if(adminflag == 'N')
								System.out.println("Administrator logged out successfully.");
						}while(adminflag != 'N');
					}
					else
					{
						throw new EventCal("Incorrect password entered.");
					}
					}//Catch wrong password exception.
					catch(Exception exc7)
					{
						System.out.println("\nException while password input: "+exc7);
					}
				}
				else if(mode == 2)//Mode 2 is for user.
				{
					System.out.println("User session active.");
					do {
					System.out.println("\nWelcome User.\n");
					dt = (obj.GetDate()).split(";");//User can only view events.
					dd = Integer.parseInt(dt[0]);
					mm = Integer.parseInt(dt[1]);
					exc = Integer.parseInt(dt[2]);
					System.out.println("\nDate entered(DD-MM-YYYY)s: "+(dd+1)+"-"+(mm+1)+"-2019.");
					if(exc == 1)
					{
						exc = 0;
						System.out.println("\nUser session failed.");
					}
					else if(exc == 0)
					{
						redir = months[mm].ViewEvent(dd);
						System.out.println("\nUser requested task completed.");
					}
					System.out.print("\nContinue User Session(Y/N)?: ");
					userflag = in.next().charAt(0);
					if(userflag == 'N')
						System.out.println("User session ended.");
					}while(userflag != 'N');
				}
				else if(mode == 3)
				{
					in.close();
					System.out.println("\nThank you for using Event Calendar 2019.");
					System.exit(0);
				}
				else
				{
					throw new EventCal("Invalid choice entered.");
				}
			}
			catch(Exception exc7)
			{
				System.out.println("\nException while choice input: "+exc7);
			}
		}while(true);
}
}