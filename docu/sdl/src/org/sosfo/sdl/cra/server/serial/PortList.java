package org.sosfo.sdl.cra.server.serial;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

import org.sosfo.framework.log.Log;

public class PortList {

    /**
     * Available 한 Serial Port 스캔해준다.
     */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	try {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		
		while (ports.hasMoreElements()) {
		    CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
		    String type;
		    
		    switch (port.getPortType()) {
		    case CommPortIdentifier.PORT_PARALLEL:
			type = "Parallel";
			break;
		    case CommPortIdentifier.PORT_SERIAL:
			type = "Serial";
			break;
		    default: // / Shouldn't happen
			type = "Unknown";
			break;
		    }
		    Log.info("",this, port.getName() + ": " + type );
		    sb.append("\n"+port.getName() + ": " + type);
		}
	} catch (Exception e) {
	    Log.error("ERROR",this,e);
	    sb.append(e.toString());
	}
	return sb.toString();
    }
    
    public static void main(String args[]){
    	PortList pl = new PortList();

   		Log.info("port information : --->"+pl.toString());

    	
    }
}
