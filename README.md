Generic Java/Jax-RS Backend
-------------------------------

Steps to run the app
* Configure the wildfly datasource, replace ``someUser`` and ``somePass`` with actual values in your environment

``<datasource jta="true" jndi-name="java:jboss/datasources/GenericBackendDS" pool-name="GenericBackendDS" enabled="true" use-ccm="true">
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
``


