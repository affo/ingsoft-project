LOCAL=${1:-0}

if [[ $LOCAL -eq 1 ]]; then
java -cp nanohttpd-webserver-2.3.2-snap.jar \
  org.nanohttpd.webserver.SimpleWebServer \
  --dir ./warehouse/classloading/
else
java -cp nanohttpd-webserver-2.3.2-snap.jar \
    org.nanohttpd.webserver.SimpleWebServer \
    --dir ./rmitter/target/classes/rmi/rmitter/
fi