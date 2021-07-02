#!/usr/bin/env bash

# Kill any existing sipp processes
pkill sipp

# Start Alice's agent in background
sipp 127.0.0.1:5060 -sf alice.xml -t t1 -m 1 -bg -trace_screen

# Get the Alice's agent port
ALICES_PORT=$(sudo  netstat -ltnp | grep "/sipp" | awk '{ print $4 }' |  grep -o "[^:]*$")

# Start Bob's agent
sipp 127.0.0.1:5060 -sf bob.xml -t t1 -m 1 -key alices_port ${ALICES_PORT} -trace_screen
