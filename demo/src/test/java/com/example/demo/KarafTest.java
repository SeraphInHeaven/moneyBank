package com.example.demo;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-01-02 10:13 AM
 */
public class KarafTest {

    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-trun");

        String username = "karaf";
        String password = "karaf";
        Map<String, String[]> environment = null;
        if (username != null && password != null) {
            String[] credentials = new String[2];
            credentials[0] = username;
            credentials[1] = password;
            environment = new HashMap<String, String[]>();
            environment.put(JMXConnector.CREDENTIALS, credentials);
        }

        JMXConnector connector = JMXConnectorFactory.connect(url, environment);
        MBeanServerConnection mbeanServer = connector.getMBeanServerConnection();
        Set<ObjectName> objectNames = mbeanServer.queryNames(null, null);
        for (ObjectName objectName : objectNames) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(objectName);
            final MBeanInfo info = mbeanServer.getMBeanInfo(objectName);

            for (MBeanAttributeInfo att : info.getAttributes()) {
                System.out.println(att.toString());
            }

            for (MBeanOperationInfo op : info.getOperations()) {
                System.out.println("public "+op.getReturnType()+" "+
                        op.getName()+"();");
            }
        }

        ObjectName bundle = new ObjectName("org.apache.karaf:type=bundle,name=trun");
        Object object = mbeanServer.getAttribute(bundle, "Bundles");
        System.out.println(object);

        ObjectName service = new ObjectName("org.apache.karaf:type=service,name=trun");
        Object serviceObject = mbeanServer.getAttribute(service, "Services");
        System.out.println(object);


//
//        ObjectName kar = new ObjectName("org.apache.karaf:type=kar,name=trun");
//        Object object = mbeanServer.getAttribute(kar, "Kars");
//        System.out.println(object);
//
//        ObjectInstance objectInstance = mbeanServer.getObjectInstance(kar);
//        MBeanInfo info = mbeanServer.getMBeanInfo(kar);
//        mbeanServer.invoke(trun, "uninstall", new Object[] {"SayHelloService-0.1"}, new String[] {"java.lang.String"});

//        File file = new File("/Users/Seraph/Documents/work/talend/TOS_ESB-20190620_1446-V7.2.1/Runtime_ESBSE/container/deploy/SayHelloService-0.1.kar");
//        mbeanServer.invoke(trun, "install", new Object[] {file.toURI().toString()}, new String[] {"java.lang.String"});

        connector.close();
    }


}
