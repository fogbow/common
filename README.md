# Common

This is a module responsible for common code (constants, exceptions, model classes, etc) used by many fogbow services like [Resource Allocation Service (RAS)](https://github.com/fogbow/resource-allocation-service) and [Authentication Service (AS)](https://github.com/fogbow/authentication-service).

## How to use

First of all, you will need Java 8, Maven and Git installed.

### Installing

```bash
git clone https://github.com/fogbow/common.git
cd common
mvn install -dSkipTests
```

### Importing

1. Start your IDE (IntelliJ, Eclipse or other);
2. Open a fogbow service (like [RAS](https://github.com/fogbow/resource-allocation-service) or [AS](https://github.com/fogbow/authentication-service)) project;
3. Import common as a module in the service project;

## Contributing

For instructions about how to contribute, check out our [contributor's guide](https://github.com/fogbow/common/blob/master/CONTRIBUTING.md).
