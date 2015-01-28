package it.progess.invoicecreator.util.accounting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;

import it.progess.invoicecreator.dao.AccountingDao;
import it.progess.invoicecreator.dao.RegistryDao;
import it.progess.invoicecreator.exception.GecoException;
import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Deadline;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Paid;
import it.progess.invoicecreator.vo.PaidSuspended;
import it.progess.invoicecreator.vo.Payment;
import it.progess.invoicecreator.vo.PaymentDeadline;
import it.progess.invoicecreator.vo.Suspended;

public class AccountingHelper {
	private static boolean checkAccounting(Head head ){
		if (head.getDocument().isCredit() == true || head.getDocument().isDebit() == true){
			return true;
		}else{
			return false;
		}
	}
	public static void generateAccount(Head head,Session session) throws GecoException{
		if (checkAccounting(head)== true ){
			Suspended susp = new AccountingDao().getSuspendedHead(head); 
			if (susp == null){
				//DEBIT
				susp = new Suspended();
				if (head.getDocument().isDebit() == true){
					//Customer
					if (head.getDocument().getCustomer() == true){
						//Create Suspended
						susp.setHead(head);
						susp.setCustomer(true);
						susp.setSupplier(false);
						susp.setAmount(0);
						susp.setPaid(false);
						createDeadlines(susp);
						Customer c = susp.getHead().getCustomer();
						c.setSuspended(c.getSuspended()+ susp.getHead().getTotal());
						new RegistryDao().saveUpdatesCustomer(c, session);
					}else{
						//Supplier
						if (head.getDocument().getSupplier() == true){
							susp.setHead(head);
							susp.setCustomer(false);
							susp.setSupplier(true);
							susp.setAmount(0);
							susp.setPaid(false);
							createDeadlines(susp);
						}
					}
				}else{//CREDIT
					//Customer
					if (head.getDocument().getCustomer() == true){
						susp.setHead(head);
						susp.setCustomer(true);
						susp.setSupplier(false);
						susp.setAmount(0);
						susp.setPaid(false);
						createDeadlines(susp);
						Customer c = susp.getHead().getCustomer();
						c.setSuspended(c.getSuspended() - susp.getHead().getTotal());
						new RegistryDao().saveUpdatesCustomer(c, session);
					}else{
						//Supplier
						if (head.getDocument().getSupplier() == true){
							susp.setHead(head);
							susp.setCustomer(false);
							susp.setSupplier(true);
							susp.setAmount(0);
							susp.setPaid(false);
							createDeadlines(susp);
						}
					}
				}
				//Save Suspended
				new AccountingDao().saveSuspended(susp, session);
			}else{
				if (susp.getAmount() == 0){
					susp.setHead(head);
					updateDeadlines(susp);
					new AccountingDao().saveSuspended(susp, session);
				}else{
					throw new GecoException("Sospeso parzialmente pagato");
				}
			}
		}
	}
	/***
	 * Create Deadlines
	 */
	public static void createDeadlines(Suspended susp){
		Payment pay = susp.getHead().getPayment();
		Date docdate = DataUtilConverter.convertDateFromString(susp.getHead().getDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime(docdate);
		Head head = susp.getHead();
		double sign = 1;
		if (head.getDocument().isCredit() == true){
			sign = -1;
		}
		if (pay.getEndofmonth() == true){
			int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, maxDay);
			docdate = cal.getTime();
		}
		if (pay.getDeadlines() == null || pay.getDeadlines().size() == 0){
			Deadline d = new Deadline();
			if (susp.getDeadlines() == null){
				susp.setDeadlines(new HashSet<Deadline>());
			}
			d.setAmount(head.getAmount()*sign);
			d.setExpiredDate(head.getDate());
			d.setSuspended(susp);
			susp.getDeadlines().add(d);
		}else{
			//Multideadlines
			if (susp.getDeadlines() == null){
				susp.setDeadlines(new HashSet<Deadline>());
			}
			Float[] amounts = calculateAmount(pay.getDeadlines().size(),head.getTotal());
			int index = 0;
			for (Iterator<PaymentDeadline> it = pay.getDeadlines().iterator();it.hasNext();){
				PaymentDeadline pd = it.next();
				cal.setTime(docdate);
				cal.add(Calendar.DAY_OF_MONTH,pd.getDays());
				Date docdateToSet = cal.getTime();
				Deadline d = new Deadline();
				d.setAmount(amounts[index]*sign);
				index = index +1;
				d.setExpiredDate(DataUtilConverter.convertStringFromDate(docdateToSet));
				d.setSuspended(susp);
				susp.getDeadlines().add(d);
			}
		}
		
	}
	public static void updateDeadlines(Suspended susp){
		Payment pay = susp.getHead().getPayment();
		Head head = susp.getHead();
		double sign = 1;
		if (head.getDocument().isCredit() == true){
			sign = -1;
		}
		if (susp.getDeadlines() != null && susp.getDeadlines().size() > 0){
			Float[] amounts = calculateAmount(pay.getDeadlines().size(),head.getTotal());
			int index = 0;
			for (Iterator<Deadline> it = susp.getDeadlines().iterator();it.hasNext();){
				Deadline d = it.next();
				d.setAmount(amounts[index]*sign);
				index = index +1;
			}
		}
	}
	private static Float[] calculateAmount(int deadlines,float total){
		Float[] setOfImports =new  Float[deadlines];
		float totalioo = total * 100.0f;
		int rest = Math.round(totalioo) % (deadlines);
		float restf = 0;
		if (rest != 0){
			restf = rest/100.0f;
			total = total - restf;
		}
		float amount = total / deadlines;
		for (int i =0; i < deadlines;i++){
			if (i == deadlines -1){
				amount = amount + restf;
			}
			setOfImports[i] = amount;
			
		}
		return setOfImports;
	}
	public static void payGeneralSuspended(Paid payment,Session session){
		//Check the customer
		Customer c = payment.getCustomer_paid();
		AccountingDao dao = new AccountingDao();
		//Get the suspended ordered by date
		TreeSet<Suspended> susp = new TreeSet<Suspended>();
		if (payment.getSusp() == null){
			susp = dao.getListFromCustomer(c);
		}else{
			susp.addAll(payment.getSusp());
		}
		paySuspendeds(payment, c, susp, session);
		//try to close a suspended
		
		//save everything
		
	}
	private static void paySuspendeds(Paid payment,Customer c,TreeSet<Suspended> susp,Session session){
		AccountingDao dao = new AccountingDao();
		double amount = payment.getAmount();
		if (susp.size() > 0){
			TreeSet<Suspended> suspdebit = new TreeSet<Suspended>();
			TreeSet<Suspended> suspcredit = new TreeSet<Suspended>();
			for (Iterator<Suspended> it = susp.iterator();it.hasNext();){
				Suspended s = it.next();
				if (s.getHead().getDocument().isDebit() == true){
					suspdebit.add(s);
				}else{
					suspcredit.add(s);
					amount = amount + s.getHead().getTotal();
				}
			}
			for (Iterator<Suspended> itd = suspdebit.iterator();itd.hasNext();){
				if (amount > 0){
					Suspended s = itd.next();
					double totalToPay = s.getHead().getTotal()-s.getAmount();
					amount = setAmountSuspended(amount,s);
					if (payment.getPaids() == null){
						payment.setPaids(new HashSet<PaidSuspended>());
					}
					PaidSuspended ps = new PaidSuspended();
					ps.setAmount(totalToPay);
					ps.setPaid(payment);
					ps.setSuspended(s);
					payment.getPaids().add(ps);
					dao.saveSuspended(s, session);
					if (amount == 0)
						break;
				}
			}
			for (Iterator<Suspended> itc = suspcredit.iterator();itc.hasNext();){
				Suspended s = itc.next();
				s.setPaid(true);
				s.setAmount(s.getHead().getTotal());
				dao.saveSuspended(s, session);
			}
		}
		if (c != null){
			c.setSuspended(c.getSuspended()-payment.getAmount());
			new RegistryDao().saveUpdatesCustomer(c, session);
		}
		
	}
	public static void paySelectedSuspended(Paid payment,Set<Suspended> suspendeds,Session session){
		//Check the customer
		//Get the suspended ordered by date
		//try to close the suspended
		//save everything
		Customer c = payment.getCustomer_paid();
		AccountingDao dao = new AccountingDao();
		//Get the suspended ordered by date
		TreeSet<Suspended> susp = new TreeSet<Suspended>();
		susp.addAll(suspendeds);
		paySuspendeds(payment, c, susp, session);
		
		
	}
	private static double setAmountSuspended(double amount, Suspended s){
		double totalToPay = s.getHead().getTotal()-s.getAmount();
		double amountToSet = 0;
		if (amount >= (totalToPay) || totalToPay-amount <= 0.03){
			if (s.getDeadlines().size() > 0){
				TreeSet<Deadline> td = new TreeSet<Deadline>();
				td.addAll(s.getDeadlines());
				
				for (Iterator<Deadline> itd = td.iterator();itd.hasNext();){
					Deadline d = itd.next();
					setAmountDeadlines(true,0, d);
				}
			}
			//amount to set
			amount = amount - totalToPay;
			s.setAmount(s.getHead().getTotal());
			s.setPaid(true);
		}else{
			double totalPayed = s.getAmount() + amount;
			s.setAmount(totalPayed);
			if (s.getDeadlines().size() > 0){
				TreeSet<Deadline> td = new TreeSet<Deadline>();
				td.addAll(s.getDeadlines());
				for (Iterator<Deadline> itd = td.iterator();itd.hasNext();){
					Deadline d = itd.next();
					amount = setAmountDeadlines(false,amount, d);
				}
			}
			if (amount == s.getHead().getTotal() || s.getHead().getTotal()-amount <= 0.03){
				s.setPaid(true);
			}
			amount = 0;
		}
		return amount;
	}
	private static double setAmountDeadlines(boolean force,double amount, Deadline d){
		double newAmount = 0;
		if (force == true ){
			d.setPaid(true);
			d.setAmountpaid(d.getAmount());
		}else{
			if (amount >= d.getAmount() - d.getAmountpaid()){
				newAmount = amount - (d.getAmount() - d.getAmountpaid());
				d.setAmountpaid(d.getAmount());
			}else{
				if (amount >0){
					d.setAmountpaid(d.getAmountpaid() + amount);
					newAmount =0;
				}
			}
		}
		return newAmount;
	}
	
}
