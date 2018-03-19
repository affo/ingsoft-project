#!/bin/bash

rmiregistry \
    -J-Djava.rmi.server.logCalls=true \
    -J-Djava.rmi.server.useCodebaseOnly=false \
    -J-Djava.rmi.server.codebase=file:///`pwd`/target/classes/

