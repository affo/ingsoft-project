#!/bin/bash
echo "Launching registry..."
rmiregistry \
  -J-Djava.rmi.server.logCalls=true \
  -J-Djava.rmi.server.useCodebaseOnly=false
