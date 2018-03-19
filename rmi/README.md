# RMI

## The Warehouse

As in the previous lesson, we start by explaining in details an easy example.
The folder `warehouse` contains the example presented in the slides.

The Maven project contains the implementation of a remote warehouse that
contains the descriptions and prices for products. Clients can consult the
product database with remote calls.

With this example we are both interested in understanding the mechanics of RMI
remote calls and a RMI application can be deployed on different machines.

The scripts contained in the folder have exactly this purpose:

  + `compile_warehouse_and_move.sh` compiles the Maven project and copies the
  `.class` files in the `classloading` directories in order to emulate the usage
  of different filesystems: `client` and `server` contain the classes of the
  client and the server respectively, `common` is available at both the client
  and the server.
  + `launch_nanohttpd.sh` launches a webserver at `localhost:8080` that hosts the
  entire codebase.
  + `launch_registry.sh` launches the RMI registry;
  + `launch_client.sh` launches the client adding `common` and `client`
  in the classpath;
  + `launch_server.sh` launches the server adding `common` and `server`
  in the classpath. If run with `0` as parameter it publishes its codebase by
  direct reference to this filesystem (testing environment).
  If run with `1` it publishes its codebase by using the nanohttpd instance provided.

The example shows how an RMI client can obtain a `.class` at runtime (in this case `Book.class`)
that it is nor in its classpath neither available in its filesystem by using [dynamic
class loading](https://docs.oracle.com/javase/7/docs/technotes/guides/rmi/codebase.html).

## RMItter (RMI Twitter)

The folder `rmitter` contains a simplified Twitter implementation using RMI.
The implementation follows the MVC pattern by separating the View on the client-side
from the Model and the Controller on the server-side. Note that:

  + The View is the remote observer of the model.
  + The Model contains only `Serializable` objects (e.g. the User) that are provided
  to the View upon update. Given that objects are serialized, they can
  only be queried and any change applied to them is not reflected server-side.
  + The Controller is remote and it maps the remote calls of the view to local calls
  of the model.

The image below represents the architecture of `rmitter`:

![rmitter architecture](rmitter.png)

Note that the implementation is very simple (compare it to the `socket` example!)
thanks to the networking layer offered by RMI.
