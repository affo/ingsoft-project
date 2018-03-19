#!/bin/bash
java -cp nanohttpd-webserver-2.3.2-snap.jar \
  org.nanohttpd.webserver.SimpleWebServer \
  --dir ./classloading/
