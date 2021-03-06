#! /usr/bin/env python3

import socket
import time
import sample_pb2 as sp

# We are able to send messages, but the problem is that
# when we receive we get this hanging on the python side
# python recv causes error

PORT = 9191        # The port used by the server
HOST = input("Address of server\n")
temp = sp.SearchRequest()
temp.query = "Hello World"
temp.page_number = 232
temp.result_per_page = 32

#for k,v in temp.__dict__.items():
print( temp.__str__() )

with socket.socket() as s:
    s.connect((HOST, PORT))
    s.send( temp.SerializeToString() )
    print( temp.SerializeToString())
    #address_book.SerializeToString()
    size = 4096
    data = b''
    s.settimeout(5.0)
    data = s.recv(size)
    my_message = sp.SearchRequest()
    my_message.ParseFromString( data )
    print( my_message.__str__() )
