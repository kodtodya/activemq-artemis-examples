Steps to setup AMQ-7 in your environment for the failover test:

1. Create 2 brokers named "broker1" and "broker2" respectively.

-- ./artemis create broker1

-- ./artemis create broker2

2. Add below sections to broker.xml files of both brokers:

```
<connectors>
   <connector name="artemis">tcp://localhost:61616</connector>
   <connector name="cluster-connector">tcp://localhost:61617</connector>
</connectors> 

<cluster-user>admin</cluster-user>
<cluster-password>admin</cluster-password>

<cluster-connections>
  <cluster-connection name="artemisCluster">
    <address>jms</address>
    <connector-ref>artemis</connector-ref>
    <retry-interval>1000</retry-interval>
    <message-load-balancing>ON_DEMAND</message-load-balancing>
    <max-hops>1</max-hops>
    <static-connectors>
       <connector-ref>cluster-connector</connector-ref>
    </static-connectors>
  </cluster-connection>
</cluster-connections>
```

3. Add master configuration to broker1 and slave configuration to broker2:

```
Master config:
<ha-policy>
    <replication>
        <master/>
     </replication>
</ha-policy>
--------------------
Slave Config:       
<ha-policy>
    <replication>
        <slave/>
     </replication>
</ha-policy>     
```

4. Change ports for the acceptors in the broker2's broker.xml:

```
<acceptor name="artemis">tcp://0.0.0.0:61617?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;protocols=CORE,AMQP,STOMP,HORNETQ,MQTT,OPENWIRE</acceptor>
```

5. Change port of admin console in the broker2's bootstrap.xml file.

```
 <web bind="http://localhost:8161" path="web">
```

----------------------------------------------------------------------------------------------------------------------

-- Start the broker and execute this application
