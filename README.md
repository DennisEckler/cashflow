# CashFlow

This is my small side project where i built an application which i can use to upload my monthly Transactions and get an Overview over it.

---

## TechStack

- Spring
- Java
- Gradle
- Docker
- Postgres

---

## Configure Keycloak

1. Create new Realm `cashflow_realm`
2. Create new Client cashflow
3. verify standard flow is active
4. Client authentication 'off'
5. configure access settings:
   > Root URL: <http://hostname:port>
   > Home URL: <http://hostname:port/>
   > Valid redirect URIs: <http://hostname:port/>\*
   > Web origins: +
