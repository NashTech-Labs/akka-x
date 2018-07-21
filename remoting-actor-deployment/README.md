ACTOR SELECTION:
===============

RUN MASTR NODE:
--------------

export NETTY_PORT=2551
sbt "project remoting-actor-selection" "run-main com.akkax.remote.master.app.Master"


RUN WORKER NODE:
---------------

sbt "project remoting-actor-selection" "run-main com.akkax.remote.worker.app.Worker"


RUN CONCURRENT REQUESTS:
-----------------------

cat remoting-actor-selection/src/main/resources/urls.txt | parallel "ab -n 10 -c 4 {}"


ACTOR DEPLOYMENT:
================

RUN MASTR NODE:
--------------

export NETTY_PORT=2551
sbt "project remoting-actor-deployment" "run-main com.akkax.remote.master.app.Master"

RUN WORKER NODE:
---------------

sbt "project remoting-actor-selection" "run-main com.akkax.remote.worker.app.Worker"

RUN CONCURRENT REQUESTS:
-----------------------


cat remoting-actor-selection/src/main/resources/urls.txt | parallel "ab -n 10 -c 4 {}"
