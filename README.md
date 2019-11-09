bitcapital-java-sdk
===================

## Getting Started

Initialize your Bit Capital client.

```java
// Initialize your client SDK instance
Bitcapital bitcapital = Bitcapital.initialize(apiUrl, clientId, clientSecret);

// Authenticate using OAuth 2.0
OAuthTokenResponse credentials = bitcapital.oauth().token()
    .execute()
    .body();

System.out.println(credentials.accessToken);
```

## Development

Compile and run tests using Gradle:

```bash
./gradlew clean build
```

Execute sample routine from main class.

```bash
./gradlew run
```