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

## Request Signing

To generate a Request Signature using HMAC SHA256.

```java
String method = request.method().toUpperCase();
String url = "/" + String.join("/", request.url().pathSegments());
String body = null;

if ((method.equals("POST") || method.equals("PUT")) && request.body() != null) {
    body = this.bodyToString(request.body());
}

long now = System.currentTimeMillis();
String signature = new RequestSigning().sign(bitcapital.getClientSecret(), method, url, body, now);
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