Generic Java/Jax-RS Backend
-------------------------------

**Generic JaxRs backend** is an open source, Java Backend based on Jax-Rs that can be used to create api backends

You can find a demo [here](http://generic-frontend.corvidpartnersltd.com). A full Java backend can also be bootstraped from [here](http://github.com/kodero/generic-java-backed)

Steps to run the app
* Configure a wildfly datasource with sample configs below, replace ``someUser`` and ``somePass`` with actual values in your environment

      <datasource jta="true" jndi-name="java:jboss/datasources/GenericBackendDS" pool-name="GenericBackendDS" enabled="true" use-ccm="true">
        <connection-url>jdbc:mysql://localhost:3306/generic_backend?zeroDateTimeBehavior=convertToNull</connection-url>
        <driver-class>com.mysql.jdbc.Driver</driver-class>
        <driver>com.mysql</driver>
        <security>
            <user-name>someUser</user-name>
            <password>somePass</password>
        </security>
        <validation>
            <check-valid-connection-sql>select now() as validation_check</check-valid-connection-sql>
            <validate-on-match>true</validate-on-match>
            <background-validation>true</background-validation>
            <background-validation-millis>5000</background-validation-millis>
        </validation>
        <timeout>
            <set-tx-query-timeout>false</set-tx-query-timeout>
            <blocking-timeout-millis>0</blocking-timeout-millis>
            <idle-timeout-minutes>0</idle-timeout-minutes>
            <query-timeout>0</query-timeout>
            <use-try-lock>0</use-try-lock>
            <allocation-retry>0</allocation-retry>
            <allocation-retry-wait-millis>0</allocation-retry-wait-millis>
        </timeout>
        <statement>
            <track-statements>false</track-statements>
            <share-prepared-statements>true</share-prepared-statements>
        </statement>
      </datasource>

The ``<driver>com.mysql</driver>`` can be installed by following this [guide](http://giordanomaestro.blogspot.co.ke/2015/02/install-jdbc-driver-on-wildfly.html)

* Next, we create the database ``generic_backend``, so login to mysql and run the below command: 

    $ mysql> create database generic_backend;
    $ mysql> exit;
    $ mysql -uroot -p generic_backend < <GenericBackend>/src/main/resources/generic_backend.sql

Replace ``<GenericBackend>`` with the actual directory path where you have cloned the GenericBackend project.

* You can now build and deploy the project using the command below

    $ mvn clean wildfly:deploy

## Bugs and Issues

Have a bug or an issue with this template? [Open a new issue](https://github.com/kodero/generic-jaxrs-backend/issues) here on GitHub.

## Contributors / Credits

**Generic Backend** is based on Java [Jax-Rs ](http://startbootstrap.com/template-overviews/startmin/) and is culmination of work done at [Corvid Parners](http://corvidpartnersltd.com)

Many thanks to the developers @Corvid


## Copyright and License

Copyright 2017-20* Kennedy O Odero / Corvid Partners Ltd.

Code released under the [Apache 2.0](https://github.com/IronSummitMedia/startbootstrap-sb-admin-2/blob/gh-pages/LICENSE) license.