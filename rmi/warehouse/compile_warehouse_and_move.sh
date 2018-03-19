#!/bin/bash
basedir=`pwd`/target/classes

mvn compile

if [[ $? -eq 0 ]]; then
  # client
  cp $basedir/Client.class ./classloading/client/

  # server
  cp $basedir/Server.class ./classloading/server/
  cp $basedir/WarehouseImpl.class ./classloading/server/
  cp $basedir/Book.class ./classloading/server/


  # codebase
  cp $basedir/Warehouse.class ./classloading/common/
  cp $basedir/Product.class ./classloading/common/
fi
