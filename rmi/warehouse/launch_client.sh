#!/bin/bash
java \
  -Djava.rmi.server.useCodebaseOnly=false \
  -Djava.rmi.server.logCalls=true \
  -cp ./classloading/client:./classloading/common \
  Client
