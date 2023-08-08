# ibm-mq-client-server

<h4>The objective of the project is to demonstrate</h4>
1. How can we connect an IBM MQ middleware with our Spring Boot application without REST APIs.
2. How can we implement a Point-to-point communication using IBM MQ.

Implementations:
<h4>1. To send and receive messages from a local queue inside a single Queue Manager</h4>
    
    
    Requirements:
    
    a. one Queue Manager
    b. one local queue
    c. one channel(type: server connection channel)
    d. another channel with same name as server connection channel (type: client connection channel)
    e. Map each channel pair in "Channel Authentication Record" with following details( channel_profile: channel name, type: Address Map, mca_user_id, ip_address: Current QM IP Address, and client connect as QM. 
    f. Create a springboot application called Producer: 
       --- It will send/put messages to a local queue of QM1 and the messages will be avail
    g. Create a another springboot application called Receiver:
       --- It will read/listen messages from the local queue of QM1
       
    
<h4>2. To send messages from your local queue manager(QM1) to a remote queue manager(QM2) located in another machine or same machine</h4>
   

    Requirements: 
    
    [Queue Manager 1: QM1]
    -- queues
    a. one remote queue
    b. one transition queue
    -- channels of QM1:
    a. server connection channel
    b. client connection channel (same name as server connection channel)
    c. sender channel
    d. Map each channel pair in "Channel Authentication Record" with following details( channel_profile: channel name, type: Address Map, mca_user_id, ip_address: Current QM IP Address, and client connect as QM. 
    e. Create a springboot application called Producer: 
       --- It will send/put messages to a remote queue of QM1 (via Client Connection Channel: QM1)
        and messages will travel from remote queue QM1 -> transition queue QM1 -> (via sender-receiver channel) -> lcoal queue QM2
    

    [Queue Manager 2: QM2]
    -- queues
    a. one local queue
    -- channels of QM1:
    a. server connection channel for QM2
    b. client connection channel for QM2 (same name as server connection channel QM2)
    c. receiver channel (same name as QM1 sender channel)
    d. Map each channel pair in "Channel Authentication Record" with following details( channel_profile: channel name, type: Address Map, mca_user_id, ip_address: Current QM IP Address, and client connect as QM. 
    f. Create a another springboot application called Receiver:
       --- It will read/listen messages from the local queue of QM2 (via Client Connection Channel: QM2)




One thing to note is that in Point-to-point messaging we can have as many producer and as many receiver applications but the messages will be uniquely assigned to a specific receiver 
and that is decided by broker itself. 


    PRODUCER1 -> sends [msg1, msg2, msg3, msg4,msg5, msg6,...]
    RECEIVER1 -> receives [msg1, msg2]
    RECEIVER2 -> receives [msg3, msg6]
    RECEIVER1 -> receives [msg4]
    RECEIVER2 -> receives [msg5]

So, if we want all the messages to be read by all the receivers then we have to use Publish-Subscribe Messaging Technique of IBM MQ
That utilizes Topics as the messaging objects unlike Queue in case of point-to-point.

