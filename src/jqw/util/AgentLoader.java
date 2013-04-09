package jqw.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public final class AgentLoader {
	
	/** @forbidden **/
	private AgentLoader() { throw null; }

    public static void loadAgent(String jarPath, String options) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {
    	RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String nameOfRunningVM = bean.getName();
        String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));

        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(jarPath, options);
        vm.detach();
    }
}
