package it.polito.dp2.NFFG.sol1.util;

import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.sol1.jaxb.ServiceType;

public class Util {

	public static ServiceType covertFunctionalToService(FunctionalType functional){
		ServiceType service = ServiceType.WEB_CACHE;
		switch(functional){
		case CACHE: service = ServiceType.WEB_CACHE;
					break;
		case DPI: service = ServiceType.DPI;
					break;
		case FW: service = ServiceType.FIREWALL;
					break;
		case NAT: service = ServiceType.NAT;
					break;
		case SPAM: service = ServiceType.ANTI_SPAM;
					break;
		case VPN: service = ServiceType.VPN_GATEWAY;
					break;
		case WEB_CLIENT: service = ServiceType.WEB_CLIENT;
					break;
		case MAIL_CLIENT: service = ServiceType.MAIL_CLIENT;
					break;
		case MAIL_SERVER: service = ServiceType.MAIL_SERVER;
					break;
		case WEB_SERVER: service = ServiceType.WEB_SERVER;
					break;
		}
		return service;
	}
	
	public static FunctionalType covertServiceToFunctional(ServiceType service){
		FunctionalType functional = FunctionalType.CACHE;
		switch(service){
	
		case WEB_CACHE: functional = FunctionalType.CACHE;
						break;
		case DPI: functional = FunctionalType.DPI;
						break;
		case FIREWALL: functional = FunctionalType.FW;
						break;
		case NAT: functional = FunctionalType.NAT;
						break;
		case ANTI_SPAM: functional = FunctionalType.SPAM;
						break;
		case VPN_GATEWAY: functional = FunctionalType.VPN;
						break;
		case WEB_CLIENT: functional = FunctionalType.WEB_CLIENT;
						break;
		case MAIL_CLIENT: functional = FunctionalType.MAIL_CLIENT;
						break;
		case MAIL_SERVER: functional = FunctionalType.MAIL_SERVER;
						break;
		case WEB_SERVER: functional = FunctionalType.WEB_SERVER;
						break;
		}
		return functional;
	}
	
	public static XMLGregorianCalendar calendarToXMLGregorianCalendar(Calendar calendar) {
		try {
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
			xgc.setYear(calendar.get(Calendar.YEAR));
			xgc.setMonth(calendar.get(Calendar.MONTH) + 1);
			xgc.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			xgc.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			xgc.setMinute(calendar.get(Calendar.MINUTE));
			xgc.setSecond(calendar.get(Calendar.SECOND));
			xgc.setMillisecond(calendar.get(Calendar.MILLISECOND));
			// Calendar ZONE_OFFSET and DST_OFFSET fields are in milliseconds.
			int offsetInMinutes = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000);
			xgc.setTimezone(offsetInMinutes);
			return xgc;
		} catch (DatatypeConfigurationException e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
}
