# LabTV REST Server

A Spring project aimed at simulating [IMDb-API](https://imdb-api.com/).

It provides **JSON** responses through any REST API Client such as [Postman](https://www.postman.com/), but was actually intended to work as a **backend** for [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client).

This project was built with [Spring Framework](https://spring.io/projects/spring-framework) v5.3.20, [Hibernate](https://hibernate.org/) v5.6.5 and [Maven](https://maven.apache.org/) v3.8.4 against JavaSE-11. Its Persistence Unit is configured to work with MariaDB v10.4.24 provided by [XAMPP](https://www.apachefriends.org/download.html) v8.1.6.

## Database Structure and JPA Entities

[![LabTV MySQL Structure](https://i.ibb.co/rF7Mwtr/labtv-full2.jpg "LabTV MySQL Structure")](https://i.ibb.co/rF7Mwtr/labtv-full2.jpg "LabTV MySQL Structure")

Behind the scenes is  a **MySQL** database built around the snapshot of a "**Most Popular Movies**" query from around June 2022 provided by our web development school and initially represented by the "evidenza" table only. We were tasked to create the "film" and "trailer" tables with specific fields and to make the according relationships.

Hibernate was set to create and update tables and fields thanks to the `hibernate.hbm2ddl.auto` property.

Each table (except join tables) are mapped as JPA Entities and feature `@OneToOne` and `@ManyToMany` uni- and bi-directional relationships. To take advantage of `FetchType.LAZY` and to avoid troubles with Hibernate's session and transaction management I ended up needing some more properties:

```
hibernate.enable_lazy_load_no_trans=true
hibernate.event.merge.entity_copy_observer=allow
```
I also included `hibernate-enhance-maven-plugin` to improve lazy loading.

In order to fill the database with missing entries, I made an ad-hoc component in [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client) accessible via `/onetime` where i fetched data from IMDb-API by making ["Movie Title" and "Movie Trailer" queries](https://imdb-api.com/api/#Title-header). To avoid adding new entries everytime so to just work with a snapshot, each query response was saved to a `*.har` file and fetching methods were commented out. These files were then sent from the client to a dedicated Spring `@RestController` via POST methods.

## JSON response modeling

Since database tables are shaped to reflect IMDb-API's JSON response structure and in order to take advantage of the redundancy of some fields, I decided not to map Entities against DTOs, but to map requests and responses directly onto Entities by making use of some Jackson annotations such as `@JsonPropertyOrder`, `@JsonGetter` and `@JsonIgnore`. I aimed at producing the same output as IMDb-API, so that [LabTV](https://github.com/Montblanc0/LabTV) needed minimal changes to keep on working seamlessly.

[!['Most Popular Movies' JSONs](https://i.ibb.co/5hQgD25/json-response.jpg "'Most Popular Movies' JSONs")](https://i.ibb.co/5hQgD25/json-response.jpg "'Most Popular Movies' JSONs")

## Instructions

### 1. Tomcat Server
This project was meant to work  with [Tomcat v9.x](https://tomcat.apache.org/download-90.cgi "Tomcat v9.x") and won't work on any other version. [Add Tomcat to Eclipse](https://www.baeldung.com/eclipse-tomcat "Add Tomcat to Eclipse") and make sure it is [configured](https://www.baeldung.com/eclipse-tomcat#configure "configured") to run on **HTTP/1.1 port 8082** as [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client) will be making calls directly to `http://localhost:8082/labtv-api/`.

### 2. Eclipse Configuration

[Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/release/2022-06/r/eclipse-ide-enterprise-java-and-web-developers "Eclipse IDE for Enterprise Java and Web Developers") is preferred.

- Import this project folder by selecting it from `File -> Open Projects from File System...` ;
- Right click on the project name from the Project Explorer and run `Maven -> Update Project...` (you may need to check `Force Update of Snapshots/Releases`);
- Right click on the project, select `Properties`, then `Web Project Settings` and make sure that `Context root` is set to `labtv-api`.

### 3. Database Import

The Persistence Unit is configured to work on a stock MySQL configuration as provided by [XAMPP](https://www.apachefriends.org/it/index.html "XAMPP"), so just make sure your MySQL process is running on **port 3306** and that you haven't added a password.

 #### Full Import (recommended)
- Make sure to **backup** any database named `labtv`, if any. The provided `*.sql` files will automatically create or select a database named exactly  "labtv".
- Open your phpMyAdmin's **homepage** (don't select any database);
- Select `Import`;
- Browse for a file and select `labtv_full.sql` from the project root;
- Leave the rest as it is and clic the **Import** button.

If done correctly, you will find a database named "labtv" in your database list. You can now go back to **Eclipse**:

- Right click on the project, `Run As` and then `Run on Server` (if prompted, select your "Tomcat v9.0 Server").

You can safely close the browser window and check the server logs from Eclipse's Console. **LabTV REST Server is now listening on port 8082.**

You can now switch to [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client) or use any client to try the [available queries](https://github.com/Montblanc0/LabTV-REST-Server#queries).

#### Initial Import

You can choose to import a minimal version of the database consisting of only one table (as provided by the school) and fill it later from [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client)'s "OneTime" component to see how Hibernate handles `INSERT`s.

- The instructions are the same as above, with the exception of selecting  `labtv_initial.sql` from the Import page instead of the full variant.

If done correctly, you will find a database named "labtv" in your database list. You can now go back to **Eclipse**:

- Right click on the project, `Run As` and then `Run on Server` (if prompted, select your "Tomcat v9.0 Server");
- Hibernate will automatically create the missing tables and set their fields, indexes and relationships.

You can safely close the browser window and check the server logs from Eclipse's Console. **LabTV REST Server is now listening on port 8082.**

Now, it's time to switch to the **frontend** to fill the database:

- Follow LabTV REST Client's [instructions](https://github.com/Montblanc0/LabTV-REST-Client#instructions) and fire it up;
- Manually navigate to `/onetime` (example: `http://localhost:4200/onetime`);
- click on `Send All Movies` and wait for it to finish;
- click on `Send All Trailers` and wait for it to finish.

You can now use [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client) as normal or use any client to try the [available queries](https://github.com/Montblanc0/LabTV-REST-Server#queries).

## Build

### Requirements
A Windows build is also provided, but still requires [Tomcat v9.x](https://tomcat.apache.org/download-90.cgi "Tomcat v9.x") to be deployed correctly.
> It is advised not to run Tomcat from Eclipse. Instructions will reference your Tomcat installation directory.

- Download [labtv-api.war](https://github.com/Montblanc0/LabTV-REST-Server/releases);
- Locate your Tomcat v9.x installation folder;
- Place the war file into the `webapps` subfolder;
- Go back to the Tomcat installation folder and open the `conf` folder;
- Right click the `server.xml` file and `Open with` an editor of your choice such as Notepad;
- Replace any occurrence of `8080` with `8082` and save;
- Go back to the Tomcat installation folder and open the `bin` folder;
- Press `CTRL + L` to highlight the address bar (it could be `CTRL + D` on certain machines);
- type `powershell` and press enter to open PowerShell directly from that folder;
- type `./startup` and press Enter;
- Tomcat will start in a new window and will automatically deploy the war file.

**LabTV REST Server is now listening on port 8082.** You can now use [LabTV REST Client](https://github.com/Montblanc0/LabTV-REST-Client) as normal or use any client to try the [available queries](https://github.com/Montblanc0/LabTV-REST-Server#queries).

### Queries
- Most Popular Movies
`http://localhost:8082/labtv-api/api/evidenze`

- Find Movie By Id
`http://localhost:8082/labtv-api/api/films/<id>`

- Find Movie By Title
`http://localhost:8082/labtv-api/api/films/titoli/<title>`

- Get Movie Trailer By Id
`http://localhost:8082/labtv-api/api/trailers/<id>`

- Get All Most Popular Entries
`http://localhost:8082/labtv-api/api/all-evidenze`

- Get Most Popular Movie By Id
`http://localhost:8082/labtv-api/api/evidenza/<id>`