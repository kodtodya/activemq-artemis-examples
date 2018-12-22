import time
import stomp
from com.kodtodya.consumers.TopicSubscriber import TopicSubscriber

class TopicPublisher():
    hosts = [('localhost', 61616)]
    destination = "myTopic"
    conn = stomp.Connection(host_and_ports=hosts)
    conn.set_listener('', TopicSubscriber())
    conn.start()
    conn.connect('admin', 'admin', wait=True)
    conn.subscribe(destination=destination, id=1, ack='auto')
    content = input("Please enter the message to send to topic(quit to stop):")
    while "QUIT".lower() != content.lower():
        conn.send(body=' '.join(content), destination=destination)
        content = input("Enter Message>")

    time.sleep(2)
    conn.disconnect()