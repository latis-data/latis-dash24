# Demo LaTiS server for the 2024 DASH meeting.

LaTiS works as middleware, adapting to external data sources and providing standard interfaces to the data.
This repository defines a LaTS server with two datasets defined using an XML format called FDML. 

To run this demo LaTiS server, clone this repository, install [scala-cli](https://scala-cli.virtuslab.org/), and run:

```scala-cli run . --resource-dir ./resources```

You can get a catalog view of the available datasets by hitting:

```
http://localhost:8080/dap2/
```

Or use the standard HAPI interfaces:

```
http://localhost:8080/hapi/
```

You should then be able to request data with HTTP queries like:

```
http://localhost:8080/dap2/noaa_goes18_xrs.csv?time>=2024-01-01&time<2024-01-02&formatTime("yyyy-MM-dd'T'HH:mm:ss")
```

With some clients, you may need to do URL encoding:

```
http://localhost:8080/dap2/noaa_goes18_xrs.csv?time%3E=2024-01-01&time%3C2024-01-02&formatTime(%22yyyy-MM-dd%27T%27HH:mm:ss%22)
```

Or make a HAPI request:

```
http://localhost:8080/hapi/data?dataset=noaa_goes18_xrs&time.min=2024-01-01&time.max=2024-01-02&format=json
```
