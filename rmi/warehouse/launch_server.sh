LOCAL=${1:-0}

if [[ $LOCAL -eq 0 ]]; then
  echo "Using local codebase..."
  java \
    -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.rmi.server.logCalls=true \
    -Djava.rmi.server.codebase="file:///`pwd`/classloading/server/ file:///`pwd`/classloading/common/" \
    -cp ./classloading/server:./classloading/common \
    Server
else
  echo "Using remote codebase..."
  java \
    -Djava.rmi.server.useCodebaseOnly=false \
    -Djava.rmi.server.logCalls=true \
    -Djava.rmi.server.codebase="http://localhost:8080/server/ http://localhost:8080/common/" \
    -cp ./classloading/server:./classloading/common \
    Server
fi
