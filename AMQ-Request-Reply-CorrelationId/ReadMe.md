
<h1>Spring-JMS request reply sample</h1>

This application contains 2 applications.

<b>MessageCreator-Reproducer:</b> This is message producer which sets correlation Id to message and sends to myQueue.

<b>MessageConsumer :</b> This is consumer which consumes message from myQueue, checks for replyTo destination and sends reply back with same correlation Id which is received with message while consumption
Reply is being sent back to producer where producer validates the correlation Id is same.
