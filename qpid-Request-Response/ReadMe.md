# qpid request response example

This example contains 2 applications:

# 1. AMQPProducer : 
This is just message producer which sends the message using AMQP protocol to broker and get the response back from consumer. After getting response back, it checks that if the correlation id we set while sending message is same or not.

# 2. AMQPConsumer : 
This is message consumer, which consumes message using AMQP protocol and sends reply back to producer along with the same correlation id to make sure message is consumed successfully for respective correlation id.
