import time
import stomp
from com.kodtodya.consumers.QueueConsumer import QueueConsumer

class QueueProducer():
    hosts = [('localhost', 61616)]
    queue = "/queue/kodtodya-talks"
    conn = stomp.Connection(host_and_ports=hosts)
    conn.set_listener('', QueueConsumer())
    conn.start()
    conn.connect('admin', 'admin', wait=True)
    conn.subscribe(destination=queue, id=1, ack='auto')
    content = input("Please enter the message to send to queue(quit to stop):")
    while "QUIT".lower() != content.lower():
        conn.send(body=' '.join(content), destination = queue)
        content = input("Enter Message>")

    time.sleep(2)
    conn.disconnect()
